package validator.utils;

import validator.model.EmployeeEntity;

public class EmployeeMapper {
    public static EmployeeEntity toEntity(validator.dto.Employee dto) {
        return new EmployeeEntity(dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getPosition(),
                dto.getDepartment(), dto.getHireDate(), dto.getSalary(), dto.getProjects());
    }
}
