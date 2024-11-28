package com.reportgenerator.app;

import com.reportgenerator.fileops.ReportReader;
import com.reportgenerator.model.Employee;
import com.sun.xml.internal.ws.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


public class ReportApp {
    public static void main(String[] args)  {
        ReportApp reportApp = new ReportApp();
        String filePath =  ReportReader.getFilePath();
        if(Objects.nonNull(filePath))
        reportApp.initApp(filePath);
    }
    public void initApp(String filePath){
        try {
            Map<Integer, Employee> employeeRecords = ReportReader.fetchAndParseFileRecords(filePath);
            employeeRecords = formEmployeeSubordinateHierarchy(employeeRecords);
            assessAverageSalariesAndLevel (employeeRecords);
        } catch (IOException e) {
            throw new RuntimeException( "Failed to locate file or file is Corrupt:" +e.getLocalizedMessage());
        }
    }
    public  Map<Integer, Employee> formEmployeeSubordinateHierarchy(Map<Integer, Employee> employeeRecords) {
        for  (Employee employee : employeeRecords.values()){
            if (employee.getManagerId() != null) {
                employeeRecords.get(employee.getManagerId()).getSubordinates().add(employee);
                 Employee  isManager = employeeRecords.get(employee.getManagerId());
                 isManager.getSubordinates().add(employee);
            }
        }
        return employeeRecords;
    }
    public  void assessAverageSalariesAndLevel (Map<Integer, Employee>  employees)  {
        Integer averageSalary = null;
        Map<Employee,Integer>  averageSalaryHolder =  new LinkedHashMap<>();
        for (Employee manager : employees.values()) {
            if (manager.getSubordinates().isEmpty()) {
                continue;
            }
            Integer salarySum = 0;
            Integer reporteeCount = 0;
            Iterator<Employee> managerIterator =  manager.getSubordinates().iterator();
            while (managerIterator.hasNext()){
                reporteeCount++;
                Employee subordinate =  managerIterator.next();
                salarySum = salarySum  + subordinate.getSalary();
            }
            averageSalary = salarySum/manager.getSubordinates().size() ;
            averageSalaryHolder.put( manager,averageSalary);
            if (manager.getSalary() <  (1.2 *averageSalary)){
                System.out.println("Manager " + manager.getFirstName() + " earns less than the average salary i.e. " +  averageSalary +
                        " He/She should earn atleast " + (1.2 *averageSalary));
            }
            else if (manager.getSalary() > averageSalary * 1.5) {
                System.out.println("Manager " + manager.getFirstName() + " earns more than the average salary: ");
            }
            assessReportingLevels( manager,  reporteeCount);
        }
    }

    private void assessReportingLevels( Employee employee , Integer reporteeCount) {
        if (reporteeCount +1  >= 4) {
            System.out.println("Employee " + employee.getFirstName() + " has a long reporting line: " + reporteeCount + " levels.");
        }
    }

}
