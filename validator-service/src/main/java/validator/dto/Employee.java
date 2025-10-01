package validator.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.*;
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
    @NotBlank(message = "id не должен быть пустым")
    String id;

    @NotBlank(message = "firstName не должен быть пустым")
    String firstName;

    @NotBlank(message = "lastName не должен быть пустым")
    String lastName;

    @NotBlank(message = "position не должен быть пустым")
    String position;

    @NotBlank(message = "department не должен быть пустым")
    String department;

    @NotNull(message = "hireDate обязателен")
    @Past(message = "Элемент hireDate должен быть раньше сегодняшней даты")
    LocalDate hireDate;

    @NotNull(message = "Элемент salary не должен быть пустым")
    BigDecimal salary;

    @JacksonXmlElementWrapper(localName = "projects")
    @Valid
    List<Project> projects = new ArrayList<>();
}
