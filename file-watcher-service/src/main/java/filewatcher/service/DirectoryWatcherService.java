package filewatcher.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import filewatcher.dto.Employee;
import filewatcher.dto.EmployeesWrapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DirectoryWatcherService {

    @Value("${watch.dir}")
    String watchDir;

    @Value("${kafka.topic}")
    String topic;

    final KafkaTemplate<String, Object> kafkaTemplate;

    final List<String> processedFiles = new CopyOnWriteArrayList<>();

    final XmlMapper xmlMapper = (XmlMapper) new XmlMapper()
            .registerModule(new JavaTimeModule());

    @Scheduled(fixedDelay = 5000)
    public void pollFiles() {
        File dir = new File(watchDir);
        log.debug("Запускаю поиск файлов в {}", dir.getAbsolutePath());
        File[] files = dir.listFiles((d, name) -> name.endsWith(".xml"));
        if (files != null) {
            for (File file : files) {
                if (processedFiles.add(file.getName())) {
                    log.info("Найден новый файл: {}", file.getName());
                    processFile(file);
                }
            }
        }
    }

    private void processFile(File file) {
        try {
            EmployeesWrapper wrapper = xmlMapper.readValue(file, EmployeesWrapper.class);
            if (wrapper != null && wrapper.getEmployees() != null) {
                for (Employee emp : wrapper.getEmployees()) {
                    kafkaTemplate.send(topic, String.valueOf(emp.getId()), emp);
                    log.info("Отправлен сотрудник в Kafka: {}", emp);
                }
            }
        } catch (IOException e) {
            log.error("Ошибка обработки файла {}", file.getName(), e);
            e.printStackTrace();
        } finally {
            try {
                Path p = file.toPath();
                Files.move(p, p.resolveSibling(p.getFileName().toString() + ".processed"), StandardCopyOption.REPLACE_EXISTING);
                log.debug("Файл переименован: {} → {}.processed", file.getName(), file.getName());
            } catch (Exception ex) {
                log.warn("Не удалось переименовать файл {}", file.getName(), ex);
            }
        }
    }
}

