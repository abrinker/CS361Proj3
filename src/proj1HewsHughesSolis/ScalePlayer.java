/*
 * File: proj1HewsHughesSolis.ScalePlayer.java
 * Names: Phoebe Hughes, Josh Hews, Ana Sofia Solis Canales
 * Class: CS361
 * Project: 1
 * Due Date: September 15, 2016
 */

package proj1HewsHughesSolis;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Optional;

/**
 * Creates application to play major scale given a starting note.
 */
public class ScalePlayer extends Application{

    /**
     * Sets up the main GUI to play a scale.
     * Player contains a menu bar with exit option and two buttons: play and stop.
     *
     * @param primaryStage the stage to display the gui
     */
    @Override
    public void start(Stage primaryStage) {
        //exit option
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> System.exit(0));

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

        play.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog("0");
            dialog.setTitle("Starting Note");
            dialog.setHeaderText("Please give me a starting note (0-115):");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                int startNote = Integer.valueOf(result.get());
                int[] notes = {startNote, startNote + 2, startNote + 4, startNote + 5,
                        startNote + 7, startNote + 9, startNote + 11, startNote + 12};

                mp.stop();
                mp.clear();
                for (int i = 0; i< notes.length; i++) {
                    mp.addNote(notes[i], 100, i, 1, 0, 0);  //notes going up scale
                    mp.addNote(notes[i], 100, notes.length - i, 1, 0, 0); //notes going down scale
                }

                mp.play();
            };
        });
        stop.setOnAction(event -> mp.stop());

        StackPane root = new StackPane();
        root.getChildren().addAll(play, stop, menuBar);

        //position relative to center
        play.setTranslateX(-50);
        stop.setTranslateX(50);
        StackPane.setAlignment(menuBar, Pos.TOP_LEFT);

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("Scale Player");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    /**
     * Launches application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
