/*
 * File: proj2HewsHughesSolis.Main.java
 * Names: Phoebe Hughes, Josh Hews, Ana Sofia Solis Canales
 * Class: CS361
 * Project: 2
 * Due Date: September 22, 2016
 */

package proj2HewsHughesSolis;

import javafx.application.Application;
import javafx.event.ActionEvent;
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

    private MidiPlayer midiPlayer;


    /**
     * Sets up the main GUI to play a scale.
     * Player contains a menu bar with exit option and two buttons: play and stop.
     *
     * @param primaryStage the stage to display the gui
     */
    @Override
    public void start(Stage primaryStage) {
        StackPane root = null;
        try{
            root = FXMLLoader.load(getClass().getResource("Main.fxml"));
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
     * Initialize elements of window. First creates a MidiPlayer, and then sets
     * action event handlers for the two buttons and the exitMenuItem.
     *
     * This is automatically called when the fxml file is loaded
     * */
    public void initialize() {
        this.midiPlayer = new MidiPlayer(1, 120);
    }

    /**
     * Play a major scale. First prompts the user to input a start note, after which the major scale starting at that
     * note is played
     *
     * */
    private void playScale(){
        TextInputDialog dialog = new TextInputDialog("55");
        dialog.setTitle("Starting Note");
        dialog.setHeaderText("Please give me a starting note (0-115):");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            int startNote = Integer.valueOf(result.get());

            int[] notes = constructMajorScale(startNote);

            this.midiPlayer.stop();
            this.midiPlayer.clear();
            for (int i = 0; i< notes.length; i++) {
                midiPlayer.addNote(notes[i], 100, i, 1, 0, 0);
            }

            this.midiPlayer.play();
        }
    }

    @FXML
    /**
     * Safely exits the program without throwing an error
     *
     * @param event the event to trigger the exit.
     */
    protected void handleExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    /**
     * Stops the midi player
this.     * For use in our stop button
     * This code/docstring is "borrowed" by Alex Rinker from his group's proj 2
     *
     * @param event the event which causes the midiplayer to stop
     */
    protected void handleStopButtonAction(ActionEvent event) {
        this.midiPlayer.stop();
    }

    @FXML
    /**
     * Creates a dialog box prompting the user for the starting note
     * for use in the midi player.
     * Then calls the playMidi method using that note (Integer)
     * This code/docstring is "borrowed" by Alex Rinker from his group's proj 2
     *
     * @param event the event which should trigger the dialog box and midiplayer combo functionality.
     */
    protected void handlePlayButtonAction(ActionEvent event) {
        playScale();
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
