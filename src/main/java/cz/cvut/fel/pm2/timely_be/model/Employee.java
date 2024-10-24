package cz.cvut.fel.pm2.timely_be.model;

import cz.cvut.fel.pm2.timely_be.enums.EmploymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String jobTitle;

    @Enumerated(STRING)
    private EmploymentStatus employmentStatus;

    private String email;

    private String phoneNumber;

    @ElementCollection
    private List<String> currentProjects;
}
