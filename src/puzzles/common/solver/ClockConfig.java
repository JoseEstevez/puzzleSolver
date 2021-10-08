package puzzles.common.solver;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * This class represents a single ClockConfig. It implements the Config interface and implements all required
 * methods. It holds the hours on the clock, the start and end time, the current location of the hour hand,
 * and the list of each number on the clock and its neighbors. It is used for the Clock puzzle.
 *
 * @author Jose Estevez
 */
public class ClockConfig implements Config {
    private int hours;
    private int start;
    private int end;
    private int current;
    private LinkedHashMap<Integer, ArrayList<Integer>> adjList;

    /**
     * Constructs a ClockConfig, setting up the correct hours, start, end, and hour hand position, and creates
     * the adjList.
     *
     * @param hours hours on the clock
     * @param start where the hour hand starts
     * @param end where the hour hand is supposed to end up
     * @param current where the hour hand currently is
     */
    public ClockConfig(int hours, int start, int end, int current) {
        this.hours = hours;
        this.start = start;
        this.end = end;
        this.current = current;
        adjList = new LinkedHashMap<>();
        for (int i = 1; i <= hours; i++) {
            adjList.put(i, new ArrayList<>());
            if (i == 1) {
                adjList.get(i).add(hours);
                adjList.get(i).add(i+1);
                continue;
            }
            if (i == hours) {
                adjList.get(i).add(i-1);
                adjList.get(i).add(1);
                continue;
            }
            adjList.get(i).add(i-1);
            adjList.get(i).add(i+1);
        }
    }

    /**
     * Creates a configuration with the hour hand one hour forward, and one with the hour hand one
     * hour backward.
     *
     * @return neighbor configs
     */
    @Override
    public ArrayList<Config> getNeighbors() {
        ArrayList<Config> neighbors = new ArrayList<>();
        ClockConfig less = new ClockConfig(hours, start, end, adjList.get(current).get(0));
        ClockConfig more = new ClockConfig(hours, start, end, adjList.get(current).get(1));
        neighbors.add(less);
        neighbors.add(more);
        return neighbors;
    }

    /**
     * Checks whether the current configuration is the same as another configuration
     *
     * @param other the other configuration
     * @return true if same config, else false
     */
    @Override
    public boolean equals(Object other) {
        if (current == ((ClockConfig)other).current) {
            return true;
        }
        return false;
    }

    /**
     * Creates an integer representing the current config
     *
     * @return appropriate int
     */
    @Override
    public int hashCode() {
        return Objects.hash(hours, start, end, current, adjList);
    }

    /**
     * Checks whether the current configuration is the goal configuration
     *
     * @return true if is goal, else false
     */
    @Override
    public boolean isSolution() {
        return current == end;
    }

    /**
     * Returns the current hour hand position, so Clock.java can use it when printing out the steps
     * of the BFS.
     *
     * @return current
     */
    @Override
    public Object necessary() {
        return current;
    }
}

