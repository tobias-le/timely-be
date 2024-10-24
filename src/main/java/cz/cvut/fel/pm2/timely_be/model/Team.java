package cz.cvut.fel.pm2.timely_be.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@Data
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "employeeId")
    private Employee manager;

    @OneToMany
    @JoinColumn(name = "team_id")
    private List<Employee> members;

    public List<Employee> getMembers() {
        if (members == null) {
            members = new ArrayList<>();
        }
        return members;
    }
}
