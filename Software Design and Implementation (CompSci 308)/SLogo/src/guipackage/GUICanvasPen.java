package guipackage;

import java.util.List;

import javafx.scene.paint.Color;
/**
 * Pen component of the main canvas. 
 */
public class GUICanvasPen {
	private static final String SOLID_LINE = "Solid Line";
	private static final int DEFAULT_PEN_SIZE = 3;
	private static final Color DEFAULT_PEN_COLOR = Color.BLACK;
	private static final int DEFAULT_PEN_COUNTER = 0;
	private static final int RGB_MAX = 255;
	private double myPenSize;
	private String myPenType;
	private int myPenColorIndex;
	private String myPenRGB;
	private Color myPenColor;
	private int myPenCounter;
	private List<String> myPenPalette;
	
	public GUICanvasPen(){
		myPenSize = DEFAULT_PEN_SIZE;
		myPenType = SOLID_LINE;
		myPenColorIndex = 0;
		myPenRGB = DEFAULT_PEN_COLOR.getRed()*RGB_MAX  + " " + DEFAULT_PEN_COLOR.getGreen()*RGB_MAX  + " " + DEFAULT_PEN_COLOR.getBlue()*RGB_MAX ;
		myPenColor = DEFAULT_PEN_COLOR;
		myPenCounter = DEFAULT_PEN_COUNTER;
	}
	
	/**
	 * Return current pen thickness.
	 * @return
	 */
	protected double getMyPenSize() {
		return myPenSize;
	}
	/**
	 * Set current pen thickness as given size.
	 * @param myPenSize
	 */
	public void setMyPenSize(double myPenSize) {
		this.myPenSize = myPenSize;
	}
	/**
	 * Returns current pen type.
	 * @return
	 */
	protected String getMyPenType() {
		return myPenType;
	}
	/**
	 * Set current pen type.
	 * @param myPenType
	 */
	protected void setMyPenType(String myPenType) {
		this.myPenType = myPenType;
	}
	/**
	 * Returns index of current color in the pen color palette.
	 * @return
	 */
	public int getMyPenColorIndex() {
		return myPenColorIndex;
	}
	/**
	 * Sets current pen color index to given index.
	 * @param myPenColorIndex
	 */
	protected void setMyPenColorIndex(int myPenColorIndex) {
		this.myPenColorIndex = myPenColorIndex;
	}
	/**
	 * Returns space separated RGB of current pen color.
	 * @return
	 */
	public String getMyPenRGB() {
		return myPenRGB;
	}
	/**
	 * Sets current space separated RGB of pen color to that which is given.
	 * @param myPenRGB
	 */
	protected void setMyPenRGB(String myPenRGB) {
		this.myPenRGB = myPenRGB;
	}
	/**
	 * Returns current pen color as a Color
	 * @return
	 */
	protected Color getMyPenColor() {
		return myPenColor;
	}	
	/**
	 * Sets current pen color to color at the given index in the current pen color palette.
	 * @param index
	 */
	public void setMyPenColor(int index){
		this.myPenColorIndex = index;
		String[] rgb = myPenPalette.get(index).split(" ");
		Color col = Color.rgb(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
		setMyPenColor(col, myPenPalette.get(index));
	}
	/**
	 * Sets current pen color to given Color and updates String RGB value.
	 * @param c
	 * @param penColorRGB
	 */
	public void setMyPenColor(Color c, String penColorRGB) {
		this.myPenColor = c;
		this.myPenRGB = penColorRGB;
		for(String turtleName:myPenPalette){
			if(turtleName.equals(penColorRGB)){
				this.myPenColorIndex = myPenPalette.indexOf(penColorRGB);
			}
		}
	}
	/**
	 * Increase value of pen counter by one.
	 */
	protected void incrementMyPenCounter(){
		this.myPenCounter++;
	}
	/**
	 * Returns current value of pen counter.
	 * @return
	 */
	protected int getMyPenCounter(){
		return myPenCounter;
	}
	/**
	 * Sets pen counter back to zero. 
	 */
	protected void resetPenCounter(){
		this.myPenCounter = DEFAULT_PEN_COUNTER;
	}
	/**
	 * Sets pen color palette to given palette.
	 * @param palette
	 */
	protected void setMyPenPalette(List<String> palette){
		this.myPenPalette = palette;
	}
}
