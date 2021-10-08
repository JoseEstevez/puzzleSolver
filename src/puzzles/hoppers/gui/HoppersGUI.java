package puzzles.hoppers.gui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersClientData;
import puzzles.hoppers.model.HoppersModel;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This class creates and controls the UI for the Hoppers puzzle
 *
 * Jose Estevez
 */
public class HoppersGUI extends Application implements Observer<HoppersModel, HoppersClientData> {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    // for demonstration purposes
    private Image redFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"red_frog.png"));
    private Image greenFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"green_frog.png"));
    private Image lilyPad = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"lily_pad.png"));
    private Image water = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"water.png"));

    private HoppersModel model;
    private ArrayList<Button> buttons;
    private Text message;
    private BorderPane borderPane;
    private Stage stage;

    /**
     * reads a file, creates a model and passes contents of file to model, then adds this GUI as
     * an observer of the model
     */
    public void init() {
        String filename = getParameters().getRaw().get(0);
        String input = "";
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
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
            System.out.println("Loaded: " + filename);
        }
        catch (IOException e) {}
        model = new HoppersModel(input);
        model.addObserver(this);
        message = new Text("Loaded: " + filename);
    }

    /**
     * Creates all necessary JavaFX components, such as borderpane, buttons, etc.
     *
     * @param stage stage GUI is displayed on
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        GridPane gridPane = makeGrid();
        FileChooser fileChooser = new FileChooser();
        Button load = new Button("Load");
        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
                currentPath += File.separator + "data" + File.separator + "hoppers";
                fileChooser.setInitialDirectory(new File(currentPath));
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    model.load("data/hoppers/" + file.getName());
                }
            }
        });
        Button reset = new Button("Reset");
        reset.setOnAction(event -> model.reset());
        Button hint = new Button("Hint");
        hint.setOnAction(event -> model.hint());
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().addAll(load, reset, hint);
        borderPane = new BorderPane();
        borderPane.setTop(message);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(flowPane);
        Scene scene = new Scene(borderPane);
        this.stage = stage;
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Creates a grid of buttons to represent the frogs, lily pads and water
     *
     * @return GridPane containing button grid
     */
    private GridPane makeGrid() {
        GridPane gridPane = new GridPane();
        buttons = new ArrayList<>();
        for (int i = 0; i < model.getCurrentConfig().getRows(); i++) {
            for (int e = 0; e < model.getCurrentConfig().getColumns(); e++) {
                Button button = new Button();
                BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(18, 145, 227), CornerRadii.EMPTY, Insets.EMPTY);
                Background background = new Background(backgroundFill);
                button.setBackground(background);
                if (model.getCurrentConfig().get(i, e) == "G".charAt(0)) {
                    ImageView imageView = new ImageView(greenFrog);
                    button.setGraphic(imageView);
                }
                if (model.getCurrentConfig().get(i, e) == "R".charAt(0)) {
                    button.setGraphic(new ImageView(redFrog));
                }
                if (model.getCurrentConfig().get(i, e) == ".".charAt(0)) {
                    button.setGraphic(new ImageView(lilyPad));
                }
                if (model.getCurrentConfig().get(i, e) == "*".charAt(0)) {
                    button.setGraphic(new ImageView(water));
                }
                int finalI = i;
                int finalE = e;
                button.setOnAction(event -> {model.select(finalI, finalE);});
                buttons.add(button);
                gridPane.add(button, e, i);
            }
        }
        return gridPane;
    }

    /**
     * Updates message and button grid to reflect choices user makes
     *
     * @param hoppersModel HoppersModel
     * @param hoppersClientData message being updated
     */
    @Override
    public void update(HoppersModel hoppersModel, HoppersClientData hoppersClientData) {
        if (hoppersClientData != null) {
            message.setText(hoppersClientData.getMessage());
        }
        borderPane.setCenter(makeGrid());
        stage.sizeToScene();
    }

    /**
     * Launches GUI
     *
     * @param args filename
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }
}
