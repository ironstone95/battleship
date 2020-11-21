package battleship;

import java.util.Objects;

public class Coordinate implements Comparable<Coordinate> {
    private final char letter;
    private final int number;
    public final int row;
    public final int col;

    public Coordinate(char letter, int number) {
        this.letter = letter;
        this.number = number;
        row = letter - 65;
        col = number - 1;
    }

    public char getLetter() {
        return letter;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public int compareTo(Coordinate o) {
        int verticalCompare = this.row - o.row;
        if (verticalCompare != 0) {
            return verticalCompare;
        } else {
            return this.col - o.col;
        }
    }

    /**
     * 0 -> Coordinates are the same.
     * 1 -> Horizontal orientation.
     * 2 -> Vertical orientation.
     * 3 -> Diagonal orientation.
     *
     * @param other coordinate
     * @return The orientation code between this{@code Coordinate} and other{@code Coordinate}.
     */

    public int getOrientation(Coordinate other) {
        if (this.equals(other)) {
            return 0;
        } else if (other.row == row) {
            return 1;
        } else if (other.col == col) {
            return 2;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return letter == that.letter &&
                number == that.number &&
                row == that.row &&
                col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, number, row, col);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "letter=" + letter +
                ", number=" + number +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}
