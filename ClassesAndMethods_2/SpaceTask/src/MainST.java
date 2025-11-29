public class MainST {
    public static void main(String[] args) {
        System.out.println(MathUtils.pi);
        System.out.println("S = " + MathUtils.calculateSurfaceArea(6371));
        System.out.println("C = " + MathUtils.calculateEquatorLength(6371));
    }
}
