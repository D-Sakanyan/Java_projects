public class MathUtils {
    public static double pi;

    static {
        pi = 3.1415;
    }

    public static double calculateSurfaceArea(double radius){
        return  4 * pi * (radius * radius);
    }

    public static double calculateEquatorLength(double radius){
        return 2 * pi * radius;
    }
}