package Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Model.Cell;
import Model.Grid;

public class GameOfLifeRules extends Rules {
	public static final String DEFAULT_RESOURCE = "Rules/GameOfLifeRules";
	private ResourceBundle myResource = ResourceBundle.getBundle(DEFAULT_RESOURCE);
	private int NUM_NEIGHBORS = Integer.parseInt(myResource.getString("NumNeighbors"));
	private String DEAD = myResource.getString("Dead");
	private String ALIVE = myResource.getString("Alive");
	private String DEFAULT_STATE = myResource.getString("DefaultState");
	private List<Integer> NUM_ALLOWABLE_LIVE_NEIGHBORS = new ArrayList<Integer>();
	private int NUM_NEIGHBORS_NEEDED_TO_REPRODUCE = Integer.parseInt(myResource.getString("NumNeighborsNeededToReproduce"));
	
	public GameOfLifeRules() {
		String[] allowableNeighbors = myResource.getString("NumAllowableLiveNeighbors").split(",");
		for (int i = 0; i < allowableNeighbors.length; i++) {
			NUM_ALLOWABLE_LIVE_NEIGHBORS.add(Integer.parseInt(allowableNeighbors[i]));
		}	
	}
	
	/**
	 * Apply the rules of the Game of Life simulation to a Cell based on its state.
	 */
	@Override
	public void applyRulesToCell(Cell cell, Grid grid) {
		String curState = cell.getCurState();
		Cell[][] neighborhood = grid.getNeighborhood(cell.getCurRow(), cell.getCurCol(), NUM_NEIGHBORS);
		int numLiveNeighbors = countNumLiveNeighbors(neighborhood);
		
		if (curState.equals(DEAD)) {
			handleDeadCells(cell, numLiveNeighbors);
		} else {
			handleAliveCells(cell, numLiveNeighbors);
		}
	}

	/**
	 * Counts the number of live neighbors a Cell has.
	 * @param neighborhood: 3x3 array of Cells with the Cell of interest in the center and its neighbors surrounding it.
	 * @return number of live neighbors.
	 */
	private int countNumLiveNeighbors(Cell[][] neighborhood) {
		return countSurroundingNeighborsOfType(neighborhood, ALIVE);
	}

	/**
	 * If a live Cell has < 2 neighbors, it dies of underpopulation; if it has > 3 neighbors, it dies of overpopulation.
	 * @param cell: alive Cell of interest.
	 * @param numLiveNeighbors: number of live neighbors the cell has.
	 */
	private void handleAliveCells(Cell cell, int numLiveNeighbors) {
		if (!NUM_ALLOWABLE_LIVE_NEIGHBORS.contains(numLiveNeighbors)) {
			cell.setNextState(DEAD);
			addCellToBeUpdated(cell);
		}
	}

	/**
	 * If a dead Cell has exactly 3 live neighbors, it becomes alive as though through reproduction.
	 * @param cell: dead Cell of interest.
	 * @param numLiveNeighbors: number of live neighbors the cell has.
	 */
	private void handleDeadCells(Cell cell, int numLiveNeighbors) {
		if (numLiveNeighbors == NUM_NEIGHBORS_NEEDED_TO_REPRODUCE) {
			cell.setNextState(ALIVE);
			addCellToBeUpdated(cell);
		}
	}

	/**
	 * Description of Game of Life simulation.
	 */
	public String toString(){
		return "Game Of Life";
	}

	/**
	 * Parameters for Game of Life simulation.
	 */
	@Override
	public List<String> getParameters() {
		List<String> parameters = new ArrayList<String>();
		return parameters;
	}
	
	/**
	 * Gets the default state for the Game of Life simulation.
	 */
	@Override
	public String getDefault() {
		return DEFAULT_STATE;
	}
	
}
