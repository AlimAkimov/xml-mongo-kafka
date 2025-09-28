package validator.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
@XmlRootElement(name = "employee")
public class Employee {
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
    @JacksonXmlElementWrapper(localName = "projects")
    List<Project> projects = new ArrayList<>();
}
