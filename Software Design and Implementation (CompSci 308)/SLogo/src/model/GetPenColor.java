package model;

import guipackage.GUICanvas;

public class GetPenColor extends DisplayNode {

	private static final String PEN_COLOR = "GetPenColor ";
	
	/**
	 * Gets the current pen color's index.
	 */
	@Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		return getPen().getMyPenColorIndex();
	}
	
	/**
	 * Not used for this function.
	 */
	@Override
	protected void performCanvasOperation(GUICanvas canvas, double val) {
		//not used
	}

	/**
	 * Returns the class name.
	 */
	@Override
	public String toString() {
		return PEN_COLOR;
	}
}
