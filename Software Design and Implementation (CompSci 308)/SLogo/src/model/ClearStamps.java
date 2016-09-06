package model;

import guipackage.GUICanvas;

public class ClearStamps extends DisplayNode {

	private static final String CLEAR_STAMP = "ClearStamps ";

	/**
	 * Clears all the stamps on the canvas.
	 */
	@Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		getCanvas().clearStamps();
		return 1;
	}
				
	/**
	 * Not used for this class.
	 */
	@Override
	protected void performCanvasOperation(GUICanvas canvas, double val) {
		// do nothing
	}

	/**
	 * Returns the class name.
	 */
	@Override
	public String toString() {
		return CLEAR_STAMP;
	}

}
