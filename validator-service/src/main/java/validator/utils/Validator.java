package validator.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import validator.dto.Employee;
import validator.dto.Project;
import lombok.experimental.FieldDefaults;
import lombok.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Validator {

    @Value("${validation.allowed.positions}")
    String allowedPositions;

    @Value("${validation.allowed.departments}")
    String allowedDepartments;

    Set<String> allowedPositionsSet;

    Set<String> allowedDepartmentsSet;

    @Value("${validation.allowed.projects}")
    String projectPattern;

    Pattern allowedProjectsPattern;

    @PostConstruct
    public void init() {
        allowedProjectsPattern = Pattern.compile(projectPattern);
        allowedPositionsSet = getAllowedPositions();
        allowedDepartmentsSet = getAllowedDepartments();
    }

    public boolean validate(Employee e) {
        if (e == null) {
            return false;
        }

        return validateField(e, this::validateFirstName, "Элемент firstName не должен быть пустым") &&
                validateField(e, this::validateLastName, "Элемент lastName не должен быть пустым") &&
                validateField(e, this::validatePosition, "Ошибка в элементе position") &&
                validateField(e, this::validateDepartment, "Ошибка в элементе department") &&
                validateField(e, this::validateProject, "Ошибка в элементе name элемента project") &&
                validateField(e, this::validateHireDate, "Элемент hireDate должен быть позже чем сегодняшняя дата") &&
                validateSalary(e);
    }

    private boolean validateField(Employee e, Function<Employee, Boolean> validator, String errorMessage) {
        if (!validator.apply(e)) {
            System.out.println(errorMessage + " у сотрудника с id " + e.getId());
            return false;
        }
        return true;
    }

    private boolean validateSalary(Employee e) {
        if (e.getSalary() == null) {
            System.out.println("Элемент salary не должен быть пустым у сотрудника с id " + e.getId());
            return false;
        }
        return true;
    }

    private boolean validateFirstName(Employee e) {
        return e.getFirstName() != null && !e.getFirstName().isBlank();
    }

    private boolean validateLastName(Employee e) {
        return e.getLastName() != null && !e.getLastName().isBlank();
    }

    private boolean validateHireDate(Employee e) {
        return e.getHireDate() != null && e.getHireDate().isBefore(LocalDate.now());
    }

    private boolean validatePosition(Employee e) {
        return e.getPosition() != null && (!e.getPosition().isBlank() && allowedPositionsSet.contains(e.getPosition()));
    }

    private boolean validateDepartment(Employee e) {
        return e.getDepartment() != null && (!e.getDepartment().isBlank() && allowedDepartmentsSet.contains(e.getDepartment()));
    }

    public boolean validateProject(Employee e) {
        if (e.getProjects() != null) {
            for (Project project : e.getProjects()) {
                Matcher matcher = allowedProjectsPattern.matcher(project.getName());
                System.out.println(project.getName());
                System.out.println(allowedProjectsPattern);
                if (project.getName() == null || project.getName().isBlank() || !matcher.matches()) {
                    return false;
                }
            }
        }
        return true;
    }

    private Set<String> getAllowedPositions() {
        return new HashSet<>(Arrays.asList(allowedPositions.split(",")));
    }

    private Set<String> getAllowedDepartments() {
        return new HashSet<>(Arrays.asList(allowedDepartments.split(",")));
    }
}
