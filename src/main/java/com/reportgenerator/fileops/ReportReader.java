package com.reportgenerator.fileops;

import com.reportgenerator.model.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.io.FileReader;
import java.util.Map;
import java.util.Objects;

import static com.reportgenerator.model.ReportConstants.FILE_NAME;
import static com.reportgenerator.model.ReportConstants.FILE_PATH;

public class ReportReader {
    public static Map<Integer,Employee> fetchAndParseFileRecords(String filePath) throws IOException {
        Map<Integer, Employee> employeeMap = null;
        if (Objects.nonNull(filePath)) {
            employeeMap = new LinkedHashMap<>();
            Reader reader = new FileReader(getFilePath());
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for (CSVRecord record : parser) {
                String[]  values = record.values();
                Integer id = Integer.parseInt(values[0]);
                String firstName = values [1];
                String lastName = values [2];
                Integer salary= Integer.parseInt ( values[3]);
                String managerId = null;
                if ( values[4].length()>0) {
                    managerId = values[4];
                    if (managerId == "" ||  managerId.equals(""))
                    employeeMap.put(id, new Employee( (id), firstName, lastName, (salary), null));
                    else{
                        employeeMap.put(id, new Employee( (id), firstName, lastName, (salary), Integer.parseInt(managerId)));
                    }
                }else
                employeeMap.put(Integer.parseInt(String.valueOf(id)), new Employee( id, firstName, lastName, salary, null));
            }
        }
        return employeeMap;
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

