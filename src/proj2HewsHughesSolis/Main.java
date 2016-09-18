/*
 * File: proj1HewsHughesSolis.ScalePlayer.java
 * Names: Phoebe Hughes, Josh Hews, Ana Sofia Solis Canales
 * Class: CS361
 * Project: 1
 * Due Date: September 15, 2016
 */

package proj2HewsHughesSolis;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Creates application to play major scale given a starting note.
 */
public class Main extends Application {

    @FXML
    private Button playButton;
    @FXML
    private Button stopButton;
    @FXML
    private MenuItem exitMenuItem;


    /**
     * Sets up the main GUI to play a scale.
     * Player contains a menu bar with exit option and two buttons: play and stop.
     *
     * @param primaryStage the stage to display the gui
     */
    @Override
    public void start(Stage primaryStage) {
        StackPane root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        try{
            root = loader.load();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        primaryStage.setTitle("Test");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.show();
    }

    /**
     * Play a major scale. First prompts the user to input a start note, after which the major scale starting at that
     * note is played
     *
     * @param mp MidiPlayer to use to play the scale with
     * */
    private void playScale(MidiPlayer mp){
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
     * Initialize elements of window. First creates a MidiPlayer, and then sets
     * action event handlers for the two buttons and the exitMenuItem.
     * */
    public void initialize() {
        MidiPlayer mp = new MidiPlayer(1, 120);
        playButton.setOnAction(event -> playScale(mp));
        stopButton.setOnAction(event -> mp.stop());
        exitMenuItem.setOnAction(event -> System.exit(0));
    }

    /**
     * Launches application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
