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

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeConsumerService {

    EmployeeRepository repository;
    Validator validator;
    EmployeeMapper employeeMapper;

    @KafkaListener(topics = "${kafka.topic:employees}", groupId = "employee-consumers")
    public void consume(@Valid Employee employee) {
        boolean valid = validator.validate(employee);
        if (!valid) {
            log.warn("Сотрудник невалидный: {}", employee.getId());
            return;
        }
        validator.model.EmployeeEntity entity = employeeMapper.toEntity(employee);
        repository.save(entity);
        log.info("Сохранен сотрудник с id={}", employee.getId());
    }
}
