package puzzles.common.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Uses a generalized BFS algorithm to solve different types of puzzles. Takes a config and solves the puzzle for
 * that config type
 *
 * @author Jose Estevez
 */
public class Solver {
    private Queue<Config> queue;
    private HashMap<Config, Config> predecessors;
    private Config config;
    private int totalConfigs;
    private int uniqueConfigs;

    /**
     * Constructs the Solver, setting up the predecessor map and queue.
     *
     * @param config the config used to solve the puzzle
     */
    public Solver(Config config) {
        this.config = config;
        totalConfigs = 1;
        uniqueConfigs = 1;
        queue = new LinkedList<>();
        predecessors = new HashMap<>();
        queue.add(this.config);
        predecessors.put(this.config, null);
    }

    /**
     * The BFS algorithm. Checks if the config at the top of the queue is the solution, finding and returning
     * a path if it is. Otherwise, it calls the config's getNeighbors() function and adds unique configs
     * to the queue and predecessor map.
     *
     * @return the path if one is found, null otherwise
     */
    public ArrayList<Config> solve() {
        while (!queue.isEmpty()) {
            Config current = queue.remove();
            if (isSolution(current)) {
                ArrayList<Config> path = new ArrayList<>();
                Config predecessor = predecessors.get(current);
                path.add(current);
                if (predecessor == null) {
                    return path;
                }
                path.add(0, predecessor);
                while (predecessor != config) {
                    predecessor = predecessors.get(predecessor);
                    path.add(0, predecessor);
                }
                return path;
            }
            ArrayList<Config> neighbors = current.getNeighbors();
            for (Config config: neighbors) {
                totalConfigs++;
                if (!predecessors.containsKey(config)) {
                    queue.add(config);
                    predecessors.put(config, current);
                    uniqueConfigs++;
                }
            }
        }
        return null;
    }

    /**
     * Accesses the total amount of configs created by the solver
     *
     * @return total configs
     */
    public int getTotalConfigs() {
        return totalConfigs;
    }

    /**
     * Accesses the amount of unique configs created by the solver
     *
     * @return unique configs
     */
    public int getUniqueConfigs() {
        return uniqueConfigs;
    }

    /**
     * Checks whether the current configuration is the goal config
     *
     * @param current the current config
     * @return true if config is goal, else false
     */
    private boolean isSolution(Config current) {
        return current.isSolution();
    }
}
