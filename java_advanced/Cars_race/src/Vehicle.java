public abstract class Vehicle {
    public String model;
    public int speed;
    public int x;
    int fuel;

    void move(){
        this.x+=speed;
        fuel -= 7;
    }

}
