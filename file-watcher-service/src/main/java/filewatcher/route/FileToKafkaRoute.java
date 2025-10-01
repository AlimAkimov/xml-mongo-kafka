package filewatcher.route;

import filewatcher.dto.EmployeesWrapper;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jacksonxml.JacksonXMLDataFormat;
import org.springframework.stereotype.Component;

@Component
public class FileToKafkaRoute extends RouteBuilder {

    private final JacksonXMLDataFormat jacksonXmlDataFormat;

    public FileToKafkaRoute(JacksonXMLDataFormat jacksonXmlDataFormat) {
        this.jacksonXmlDataFormat = jacksonXmlDataFormat;
    }

    @Override
    public void configure() {

        from("file:{{watch.dir}}?include=.*\\.xml&move={{watch.dir}}/processed")
                .routeId("file-to-kafka-route")
                .log("Найден файл: ${header.CamelFileName}")
                .unmarshal(jacksonXmlDataFormat)
                .split(body().method("getEmployees"))
                .log("Отправка сотрудника в Kafka: ${body}")
                .to("kafka:{{kafka.topic}}?brokers={{spring.kafka.bootstrap-servers}}&valueSerializer=org.springframework.kafka.support.serializer.JsonSerializer");
    }
}
