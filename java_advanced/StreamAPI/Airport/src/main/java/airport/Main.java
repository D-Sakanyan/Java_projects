package airport;

import com.skillbox.airport.Aircraft;
import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;
import net.sf.saxon.trans.SymbolicName;

import java.time.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static long findCountAircraftWithModelAirbus(Airport airport, String model) {
        //TODO Метод должен вернуть количество самолетов указанной модели.
        // подходят те самолеты, у которых name начинается со строки model
        return  airport.getAllAircrafts()
                .stream()
                .filter(Predicate.isEqual(model))
                .count();
    }

    public static Map<String, Integer> findMapCountParkedAircraftByTerminalName(Airport airport) {
        //TODO Метод должен вернуть словарь с количеством припаркованных самолетов в каждом терминале.
        Map<String,Integer> countAircraft = airport.getTerminals()
                .stream()
                .collect(Collectors.toMap(
                        terminal -> terminal.getName(),
                        terminal -> terminal.getParkedAircrafts().size()));
        return countAircraft;
    }

    public static List<Flight> findFlightsLeavingInTheNextHours(Airport airport, int hours) {
        //TODO Метод должен вернуть список отправляющихся рейсов в ближайшее количество часов

        List<Flight> flightList = airport.getTerminals()
                .stream()
                .flatMap(terminal -> terminal.getFlights().stream())
                .filter(flight -> {
                    Instant flightTime = flight.getDate();
                    Instant now = Instant.now();
                    Flight.Type flightType = flight.getType();
                    Instant upper = now.plus(Duration.ofHours(hours));
                    return !flightTime.isBefore(now) &&
                            !flightTime.isAfter(upper) &&
                            flightType== Flight.Type.DEPARTURE;
                }).toList();
        return flightList;
    }

    public static Optional<Flight> findFirstFlightArriveToTerminal(Airport airport, String terminalName) {
        //TODO Найти ближайший прилет в указанный терминал.
        Instant now = Instant.now();
        Terminal terminal = airport.getTerminals()
                .stream()
                .filter(terminal1 -> terminal1.getName().equals(terminalName))
                .findFirst()
                .orElseThrow();

        Optional<Flight> optionalFlight = terminal.getFlights()
                .stream().filter(flight ->
                        flight.getType() == Flight.Type.ARRIVAL &&
                                !flight.getDate().isBefore(now)).min(Comparator.comparing(Flight::getDate));

        return optionalFlight;
    }
}