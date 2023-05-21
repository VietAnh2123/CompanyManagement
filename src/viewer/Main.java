package viewer;

import java.util.*;
import controller.*;
public class Main {

    public static void main(String[] args) throws Exception {
        // Menu options
        String[] options = {"Read all Employees and print to screen",
            "Show staff proficient in a Programming Language", "Show Tester has a height salary",
            "Show Employee’s higest salary", "Show Leader of the Team has most Employees",
            "Sort Employees as descending salary", "Write file"};
        final String fileEmp = "src\\input\\ListOfEmployees.txt";
        final String filePL = "src\\input\\PLInfo.txt";
        final String fileWrite1 = "src\\output\\Req2.dat";
        final String fileWrite2 = "src\\output\\Req3.dat";
        int choice;
        Scanner sc = new Scanner(System.in);
        CompanyManagement cm = new CompanyManagement(fileEmp, filePL);
        System.out.println(
                "Note: \nAll employee's salary based on the actual salary after multiply with the bonus and casted into integer!!!");
        do {
            System.out.println("\nCompany Employee Management Program");
            choice = Menu.getChoice(options); // show Menu options
            switch (choice) {
                case 1:
                    cm.printEmpList();
                    break;
                case 2:
                    cm.printDev();
                    break;
                case 3:
                    cm.printTester();
                    break;
                case 4:
                    cm.printHighestEmp();
                    break;
                case 5:
                    cm.printLeaderWithMostEmployees();
                    break;
                case 6:
                    cm.printSortedList();
                    break;
                case 7: // write file
                    cm.writeDeveloperList(fileWrite1);
                    cm.writeTesterList(fileWrite2);
                    break;
                default:
                    System.out.println("Good bye!");
            }
        } while (choice > 0 && choice < options.length);
    }
}
