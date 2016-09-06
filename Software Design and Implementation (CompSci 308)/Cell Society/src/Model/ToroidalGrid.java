package Model;

public class ToroidalGrid extends Grid{

	Cell[][] myGrid;
	
	public ToroidalGrid(int rows, int cols, String[][] initialStates) {
		super(rows, cols, initialStates);
		myGrid = this.getGrid();
	}
	
	/**
	 * Uses mod to wrap out of bounds cells.
	 * @param num: row or col
	 * @return mod'd row or col
	 */
	public int getModLocation(int num){
		
		int modNum = num % (myGrid.length);
		if (modNum < 0){
			modNum += myGrid.length;
		}
		return modNum;
		
	}
	
	/**
	 * Gets the wrapped cell.
	 */
	public Cell getCell(int row, int col){
		
		int x = getModLocation(row);
		int y = getModLocation(col);
		return myGrid[x][y];
		
	}

	/**
	 * Gets a toroidal neighborhood.
	 */
	@Override
	public Cell[][] getNeighborhood(int row, int col, int numNeighbors) {
		
		Cell[][] myNeighborhood = new Cell[getNeighborGridSideLength()][getNeighborGridSideLength()];
		myNeighborhood[0][0] = getCell(row - 1, col - 1);
		myNeighborhood[0][1] = getCell(row - 1, col);
		myNeighborhood[0][2] = getCell(row - 1, col + 1);
		myNeighborhood[1][0] = getCell(row, col - 1);
		myNeighborhood[1][1] = getCell(row, col);
		myNeighborhood[1][2] = getCell(row, col + 1);
		myNeighborhood[2][0] = getCell(row + 1, col - 1);
		myNeighborhood[2][1] = getCell(row + 1, col);
		myNeighborhood[2][2] = getCell(row + 1, col + 1);
		
		return myNeighborhood;
	}

}
