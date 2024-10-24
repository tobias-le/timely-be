package cz.cvut.fel.pm2.timely_be.dto;

import cz.cvut.fel.pm2.timely_be.enums.EmploymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Data
public class EmployeeDto {
    private String name;
    private String jobTitle;
    private String employmentStatus;
    private String email;
    private String phoneNumber;
    private List<String> currentProjects;
}
