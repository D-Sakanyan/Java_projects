package advanced;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main extends Commands {

    public static void main(String[] args) {
        System.out.println("Enter one of the commands: in, del, count, avg," +
                            " median, young, old, print, help, exit");

        while(true) {
            System.out.print("Command: ");
            String command = scanner.nextLine();
            switch (command){
                case "in":
                    in();
                    break;
                case "del":
                    del();
                    break;
                case "count":
                    count();
                    break;
                case "avg":
                    avg();
                    break;
                case "median":
                    median();
                    break;
                case "young":
                    young();
                    break;
                case "old":
                    old();
                    break;
                case "print":
                    print();
                    break;
                case "help":
                    help();
                    break;
                case "exit":
                    exit();
                    break;
                default:
                    System.out.println("Unknown command, try again!");
                    break;
            }
        }


    }

}
