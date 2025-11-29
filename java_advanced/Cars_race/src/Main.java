public class Main {
    public static void main(String[] args) {
        Bicycle bicycle = new Bicycle("bike", 5, 0);
        Car car = new Car("car",30, 0, 20);
        Motorcycle motorcycle = new Motorcycle("motorcycle",25, 0, 10);
        Truck truck = new Truck("truck", 15, 0, 30);
        Competition competition = new Competition(100);
        Vehicle[] vehicles = new Vehicle[]{bicycle, car, motorcycle, truck};

        System.out.println(competition.race(vehicles));
    }
}
