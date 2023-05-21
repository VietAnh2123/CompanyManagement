package model;

import java.util.ArrayList;

public class Developer extends Employee {

    protected String teamName;
    protected ArrayList<String> programmingLanguages;
    protected int expYear;

    public Developer(String empID, String empName, int baseSal, String teamName, ArrayList<String> programmingLanguages, int expYear) {
        super(empID, empName, baseSal);
        this.teamName = teamName;
        this.programmingLanguages = programmingLanguages;
        this.expYear = expYear;
    }

    public String getTeamName() {
        return teamName;
    }

    public ArrayList<String> getProgrammingLanguages() {
        return programmingLanguages;
    }

    public int getExpYear() {
        return expYear;
    }

    @Override
    public double getSalary() {
        if (expYear >= 5) {
            return baseSal + expYear * 2000000;
        } else if (expYear >= 3 && expYear < 5) {
            return baseSal + expYear * 1000000;
        } else {
            return baseSal;
        }

    }

    @Override
    public String toString() {
        return super.toString() + "_" + this.teamName + "_" + this.programmingLanguages + "_" + this.expYear; 
    }

    
}
