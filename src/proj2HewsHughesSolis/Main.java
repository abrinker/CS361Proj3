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
import javafx.scene.shape.Line;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.animation.Interpolator;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Creates application to play major scale given a starting note.
 */
public class Main extends Application {

    private MidiPlayer midiPlayer = new MidiPlayer(100, 60);

    @FXML
    private Pane fxCompositionSheet;
    private CompositionSheet compositionSheet;

    @FXML
    private Line fxTempoLine;
    private TempoLine tempoLine;

    /**
     * Seeds our CompositionSheet and TempoLine objects with the
     * fields from the FXML file after the FXML has been initialized
     */
    public void initialize() {
        this.compositionSheet = new CompositionSheet(this.fxCompositionSheet);
        this.tempoLine = new TempoLine(this.fxTempoLine);
    }

    /**
     * Sets up the main GUI to play a scale.
     * Player contains a menu bar with exit option and two buttons:
     * play and stop.
     *
     * @param primaryStage the stage to display the gui
     */
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = null;

        try{
            root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        primaryStage.setTitle("Composition Sheet");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.show();
    }

    @FXML
    /**
     * Adds a note to the composition panel
     *
     * @param mouseEvent this is the mouseEvent that is mapped
     * to this functionality (e.g. left click event)
     */
    protected void handleAddNote(MouseEvent mouseEvent) {
        this.compositionSheet.addNoteToComposition(
            mouseEvent.getX(), mouseEvent.getY()
        );
    }

    /**
     * starts the midiplayer and the animation so they are
     * very close in time with each other (milliseconds)
     */
    protected void playMusicAndAnimation() {
        this.tempoLine.playAnimation();
        this.midiPlayer.play();
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
     * Stops the midi player, the animation, and hides the tempo bar
     * This code/docstring is "borrowed" by Alex Rinker from his group's proj 2
     *
     * @param event the event which causes the midiplayer to stop
     */
    protected void handleStopMusic(ActionEvent event) {
        this.midiPlayer.stop();
        this.tempoLine.stopAnimation();
        this.tempoLine.hideTempoLine();
    }

    @FXML
    /**
     * Creates a dialog box prompting the user for the starting note
     * for use in the midi player.
     * Then calls the playMidi method using that note (Integer)
     * This code/docstring is "borrowed" by Alex Rinker from his group's proj 2
     *
     * @param event the event which should trigger the dialog box and
     * midiplayer combo functionality.
     */
    protected void handlePlayMidi(ActionEvent event) {
        this.midiPlayer.stop();
        this.midiPlayer.clear();
        double stopTime = compositionSheet.buildSong(this.midiPlayer);
        this.tempoLine.updateTempoLine(stopTime);
        playMusicAndAnimation();
    }

    /**
     * Launches application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
