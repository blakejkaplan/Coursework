package guipackage;


import java.util.List;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Turtle;
/**
 * Turtle ImageView component of the main GUICanvas
 */
public class GUICanvasTurtleImageView {
	private static final String DEFAULT_TURTLE = "tortoise.png";
	private static final int TURTLE_SIZE = 25;
	private Group root;
	private List<String> myImagePalette;
	private Image myTurtleShape;
	private String myTurtleShapeName;
	private int myTurtleShapeIndex;
	private Map<Turtle, List<GraphicsContext>> myTurtles;
	private boolean showInactive;
	
	public GUICanvasTurtleImageView(Group root, Map<Turtle, List<GraphicsContext>> myTurtles) {
		this.root = root;
		this.myTurtles = myTurtles;
		myTurtleShape = new Image(getClass().getClassLoader().getResourceAsStream(DEFAULT_TURTLE));
		myTurtleShapeName = DEFAULT_TURTLE;
		myTurtleShapeIndex = 0;
		showInactive = true;
	}
	/**
	 * Places ImageView of given turtle onto the canvas. 
	 * @param turtle
	 * @param x
	 * @param y
	 * @param canvasRight
	 */
	protected void createImageViewForTurtle(Turtle turtle, double x, double y, GUICanvasRight canvasRight){
		ImageView turtleImageView = new ImageView(myTurtleShape);
		turtleImageView.setFitHeight(TURTLE_SIZE);
		turtleImageView.setPreserveRatio(true);
		turtleImageView.setX(x);
		turtleImageView.setY(y);
		turtleImageView.setOnMouseEntered(event -> {
				 canvasRight.showTurtleState(turtle);
		});
		turtleImageView.setOnMouseClicked(event -> {
			turtle.setActive(!turtle.isActive());
			if(!showInactive){
				removeInactive();
			}
		});
		turtle.setImageView(turtleImageView);
		root.getChildren().add(turtle.getImageView());
	}
	/**
	 * Updates coordinates, rotation, and image of given ImageView.
	 * @param imageView
	 * @param x
	 * @param y
	 * @param rotation
	 * @param newShape
	 */
	protected void updateImageView(ImageView imageView, double x, double y, double rotation, Image newShape){
		updateImageViewLocation(imageView, x, y, newShape);
		imageView.setRotate(rotation);
	}
	
	protected void updateImageViewLocation(ImageView imageView, double x, double y, Image newShape) {
		imageView.setX(x);
		imageView.setY(y);
		imageView.setImage(newShape);
	}
	
	/**
	 * Set current palette of images to given palette.
	 * @param palette
	 */
	protected void setMyImagePalette(List<String> palette){
		this.myImagePalette = palette;
	}
	/**
	 * Returns current image being used for the turtle. 
	 * @return
	 */
	protected Image getTurtleShape(){
		return myTurtleShape;
	}
	
	/**
	 * Sets Turtle shape/image based on index within palette.
	 * @param index of image in palette.
	 */
	public void setTurtleShape(int index) {
		myTurtleShapeIndex = index;
		String imageName = myImagePalette.get(index);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageName));
		setTurtleShape(image, imageName);
	}
	
	/**
	 * Sets user-inputed image as the Canvas turtle.
	 * @param Image
	 */
	public void setTurtleShape(Image image, String imageName){
		myTurtleShape = image;
		myTurtleShapeName = imageName;
		for(String turtleName: myImagePalette){
			if(turtleName.equals(imageName)){
				myTurtleShapeIndex = myImagePalette.indexOf(turtleName);
			}
		}
	}
	
	/**
	 * returns current turtle image filename
	 * @return
	 */
	public String getTurtleImageName(){
		return myTurtleShapeName;
	}
	
	/**
	 * Returns current index of shape/image of turtle;
	 */
	public int getTurtleShapeIndex() {
		return myTurtleShapeIndex;
	}
	/**
	 * Based on given visibility string (true/false), either show inactive turtles or hide them. 
	 * @param visibility
	 */
	protected void setVisibility(String visibility){
		showInactive = Boolean.parseBoolean(visibility);
		if (showInactive) {
			showInactive();
		}
		else {
			removeInactive();
		}
	}	
	/**
	 * Show all turtles on canvas, including inactive.
	 */
	private void showInactive(){
		for(Turtle turtle:myTurtles.keySet()){
			if(!root.getChildren().contains(turtle.getImageView())){
				root.getChildren().add(turtle.getImageView());
			}
		}
	}
	/**
	 * Hide inactive turtles on canvas.
	 */
	private void removeInactive(){
		for(Turtle turtle:myTurtles.keySet()){
			if(!turtle.isActive()){
				root.getChildren().remove(turtle.getImageView());
			}
		}
	}
}
