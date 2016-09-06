package guipackage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import model.Turtle;


/**
 * Returns one Node that contains the Turtle Canvas and its set-able components.
 */

public class GUICanvas implements Observer{
	private static final String DOTTED_LINE = "Dotted Line";
	private static final String DASHED_LINE = "Dashed Line";
	private static final String SOLID_LINE = "Solid Line";
	private static final int TURTLE_SIZE = 20;
	private static final int CENTER_FACTOR = TURTLE_SIZE/2;
	private static final int CANVAS_WIDTH = 500;
	private static final int CANVAS_X_TRANSFORM = CANVAS_WIDTH/2;
	private static final int CANVAS_HEIGHT = 500;
	private static final int CANVAS_Y_TRANSFORM = CANVAS_HEIGHT/2;
	private static final int STARTING_X = CANVAS_X_TRANSFORM - CENTER_FACTOR;
	private static final int STARTING_Y = CANVAS_Y_TRANSFORM - CENTER_FACTOR;	
	private static final int PEN_SCALE = 10;
	private static final int DEFAULT = 0;
	private static final int MAX_COORDINATE = 500;
	private static final int MIN_COORDINATE = 0;
	private Canvas canvasStamps;
	private Pane myCanvasRoot;
	private GraphicsContext gcStamps;
	private Map<Turtle, List<GraphicsContext>> myTurtles;
	private List<Double[]> turtleParameters;	
	private ResourceBundle myResources;
	private GUICanvasPen myPen;
	private GUICanvasRight canvasRight;
	private Group root;
	private GUICanvasTurtleImageView myTurtleImageView;
	private GUICanvasBackground myBackgroundCanvas;
	private GUICanvasAnimation myAnimation;
	private HBox toReturn;
	
	public GUICanvas(ResourceBundle myResources) {
		this.myResources = myResources;
		myTurtles = new HashMap<>();
		turtleParameters = new ArrayList<>();
		canvasStamps = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		gcStamps = canvasStamps.getGraphicsContext2D();
		myPen = new GUICanvasPen();
		myBackgroundCanvas = new GUICanvasBackground(CANVAS_WIDTH, CANVAS_HEIGHT);
		myCanvasRoot = new Pane(myBackgroundCanvas.getCanvas(), canvasStamps);
		root = new Group(myCanvasRoot);
		myTurtleImageView = new GUICanvasTurtleImageView(root, myTurtles);
		myAnimation = new GUICanvasAnimation();
		setRightCanvas();
	}
	
	/**
	 * Creates the Canvas Node to be displayed.
	 * @return Canvas Node
	 */
	public Node createNode() {
		toReturn = new HBox();
		toReturn.getChildren().addAll(root,canvasRight.createNode());
		return toReturn;
	}
	/**
	 * Adds object to position right of the canvas.
	 * @param rightOfCanvas
	 */
	public void setRightCanvas(){
		canvasRight = new GUICanvasRight(myResources, new GUIComboBoxColorB(this, myResources, myResources.getString("BackgroundColorPalettePromptText"),
				myResources.getString("DefaultBackgroundColors")), new GUIComboBoxColorP(this, myResources, 
						myResources.getString("PenColorPalettePromptText"), myResources.getString("DefaultPenColors")),
				new GUIComboBoxImages(this, myResources, myResources.getString("ImageComboBoxPromptText")),
				new GUIPenSettings(myResources, this),
				new GUITurtleState(myResources, new GUILabeled(myResources, myResources.getString("TurtleLocation")),
												new GUILabeled(myResources, myResources.getString("TurtleHeading")), 
												new GUILabeled(myResources, myResources.getString("TurtlePen")),
												new GUILabeled(myResources, myResources.getString("TurtleActive"))));
		myPen.setMyPenPalette(canvasRight.getPenPalette());
		myBackgroundCanvas.setMyBackgroundPalette(canvasRight.getBackgroundPalette());
		myTurtleImageView.setMyImagePalette(canvasRight.getImagePalette());
	}
	
	/**
	 * Updates the Canvas when the Turtle is updated.
	 */
	@Override
	public void update(Observable o, Object args) {
		Turtle turtle = (Turtle) o;
		if (turtle.shouldReset()) {
			resetCanvas(turtle);
		} else {
			updatePenColors();
			addTurtleToMap(turtle); 
			clearPreviousTurtle(turtle);
			drawTurtle(turtle);
		}
	}
	
	/**
	 * Resets Canvas. Deletes all of the Turtle's trails and changes the Turtle back to default.
	 */
	private void resetCanvas(Turtle turtle) {
		for(Turtle key: myTurtles.keySet()){
			List<GraphicsContext> lst = myTurtles.get(key);
			lst.get(0).clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
			lst.get(1).clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
			drawTurtle(key);
			myTurtleImageView.updateImageView(turtle.getImageView(), STARTING_X, STARTING_Y, DEFAULT, myTurtleImageView.getTurtleShape());
			key.doneResetting();
		}
	}
	
