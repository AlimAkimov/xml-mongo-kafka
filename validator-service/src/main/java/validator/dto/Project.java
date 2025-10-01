package validator.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Project {
    @NotBlank(message = "name проекта обязателен")
    String name;

    @PastOrPresent(message = "startDate должна быть в прошлом или сегодня")
    LocalDate startDate;

    @FutureOrPresent(message = "endDate должна быть в будущем или сегодня")
    LocalDate endDate;
}
