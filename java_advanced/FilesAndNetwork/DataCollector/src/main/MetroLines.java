package main;

public class MetroLines {
    private String nameLine;
    private String numLine;

    public MetroLines(String numLine, String nameLine) {
        this.numLine = numLine;
        this.nameLine = nameLine;
    }

    public String getNumLine() {
        return numLine;
    }

    public String getNameLine() {
        return nameLine;
    }

    @Override
    public String toString() {
        return "Lines: " + numLine + ": " + nameLine;
    }
}
