package guipackage;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * Background canvas of the main GUICanvas
 */
public class GUICanvasBackground {
	private static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
	private static final int RGB_MAX = 255;
	private static final int DEFAULT_INT = 0;
	private int canvasWidth;
	private int canvasHeight;
	private Canvas canvasBackground;
	private GraphicsContext gcBackground;
	private String myBackgroundRGB;
	private List<String> myBackgroundPalette;
	
	
	public GUICanvasBackground(int canvasWidth, int canvasHeight) {
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
	}
	/**
	 * Returns background Canvas object.
	 * @return
	 */
	public Canvas getCanvas(){
		canvasBackground = new Canvas(canvasWidth, canvasHeight);
		gcBackground = canvasBackground.getGraphicsContext2D();
		gcBackground.setFill(DEFAULT_BACKGROUND_COLOR);
		gcBackground.fillRect(0, 0, canvasWidth, canvasHeight);
		myBackgroundRGB = DEFAULT_BACKGROUND_COLOR.getRed()*RGB_MAX + " " + DEFAULT_BACKGROUND_COLOR.getGreen()*RGB_MAX  + " " + DEFAULT_BACKGROUND_COLOR.getBlue()*RGB_MAX ;
		return canvasBackground;
	}
	
	/**
	 * Sets current background color palette to given color palette.
	 * @param backgroundPalette
	 */
	public void setMyBackgroundPalette(List<String> backgroundPalette) {
		this.myBackgroundPalette = backgroundPalette;
	}
	
	/**
	 * Sets background color based on index within palette.
	 * @param index
	 */
	public void setBackgroundColor(int index) {
		String[] rgb = myBackgroundPalette.get(index).split(" ");
		setBackgroundColor(Color.rgb(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])), 
				myBackgroundPalette.get(index));
	}
	
	/**
	 * Sets background color based on User preference.
	 * @param Color that user chose.
	 */
	public void setBackgroundColor(Color col, String backgroundColorName){
		myBackgroundRGB = backgroundColorName;
		gcBackground.setFill(col);
		gcBackground.fillRect(DEFAULT_INT, DEFAULT_INT, canvasWidth, canvasHeight);
	}
	
	/**
	 * Returns space separated RGB string for the current background color of the canvas. 
	 * @return
	 */
	public String getBackgroundColor(){
		return myBackgroundRGB;
	}

}
