public class Contact {
    private String fullName;
    private String jobTitle;
    private String dateOfBirth;
    private String phoneNumber;
    private String email;

    public Contact(String fullName, String jobTitle, String dateOfBirth, String phoneNumber, String email) {
        this.fullName = fullName;
        this.jobTitle = jobTitle;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contact: " +
                "Full name: " + fullName + '\n' +
                "Job title: " + jobTitle + '\n' +
                "Date of birth: " + dateOfBirth + '\n' +
                "Phone number: " + phoneNumber + '\n' +
                "Email: " + email + '\n';
    }
}
