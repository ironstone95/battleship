package battleship;

public class Ship {
    private final String name;
    private final int size;
    private int shotTaken = 0;
    private boolean isSunk = false;

    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public void getDamage() {
        if (!isSunk) {
            shotTaken++;
        }
    }

    public boolean isSunk() {
        return size - shotTaken == 0;
    }
}
