package model;

import guipackage.GUICanvas;

public class SetPenSize extends DisplayNode {
	private static final String SET_PEN_SIZE = "SetPenSize ";
	
	/**
	 * Sets the pen size to the given size.
	 */
	@Override
	protected void performCanvasOperation(GUICanvas canvas, double val) {
		canvas.getPen().setMyPenSize(val);
	}

	/**
	 * Returns the class name and its children.
	 */
	@Override
	public String toString() {
		return SET_PEN_SIZE + childrenToString();
	}
}
