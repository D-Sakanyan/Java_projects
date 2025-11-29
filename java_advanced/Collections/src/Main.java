package collections_base;

import java.util.*;

public class Main {
    private static Set<Integer> unique = new HashSet<>();
    private static Set<Integer> duplicate = new HashSet<>();
    private static int summ = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Input: ");
        while (true){
            String line = in.nextLine();
            if(line.isEmpty()) {
                break;
            }

            try{
                int x = Integer.parseInt(line);
                if (!unique.add(x)) {
                    duplicate.add(x);
                }
            }catch (NumberFormatException e){
                System.out.println("Input only integers!");
            }
        }
        in.close();

        for (int num : unique){
            if (!duplicate.contains(num))
                summ += num;
        }

        System.out.println("Summary: " + summ);
        System.out.println("Duplicates: " + duplicate);
    }
}