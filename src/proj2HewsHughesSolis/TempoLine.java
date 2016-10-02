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

public class TempoLine {
    private Line tempoLine;
    private TranslateTransition tempoAnimation = new TranslateTransition();

    /**
      * Initializes the tempoAnimation object with the default
      * values it needs
      * Provides the animation with the tempoLine which it moves
      * makes sure the animation is linear
      * and sets our onFinished event
      */
    public TempoLine(Line tempoLine) {
        this.tempoLine = tempoLine;
        this.tempoAnimation.setNode(this.tempoLine);
        this.tempoAnimation.setInterpolator(Interpolator.LINEAR); // Don't ease
        this.tempoAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hideTempoLine();
            }
        });
    }

    /**
     * if there is a tempoline in the compositionsheet, remove it.
     */
    public void hideTempoLine() {
        this.tempoLine.setVisible(false);
    }

    /**
     * Moves the tempoline across the screen based on the input
     * stop "time"/location
     * Uses a TranslateTransition to do so.
     *
     * @param stopTime this is the stop location (e.g time) which is the
     * location of the right edge of the final note to be played
     */
     public void updateTempoLine(double stopTime) {
         this.tempoAnimation.stop();
         this.tempoLine.setTranslateX(0);
         this.tempoAnimation.setDuration(new Duration(stopTime*10));
         this.tempoAnimation.setToX(stopTime);
         this.tempoLine.setVisible(true);
     }

     /**
      * Accessor method for the animation field's play feature
      */
     public void playAnimation() {
         this.tempoAnimation.play();
     }

     /**
      * Accessor method for the animation field's stop feature
      */
     public void stopAnimation() {
         this.tempoAnimation.stop();
     }
}
