/*
 * File: proj1HewsHughesSolis.ScalePlayer.java
 * Names: Phoebe Hughes, Josh Hews, Ana Sofia Solis Canales
 * Class: CS361
 * Project: 1
 * Due Date: September 15, 2016
 */

package proj2HewsHughesSolis;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Optional;

/**
 * Creates application to play major scale given a starting note.
 */
public class Main extends Application{

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
            TextInputDialog dialog = new TextInputDialog("55");
            dialog.setTitle("Starting Note");
            dialog.setHeaderText("Please give me a starting note (0-115):");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                int startNote = Integer.valueOf(result.get());

                int[] notes = constructMajorScale(startNote);

                mp.stop();
                mp.clear();
                for (int i = 0; i< notes.length; i++) {
                    mp.addNote(notes[i], 100, i, 1, 0, 0);
                }

                mp.play();
            }
        });
        stop.setOnAction(event -> mp.stop());

        // Create container for the buttons
        HBox buttons = new HBox();
        buttons.setPadding(new Insets(15, 12, 15, 12));
        buttons.setSpacing(10);

        // Add the buttons to container and center the container
        buttons.getChildren().addAll(play, stop);
        buttons.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        root.getChildren().addAll(buttons, menuBar);

        StackPane.setAlignment(menuBar, Pos.TOP_LEFT);

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("Scale Player");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    /**
     * Construct a major scale from the starting pitch
     *
     * @param startNote the note to begin the scale from.
     *
     * @return The sequence of note defining the scale, going up and down.
     * */
    private int[] constructMajorScale(int startNote){
        // Construct the sequence of notes defining the major scale.
        int[] scaleUp = {startNote, startNote + 2, startNote + 4, startNote + 5,
                         startNote + 7, startNote + 9, startNote + 11, startNote + 12};

        // Create the sequence going up and down.
        int[] scaleUpDown = new int[16];
        for(int i=0; i<scaleUp.length; i++){
            scaleUpDown[i] = scaleUp[i]; // placement in up sequence
            scaleUpDown[scaleUpDown.length - (i+1)] = scaleUp[i]; // reverse in down sequence
        }
        return scaleUpDown;

    }

    /**
     * Launches application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