	/**
	 * Keeps track of the old coordinates after updating the Turtle.
	 */
	private void setOldCoordinates(Turtle turtle, double x, double y, double direction) {
		Double[] coordinates = {x, y, direction};
		if (turtleParameters.size() <= turtle.getID())
			turtleParameters.add((int) turtle.getID() - 1, coordinates);
		else turtleParameters.set((int) turtle.getID() - 1, coordinates);
	}
	
	/**
	 * Updates image for each turtle on the canvas.
	 */
	protected void updateTurtleImageView(){
		for(Turtle turtle: myTurtles.keySet()){
				drawTurtle(turtle);
		}
	}
	/**
	 * Updates pen color for each turtle on the canvas.
	 */
	private void updatePenColors(){
		for(List<GraphicsContext> lst: myTurtles.values()){
			if (lst != null){
				GraphicsContext gcPen = lst.get(1);
				gcPen.setFill(myPen.getMyPenColor());
			}
		}
	}
	
	/**
	 * Adds each new turtle to the map when created.
	 * @param turtle
	 */
	private void addTurtleToMap(Turtle turtle){
		if (!myTurtles.containsKey(turtle)) {
			Canvas turtleCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
			Canvas drawingCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
			GraphicsContext drawingGC = drawingCanvas.getGraphicsContext2D();
			drawingGC.setFill(myPen.getMyPenColor());
			myCanvasRoot.getChildren().addAll(turtleCanvas, drawingCanvas);
			myTurtles.put(turtle, new ArrayList<GraphicsContext>(
					Arrays.asList(turtleCanvas.getGraphicsContext2D(), drawingGC)));
			int myX = STARTING_X;
			int myY = STARTING_Y;
			setOldCoordinates(turtle, myX, myY, DEFAULT);
			myTurtleImageView.createImageViewForTurtle(turtle, toroidalBounds(myX), toroidalBounds(myY), canvasRight);
		}
	}
	
	/**
	 * Clears the previous instance of the Turtle on the canvas.
	 */
	private void clearPreviousTurtle(Turtle turtle) {
		GraphicsContext gc = myTurtles.get(turtle).get(0);
		double myOldX = turtleParameters.get((int) turtle.getID() - 1)[0].doubleValue();
		double myOldY = turtleParameters.get((int) turtle.getID() - 1)[1].doubleValue();
		double myOldDirection = turtleParameters.get((int) turtle.getID() - 1)[2].doubleValue();
		gc.save(); // saves the current state on stack, including the current transform
		Rotate r = new Rotate(myOldDirection, myOldX + CENTER_FACTOR, myOldY + CENTER_FACTOR);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		gc.clearRect(myOldX, myOldY, TURTLE_SIZE, TURTLE_SIZE);
		gc.restore();
	}

	/**
	 * Draws the turtle onto canvas based on turtle's X and Y values and its direction.
	 */
	private void drawTurtle(Turtle turtle) {
		GraphicsContext gc = myTurtles.get(turtle).get(0);
		GraphicsContext gcDrawing = myTurtles.get(turtle).get(1);
		double myX = toroidalBounds(turtle.getCurX() + CANVAS_X_TRANSFORM - CENTER_FACTOR);
		double myY = toroidalBounds(-(turtle.getCurY() - CANVAS_Y_TRANSFORM + CENTER_FACTOR));
		gc.save(); // saves the current state on stack, including the current transform
		Rotate r = new Rotate(turtle.getDirection(), myX + CENTER_FACTOR, myY + CENTER_FACTOR);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		if (!turtle.isPenUp()) {
			drawLine(gcDrawing, myX, myY);
		}
		if (turtle.showing()) {
			checkForAnimation(turtle, myX, myY);
		}
		gc.restore();
		setOldCoordinates(turtle, myX, myY, turtle.getDirection());
	}
	
	/**
	 * Checks to see if the user wants to 
	 * @param turtle
	 * @param myX
	 * @param myY
	 */
	private void checkForAnimation(Turtle turtle, double myX, double myY) {
		Double[] parameters = turtleParameters.get((int) turtle.getID() - 1);
		ImageView currentImageView = turtle.getImageView();
		if (myAnimation.willAnimate()) {
			myAnimation.addAnimation(currentImageView, normalizeCoordinates(parameters[0]), 
					normalizeCoordinates(parameters[1]), normalizeCoordinates(myX),
					normalizeCoordinates(myY), turtle.getDirection() - parameters[2]);
			myTurtleImageView.updateImageViewLocation(currentImageView, myX,
					myY, myTurtleImageView.getTurtleShape());
		} else {
			myTurtleImageView.updateImageView(currentImageView, myX, myY,
					turtle.getDirection(), myTurtleImageView.getTurtleShape());
		}
	}
	
