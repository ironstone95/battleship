package battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoordinateUtil {
    public static boolean isDiagonal(Coordinate[] coordinates) {
        boolean firstCheck = coordinates[0].getLetter() == coordinates[1].getLetter() && coordinates[0].getNumber() != coordinates[1].getNumber();
        boolean secondCheck = coordinates[0].getLetter() != coordinates[1].getLetter() && coordinates[0].getNumber() == coordinates[1].getNumber();
        return !(firstCheck || secondCheck);
    }

    public static int calculateDistance(Coordinate[] coordinates) {
        int letterDistance = Math.abs(coordinates[0].getLetter() - coordinates[1].getLetter());
        int numberDistance = Math.abs(coordinates[0].getNumber() - coordinates[1].getNumber());
        return letterDistance + numberDistance + 1;
    }

    public static Coordinate createCoordinate(String input) {
        char letter = input.charAt(0);
        int number = Integer.parseInt(input.substring(1));
        return new Coordinate(letter, number);
    }

    public static Coordinate[] createCoordinate(String[] input) {
        return new Coordinate[]{createCoordinate(input[0]), createCoordinate(input[1])};
    }

    public static List<Coordinate> getCoordinatesBetween(Coordinate[] coordinates) {
        Arrays.sort(coordinates);
        List<Coordinate> coordinateList = new ArrayList<>();
        Coordinate cMin = coordinates[0];
        Coordinate cMax = coordinates[1];
        int orientation = cMin.getOrientation(cMax);
        if (orientation == 1) {
            char letter = cMin.getLetter();
            int numMin = cMin.getNumber();
            int numMax = cMax.getNumber();
            for (int i = numMin; i <= numMax; i++) {
                coordinateList.add(new Coordinate(letter, i));
            }
        } else {
            int num = cMin.getNumber();
            char letterMin = cMin.getLetter();
            char letterMax = cMax.getLetter();
            for (char i = letterMin; i <= letterMax; i++) {
                coordinateList.add(new Coordinate(i, num));
            }
        }
        return coordinateList;
    }
}
