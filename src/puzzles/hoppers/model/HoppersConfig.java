package puzzles.hoppers.model;

import puzzles.common.solver.Config;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a hopper config. Implements Config interface and all required methods. Holds a board of chars, the number of
 * rows and columns, and the number of green frogs remaining.
 *
 * Jose Estevez
 */
public class HoppersConfig implements Config {
    private char[][] board;
    private int rows;
    private int columns;
    private int numG = 0;

    /**
     * Constructs a hopper config, using the contents of the file to create the grid of chars, num of rows, columns, and
     * green frogs
     *
     * @param input contents of file
     */
    public HoppersConfig(String input) {
        int iter = 0;
        String row = "";
        while (input.charAt(iter) != "/".charAt(0)) {
            row += input.charAt(iter);
            iter++;
        }
        rows = Integer.parseInt(row);
        String column = "";
        iter++;
        while (input.charAt(iter) != "/".charAt(0)) {
            column += input.charAt(iter);
            iter++;
        }
        columns = Integer.parseInt(column);
        iter++;
        board = new char[rows][columns];
        //int index = 2;
        for (int i = 0; i < rows; i++) {
            for (int e = 0; e < columns; e++) {
                board[i][e] = input.charAt(iter);
                if (input.charAt(iter) == "G".charAt(0)) {
                    numG++;
                }
                iter++;
            }
        }
    }

    /**
     * Copy constructor; copies a given config so original config isn't altered
     *
     * @param config config being copied
     */
    public HoppersConfig(HoppersConfig config) {
        rows = config.rows;
        columns = config.columns;
        board = new char[rows][columns];
        numG = 0;
        for (int i = 0; i < config.numG; i++) {
            numG++;
        }
        for (int i = 0; i < rows; i++) {
            System.arraycopy(config.board[i], 0, board[i], 0, columns);
        }
    }

    /**
     * Given a frogs coordinates, checks every possible jump that frog could make, and creates a HoppersConfig
     * for said jump.
     *
     * @param row row of frog
     * @param column column of frog
     * @param frog type of frog (green or red)
     * @return ArrayList of HopperConfigs for each jump
     */
    private ArrayList<Config> frogJump(int row, int column, char frog) {
        ArrayList<Config> possibleJumps = new ArrayList<>();
        if (row % 2 == 0) { //Frog at even coordinates
            if (column - 4 >= 0 && board[row][column-4] == ".".charAt(0)) { //jumping to [row][column-4]
                if (board[row][column-2] == "G".charAt(0)) {
                    HoppersConfig config = new HoppersConfig(this);
                    config.board[row][column] = ".".charAt(0);
                    config.board[row][column-2] = ".".charAt(0);
                    config.board[row][column-4] = frog;
                    config.numG--;
                    possibleJumps.add(config);
                }
            }
            if (row - 4 >= 0 && board[row-4][column] == ".".charAt(0)) { //Jumping to [row-4][column]
                if (board[row-2][column] == "G".charAt(0)) {
                    HoppersConfig config = new HoppersConfig(this);
                    config.board[row][column] = ".".charAt(0);
                    config.board[row-2][column] = ".".charAt(0);
                    config.board[row-4][column] = frog;
                    config.numG--;
                    possibleJumps.add(config);
                }
            }
            if (row + 4 <= rows-1 && board[row+4][column] == ".".charAt(0)) { //Jumping to [row+4][column]
                if (board[row+2][column] == "G".charAt(0)) {
                    HoppersConfig config = new HoppersConfig(this);
                    config.board[row][column] = ".".charAt(0);
                    config.board[row+2][column] = ".".charAt(0);
                    config.board[row+4][column] = frog;
                    config.numG--;
                    possibleJumps.add(config);
                }
            }
            if (column + 4 <= columns-1 && board[row][column+4] == ".".charAt(0)) { //jumping to [row][column+4]
                if (board[row][column+2] == "G".charAt(0)) {
                    HoppersConfig config = new HoppersConfig(this);
                    config.board[row][column] = ".".charAt(0);
                    config.board[row][column+2] = ".".charAt(0);
                    config.board[row][column+4] = frog;
                    config.numG--;
                    possibleJumps.add(config);
                }
            }
        }
        if (column - 2 >= 0) { //jumping to [row-2][column-2] and [row+2][column-2]
            if (row - 2 >= 0 && board[row-2][column-2] == ".".charAt(0)) { //[row-2][column-2]
                if (board[row-1][column-1] == "G".charAt(0)) {
                    HoppersConfig config = new HoppersConfig(this);
                    config.board[row][column] = ".".charAt(0);
                    config.board[row-1][column-1] = ".".charAt(0);
                    config.board[row-2][column-2] = frog;
                    config.numG--;
                    possibleJumps.add(config);
                }
            }
            if (row + 2 <= rows-1 && board[row+2][column-2] == ".".charAt(0)) { //[row+2][column-2]
                if (board[row+1][column-1] == "G".charAt(0)) {
                    HoppersConfig config = new HoppersConfig(this);
                    config.board[row][column] = ".".charAt(0);
                    config.board[row+1][column-1] = ".".charAt(0);
                    config.board[row+2][column-2] = frog;
                    config.numG--;
                    possibleJumps.add(config);
                }
            }
        }
        if (column + 2 <= columns-1) { //jumping to [row-2][column+2] and [row+2][column+2]
            if (row - 2 >= 0 && board[row-2][column+2] == ".".charAt(0)) { //[row-2][column+2]
                if (board[row-1][column+1] == "G".charAt(0)) {
                    HoppersConfig config = new HoppersConfig(this);
                    config.board[row][column] = ".".charAt(0);
                    config.board[row-1][column+1] = ".".charAt(0);
                    config.board[row-2][column+2] = frog;
                    config.numG--;
                    possibleJumps.add(config);
                }
            }
            if (row + 2 <= rows-1 && board[row+2][column+2] == ".".charAt(0)) { //[row+2][column+2]
                if (board[row+1][column+1] == "G".charAt(0)) {
                    HoppersConfig config = new HoppersConfig(this);
                    config.board[row][column] = ".".charAt(0);
                    config.board[row+1][column+1] = ".".charAt(0);
                    config.board[row+2][column+2] = frog;
                    config.numG--;
                    possibleJumps.add(config);
                }
            }
        }
        return possibleJumps;
    }

