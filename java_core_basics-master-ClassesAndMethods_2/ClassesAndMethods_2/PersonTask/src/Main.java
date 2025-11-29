public class Main {
    public static void main(String[] args) {
        Person person1 = new Person(18, "Anna");
        Person person2 = new Person(20,"Ani");

        System.out.println(person1.equals(person2));

        System.out.println(person1.hashCode() + " " + person2.hashCode());
    }
}
