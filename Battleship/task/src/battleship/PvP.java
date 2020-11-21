package battleship;

import java.util.Scanner;

public class PvP {
    private final Battlefield player1Field;
    private final Battlefield player2Field;
    private int currentPlayer = 1;

    public PvP() {
        player1Field = new Battlefield();
        player2Field = new Battlefield();
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            preGamePreparation(scanner);
            startGame(scanner);
        }
    }


    private void startGame(Scanner scanner) {
        GameLoop:
        while (true) {
            Battlefield currentPlayerField = currentPlayer == 1 ? player1Field : player2Field;
            Battlefield opponentsField = currentPlayerField.equals(player1Field) ? player2Field : player1Field;
            opponentsField.printBattlefield(true);
            drawLine(30);
            currentPlayerField.printBattlefield(false);
            System.out.println("Player " + currentPlayer + ", it's your turn:\n");
            InnerLoop:
            while (true) {
                String input = scanner.nextLine();
                int shotResult = opponentsField.shot(input);
                switch (shotResult) {
                    case -2: {
                        System.out.println("Wrong input try again!");
                        break;
                    }
                    case -1: {
                        System.out.println("You missed!");
                        break InnerLoop;
                    }
                    case 0: {
                        System.out.println("You hit a ship!");
                        break InnerLoop;
                    }
                    case 1: {
                        System.out.println("You sank a ship! Specify a new target:");
                        break InnerLoop;
                    }
                    case 2: {
                        System.out.println("You sank the last ship. You won player " + currentPlayer + ". Congratulations!");
                        break GameLoop;
                    }
                }
            }
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            showMessageOnTurnEnd(scanner, 50);
        }
    }

    private void drawLine(int length) {
        System.out.println("-".repeat(Math.max(0, length - 1)) + "\n");
    }

    private void preGamePreparation(Scanner scanner) {
        placePlayerShips(scanner, 1);
        showMessageOnTurnEnd(scanner, 50);
        placePlayerShips(scanner, 2);
        showMessageOnTurnEnd(scanner, 50);
    }

    private void showMessageOnTurnEnd(Scanner scanner, int line) {
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        printEmptyLine(line);
    }

    private void placePlayerShips(Scanner scanner, int playerNumber) {
        Battlefield battlefield;
        if (playerNumber == 1) {
            battlefield = player1Field;
        } else {
            battlefield = player2Field;
        }
        System.out.println("Player " + playerNumber + ", place your ships on the game field\n");
        battlefield.printBattlefield(false);
        while (battlefield.canPlaceAnother()) {
            battlefield.printNextShipInfo();
            String input = scanner.nextLine();
            if (battlefield.placeShip(input)) {
                battlefield.printBattlefield(false);
            }
        }
    }

    private void printEmptyLine(int times) {
        StringBuilder builder = new StringBuilder(times);
        for (int i = 0; i < times; i++) {
            builder.append("\n");
        }
        System.out.println(builder.toString());
    }
}