	/**
	 * Converts coordinate to its toroidal coordinate if it is greater than the maximum or less than the minimum coordinate.
	 * @param coordinate
	 * @return
	 */
	private double toroidalBounds(double coordinate) {
		if (coordinate > MAX_COORDINATE) {
			coordinate = coordinate%MAX_COORDINATE;
		}
		else if(coordinate < MIN_COORDINATE){
			double temp_pos = Math.abs(coordinate);
			double temp_mod = temp_pos%MAX_COORDINATE;
			coordinate = MAX_COORDINATE - temp_mod;
		}
		return coordinate;
	}
	
	/**
	 * Draws the pen line by creating a circle every time the turtle is updated.
	 * @param gcDrawing
	 * @param myX
	 * @param myY
	 */
	private void drawLine(GraphicsContext gcDrawing, double myX, double myY) {
		int scaledPen = (int) myPen.getMyPenSize() * PEN_SCALE;
		switch (myPen.getMyPenType()){
			case SOLID_LINE: {
				drawOval(gcDrawing, myX, myY);
			}
			case DASHED_LINE: {
				if (myPen.getMyPenCounter() < scaledPen / 2) {
					drawOval(gcDrawing, myX, myY);
				} else if (myPen.getMyPenCounter() == scaledPen) {
					myPen.resetPenCounter();
				}
				myPen.incrementMyPenCounter();
			}
			case DOTTED_LINE: {
				if (myPen.getMyPenCounter() == scaledPen / 2) {
					drawOval(gcDrawing, myX, myY);
				} else if (myPen.getMyPenCounter()== scaledPen) {
					drawOval(gcDrawing, myX, myY);
					myPen.resetPenCounter();
				}
				myPen.incrementMyPenCounter();
			}
		}
	}
	
	/**
	 * Creates the circle on the canvas.
	 * @param gcDrawing
	 * @param myX
	 * @param myY
	 */
	private void drawOval(GraphicsContext gcDrawing, double myX, double myY) {
		long penSize = Math.round(myPen.getMyPenSize());
		gcDrawing.fillOval(myX + CENTER_FACTOR - penSize/2, myY + CENTER_FACTOR - penSize/2,
				penSize, penSize);
	}
	
	/**
	 * Creates stamps for each turtle when the stamp command is called.
	 * @return
	 */
	public double drawStamps() {
		for (Turtle turtle: myTurtles.keySet()) {
			if (turtle.isActive()) {
				gcStamps.drawImage(myTurtleImageView.getTurtleShape(), toroidalBounds(turtle.getCurX() + STARTING_X),
						toroidalBounds(-turtle.getCurY() + STARTING_Y), TURTLE_SIZE, TURTLE_SIZE);
			}
		}
		return myTurtleImageView.getTurtleShapeIndex();
	}
	
	/**
	 * Clears the stamps that have previously been created.
	 */
	public void clearStamps() {
		clearGraphicsContext(gcStamps);
	}
	
	/**
	 * Clears the Graphics Context with a large clear rectangle.
	 * @param gc
	 */
	private void clearGraphicsContext(GraphicsContext gc) {
		gc.clearRect(DEFAULT, DEFAULT, CANVAS_WIDTH, CANVAS_HEIGHT);
	}
	
	/**
	 * Normalizes the coordinates to the center of the node.
	 * @param coordinate
	 * @return
	 */
	private double normalizeCoordinates(double coordinate) {
		return coordinate + CENTER_FACTOR;
	}
	
	/**
	 * Update both pen and background color palette at given index to given RGB color.
	 * @param Space separated string of RGB values
	 * @param index of color in palette that user wants to change
	 */
	public void setPalette(String RGB, int index){
		canvasRight.changePalettes(RGB, index);
	}
	
	/**
	 * returns current Pen object
	 */
	public GUICanvasPen getPen() {
		return myPen;
	}
	
	public GUICanvasBackground getBackgroundCanvas(){
		return myBackgroundCanvas;
	}
	
	public GUICanvasTurtleImageView getTurtleImageView(){
		return myTurtleImageView;
	}
	
	protected GUICanvasAnimation getAnimation(){
		return myAnimation;
	}
	/**
	 * Set pen state (up/down) of every turtle on the canvas. 
	 * @param penState
	 */
	protected void setTurtlePenStatus(String penState) {
		for (Turtle t: myTurtles.keySet()) {
			if (t.isActive() && penState.equals(myResources.getString("PenUp"))) {
				t.setPenUp(true);
			} else if (t.isActive() && penState.equals(myResources.getString("PenDown"))){
				t.setPenUp(false);
			}
		}
	}
}