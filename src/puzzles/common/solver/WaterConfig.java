package puzzles.common.solver;

import puzzles.water.Bucket;
import puzzles.water.Water;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents a single WaterConfig. It implements the Config interface and implements all required
 * methods. It holds the goal amount and an array of Buckets. It is used for the Water puzzle.
 *
 * @author Jose Estevez
 */
public class WaterConfig implements Config{
    private int goal;
    private Bucket[] buckets;

    /**
     * Constructs a WaterConfig, setting up the goal and array of Buckets from a String array of arguments.
     *
     * @param args the String array of arguments
     */
    public WaterConfig(String[] args) {
        goal = Integer.parseInt(args[0]);
        buckets = new Bucket[args.length-1];
        for (int i = 1; i < args.length; i++) {
            buckets[i-1] = new Bucket(Integer.parseInt(args[i]));
        }
    }

    /**
     * Copy Constructor used to create copies of WaterConfigs without changes to the original affecting the copy
     *
     * @param other WaterConfig being copied
     */
    public WaterConfig(WaterConfig other) {
        this.goal = other.goal;
        this.buckets = new Bucket[other.buckets.length];
        for (int i = 0; i < other.buckets.length; i++) {
            this.buckets[i] = new Bucket(other.buckets[i].getCapacity());
            this.buckets[i].fill(other.buckets[i].getCurrent());
        }
    }

    /**
     * For each bucket in the buckets array, this function creates a new WaterConfig where this bucket is filled,
     * one where its dumped, and some where its poured into each other bucket in the array, one config for each other
     * bucket.
     *
     * @return successors created by methodology described above
     */
    @Override
    public ArrayList<Config> getNeighbors() {
        ArrayList<Config> configs = new ArrayList<>();
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i].space() > 0) {
                WaterConfig fill = new WaterConfig(this);
                fill.buckets[i].fill();
                configs.add(fill);
            }
            if (buckets[i].getCurrent() > 0) {
                WaterConfig dump = new WaterConfig(this);
                dump.buckets[i].dump();
                configs.add(dump);
                for (int e = 0; e < buckets.length; e++) {
                    if (i != e) {
                        WaterConfig pour = new WaterConfig(this);
                        pour.buckets[i].pour(pour.buckets[e]);
                        configs.add(pour);
                    }
                }
            }
        }
        return configs;
    }

    /**
     * Checks whether the current configuration is the goal configuration
     *
     * @return true if is goal, else false
     */
    @Override
    public boolean isSolution() {
        for (Bucket b: buckets) {
            if (b.getCurrent() == goal) {
                return true;
            }
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
        int[] currents = new int[buckets.length];
        for (int i = 0; i < buckets.length; i++) {
            currents[i] = buckets[i].getCurrent();
        }
        return Arrays.hashCode(currents);
    }

    /**
     * Checks whether the current configuration is the same as another configuration
     *
     * @param obj the other configuration
     * @return true if same config, else false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WaterConfig) {
            for (int i = 0; i < buckets.length; i++) {
                if (!this.buckets[i].equals(((WaterConfig)obj).buckets[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns a string representing the current amount of water in each of this configs bucket,
     * so Water.java can use it when printing out the steps
     * of the BFS.
     *
     * @return string
     */
    @Override
    public Object necessary() {
        String step = "[";
        for (int i = 0; i < buckets.length; i++) {
            step += buckets[i].getCurrent();
            if (i + 1 < buckets.length) {
                step += ", ";
            }
        }
        step += "]";
        return step;
    }
}
