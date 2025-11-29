public class Bicycle extends Vehicle implements Breakable, Quicken, Capacity{

    public Bicycle(String model, int speed, int x) {
        this.model = model;
        this.speed = speed;
        this.x = x;
    }

    @Override
    void move() {
        if(isBroken()) {
            quicken();
            return;
        }
        super.move();
    }

    @Override
    public boolean isBroken() {
        return Math.random() <= 0.40;
    }

    @Override
    public void quicken() {
        this.speed += 3;
        this.x += this.speed;
    }

    @Override
    public void capacity() {
        int capacity = 2;
    }

    @Override
    public String toString() {
        return "Bicycle{" +
                "model='" + model + '\'' +
                ", speed=" + speed +
                ", x=" + x +
                '}';
    }
}
