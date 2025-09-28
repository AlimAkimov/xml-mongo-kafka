package validator.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import validator.dto.Employee;
import validator.repository.EmployeeRepository;
import validator.utils.EmployeeMapper;
import validator.utils.Validator;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeConsumerService {

    final EmployeeRepository repository;
    final Validator validator;

    @KafkaListener(topics = "${kafka.topic:employees}", groupId = "employee-consumers")
    public void consume(Employee employee) {
        boolean valid = validator.validate(employee);
        if (!valid) {
            log.warn("Сотрудник невалидный: {}", employee.getId());
            return;
        }
        validator.model.EmployeeEntity entity = EmployeeMapper.toEntity(employee);
        repository.save(entity);
        log.info("Сохранен сотрудник с id={}", employee.getId());
    }
}
