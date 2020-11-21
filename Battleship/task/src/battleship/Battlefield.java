package battleship;

import java.util.*;

public class Battlefield {
    private List<Ship> crew;
    private final char[][] battlefield;
    private int currentIndex = 0;
    private int numberOfShips = 0;
    private int sunkenShip = 0;

    private final Map<Coordinate, Ship> battlefieldMap;

    public Battlefield() {
        setCrew();
        battlefield = new char[10][10];
        for (char[] row : battlefield) {
            Arrays.fill(row, '~');
        }
        numberOfShips = crew.size();

        battlefieldMap = new HashMap<>();
    }

    public int shot(String input) {
        if (!checkInput(input)) {
            return -2;
        }
        Coordinate coordinate = CoordinateUtil.createCoordinate(input);
        if (battlefieldMap.containsKey(coordinate)) {
            battlefield[coordinate.row][coordinate.col] = 'X';
            Ship ship = battlefieldMap.get(coordinate);
            ship.getDamage();
            if (ship.isSunk()) {
                sunkenShip++;
                if (isGameOver()) {
                    return 2;
                } else {
                    return 1;
                }
            } else {
                return 0;
            }
        } else {
            battlefield[coordinate.row][coordinate.col] = 'M';
            return -1;
        }
    }


    public void printBattlefield(boolean fogOfWar) {
        StringBuilder builder = new StringBuilder();
        builder.append("  ");
        for (int i = 1; i <= 10; i++) {
            builder.append(i).append(" ");
        }
        builder.append("\n");
        for (int i = 0; i < battlefield.length; i++) {
            builder.append((char) (i + 65)).append(" ");
            for (int j = 0; j < battlefield[i].length; j++) {
                char current = battlefield[i][j];
                if (fogOfWar && current == 'O') {
                    current = '~';
                }
                builder.append(current).append(" ");

            }
            builder.append("\n");
        }
        System.out.println(builder.toString());
    }

    public boolean canPlaceAnother() {
        return currentIndex < crew.size();
    }

    public void printNextShipInfo() {
        System.out.println("Enter the coordinates of " + crew.get(currentIndex).getName() + " (" + crew.get(currentIndex).getSize() + " cells):");
    }

    public boolean placeShip(String rawInput) {
        String[] input = rawInput.trim().split("\\s+");
        if (!checkInput(input)) {
            System.out.println("Wrong input! Please Enter Coordinates Correctly.");
            return false;
        }
        Coordinate[] coordinates = CoordinateUtil.createCoordinate(input);
        Arrays.sort(coordinates);
        if (CoordinateUtil.isDiagonal(coordinates)) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }

        Ship ship = crew.get(currentIndex);
        if (CoordinateUtil.calculateDistance(coordinates) != ship.getSize()) {
            System.out.println("Error! Wrong length of the " + ship.getName() + " Try again:");
            return false;
        }
        if (isCloseAnother(coordinates)) {
            System.out.println("Error! You placed it too close to another one. Try again:");
            return false;
        }
        drawCoordinates(coordinates, ship);
        currentIndex++;
        return true;
    }

    public boolean isGameOver() {
        return sunkenShip == numberOfShips;
    }

    private void drawCoordinates(Coordinate[] coordinates, Ship ship) {
        int rowStart = coordinates[0].row;
        int rowEnd = coordinates[1].row;

        int colStart = coordinates[0].col;
        int colEnd = coordinates[1].col;

        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = colStart; j <= colEnd; j++) {
                battlefield[i][j] = 'O';
            }
        }

        for (Coordinate coordinate : CoordinateUtil.getCoordinatesBetween(coordinates)) {
            battlefieldMap.put(coordinate, ship);
        }
    }

    private boolean isCloseAnother(Coordinate[] coordinates) {
        int minRow = coordinates[0].row;
        int maxRow = coordinates[1].row;

        int minCol = coordinates[0].col;
        int maxCol = coordinates[1].col;

        int rowStart = minRow > 0 ? minRow - 1 : minRow;
        int rowEnd = maxRow < battlefield.length - 1 ? maxRow + 1 : maxRow;

        int colStart = minCol > 0 ? minCol - 1 : minCol;
        int colEnd = maxCol < battlefield[0].length - 1 ? maxCol + 1 : maxCol;

        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = colStart; j <= colEnd; j++) {
                if (battlefield[i][j] == 'O') {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkInput(String input) {
        String regex = "^[A-J](10|[0-9])$";
        boolean regexCheck = input.matches(regex);
        boolean inputCheck = !input.equals("");
        return regexCheck && inputCheck;
    }

    private static boolean checkInput(String... inputs) {
        if (inputs.length > 2) {
            return false;
        } else {
            return checkInput(inputs[0]) && checkInput(inputs[1]);
        }
    }

    private void setCrew() {
        Ship carrier = new Ship("Aircraft Carrier", 5);
        Ship battleship = new Ship("Battleship", 4);
        Ship submarine = new Ship("Submarine", 3);
        Ship cruiser = new Ship("Cruiser", 3);
        Ship destroyer = new Ship("Destroyer", 2);
        crew = new ArrayList<>(List.of(carrier, battleship, submarine, cruiser, destroyer));
    }
}
