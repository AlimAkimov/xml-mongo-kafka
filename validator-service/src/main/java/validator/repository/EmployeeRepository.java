package validator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import validator.model.EmployeeEntity;

public interface EmployeeRepository extends MongoRepository<EmployeeEntity, String> {
}
