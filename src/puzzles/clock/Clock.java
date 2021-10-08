package puzzles.clock;

import puzzles.common.solver.ClockConfig;
import puzzles.common.solver.Config;
import puzzles.common.solver.ClockConfig;
import puzzles.common.solver.Config;
import puzzles.common.solver.Solver;

import java.util.ArrayList;

/**
 * This class represents the Clock puzzle. It creates a ClockConfig and a solver, and passes the config to
 * the solver to try and find a path. It prints out the results of the attempted search regardless of the
 * outcome.
 *
 * @author Jose Estevez
 */
public class Clock {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Clock hours start stop");
        }
        else {
            System.out.println("Hours: " + args[0] + ", Start: " + args[1] + " End: " + args[2]);
            ClockConfig clock = new ClockConfig(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[1]));
            Solver solver = new Solver(clock);
            ArrayList<Config> path = solver.solve();
            System.out.println("Total configs: " + solver.getTotalConfigs());
            System.out.println("Unique configs: " + solver.getUniqueConfigs());
            if (path == null) {
                System.out.println("No solution.");
            }
            else {
                int step = 0;
                for (Config config : path) {
                    ClockConfig clockConfig = (ClockConfig) config;
                    System.out.println("Step " + step + ": " + clockConfig.necessary());
                    step++;
                }
            }
        }
    }
}
