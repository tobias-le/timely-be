package cz.cvut.fel.pm2.timely_be.rest;

import cz.cvut.fel.pm2.timely_be.dto.AttendanceSummaryDTO;
import cz.cvut.fel.pm2.timely_be.model.AttendanceRecord;
import cz.cvut.fel.pm2.timely_be.model.Employee;
import cz.cvut.fel.pm2.timely_be.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendance")
@Tag(name = "Attendance", description = "The Attendance API")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an attendance record by ID")
    public Optional<AttendanceRecord> getAttendanceRecordById(@PathVariable Long id) {
        return attendanceService.getAttendanceRecordById(id);
    }

    @GetMapping("/member/{memberId}")
    @Operation(summary = "Get attendance records for a specific member")
    public List<AttendanceRecord> getAttendanceRecordsByMember(@PathVariable Long memberId) {
        Employee member = new Employee();
        member.setEmployeeId(memberId);
        return attendanceService.getAttendanceRecordsByMember(member);
    }

    @GetMapping("/team/{teamId}")
    @Operation(summary = "Get attendance records for a team on a specific date")
    public List<AttendanceRecord> getAttendanceRecordsByTeamAndDate(@PathVariable Long teamId) {
        return attendanceService.getAttendanceRecordsByTeamSinceStartOfWeek(teamId);
    }

    @GetMapping("/team/{teamId}/summary")
    @Operation(summary = "Get an attendance summary for the current work week")
    public AttendanceSummaryDTO getCurrentWeekAttendanceSummary(@PathVariable Long teamId) {
        // Call the service method to get the current week's attendance performance
        return attendanceService.getCurrentWeekAttendancePerformance(teamId);
    }
}
