package main;

public class Connections {
    private String line;
    private String stationName;

    public Connections(String line, String stationName) {
        this.line = line;
        this.stationName = stationName;
    }

    public String getLine() {
        return line;
    }

    public String getStationName() {
        return stationName;
    }

    @Override
    public String toString() {
        return line + " " + stationName;
    }
}
