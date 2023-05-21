package controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import model.Developer;
import model.TeamLeader;
import model.Tester;
import model.Employee;

public class CompanyManagement<E> {

    private ArrayList<Employee> empList;

    // Contructor and read file
    public CompanyManagement(String path1, String path2) throws Exception {
        empList = getEmployeeFromFile(path1, path2);
    }

    // reads from the file into the empList
    public static ArrayList<Employee> getEmployeeFromFile(String path1, String path2) throws Exception {
        ArrayList<Employee> list = null;
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        try {
            HashMap<String, ArrayList<String>> hPlInfo = new HashMap<>();

            fileInputStream = new FileInputStream(path2);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String linePL = bufferedReader.readLine();
            while (linePL != null) {
                if (!linePL.isEmpty()) {
                    String[] split = linePL.trim().split(",");
                    ArrayList<String> pl = new ArrayList<>();
                    for (int i = 1; i < split.length; i++) {
                        pl.add(split[i]);
                    }
                    hPlInfo.put(split[0], pl);
                }
                linePL = bufferedReader.readLine();
            }
            fileInputStream.close();
            bufferedReader.close();

            fileInputStream = new FileInputStream(path1);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String lineEmp = bufferedReader.readLine();
            while (lineEmp != null) {
                if (!lineEmp.isEmpty()) {
                    String[] split = lineEmp.trim().split(",");
                    Employee emp = null;
                    if (split[5].charAt(0) == 'L') { //teamLeader
                        String id = split[1];
                        String name = split[2];
                        String team = split[3];
                        int year = Integer.parseInt(split[4]);
                        double bonus = Double.parseDouble(split[6]);
                        int balS = Integer.parseInt(split[7]);
                        ArrayList<String> pl = hPlInfo.get(id);
                        emp = new TeamLeader(id, name, balS, team, pl, year, bonus);
                    } else if (split[1].charAt(0) == 'D') { //developer
                        String id = split[1];
                        String name = split[2];
                        String team = split[3];
                        int year = Integer.parseInt(split[4]);
                        int balS = Integer.parseInt(split[5]);
                        ArrayList<String> pl = hPlInfo.get(id);
                        emp = new Developer(id, name, balS, team, pl, year);
                    } else if (split[1].charAt(0) == 'T') { //tester
                        String id = split[1];
                        String name = split[2];
                        double bonus_T = Double.parseDouble(split[3]);
                        String type = split[4];
                        int balS = Integer.parseInt(split[5]);
                        emp = new Tester(id, name, balS, bonus_T, type);
                    }

                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    if (emp != null) {
                        list.add(emp);
                    }
                }
                lineEmp = bufferedReader.readLine();
            }
        } catch (Exception ex) {

        } finally {
            fileInputStream.close();
            bufferedReader.close();
        }
        return list;
    }

    // list of programmers who are proficient in the input pl programmingLanguage.
    public ArrayList<Developer> getDeveloperByProgrammingLanguage(String pl) {
        if (empList == null) {
            return null;
        }
        ArrayList<Developer> devList = null;
        for (Employee emp : empList) {
            if (emp instanceof Developer) {
                for (String plOfDev : ((Developer) emp).getProgrammingLanguages()) {
                    if ((plOfDev).equals(pl)) {
                        if (devList == null) {
                            devList = new ArrayList<>();
                        }
                        devList.add(((Developer) emp));
                    }
                }
            }
        }
        return devList;
    }

    // list of testers whose total salary is greater than the value of the parameter
    public ArrayList<Tester> getTestersHaveSalaryGreaterThan(double value) throws Exception {
        if (empList == null) {
            return null;
        }
        if (value < 0) {
            return null;
        }
        ArrayList<Tester> salaryGreaterValue = null;
        for (Employee emp : empList) {
            if (emp instanceof Tester) {
                if (emp.getSalary() > value) {
                    if (salaryGreaterValue == null) {
                        salaryGreaterValue = new ArrayList<>();
                    }
                    salaryGreaterValue.add(((Tester) emp));
                }
            }
        }
        return salaryGreaterValue;
    }

    public Employee getEmployeeWithHighestSalary() throws Exception {
        if (empList == null) {
            return null;
        }
        Employee highestEmp = null;
        for (Employee emp : empList) {
            if (highestEmp == null) {
                highestEmp = emp;
            }
            if (highestEmp.getSalary() <= emp.getSalary()) {
                highestEmp = emp;
            }
        }
        for (int i = empList.size() - 1; i >= 0; i--) {
            if (empList.get(i).getSalary() == highestEmp.getSalary()) {
                highestEmp = empList.get(i);
                break;
            }
        }
        return highestEmp;
    }

