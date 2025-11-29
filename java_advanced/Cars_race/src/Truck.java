public class Truck extends Vehicle implements Breakable,Capacity,Quicken,Refuelled {

    public Truck(String model, int speed, int x, int fuel) {
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
        return Math.random() <= 0.60;
    }

    @Override
    public void capacity() {
        int capacity = 30;
    }

    @Override
    public void quicken() {
        this.speed += 7;
    }

    @Override
    public void refuel() {
        System.out.println("Truck is refuelling");
        fuel = 35;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "model='" + model + '\'' +
                ", speed=" + speed +
                ", x=" + x +
                ", fuel=" + fuel +
                '}';
    }
}
