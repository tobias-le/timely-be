package cz.cvut.fel.pm2.timely_be.config;

import cz.cvut.fel.pm2.timely_be.model.Employee;
import cz.cvut.fel.pm2.timely_be.model.Team;
import cz.cvut.fel.pm2.timely_be.model.AttendanceRecord;
import cz.cvut.fel.pm2.timely_be.enums.EmploymentStatus;
import cz.cvut.fel.pm2.timely_be.repository.EmployeeRepository;
import cz.cvut.fel.pm2.timely_be.repository.TeamRepository;
import cz.cvut.fel.pm2.timely_be.repository.AttendanceRecordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DatabaseInitializer {

    @Bean
    CommandLineRunner initEmployeeDatabase(EmployeeRepository employeeRepository, TeamRepository teamRepository
    , AttendanceRecordRepository attendanceRecordRepository) {
        return args -> {
            // Sample Employee data
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
            employee2.setCurrentProjects(List.of("Project Gamma"));

            Employee employee3 = new Employee();
            employee3.setName("Alice Johnson");
            employee3.setJobTitle("UX Designer");
            employee3.setEmploymentStatus(EmploymentStatus.CONTRACT);
            employee3.setEmail("alice.johnson@example.com");
            employee3.setPhoneNumber("555-123-4567");
            employee3.setCurrentProjects(Arrays.asList("Project Delta", "Project Epsilon"));

            // Save employees to the database
            List<Employee> employees = employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3));

            // Sample Team data
            Team team = new Team();
            team.setName("Product Team 1");
            team.setManager(employee2); // Jane Smith as the manager
            team.setMembers(employees);

            // Sample Team data
            Team team2 = new Team();
            team2.setName("Product Team 2");
            team2.setManager(employee3);

            // Sample Team data
            Team team3 = new Team();
            team3.setName("Product Team 3");
            team3.setManager(employee1);

            // Save team to the database
            teamRepository.saveAll(List.of(team, team2, team3));

            AttendanceRecord record1 = new AttendanceRecord();
            record1.setTeam(team);
            record1.setMember(employees.get(0)); // John Doe
            record1.setDate(LocalDate.now());
            record1.setClockInTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)));
            record1.setClockOutTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0)));

            AttendanceRecord record2 = new AttendanceRecord();
            record2.setTeam(team);
            record2.setMember(employees.get(1)); // Jane Smith
            record2.setDate(LocalDate.now());
            record2.setClockInTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 30)));
            record2.setClockOutTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 30)));

            AttendanceRecord record3 = new AttendanceRecord();
            record3.setTeam(team);
            record3.setMember(employees.get(2)); // Alice Johnson
            record3.setDate(LocalDate.now());
            record3.setClockInTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
            record3.setClockOutTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 0)));
            // Save attendance records to the database
            attendanceRecordRepository.saveAll(Arrays.asList(record1, record2, record3));
        };
    }
}
