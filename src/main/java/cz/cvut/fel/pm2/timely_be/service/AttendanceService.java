package cz.cvut.fel.pm2.timely_be.service;

import cz.cvut.fel.pm2.timely_be.dto.AttendanceSummaryDTO;
import cz.cvut.fel.pm2.timely_be.model.AttendanceRecord;
import cz.cvut.fel.pm2.timely_be.model.Employee;
import cz.cvut.fel.pm2.timely_be.model.Team;
import cz.cvut.fel.pm2.timely_be.repository.AttendanceRecordRepository;
import cz.cvut.fel.pm2.timely_be.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;

@Service
public class AttendanceService {

    private final AttendanceRecordRepository attendanceRecordRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public AttendanceService(AttendanceRecordRepository attendanceRecordRepository, TeamRepository teamRepository) {
        this.attendanceRecordRepository = attendanceRecordRepository;
        this.teamRepository = teamRepository;
    }

    public List<AttendanceRecord> getAttendanceRecordsByTeamSinceStartOfWeek(Long teamId) {
        LocalDate startOfWeek = getStartOfWeek();
        LocalDate today = getToday();

        // Fetch attendance records for the team from the start of the week until today
        return attendanceRecordRepository.findByTeamIdAndDateBetween(teamId, startOfWeek, today);
    }

    // Method to find attendance records for a specific member
    public List<AttendanceRecord> getAttendanceRecordsByMember(Employee member) {
        return attendanceRecordRepository.findByMember(member);
    }

    // Method to find a specific attendance record by its ID
    public Optional<AttendanceRecord> getAttendanceRecordById(Long attendanceId) {
        return attendanceRecordRepository.findById(attendanceId);
    }

    public AttendanceSummaryDTO getCurrentWeekAttendancePerformance(Long teamId) {
        // Determine the current date
        Team team = teamRepository.findById(teamId).orElse(null);

        var startOfWeek = getStartOfWeek();
        var endOfWeek = getEndOfWeek();


        // Fetch attendance records for the team within the current work week
        List<AttendanceRecord> records = attendanceRecordRepository.findByTeamAndDateBetween(team, startOfWeek, endOfWeek);

        // Group records by date to determine attendance rate
        Map<LocalDate, List<AttendanceRecord>> recordsByDate = records.stream()
                .collect(Collectors.groupingBy(AttendanceRecord::getDate));

        // Calculate total hours worked and number of present days per employee
        long totalHours = 0;
        int totalDaysPresent = 0;

        for (Map.Entry<LocalDate, List<AttendanceRecord>> entry : recordsByDate.entrySet()) {
            List<AttendanceRecord> dailyRecords = entry.getValue();

            totalDaysPresent += dailyRecords.size();

            for (AttendanceRecord record : dailyRecords) {
                if (record.getClockInTime() != null && record.getClockOutTime() != null) {
                    Duration duration = Duration.between(record.getClockInTime(), record.getClockOutTime());
                    totalHours += duration.toHours();
                }
            }
        }

        // Calculate average hours per day and attendance rate
        int totalDaysInRange = (int) (Duration.between(startOfWeek.atStartOfDay(), endOfWeek.atStartOfDay()).toDays()) + 1;
        double averageHoursPerDay = totalHours / (double) totalDaysInRange;
        double attendanceRate;
        if (!team.getMembers().isEmpty()) {
            attendanceRate = totalDaysPresent / (double) (team.getMembers().size() * totalDaysInRange) * 100;
        } else {
            attendanceRate = 0;
        }

        // Create and return the DTO with the performance summary for the current work week
        return new AttendanceSummaryDTO(
                team.getName(),
                totalHours,
                averageHoursPerDay,
                attendanceRate
        );
    }

    private LocalDate getStartOfWeek() {
        var today = now();
        return today.with(MONDAY);
    }

    private LocalDate getEndOfWeek() {
        var today = now();
        return today.with(FRIDAY);
    }

    private LocalDate getToday() {
        return now();
    }
}
