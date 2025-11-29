package advanced;

public class userInfo {
    private int passport;
    private String userName;
    private int userAge;

    userInfo(String userName, int userAge){
        this.userName = userName;
        this.userAge = userAge;
    }

    public int getUserAge() {
        return userAge;
    }

    public int getPassport() {
        return passport;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "User Name: " +
                userName + ", User Age, " +
                userAge;
    }
}