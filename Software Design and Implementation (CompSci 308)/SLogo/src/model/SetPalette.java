package model;

import java.util.List;
import guipackage.GUICanvas;

public class SetPalette extends DisplayNode {

	private static final String SET_PALETTE = "SetPalette ";
	private static final int INDEX = 0;
	private static final int[] RGB_INDICES = new int[]{1,2,3};

	/**
	 * Sets the color at the given index in the palette to a new color specified by the RGB values.
	 */
	@Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		List<IFunctions> children = getChildren();
		StringBuilder rgb = new StringBuilder();
		for (int i = 0; i < RGB_INDICES.length; i++) {
			rgb.append(Integer.toString((int) children.get(RGB_INDICES[i]).interpret(commandDict, varDict)));
			rgb.append(" ");
		}
		getCanvas().setPalette(rgb.toString().trim(), (int) children.get(INDEX).interpret(commandDict, varDict));
		return children.get(INDEX).interpret(commandDict, varDict);
	}
	
	/**
	 * Not used in this class.
	 */
	@Override
	protected void performCanvasOperation(GUICanvas canvas, double val) {
		//not used
	}
	
	/**
	 * Returns the class name and its children.
	 */
	@Override
	public String toString() {
		return SET_PALETTE + childrenToString();
	}

}
