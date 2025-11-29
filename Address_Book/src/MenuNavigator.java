import java.util.Scanner;

public class MenuNavigator {
    public void optionMenu() {
        System.out.println("Адресная книга\n" +
                "1 – новый контакт\n" +
                "2 – удаление существующего контакта\n" +
                "3 – вывод всех контактов\n" +
                "4 – завершение работы\n" +
                "Введите нужную опцию и нажмите Enter:");
    }

    public void start() {
        AddressBook addressBook = new AddressBook();

        while (true) {
            optionMenu();
            Scanner scanner = new Scanner(System.in);
            String field = scanner.nextLine();
            switch (field) {
                case "1":
                    addressBook.add();
                    continue;
                case "2":
                    addressBook.del();
                    continue;
                case "3":
                    addressBook.returnInfo();
                    continue;
                case "4":
                    System.exit(0);
                    System.out.println("The program is completed");
            }
        }
    }
}