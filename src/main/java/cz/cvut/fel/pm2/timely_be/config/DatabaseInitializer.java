package cz.cvut.fel.pm2.timely_be.config;

import cz.cvut.fel.pm2.timely_be.model.Employee;
import cz.cvut.fel.pm2.timely_be.enums.EmploymentStatus;
import cz.cvut.fel.pm2.timely_be.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DatabaseInitializer {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository) {
        return args -> {
            // Sample data
            Employee employee1 = new Employee();
            employee1.setName("John Doe");
            employee1.setJobTitle("Software Developer");
            employee1.setEmploymentStatus(EmploymentStatus.FULL_TIME);
            employee1.setEmail("john.doe@example.com");
            employee1.setPhoneNumber("123-456-7890");
            employee1.setCurrentProjects(Arrays.asList("Project Alpha", "Project Beta"));

            Employee employee2 = new Employee();
            employee2.setName("Jane Smith");
            employee2.setJobTitle("Project Manager");
            employee2.setEmploymentStatus(EmploymentStatus.PART_TIME);
            employee2.setEmail("jane.smith@example.com");
            employee2.setPhoneNumber("098-765-4321");
            employee2.setCurrentProjects(Arrays.asList("Project Gamma"));

            Employee employee3 = new Employee();
            employee3.setName("Alice Johnson");
            employee3.setJobTitle("UX Designer");
            employee3.setEmploymentStatus(EmploymentStatus.CONTRACT);
            employee3.setEmail("alice.johnson@example.com");
            employee3.setPhoneNumber("555-123-4567");
            employee3.setCurrentProjects(Arrays.asList("Project Delta", "Project Epsilon"));

            // Save employees to the database
            employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3));
        };
    }
}
