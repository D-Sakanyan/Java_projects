import java.util.Scanner;

public class AddressBook {
    String fullName;
    String jobTitle;
    String dateOfBirth;
    String phoneNumber;
    String email;
    int size = 0;


    Contact[] contacts = new Contact[100];
    Scanner scanner = new Scanner(System.in);

    Contact[] add() {
        String name = contactName();
        String job = contactJob();
        String birth = contactBirth();
        String phone = contactPhone();
        String email1 = contactEmail();

        boolean exists = false;
        for (int i = 0; i < size; i++) {
            if (contacts[i] != null) {
                if (contacts[i].getFullName().trim().replaceAll("\\s+", " ").equalsIgnoreCase(name)) {
                    System.out.println("The contact is already exist");
                    contacts[i].setFullName(name);
                    contacts[i].setJobTitle(job);
                    contacts[i].setDateOfBirth(birth);
                    contacts[i].setPhoneNumber(phone);
                    contacts[i].setEmail(email1);
                    exists = true;
                    System.out.println("The contact is updated");
                    break;
                }
            }
        }
        if (exists == false) {
            contacts[size] = new Contact(fullName, jobTitle, dateOfBirth, phoneNumber, email);
            size++;
            System.out.println("The contact is added");
            exists = true;
        }
        return contacts;
    }

    void del() {
        while (true) {
            System.out.println("Enter contact ID");
            String input = scanner.nextLine();
            if (!input.matches("\\d+")) {
                System.out.println("Enter integer");
                continue;
            }
            int id = Integer.parseInt(input);
            if (id >= 0 && id < size && contacts[id] != null) {
                contacts[id] = null;
                System.out.println("Contact with ID " + id + " is deleted ");
                break;
            } else {
                System.out.println("There is no contact with that ID");
            }

        }
    }

    void returnInfo() {
        if (size == 0) {
            System.out.println("Contacts book is empty");
            return;
        }
        for (int i = 0; i < size; i++) {
            if (contacts[i] != null) {
                System.out.println("ID: " + i + "\n" + contacts[i]);
            }
        }
    }

    String contactName() {
        while (true) {
            System.out.println("Input full name: ");
            String name = scanner.nextLine();
            name = name.trim().replace("\\s+", " ");
            if (!name.matches("[a-zA-Zа-яА-Я]+")) {
                System.out.println("Full name must contains only letters");
                continue;
            }
            return fullName = name;
        }
    }

    String contactJob() {
        while (true) {
            System.out.println("Input job title: ");
            String job = scanner.nextLine();
            if (job.matches("[a-zA-Zа-яА-Я]+")) {
                jobTitle = job;
                break;
            } else {
                System.out.println("Job title must contains only letters");
                continue;
            }
        }
        return jobTitle;
    }

    String contactBirth() {
        while (true) {
            System.out.println("Input date of birth: ");
            String birth = scanner.nextLine();
            if (birth.matches("\\d+")) {
                dateOfBirth = birth;
                break;
            } else {
                System.out.println("Date of birth must contain numbers");
                continue;
            }
        }
        return dateOfBirth;
    }

    String contactPhone() {
        while (true) {
            System.out.println("Input phone number: ");
            String phone = scanner.nextLine();
            if (phone.matches("\\d+")) {
                phoneNumber = phone;
                break;
            } else {
                System.out.println("Phone number must contain numbers");
                continue;
            }
        }
        return phoneNumber;
    }

    String contactEmail() {
        while (true) {
            System.out.println("Input email ");
            String mail = scanner.nextLine();
            email = mail;
            break;
        }
        return email;
    }


}