package cz.cvut.fel.pm2.timely_be.mapper;

import cz.cvut.fel.pm2.timely_be.dto.EmployeeDto;
import cz.cvut.fel.pm2.timely_be.model.Employee;

public class EmployeeMapper {
    public static EmployeeDto toDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName(employee.getName());
        employeeDto.setJobTitle(employee.getJobTitle());
        employeeDto.setEmploymentStatus(employee.getEmploymentStatus().name());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setPhoneNumber(employee.getPhoneNumber());
        employeeDto.setCurrentProjects(employee.getCurrentProjects());
        return employeeDto;
    }
}
