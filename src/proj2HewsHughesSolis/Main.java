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
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Creates application to play major scale given a starting note.
 */
public class Main extends Application {

    private MidiPlayer midiPlayer = new MidiPlayer(1, 120);


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

        createCompositionSheet(root);

        primaryStage.setTitle("Composition Sheet");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.show();
    }

    /**
     * Generates the Composition page on the GUI
     */
    public void createCompositionSheet(StackPane root) {
        Pane compositionSheet = new Pane();
        ScrollPane scrollPane = new ScrollPane();
        compositionSheet.setPrefSize(2000, 1280);
        scrollPane.setTranslateY(30);
        Rectangle staffLine;
        for(int i=0; i<127; i++) {
            staffLine = new Rectangle(2000.0, 1.0, Color.BLACK);
            staffLine.setY(i*10);
            compositionSheet.getChildren().add(staffLine);
        }
        scrollPane.setContent(compositionSheet);
        root.getChildren().add(scrollPane);
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
    protected void handleStopMidi(ActionEvent event) {
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
    protected void handlePlayMidi(ActionEvent event) {
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
