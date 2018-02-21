package lt.laurynasmalinauskas.vcs.services;

import lt.laurynasmalinauskas.vcs.beans.BoatTable;
import lt.laurynasmalinauskas.vcs.beans.EventTypeBean;
import lt.laurynasmalinauskas.vcs.beans.TableStatus;

import java.util.Scanner;

public class LocalServices {


    static private Scanner scanner = new Scanner(System.in);
    public static final int[][] shipAddingRule = {{4}, {3, 3}, {2, 2, 2}, {1, 1, 1, 1}};


    public String createPlayerBoatTable() {
        String empty = "";
        StringBuilder playerBoatString = new StringBuilder(empty);
        BoatTable[][] playerBoatTable = initEmptyBoatTable();
        for (int i = 0; i < shipAddingRule.length; i++) {
            for (int y = 0; y < shipAddingRule[i].length; y++) {
                printTable(playerBoatTable);
                print("Add " + shipAddingRule[i][y] + " places boat\n");
                printShipDirectionOptions();
                String DirectionOption = scanner.next();
                if (DirectionOption.equals("1")) {
                    try {
                        String coordinate = enterACoordinate();
                        int numberCoordinate = Integer.valueOf(coordinate.charAt(1)) - 48;
                        char letterCoordinate = coordinate.charAt(0);
                        playerBoatTable = addShipVertically(playerBoatTable, shipAddingRule[i][y], numberCoordinate, letterCoordinate);
                        if (playerBoatString.toString().equals("")) {
                            playerBoatString.append(coordinate).append("-").append(letterCoordinate).append(numberCoordinate + shipAddingRule[i][y] - 1);//Padaryti kad graziai atvaizduojami butu
                        } else {
                            playerBoatString.append("!").append(coordinate).append("-").append(letterCoordinate).append(numberCoordinate + shipAddingRule[i][y] - 1);
                        }
                    } catch (Exception e) {
                        print(e.getMessage() + "\n");
                        //e.printStackTrace();
                        y--;
                    }


                } else {
                    try {
                        String coordinate = enterACoordinate();
                        int numberCoordinate = Integer.valueOf(coordinate.charAt(1) - 48);
                        char letterCoordinate = coordinate.charAt(0);
                        playerBoatTable = addShipHorizontally(playerBoatTable, shipAddingRule[i][y], numberCoordinate, letterCoordinate);
                        if (playerBoatString.equals("")) {
                            playerBoatString.append(coordinate).append("-").append(makeOutputChar(letterCoordinate, shipAddingRule[i][y])).append(numberCoordinate);
                        } else {
                            playerBoatString.append("!").append(coordinate).append("-").append(makeOutputChar(letterCoordinate, shipAddingRule[i][y])).append(numberCoordinate);
                        }
                    } catch (Exception e) {
                        print(e.getMessage() + "\n");
                        //e.printStackTrace();
                        y--;
                    }


                }
            }
        }
        printTable(playerBoatTable);
        return playerBoatString.toString();
    }


    public static BoatTable[][] initEmptyBoatTable() {
        BoatTable[][] emptyBoatTable = new BoatTable[BoatTable.lineOptions.length][BoatTable.columnOptions.length()];
        for (int i = 0; i < emptyBoatTable.length; i++) {
            for (int j = 0; j < emptyBoatTable[i].length; j++) {
                emptyBoatTable[i][j] = new BoatTable(TableStatus.UNCHECKED);
            }
        }
        return emptyBoatTable;
    }

    private void printTable(BoatTable[][] tableForPrint) {
        print("  | ");
        for (int x = 0; x < BoatTable.columnOptions.length(); x++) {
            print("" + BoatTable.columnOptions.charAt(x) + " | ");
        }
        print("\n");
        print("-------------------------------------------\n");
        for (int i = 0; i < tableForPrint.length; i++) {
            print(BoatTable.lineOptions[i] + " | ");
            for (int j = 0; j < tableForPrint[i].length; j++) {
                print("" + tableForPrint[i][j] + " | ");
            }
            print("\n");
            print("-------------------------------------------\n");
        }


    }

    private void print(String temp) {
        System.out.print(temp);
    }

    private String enterACoordinate() {
        print("Iveskite pradzios koordinate");
        String temp = scanner.next();
        while (checkIfCorrectCoordinate(temp) == null) {
            print("Iveskite pradzios koordinate");
            temp = scanner.next();
        }
        return temp;


    }

    private String enterBCoordinate() {
        print("Iveskite pabaigos koordinate");
        String temp = scanner.next();
        while (checkIfCorrectCoordinate(temp) == null) {
            print("Iveskite pabaigos koordinate");
            temp = scanner.next();
        }
        return temp;
    }

    private String checkIfCorrectCoordinate(String check) {
        if (check.length() != 2) {
            return null;
        }
        if (BoatTable.columnOptions.indexOf(check.charAt(0)) < 0) {
            return null;
        }
        for (int i = 0; i < BoatTable.lineOptions.length; i++) {
            if (BoatTable.lineOptions[i] == Character.getNumericValue(check.charAt(1))) {
                return check;
            }
        }
        return null;

    }

    private void printShipDirectionOptions() {
        print("-----> kryptimi spauskite betka + enter\n");
        print("| kryptimi spauskite 1 + Enter\n" +
                "|\n" +
                "|\n" +
                "V\n");
    }

