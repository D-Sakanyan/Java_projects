public class Competition {
    int distance;

    Vehicle race(Vehicle[] vehicles){
        Vehicle winner = vehicles[0];
        for (int i = 1; i < vehicles.length; i++){
            vehicles[i].move();
            if (distance - vehicles[i].x < distance - winner.x){
                winner = vehicles[i];
            }
        }
        return winner;
    }

    Competition(int distance){
        this.distance = distance;
    }
}
