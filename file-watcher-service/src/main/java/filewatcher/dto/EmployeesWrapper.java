package filewatcher.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EmployeesWrapper {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "employee")
    private List<Employee> employees;

}