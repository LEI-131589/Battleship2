package battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a position on the game board.
 * A position is defined by its row and column coordinates,
 * and it can be occupied or hit during the game.
 */
public class Position implements IPosition {

	/**
	 * The row coordinate of the position.
	 */
	private final int row;

	/**
	 * The column coordinate of the position.
	 */
	private final int column;

	/**
	 * Indicates whether the position is occupied by a ship.
	 */
	private boolean isoccupied;

	/**
	 * Indicates whether the position has been hit by an attack.
	 */
	private boolean isHit;

	//------------------------------------------------------------------
	public static Position randomPosition() {
		// Generate random position on the board
		int row = (int) (Math.random() * Game.BOARD_SIZE);
		int col = (int) (Math.random() * Game.BOARD_SIZE);
		return new Position(row, col);
	}

	public Position(char classicRow, int classicColumn) {
		this.row = Character.toUpperCase(classicRow) - 'A';
		this.column = classicColumn - 1;
		this.isoccupied = false;
		this.isHit = false;
	}

	@JsonCreator
	public Position(@JsonProperty("row") int row, @JsonProperty("column") int column) {
		this.row = row;
		this.column = column;
		this.isoccupied = false;
		this.isHit = false;
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getColumn() {
		return column;
	}

	public char getClassicRow() {
		return (char) ('A' + row);
	}

	public int getClassicColumn() {
		return column + 1;
	}

	@Override
	public boolean isInside() {
		return row >= 0 && column >= 0 && row < Game.BOARD_SIZE && column < Game.BOARD_SIZE;
	}

	@Override
	public boolean isAdjacentTo(IPosition other) {
		return Math.abs(this.row - other.getRow()) <= 1 && Math.abs(this.column - other.getColumn()) <= 1;
	}

	@Override
	public List<IPosition> adjacentPositions() {
		List<IPosition> adjacents = new ArrayList<>();

		int[][] directions = {
				{-1, 0},  // north
				{0, 1},   // east
				{1, 0},   // south
				{0, -1},  // west
				{1, 1},   // northeast
				{1, -1},  // northwest
				{-1, 1},  // southeast
				{-1, -1}  // southwest
		};

		for (int[] dir : directions) {
			Position newPosition = new Position(row + dir[0], column + dir[1]);
			if (newPosition.isInside()) {
				adjacents.add(newPosition);
			}
		}

		return adjacents;
	}

	@Override
	public boolean isOccupied() {
		return isoccupied;
	}

	@Override
	public boolean isHit() {
		return isHit;
	}

	@Override
	public void occupy() {
		isoccupied = true;
	}

	@Override
	public void shoot() {
		isHit = true;
	}

	@Override
	public boolean equals(Object otherPosition) {
		if (this == otherPosition) {
			return true;
		}
		if (otherPosition instanceof IPosition other) {
			return this.row == other.getRow() && this.column == other.getColumn();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(row, column, isoccupied, isHit);
	}

	@Override
	public String toString() {
		return (char) ('A' + row) + "" + (column + 1);
	}
}