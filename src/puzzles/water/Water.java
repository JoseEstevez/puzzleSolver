package puzzles.water;

import puzzles.common.solver.Solver;

import puzzles.common.solver.Config;
import puzzles.common.solver.WaterConfig;

import java.util.ArrayList;

/**
 * This class represents the Water puzzle. It creates a WaterConfig and a solver, and passes the config to
 * the solver to try and find a path. It prints out the results of the attempted search regardless of the
 * outcome.
 *
 * @author Jose Estevez
 */
public class Water {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Water amount bucket1 bucket2 ..."));
        }
        System.out.print("Amount: " + args[0] + ", Buckets: [");
        for (int i = 1; i < args.length; i++) {
            System.out.print(args[i]);
            if (i + 1 < args.length) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        WaterConfig waterConfig = new WaterConfig(args);
        Solver s = new Solver(waterConfig);
        ArrayList<Config> path = s.solve();
        System.out.println("Total configs: " + s.getTotalConfigs());
        System.out.println("Unique configs: " + s.getUniqueConfigs());
        if (path == null) {
            System.out.println("No solution.");
        }
        else {
            int step = 0;
            for (Config config: path) {
                System.out.println("Step " + step + ": " + config.necessary());
                step++;
            }
        }
    }
}
