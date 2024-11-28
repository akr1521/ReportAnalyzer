package com.reportgenerator.tests;
import com.reportgenerator.app.ReportApp;
import com.reportgenerator.fileops.ReportReader;
import com.reportgenerator.model.Employee;
import com.reportgenerator.model.ReportConstants;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ReportAppTest {

    @Test
    public void testEmployeeHierarchy() {
     ReportApp reportAppTest= new ReportApp();
     Map<Integer, Employee> employeeSubordinateHierarchy =reportAppTest.formEmployeeSubordinateHierarchy(prepareMockData());
        Assert.assertNotNull( employeeSubordinateHierarchy);
        Assert.assertEquals(   "Martin" ,employeeSubordinateHierarchy.get(123).getSubordinates().get(0).getFirstName());
        Assert.assertEquals(   "Chekov" ,employeeSubordinateHierarchy.get(123).getSubordinates().get(0).getFirstName());
    }

    @Test
    public  void testPropertyLoader(){
       String  property  =  ReportReader.getFilePath();
       Assert.assertNotNull( property);
    }

    @Test
    public void testFileReaderWhenPathIsProvided() throws IOException {
        Map<Integer,Employee> employees =  ReportReader.fetchAndParseFileRecords(ReportConstants.FILE_PATH+ReportConstants.FILE_NAME);
        Assert.assertNotNull(employees);
    }


    @Test
    public void testAssessSalaries(){
        Map<Integer, Employee>  employeeMap = prepareMockData() ;
        for  (Employee employee : employeeMap.values()){
            if (employee.getManagerId() != null) {
                employeeMap.get(employee.getManagerId()).getSubordinates().add(employee);
                Employee  isManager = employeeMap.get(employee.getManagerId());
                isManager.getSubordinates().add(employee);
            }
        }
        ReportApp reportAppTest= new ReportApp();
        reportAppTest.assessAverageSalariesAndLevel( employeeMap);
    }


    Map<Integer, Employee> prepareMockData(){
        Map<Integer, Employee>  employeeMap  = new HashMap<>();
        employeeMap.put(new Integer(123) , new Employee(123,"Joe","Doe",60000,null));
        employeeMap.put(new Integer(124) , new Employee(124,"Martin","Chekov",45000,123));
        employeeMap.put(new Integer(125) , new Employee(125,"Bob","Ronstad",47000,123));
        employeeMap.put(new Integer(300) , new Employee(300,"Alice","Hasacat",50000,124));
        employeeMap.put(new Integer(305) , new Employee(300,"Brett","Hardleaf",34000,300));
        return employeeMap;
    }
}
