package cz.cvut.fel.pm2.timely_be.repository;

import cz.cvut.fel.pm2.timely_be.model.AttendanceRecord;
import cz.cvut.fel.pm2.timely_be.model.Employee;
import cz.cvut.fel.pm2.timely_be.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    List<AttendanceRecord> findByTeamAndDate(Team team, LocalDate date);

    List<AttendanceRecord> findByMember(Employee member);

    List<AttendanceRecord> findByTeamAndDateBetween(Team team, LocalDate startDate, LocalDate endDate);

    List<AttendanceRecord> findByTeamIdAndDateBetween(Long teamId, LocalDate startOfWeek, LocalDate today);
}
