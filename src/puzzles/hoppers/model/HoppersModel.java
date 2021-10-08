package puzzles.hoppers.model;

import puzzles.common.Observer;
import puzzles.common.solver.Config;
import puzzles.common.solver.Solver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Creates and controls the model for the HopperPuzzle
 *
 * Jose Estevez
 */
public class HoppersModel {
    /** the collection of observers of this model */
    private final List<Observer<HoppersModel, HoppersClientData>> observers = new LinkedList<>();


    /** the current configuration */
    private HoppersConfig currentConfig;
    private int row = Integer.MIN_VALUE;
    private int column = Integer.MIN_VALUE;
    private HoppersConfig origConfig;

    /**
     * Constructs HopperModel
     *
     * @param input contents of Hoppers file
     */
    public HoppersModel(String input) {
        origConfig = new HoppersConfig(input);
        currentConfig = new HoppersConfig(origConfig);
    }

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<HoppersModel, HoppersClientData> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(HoppersClientData data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }

    /**
     * Does the next step of the puzzle if the puzzle has a solution
     */
    public void hint() {
        if (currentConfig.isSolution()) {
            alertObservers(new HoppersClientData("Already solved!"));
        }
        else {
            Solver solver = new Solver(currentConfig);
            ArrayList<Config> path = solver.solve();
            if (path == null) {
                alertObservers(new HoppersClientData("There is no solution, reset the game"));
            }
            else {
                currentConfig = (HoppersConfig) path.get(path.indexOf(currentConfig) + 1);
                alertObservers(new HoppersClientData("Next step!"));
            }
        }
    }

    /**
     * Loads a hoppers puzzle specified by the user
     *
     * @param fileName filename specified by user
     */
    public void load(String fileName) {
        String input = "";
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            line = reader.readLine();
            String[] split1 = line.split(" ");
            for (String s : split1) {
                input += s + "/";
            }
            while ((line = reader.readLine()) != (null)) {
                String[] split = line.split(" ");
                for (String s : split) {
                    input += s;
                }
            }
            origConfig = new HoppersConfig(input);
            currentConfig = new HoppersConfig(origConfig);
            alertObservers(new HoppersClientData("Loaded: " + fileName));
        }
        catch (IOException e) {
            alertObservers(new HoppersClientData("Failed to load: " + fileName));
        }
    }

    /**
     * Resets the current puzzle to its initial state
     */
    public void reset() {
        currentConfig = new HoppersConfig(origConfig);
        alertObservers(new HoppersClientData("Puzzle reset!"));
    }

    /**
     * Selects a coordinate of a frog if coordinates are valid, makes the frog move if a frog has been
     * selected already and move is valid
     *
     * @param row row coordinate
     * @param column column coordinate
     */
    public void select(int row, int column) {
        if (row < 0 || column < 0 || row  > currentConfig.getRows()-1 || column > currentConfig.getColumns()-1) {
            alertObservers(new HoppersClientData("Out of bounds, choose a different row and/or column"));
        }
        else if (this.row == Integer.MIN_VALUE) {
            if (currentConfig.get(row, column) == "G".charAt(0) || currentConfig.get(row, column) == "R".charAt(0)) {
                this.row = row;
                this.column = column;
                alertObservers(new HoppersClientData("Selected(" + row + ", " + column + ")"));
            }
            else {
                alertObservers(new HoppersClientData("Invalid Selection"));
            }
        }
        else {
            HoppersConfig hoppersConfig = currentConfig.jump(this.row, this.column, row, column, currentConfig.get(this.row, this.column));
            if (hoppersConfig != null) {
                currentConfig = hoppersConfig;
                alertObservers(new HoppersClientData("Jumped from (" + this.row + ", " + this.column + ") to (" + row + ", " + column + ")"));
            }
            else {
                alertObservers(new HoppersClientData("Invalid Move"));
            }
            this.row = Integer.MIN_VALUE;
            this.column = Integer.MIN_VALUE;
        }
    }

    /**
     * returns current hopper config
     *
     * @return current hopper config
     */
    public HoppersConfig getCurrentConfig() {
        return currentConfig;
    }
}
