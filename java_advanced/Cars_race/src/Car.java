public class Car extends Vehicle implements Breakable, Capacity, Quicken, Refuelled{
    public Car(String model, int speed, int x, int fuel) {
        this.model = model;
        this.speed = speed;
        this.x = x;
        this.fuel = fuel;
    }

    @Override
    void move() {
        if(fuel < 1) {
            refuel();
            return;
        }
        super.move();
    }

    @Override
    public boolean isBroken() {
        return Math.random() <= 0.55;
    }

    @Override
    public void refuel() {
        System.out.println("Car is refueling");
        fuel = 20;
    }

    @Override
    public void quicken() {
        this.speed += 20;
    }

    @Override
    public void capacity() {
        int capacity = 5;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", speed=" + speed +
                ", x=" + x +
                ", fuel=" + fuel +
                '}';
    }
}
