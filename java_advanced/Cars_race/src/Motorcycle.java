public class Motorcycle extends Vehicle implements Breakable, Capacity, Quicken, Refuelled {

    public Motorcycle(String model, int speed, int x, int fuel) {
        this.model = model;
        this.speed = speed;
        this.x = x;
        this.fuel = fuel;
    }

    @Override
    void move() {
        if(fuel < 1){
            refuel();
            return;
        }
        super.move();
    }

    @Override
    public boolean isBroken() {
        return Math.random() <= 0.45;
    }

    @Override
    public void capacity() {
        int capacity = 2;
    }

    @Override
    public void quicken() {
        this.speed += 15;
    }

    @Override
    public void refuel() {
        System.out.println("Motorcycle is refuelling");
        fuel = 10;
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
                "model='" + model + '\'' +
                ", speed=" + speed +
                ", x=" + x +
                ", fuel=" + fuel +
                '}';
    }
}
