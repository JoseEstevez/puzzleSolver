package puzzles.common.solver;

import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * An interface which represents configurations used by Solver.
 * Each puzzle, such as Clock and Water, uses this interface
 *
 * @author Jose Estevez
 */
public interface Config {
    /**
     * Creates an ArrayList of configurations representing the possible successors of the current
     * configuration
     *
     * @return successors
     */
    public ArrayList<Config> getNeighbors();

    /**
     * Checks whether the current configuration is the goal configuration
     *
     * @return true if is goal, else false
     */
    public boolean isSolution();

    /**
     * Returns whatever information from the config which the original puzzle class needs to print out the
     * puzzle's steps. Essentially a toString method
     *
     * @return necessary information
     */
    public Object necessary();

    /**
     * Checks whether the current configuration is the same as another configuration
     *
     * @param other the other configuration
     * @return true if same config, else false
     */
    @Override
    public boolean equals(Object other);

    /**
     * Creates an integer representing the current config
     *
     * @return appropriate int
     */
    @Override
    public int hashCode();
}
