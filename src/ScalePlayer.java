/**
 * Created by Phoebe Hughes on 9/8/2016.
 */


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Optional;


public class ScalePlayer extends Application{

    @Override
    public void start(Stage primaryStage) {
        //exit option
        MenuItem exit = new MenuItem("Exit");
        exit.addEventHandler(ActionEvent.ACTION, event -> System.exit(0));

        //menubar
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        menuFile.getItems().add(exit);
        menuBar.getMenus().add(menuFile);

        //midiPlayer
        MidiPlayer mp = new MidiPlayer(1, 120);

        //buttons
        Button play = new Button();
        Button stop = new Button();

        play.setText("Play Scale");
        play.setStyle("-fx-base: #b6e7c9;");

        stop.setText("Stop Playing");
        stop.setStyle("-fx-base: #ff7f7f;");


        play.addEventHandler(ActionEvent.ACTION, event -> {
                    TextInputDialog dialog = new TextInputDialog("0");
                    dialog.setTitle("Starting Note");
                    dialog.setHeaderText("Please give me a starting note (0-115):");
                    Optional<String> result = dialog.showAndWait();

                    if (result.isPresent()){
                        int startNote = Integer.valueOf(result.get());
                        int[] notes = {startNote, startNote + 2, startNote + 4, startNote + 5,
                                startNote + 7, startNote + 9, startNote + 11, startNote + 12,
                                //scale going down
                                startNote + 12, startNote + 10, startNote + 8, startNote + 7,
                                startNote + 5, startNote + 3, startNote + 1, startNote
                        };

                        mp.stop();
                        mp.clear();
                        for (int i = 0; i< notes.length; i++)
                            mp.addNote(notes[i], 100, i, 1, 0, 0);

                        mp.play();
                    };
            }
        );


        stop.addEventHandler(ActionEvent.ACTION, event -> mp.stop());

        StackPane root = new StackPane();
        root.getChildren().addAll(play, stop, menuBar);

        play.setTranslateX(-50);
        stop.setTranslateX(50);
        StackPane.setAlignment(menuBar, Pos.TOP_LEFT);

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("Scale Player");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
