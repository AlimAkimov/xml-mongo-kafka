package validator.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import validator.dto.Project;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "employees")
public class EmployeeEntity {
    @Id
    @NotNull
    String id;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    String position;
    @NotNull
    String department;
    @NotNull
    LocalDate hireDate;
    @NotNull
    BigDecimal salary;
    List<Project> projects = new ArrayList<>();
}
