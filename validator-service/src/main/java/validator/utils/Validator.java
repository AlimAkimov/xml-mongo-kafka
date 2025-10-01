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

    @Value("${validation.allowed.projects}")
    String projectPattern;

    Set<String> allowedPositionsSet;

    Set<String> allowedDepartmentsSet;

    Pattern allowedProjectsPattern;

    @PostConstruct
    public void init() {
        allowedPositionsSet = new HashSet<>(Arrays.asList(allowedPositions.split(",")));
        allowedDepartmentsSet = new HashSet<>(Arrays.asList(allowedDepartments.split(",")));
        allowedProjectsPattern = Pattern.compile(projectPattern);
    }

    public boolean validate(Employee e) {
        return validatePosition(e) &&
                validateDepartment(e) &&
                validateProjects(e);
    }

    private boolean validatePosition(Employee e) {
        return allowedPositionsSet.contains(e.getPosition());
    }

    private boolean validateDepartment(Employee e) {
        return allowedDepartmentsSet.contains(e.getDepartment());
    }

    private boolean validateProjects(Employee e) {
        if (e.getProjects() == null) return true;
        for (Project project : e.getProjects()) {
            if (!allowedProjectsPattern.matcher(project.getName()).matches()) {
                return false;
            }
        }
        return true;
    }
}
