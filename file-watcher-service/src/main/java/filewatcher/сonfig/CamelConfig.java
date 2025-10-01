package filewatcher.сonfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import filewatcher.dto.EmployeesWrapper;
import org.apache.camel.component.jacksonxml.JacksonXMLDataFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

    @Bean
    public JacksonXMLDataFormat jacksonXmlDataFormat() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());

        JacksonXMLDataFormat format = new JacksonXMLDataFormat();
        format.setXmlMapper(xmlMapper);
        format.setUnmarshalType(EmployeesWrapper.class);
        return format;
    }
}
