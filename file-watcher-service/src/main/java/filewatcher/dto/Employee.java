package filewatcher.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate hireDate;
    @NotNull
    BigDecimal salary;
    @JacksonXmlElementWrapper(localName = "projects")
    @JacksonXmlProperty(localName = "project")
    List<Project> projects = new ArrayList<>();

}
