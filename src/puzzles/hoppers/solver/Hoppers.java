package puzzles.hoppers.solver;

import puzzles.common.solver.Config;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents the Hoppers puzzle. Reads a file and uses it to create a Hopper config and passes
 * it to a solver, then uses the solver to find the solution path for the config. Prints results of search
 *
 * Jose Estevez
 */
public class Hoppers {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Hoppers filename");
        }
        String input = "";
        try(BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            line = reader.readLine();
            String[] split1 = line.split(" ");
            for (int e = 0; e < split1.length; e++) {
                input += split1[e] + "/";
            }
            while ((line = reader.readLine()) != (null)) {
                String[] split = line.split(" ");
                for (int i = 0; i < split.length; i++) {
                    input += split[i];
                }
            }
        }
        catch (IOException e) {}
        HoppersConfig hopper = new HoppersConfig(input);
        Solver solver = new Solver(hopper);
        ArrayList<Config> path = solver.solve();
        System.out.println("Total configs: " + solver.getTotalConfigs());
        System.out.println("Unique configs: " + solver.getUniqueConfigs());
        if (path == null) {
            System.out.println("No solution.");
        }
        else {
            int step = 0;
            for (Config config : path) {
                HoppersConfig hoppersConfig = (HoppersConfig) config;
                System.out.println("Step " + step + ": " + hoppersConfig.necessary());
                step++;
            }
        }
    }
}
