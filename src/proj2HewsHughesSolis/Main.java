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
import javafx.scene.shape.Line;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Creates application to play major scale given a starting note.
 */
public class Main extends Application {

    private MidiPlayer midiPlayer = new MidiPlayer(100, 60);

    @FXML
    private Pane compositionSheet;
    private Line tempoLine;

    /**
     * Sets up the FXML elements that are dynamically built
     */
    public void initialize() {
        createCompositionSheet();
        buildTempoLine();
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
        StackPane root = null;

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

    /**
     * Generates the Composition nodes by creating a scrollPane
     * which holds a regular pane object (serves as the canvas)
     * to which we add a bunch of rectangles which serve as the lines
     *
     * @param root (the root StackPane for our scene)
     */
    private void createCompositionSheet() {
        Line staffLine;
        for(int i=0; i<127; i++) {
            staffLine = new Line(0,i*10,2000,i*10);
            this.compositionSheet.getChildren().add(staffLine);
        }
    }

    /**
     * Creates a composition using all of the rectangles in our
     * compositionSheet, based on their X and Y positions
     * (timing and pitch respectively)
     *
     * returns the time that the song ends at
     */
     private double buildSong() {
         Rectangle tempNote;
         double stopTime = 0.0;
         for (Node note : this.compositionSheet.getChildren()) {
             if (note instanceof Rectangle) {
                 tempNote = (Rectangle) note;
                 this.midiPlayer.addNote(
                     //pitch
                     (int) (127.0 -(tempNote.getY() - (tempNote.getY()%10))/10),
                     100,                        //volume
                     (int) tempNote.getX(),      //startTick
                     (int) tempNote.getWidth(),  //duration
                     0,                          //channel
                     0                           //trackIndex
                 );
                 System.out.println("Note X: " + tempNote.getX() + "Width: " + tempNote.getWidth());
                 if (stopTime < tempNote.getX()+tempNote.getWidth()) {
                     stopTime = tempNote.getX()+tempNote.getWidth();
                 }
             }
         }
         return stopTime;
     }

    /**
     *
     */
     private void buildTempoLine() {
         this.tempoLine = new Line(0,0,0,1280);
         this.tempoLine.getStyleClass().add("tempo-line");
         this.compositionSheet.getChildren().add(this.tempoLine);
     }

     private void removeTempoLine() {
         if (this.compositionSheet.getChildren().contains(this.tempoLine)) {
             this.compositionSheet.getChildren().remove(this.tempoLine);
         }
     }

    /**
     * Draws a red line and moves it across the screen based on the
     * current composition
     */
     private void moveTempoLine(double stopTime) {
         removeTempoLine();
         buildTempoLine();
         TranslateTransition tt = new TranslateTransition(
             new Duration(stopTime*10), this.tempoLine
         );
         tt.setFromX(0.0);
         tt.setToX(stopTime);
         tt.setByX(1.0);
         tt.play();
     }

    /**
     * Generates a rectangle which represents a note on the composition Pane
     * The rectangle will be colored blue, adjusted to fit within the
     * clicked lines, and be added to the composition pane.
     *
     * @param xPos the input x position of the note
     * @param yPos the input y position of the note
     *        (will be adjusted to look nice)
     */
    private void addNoteToComposition(double xPos, double yPos) {
        if (yPos > 0 && yPos < 1270) {
            Rectangle note = new Rectangle(100.0, 10.0, Color.BLUE);
            note.getStyleClass().add("note");
            note.setX(xPos); note.setY(yPos - (yPos % 10));
            this.compositionSheet.getChildren().add(note);
        }
    }

    @FXML
    /**
     * Adds a note to the composition panel
     */
    protected void handleAddNote(MouseEvent mouseEvent) {
        addNoteToComposition(mouseEvent.getX(), mouseEvent.getY());
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
        removeTempoLine();
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
        double stopTime = buildSong();
        moveTempoLine(stopTime);
        this.midiPlayer.play();
        //removeTempoLine();
    }

    /**
     * Launches application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
