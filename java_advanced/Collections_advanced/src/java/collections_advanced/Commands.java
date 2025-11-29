package advanced;

import java.util.*;

public class Commands {
    private static HashMap<String, userInfo> map = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    //in
    protected static void in() {
        System.out.println("Enter passport number, user name and age");
        while (true) {
            String passport = scanner.nextLine();
            try {
                if (passport.equalsIgnoreCase("back")) {
                    break;
                }
                if (!passport.matches("\\d{5}")) {
                    System.out.println("The passport must countain 5 digits!");
                    continue;
                }
                if (map.containsKey(passport)) {
                    System.out.println("There is already such passport!");
                    continue;
                }

                String name = scanner.nextLine();
                if (!name.matches("[a-zA-Z]+")) {
                    System.out.println("Name must contain only letters!");
                    continue;
                }
                int age;
                age = Integer.parseInt(scanner.nextLine());
                if (age <= 0) {
                    System.out.println("The age must be positive!");
                    continue;
                }
                userInfo user = new userInfo(name, age);
                map.put(passport, user);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            System.out.println("\nAll users:");
            for (Map.Entry<String, userInfo> entry : map.entrySet()) {
                System.out.println("Passport: " + entry.getKey() + ", " + entry.getValue());
            }
        }
    }

    //del
    protected static void del() {
        System.out.println("Enter passport number");
        while (true) {
            String numberForDelete = scanner.nextLine();
            try {
                //String command = scanner.nextLine();
                if (numberForDelete.equalsIgnoreCase("back")) {
                    break;
                }
                if (!numberForDelete.matches("\\d{5}")) {
                    System.out.println("The passport must countain 5 digits!");
                    continue;
                }
                if (!map.containsKey(numberForDelete)) {
                    System.out.println("There is not such passport!");
                    continue;
                }
                map.remove(numberForDelete);
                System.out.println("User " + numberForDelete + " is removed");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
        }
    }

    //count
    protected static void count() {
        while (true) {
            System.out.println(map.size());
            break;
        }
    }

    //avg
    protected static void avg() {
        double avgSum = 0;
        if (!map.isEmpty()) {
            for (Map.Entry<String, userInfo> entry : map.entrySet()) {
                avgSum += entry.getValue().getUserAge() / map.size();
            }
            System.out.println("Average age is: " + avgSum);
        } else {
            System.out.println("There are not users, you can't count the sum!");
        }
    }

    //Quick Select {
    private static int quickSelect(List<Integer> nums, int left, int right, int k) {
        if (left == right) {
            return nums.get(left);
        }
        int pivotIndex = left + (right - left) / 2;
        pivotIndex = partition(nums, left, right, pivotIndex);

        if (k == pivotIndex) return nums.get(k);
        else if (k < pivotIndex) return quickSelect(nums, left, pivotIndex - 1, k);
        else return quickSelect(nums, pivotIndex + 1, right, k);
    }

    private static int partition(List<Integer> nums, int left, int right, int pivotIndex) {
        int pivotValue = nums.get(pivotIndex);
        Collections.swap(nums, pivotIndex, right);
        int leftIndex = left;

        for (int i = left; i < right; i++) {
            if (nums.get(i) < pivotValue) {
                Collections.swap(nums, leftIndex, i);
                leftIndex++;
            }
        }
        Collections.swap(nums, leftIndex, right); // возвращаем pivot
        return leftIndex;
    }
    //}

    //median
    protected static void median() {
        List<Integer> list = new ArrayList<>();
        while (true) {
            for (userInfo u : map.values()) {
                list.add(u.getUserAge());
            }

            int middleIndex = list.size();
            int left = middleIndex / 2 - 1;
            int right = middleIndex / 2;

            if (!list.isEmpty()) {
                if (middleIndex % 2 == 0) {
                    System.out.println((quickSelect(new ArrayList<>(list), 0, middleIndex - 1, left)
                            + quickSelect(new ArrayList<>(list), 0, middleIndex - 1, right)) / 2.0);
                } else {
                    System.out.println(quickSelect(list, 0, middleIndex - 1, middleIndex / 2));
                }
                break;
            } else {
                System.out.println("There are not users, you can't find the median!");
                break;
            }
        }
    }


    //young
    protected static void young() {
        List<Integer> list = new ArrayList<>();
        for (userInfo u : map.values()) {
            list.add(u.getUserAge());
        }
        int youngUser = quickSelect(list, 0, list.size() - 1, 0);
        System.out.println("The youngest user is " + youngUser);
    }

    //old
    protected static void old() {
        List<Integer> list = new ArrayList<>();
        for (userInfo u : map.values()) {
            list.add(u.getUserAge());
        }
        int oldUser = quickSelect(list, 0, list.size() - 1, list.size() - 1);
        System.out.println("The oldest user is " + oldUser);
    }

    //print
    protected static void print() {
        int max_age = 150;
        List<userInfo>[] buckets = new ArrayList[max_age + 1];
        for (int i = 0; i <= max_age; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (userInfo u : map.values()) {
            buckets[u.getUserAge()].add(u);
        }

        for (int age = 0; age <= max_age; age++) {
            for (userInfo u : buckets[age]) {
                System.out.println("Passport: " + u.getPassport() +
                        ", Name: " + u.getUserName() +
                        ", Age: " + u.getUserAge());
            }
        }
    }

    //help
    protected static void help(){
        System.out.println("in: Adds users" +
                "\ndel: Deleting users by passport number" +
                "\ncount: Counting count of users" +
                "\navg: Average sum of users age" +
                "\nmedian: Median age of users" +
                "\nyoung: Is outputing the youngest user" +
                "\nold: Is outputing the oldest user" +
                "\nprint: Is printing users by sorted age" +
                "\nexit: Programm is exiting");
    }

    //exit
    protected static void exit(){
        System.out.println("Program is complated");
        System.exit(0);
    }
}
