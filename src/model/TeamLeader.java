package model;

import java.util.ArrayList;

public class TeamLeader extends Developer {

    private double bonus_rate;

    public TeamLeader(String empID, String empName, int baseSal, String teamName, ArrayList<String> progammingLanguages, int expYear, double bonus_rate ) {
        super(empID, empName, baseSal, teamName, progammingLanguages, expYear);
        this.bonus_rate = bonus_rate;
    }

    public double getBonus_rate() {
        return bonus_rate;
    }

    @Override
    public double getSalary() {
        
        return super.getSalary() + (super.getSalary() * this.bonus_rate);
    }

}
