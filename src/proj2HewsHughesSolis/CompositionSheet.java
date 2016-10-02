/**
 * File: CompositionSheet.java
 * Names: Alex Rinker, Ana Sofia Solis Canales, Ryan Salerno, Larry Patrizio
 * Class: CS361
 * Project: 3
 * Due Date: October 3rd 2016
 *
 * Created by Alex on 10/2/16.
 * Takes in a Pane which it then adds lines to in order
 * to create a music composition sheet
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

public class CompositionSheet {

    /**
     * Generates the Composition nodes by creating a scrollPane
     * which holds a regular pane object (serves as the canvas)
     * to which we add a bunch of rectangles which serve as the lines
     */
    public static void createCompositionSheet(Pane composition) {
        Line staffLine;
        for(int i=0; i<127; i++) {
            staffLine = new Line(0,i*10,2000,i*10);
            composition.getChildren().add(staffLine);
        }
    }

    /**
     * Creates a composition using all of the rectangles in our
     * compositionSheet, based on their X and Y positions
     * (timing and pitch respectively)
     *
     * Also Keeps track of the last note and when it ends
     * and returns it for use in the animation
     */
     public static double buildSong(Pane composition, MidiPlayer midiPlayer) {
         Rectangle tempNote;
         double stopTime = 0.0;
         for (Node note : composition.getChildren()) {
             if (note instanceof Rectangle) {
                 tempNote = (Rectangle) note;
                 midiPlayer.addNote(
                     //pitch
                     (int) (127.0 -(tempNote.getY() - (tempNote.getY()%10))/10),
                     100,                        //volume
                     (int) tempNote.getX(),      //startTick
                     (int) tempNote.getWidth(),  //duration
                     0,                          //channel
                     0                           //trackIndex
                 );
                 //Update Stoptime if the note is the last one so far
                 if (stopTime < tempNote.getX()+tempNote.getWidth()) {
                     stopTime = tempNote.getX()+tempNote.getWidth();
                 }
             }
         }
         return stopTime;
     }
}
