package model;

import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is the main component of the model. It records the current state of a Turtle and updates the view via the
 * Observer pattern.
 */

public class Turtle extends Observable {
    private Image myImage;
    private double myID;
    private double myX;
    private double myY;
    private boolean penUp;
    private boolean isVisible;
    private double myDirection;
    private boolean isActive;
    private boolean isCurrentTurtle;
    private boolean reset;
    private ImageView turtleImageView;
    private static final double ONE_REVOLUTION = 360;
    private static final double INCREMENT = 1;
    private static final int SQUARE = 2;

    /**
     * Creates a turtle at the origin with the given ID.
     * @param ID: turtle ID.
     */
    public Turtle(double ID) {
        myID = ID;
        myX = 0;
        myY = 0;
        penUp = true;
        isVisible = true;
        myDirection = 0;
        isCurrentTurtle = false;
    }

    /**
     * Moves the turtle the specified distance in its current direction in increments of (1,1).
     *
     * @param dist: distance to move.
     */
    public double move(double dist) {
        double inc = INCREMENT;
        if (dist < 0) {
            inc = -inc;
        }

        for (double i = 0; i < Math.abs(dist); i = i + INCREMENT) {
            myX = myX + inc * Math.sin(Math.toRadians(myDirection));
            myY = myY + inc * Math.cos(Math.toRadians(myDirection));
            updateObservers();
        }
        return dist;
    }

    /**
     * Moves the turtle to a given (x, y) location.
     * @param x: x-position.
     * @param y: y-position.
     */
    public void setXY(double x, double y) {
    	myX = x;
    	myY = y;
    }
    
    /**
     * Turns the turtle towards a given (x, y).
     * @param x: x-position to turn towards.
     * @param y: y-position to turn towards.
     */
    public double turnTowards(double x, double y) {
        double angle = Math.toDegrees(Math.atan2(x - myX, y - myY));
        setDirection(angle);
        return angle;
    }

    /**
     * Change a turtle either to be the current turtle or to no longer be the current turtle.
     * @param currentTurtleStatus: true if turtle is current turtle; false o.w.
     */
    public void changeCurrentTurtleStatus(boolean currentTurtleStatus) {
        isCurrentTurtle = currentTurtleStatus;
    }

    /**
     * Checks if the turtle is the current turtle.
     * @return true if turtle is current turtle; false o.w.
     */
    public boolean isCurrentTurtle() {
        return isCurrentTurtle;
    }
    
    /**
     * Resets the reset flag.
     */
    public void doneResetting() {
    	reset = false;
    }

    /**
     * Determines distance between current position and specified (x, y)
     * @param x: x-coordinate of position of interest
     * @param y: y-coordinate of position of interest
     * @return distance between current position and (x, y)
     */
    public double calcDistance(double x, double y) {
        return Math.sqrt(Math.pow(myX - x, SQUARE) + Math.pow(myY - y, SQUARE));
    }

    /**
     * Sets the direction the turtle is facing.
     * @param dir: the direction the turtle should face in degrees.
     */
    public void setDirection(double dir) {
        if (dir > ONE_REVOLUTION) {
            dir = dir % ONE_REVOLUTION;
        } else if (dir < 0) {
            dir = dir + ONE_REVOLUTION;
        }

        myDirection = dir;
        updateObservers();
    }

    /**
     * Lets the observers know that the turtle has changed.
     */
    public void updateObservers() {
        setChanged();
        notifyObservers();
    }

    /**
     * Lifts the turtle's pen up so no trail is drawn when it moves.
     */
    public void setPenUp(boolean penUpStatus) {
        penUp = penUpStatus;
    }

    /**
     * Makes the turtle invisible.
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
        updateObservers();
    }

    /**
     * Checks if the pen is up.
     *
     * @return true if penUp is true; false otherwise.
     */
    public boolean isPenUp() {
        return penUp;
    }

    /**
     * Resets the Canvas.
     */
    public void resetTurtle() {
        reset = true;
        updateObservers();
    }

    /**
     * Checks if turtle wants to be reset
     * @return true if reset is true; false otherwise.
     */
    public boolean shouldReset() {
        return reset;
    }

    /**
     * Moves the turtle to home (0, 0).
     * @return distance moved to get back home.
     */
    public double moveToHome() {
        double dist = calcDistance(0, 0);
        turnTowards(0, 0);
        move(dist);
        setDirection(0);
        return dist;
    }

    /**
     * Checks if the turtle is visible.
     * @return true if the turtle's isVisible field is true; false otherwise.
     */
    public boolean showing() {
        return isVisible;
    }

    /**
     * Checks the turtle's current direction.
     * @return the turtle's current direction in degrees.
     */
    public double getDirection() {
        return myDirection;
    }

    /**
     * Checks the turtle's current x-coordinate.
     * @return the turtle's current x-coordinate.
     */
    public double getCurX() {
        return myX;
    }

    /**
     * Checks the turtle's current y-coordinate.
     * @return the turtle's current y-coordinate.
     */
    public double getCurY() {
        return myY;
    }

    /**
     * Sets the turtle as active or inactive.
     * @param active: true if active; false if inactive.
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Gets the turtle's active state.
     * @return true if active; false if inactive.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Gets the turtle's ID.
     * @return turtle's ID.
     */
    public double getID() {
        return myID;
    }

    /**
     * Gets the turtle's image.
     * @return turtle's image.
     */
    public Image getImage() {
        return myImage;
    }
    
    /**
     * Sets the turtle's image.
     * @param iv: imageview for turtle.
     */
    public void setImageView(ImageView iv){
    	turtleImageView = iv;
    	updateObservers();
    }
    
    /**
     * Gets the turtle's imageview.
     * @return turtle's imageview.
     */
    public ImageView getImageView(){
    	return turtleImageView;
    }
}
