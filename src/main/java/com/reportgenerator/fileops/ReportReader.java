package com.reportgenerator.fileops;

import com.reportgenerator.model.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.io.FileReader;
import java.util.Map;
import java.util.Objects;

import static com.reportgenerator.model.ReportConstants.FILE_NAME;
import static com.reportgenerator.model.ReportConstants.FILE_PATH;

public class ReportReader {

    public static Map<Integer,Employee> readFile(String filename){
        Map<Integer, Employee>employeeMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Integer id = parseId(values[0]);
                String firstName = values[1].trim();
                String lastName = values[2].trim();
                Integer salary = null;
                try {
                    salary = Integer.parseInt(values[3].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing salary for ID: " + id + ". Invalid format: " + values[3]);
                }
                if (values.length >4 && values[4].length()>0) {
                   String managerId = values[4];
                    if (managerId == "" ||  managerId.equals(""))
                        employeeMap.put((id), new Employee( (id), firstName, lastName, salary, null));
                    else{
                        employeeMap.put((id), new Employee((id), firstName, lastName, salary, Integer.parseInt(managerId)));
                    }
                }else
                    employeeMap.put(id, new Employee( (id), firstName, lastName, (salary), null));
            }
            } catch (IOException ex) {
            throw new RuntimeException( "Failed to locate file or File is corrupt"+ ex);
        }
        return employeeMap;
    }

    private static  Integer parseId(String rawId){
        if ( rawId.length()>3) {
            return Integer.parseInt(rawId.substring(1, 4).trim());
        }
        else{
            return Integer.parseInt(rawId.trim());
        }
    }

    public static String getFilePath() {
        try
         (FileInputStream  input = new FileInputStream("src/main/resources/application.properties")) {
            Properties property = new Properties();
            property.load(input);
            String  filePath = FILE_PATH+ property.getProperty(FILE_NAME);
            return filePath;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}