    // get the team leader of the group with the most programmers
    public TeamLeader getLeaderWithMostEmployees() throws Exception {
        if(empList == null){
            return null;
        }
        TeamLeader leader = null;
        int count = 0;
        for(Employee emp : empList){
            if(emp instanceof TeamLeader){
                if(leader == null){
                    leader = (TeamLeader) emp;
                    for(Employee empCount : empList){
                        if(empCount instanceof Developer){
                            if(((Developer) empCount).getTeamName().equals(leader.getTeamName())){
                                count++;
                            }
                        }
                    }
                }
                int count1 = 0;
                if(((Developer) emp).getTeamName().compareTo(leader.getTeamName()) != 0){
                    for(Employee empCount1 : empList){
                        if(empCount1 instanceof Developer){
                            if(((Developer) empCount1).getTeamName().equals(((TeamLeader) emp).getTeamName())){
                                count1++;
                            }
                        }
                    }
                    if(count1 > count){
                        leader = (TeamLeader)emp;
                        count = count1;
                    }else if(count1 == count){
                        if(((TeamLeader) emp).getExpYear() > leader.getExpYear()){
                            leader = (TeamLeader)emp;
                            count = count1;
                        }
                    }
                }
            }
        }
        return leader;
    }

    // Sort Employees as descending salary
    public ArrayList<Employee> sorted() throws Exception {
        ArrayList<Employee> sortedList = null;
        if (empList != null) {
            sortedList = (ArrayList<Employee>) empList.clone();
            Comparator<Employee> com = (Employee o1, Employee o2) -> (int) (o2.getSalary() - o1.getSalary());
            Collections.sort(sortedList, com);
        }
        return sortedList;
    }

    // print empList
    public void printEmpList() {
        for (Employee emp : empList) {
            System.out.println(emp);
        }
    }

    //print staff proficient in a programming language
    public void printDev() {
        Scanner intput = new Scanner(System.in);
        System.out.print("Input programming Language: ");
        String pl = intput.nextLine();
        ArrayList<Developer> devList = this.getDeveloperByProgrammingLanguage(pl);
        for(Developer emp : devList){
            System.out.println(emp);
        }
    }
    
    //print tester has high salary
    public void printTester() throws Exception{
        Scanner intput = new Scanner(System.in);
        System.out.print("Input value: ");
        double value = intput.nextDouble();
        ArrayList<Tester> testerList = this.getTestersHaveSalaryGreaterThan(value);
        for(Tester emp : testerList){
            System.out.println(emp);
        }
    }
    
    //print employee has highest salary
    public void printHighestEmp() throws Exception{
        Employee hEmp = this.getEmployeeWithHighestSalary();
        System.out.println(hEmp);
    }
    
    //print leader has most employee
    public void printLeaderWithMostEmployees() throws Exception{
        Employee mostEmp = this.getLeaderWithMostEmployees();
        System.out.println(mostEmp);
    }
    
    //print descending salary
    public void printSortedList() throws Exception{
        ArrayList<Employee> sorted = this.sorted();
        for(Employee sortedList : sorted){
            System.out.println(sortedList);
        }
    }
    
    // write emplist
   public <E> boolean writeFile(String path, ArrayList<E> list) throws Exception {
        if (list == null) {
            return false;
        }
        FileWriter fWriter = new FileWriter(path);
        for (E emp : list) {
            fWriter.write(emp.toString());
            fWriter.write("\n");
        }

        fWriter.close();
        return true;
    }

    public <E> boolean writeFile(String path, Object obj) throws Exception {
        if (obj == null) {
            return false;
        }

        File fInfo = new File(path);
        FileWriter fWriter = null;

        fWriter = new FileWriter(fInfo);

        fWriter.write(obj.toString());
        fWriter.close();
        return true;
    }

    public void writeDeveloperList(String path) {
        Scanner input = new Scanner(System.in);
        System.out.print("Input programming Language: ");
        String pl = input.nextLine();
        ArrayList<Developer> devList = this.getDeveloperByProgrammingLanguage(pl);
        try {
            writeFile(path, devList);
            System.out.println("Tester List is saved to " + path);
        } catch (Exception e) {
        }
    }

    public void writeTesterList(String path) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.print("Input value: ");
        double value = input.nextDouble();
        ArrayList<Tester> testerList = this.getTestersHaveSalaryGreaterThan(value);
        try {
            writeFile(path, testerList);
            System.out.println("Tester List is saved to " + path);
        } catch (Exception e) {
        }
    }
}