    /**
     * Creates an ArrayList of HopperConfigs, with each frog jumping to all valid jump locations.
     *
     * @return ArrayList of HopperConfigs
     */
    @Override
    public ArrayList<Config> getNeighbors() {
        ArrayList<Config> neighbors = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int e = 0; e < columns; e++) {
                if (board[i][e] == "G".charAt(0)) {
                    HoppersConfig config = new HoppersConfig(this);
                    ArrayList<Config> jumps = config.frogJump(i,e, "G".charAt(0));
                    neighbors.addAll(jumps);
                }
                if (board[i][e] == "R".charAt(0)) {
                    HoppersConfig config = new HoppersConfig(this);
                    ArrayList<Config> jumps = config.frogJump(i,e, "R".charAt(0));
                    neighbors.addAll(jumps);
                }
            }
        }
        return neighbors;
    }

    /**
     * Checks whether current config is the goal config
     *
     * @return true if goal config, false if not
     */
    @Override
    public boolean isSolution() {
        if (numG > 0) {
            return false;
        }
        return true;
    }

    /**
     * Creates an integer representing the current config
     *
     * @return appropriate int
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    /**
     * Checks whether the current configuration is the same as another configuration
     *
     * @param obj the other configuration
     * @return true if same config, else false
     */
    @Override
    public boolean equals(Object obj) {
        if (Arrays.deepEquals(board, ((HoppersConfig)obj).board)) {
            return true;
        }
        return false;
    }

    /**
     * Returns a string representing the grid of chars of the Hopper puzzle
     *
     * @return string
     */
    @Override
    public Object necessary() {
        String result = "" + "\n" + "  ";
        for (int f = 0; f < columns; f++) {
            result += " " + f;
        }
        result += "\n" + " ";
        result += " " + ("-".repeat(columns*2) + "\n");
        for (int i = 0; i < rows; i++) {
            result += i + "|";
            for (int e = 0; e < columns; e++) {
                result += " " + board[i][e];
            }
            result += "\n";
        }
        return result;
    }

    /**
     * Returns the char at the given row and column
     *
     * @param row row of char
     * @param column column of char
     * @return char
     */
    public char get(int row, int column) {
        return board[row][column];
    }

    /**
     * Checks whether a jump from a set of coordinates to another set of coordinates is valid
     *
     * @param row1 row of first set
     * @param col1 column of first set
     * @param row2 row of second set
     * @param col2 column of second set
     * @param frog type of frog jumping (red or green)
     * @return HopperConfig if jump is valid, null otherwise
     */
    public HoppersConfig jump(int row1, int col1, int row2, int col2, char frog) {
        ArrayList<Config> validJumps = frogJump(row1, col1, frog);
        for (Config config: validJumps) {
            if (((HoppersConfig)config).board[row2][col2] == frog) {
                return (HoppersConfig)config;
            }
        }
        return null;
    }

    /**
     * Returns number of rows in char grid
     *
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns number of columns in char grid
     *
     * @return number of columns
     */
    public int getColumns() {
        return columns;
    }
}
