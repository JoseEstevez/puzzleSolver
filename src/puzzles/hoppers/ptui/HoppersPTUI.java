package puzzles.hoppers.ptui;

import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersClientData;
import puzzles.hoppers.model.HoppersModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class creates and controls the text-based UI for the Hoppers puzzle
 *
 * Jose Estevez
 */
public class HoppersPTUI implements Observer<HoppersModel, HoppersClientData> {
    private HoppersModel model;

    /**
     * Constructs ptui and model
     *
     * @param input contents of Hoppers file
     */
    public HoppersPTUI(String input) {
        model = new HoppersModel(input);
        initializeView();
    }

    /**
     * Adds this ptui as observer of HoppersModel
     */
    public void initializeView() {
        model.addObserver(this);
        update(model, null);
    }

    /**
     * Repeatedly asks user for input, uses input to execute commands for Hopper puzzle
     */
    private void run() {
        Scanner in = new Scanner(System.in);
        displayHelp();
        for ( ; ; ) {
            System.out.println("Game command: ");
            String line = in.nextLine();
            String[] words = line.split("\\s+");
            if (words.length > 0) {
                if (words[0].startsWith("h")) {
                    model.hint();
                }
                else if (words[0].startsWith("l")) {
                    model.load("data/hoppers/" + words[1]);
                }
                else if (words[0].startsWith("s")) {
                    model.select(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
                }
                else if (words[0].startsWith("q")) {
                    break;
                }
                else if (words[0].startsWith("r")) {
                    model.reset();
                }
                else {
                    displayHelp();
                }
            }
        }
    }

    /**
     * Prints hoppers board
     */
    private void displayBoard() {
        System.out.println(model.getCurrentConfig().necessary());
    }

    /**
     * Updates HoppersPTUI based off HoppersModel whenever an action is taken
     *
     * @param model HoppersModel
     * @param data optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(HoppersModel model, HoppersClientData data) {
        if (data != null) {
            System.out.println(data.getMessage());
        }
        displayBoard();
    }

    /**
     * Prints command options for user
     */
    public void displayHelp() {
        System.out.println("h(int)                     -- hint next move");
        System.out.println("l(oad) filename            -- load new puzzle file");
        System.out.println("s(elect) r c               -- select cell at r, c");
        System.out.println("q(uit)                     -- quit the game");
        System.out.println("r(eset)                    -- reset the current game");
    }

    /**
     * Reads a file and passes contents of file to ptui constructor, then runs ptui
     *
     * @param args file name
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
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
            System.out.println("Loaded: " + args[0]);
            HoppersPTUI ptui = new HoppersPTUI(input);
            ptui.run();
        }
        catch (IOException e) {}
    }
}