    private BoatTable[][] addShipVertically(BoatTable[][] tableForAdd, int shipSize, int x, char y) {
        BoatTable[][] temp = tableForAdd;
        for (int i = 0; i < shipSize; i++) {
            if (temp[x + i][BoatTable.columnOptions.indexOf(y)].getStatus() == (TableStatus.UNCHECKED)) {
//                temp[x+i][BoatTable.columnOptions.indexOf(y)].setStatus(TableStatus.YOURBOAT);
            } else {
                throw new RuntimeException("Norima pastatyti laiva ant uzimtos teritorijos");
            }
        }
        for (int i = 0; i < shipSize; i++) {
            if (temp[x + i][BoatTable.columnOptions.indexOf(y)].getStatus() == (TableStatus.UNCHECKED)) {
                temp[x + i][BoatTable.columnOptions.indexOf(y)].setStatus(TableStatus.YOURBOAT);
            } else {
//                throw new RuntimeException("Norima pastatyti laiva ant uzimtos teritorijos");
            }
        }
        return temp;
    }

    private BoatTable[][] addShipHorizontally(BoatTable[][] tableForAdd, int shipSize, int x, char y) {
        BoatTable[][] temp = tableForAdd;
        for (int i = 0; i < shipSize; i++) {
            if (temp[x][BoatTable.columnOptions.indexOf(y) + i].getStatus() == (TableStatus.UNCHECKED)) {
//                temp[x][BoatTable.columnOptions.indexOf(y)+i].setStatus(TableStatus.YOURBOAT);
            } else {
                throw new RuntimeException("Norima pastatyti laiva ant uzimtos teritorijos");
            }
        }
        for (int i = 0; i < shipSize; i++) {
            if (temp[x][BoatTable.columnOptions.indexOf(y) + i].getStatus() == (TableStatus.UNCHECKED)) {
                temp[x][BoatTable.columnOptions.indexOf(y) + i].setStatus(TableStatus.YOURBOAT);
            } else {
//                throw new RuntimeException("Norima pastatyti laiva ant uzimtos teritorijos");
            }
        }
        return temp;
    }

    private char makeOutputChar(char currentLetter, int letterPlusList) {
        return BoatTable.columnOptions.charAt(BoatTable.columnOptions.indexOf(currentLetter) + letterPlusList - 1);
    }

    public void tryEndGame() {
        System.out.println("Tired of waiting? Want to end game? Press y");
        String temp = scanner.next();
        if ("y".equals(temp)) {
            System.exit(0);
        }
    }

    public String getYourShotCoordinate() {
        try {
            String resultCoordinate = "";
            while (resultCoordinate.equals("")) {
                print("Iveskite suvio koordinate\n");
                String coordinate = checkIfCorrectCoordinate(scanner.next());
                if (!coordinate.equals("")) {
                    resultCoordinate = coordinate;
                }
            }
            return resultCoordinate;
        } catch (Exception e) {
            System.out.println("NullPointError");
            e.printStackTrace();
        }

        return "";
    }

    public void printGameTables(EventTypeBean[] gameInfo, String playerId, String myShipsMapString) {
        BoatTable[][] playerTable = initEmptyBoatTable();
        BoatTable[][] oponentTable = stringToBoatTable(myShipsMapString);
        for (EventTypeBean temporary : gameInfo) {
            if (playerId.equals(temporary.getUserId())) {
                if (temporary.getShotCorrect()) {
                    playerTable[temporary.getRow()][BoatTable.columnOptions.indexOf(temporary.getColumn())].setStatus(TableStatus.CORRECTSHOT);
                } else {
                    playerTable[temporary.getRow()][BoatTable.columnOptions.indexOf(temporary.getColumn())].setStatus(TableStatus.FALSESHOT);
                }
            } else {
                if (temporary.getShotCorrect()) {
                    oponentTable[temporary.getRow()][BoatTable.columnOptions.indexOf(temporary.getColumn())].setStatus(TableStatus.CORRECTSHOT);
                } else {
                    oponentTable[temporary.getRow()][BoatTable.columnOptions.indexOf(temporary.getColumn())].setStatus(TableStatus.FALSESHOT);
                }
            }
        }
        print("My shooting table\n");
        printTable(playerTable);
        print("Oponent shooting table\n");
        printTable(oponentTable);

    }

    public static BoatTable[][] stringToBoatTable(String table) {
        BoatTable[][] returnTable = initEmptyBoatTable();
        String[] coordinatesArrayString = table.split("!");
        for (String temp : coordinatesArrayString) {
            String[] twoCoordinatesArray = temp.split("-");
            int[] coordinateStartArray = {BoatTable.columnOptions.indexOf(twoCoordinatesArray[0].charAt(0)),
                    BoatTable.lineOptionsString.indexOf(twoCoordinatesArray[0].charAt(1))};
            int[] coordinateEndArray = {BoatTable.columnOptions.indexOf(twoCoordinatesArray[1].charAt(0)),
                    BoatTable.lineOptionsString.indexOf(twoCoordinatesArray[1].charAt(1))};
            if (coordinateEndArray[0] > coordinateStartArray[0]) {
                for (int i = 0; i <= coordinateEndArray[0] - coordinateStartArray[0]; i++) {
                    returnTable[coordinateStartArray[1]][coordinateStartArray[0] + i].setStatus(TableStatus.YOURBOAT);
                }

            } else {
                if (coordinateEndArray[1] > coordinateStartArray[1]) {
                    for (int i = 0; i <= coordinateEndArray[1] - coordinateStartArray[1]; i++) {
                        returnTable[coordinateStartArray[1] + i][coordinateStartArray[0]].setStatus(TableStatus.YOURBOAT);
                    }

                } else {
                    returnTable[coordinateStartArray[1]][coordinateStartArray[0]].setStatus(TableStatus.YOURBOAT);
                }

            }

        }
        return returnTable;

    }


}
