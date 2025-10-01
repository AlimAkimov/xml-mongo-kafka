package validator.utils;

import org.mapstruct.Mapper;
import validator.dto.Employee;
import validator.model.EmployeeEntity;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeEntity toEntity(Employee dto);
}
