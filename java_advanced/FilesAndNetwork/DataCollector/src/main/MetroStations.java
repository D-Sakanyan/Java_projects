package main;

import java.util.ArrayList;
import java.util.List;

public class MetroStations {
    private String num;
    private String name;
    private String lineNum;
    private List<Connections> connections = new ArrayList<>();

    public MetroStations(String num, String name, String lineNum) {
        this.num = num;
        this.name = name;
        this.lineNum = lineNum;
    }

    @Override
    public String toString() {
        return num + name;
    }

    public String getLineNum() {
        return lineNum;
    }

    public String getName() {
        return name;
    }

    public void setConnections(Connections numOfConnection) {
        connections.add(numOfConnection);
    }

    public List<Connections> getConnections() {
        return connections;
    }
}
