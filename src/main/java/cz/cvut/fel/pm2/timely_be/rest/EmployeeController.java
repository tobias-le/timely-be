package cz.cvut.fel.pm2.timely_be.rest;

import cz.cvut.fel.pm2.timely_be.dto.EmployeeDto;
import cz.cvut.fel.pm2.timely_be.mapper.EmployeeMapper;
import cz.cvut.fel.pm2.timely_be.model.Employee;
import cz.cvut.fel.pm2.timely_be.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee", description = "The Employee API")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(summary = "Get all employees", description = "Returns a list of all employees")
    public Page<EmployeeDto> getEmployees(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeService.getEmployees(pageable);

        // Convert Employee entities to EmployeeDto using EmployeeMapper
        List<EmployeeDto> employeeDtoList = employeePage.getContent().stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());

        // Return a new Page<EmployeeDto> based on the mapped list and pageable information
        return new PageImpl<>(employeeDtoList, pageable, employeePage.getTotalElements());
    }
}