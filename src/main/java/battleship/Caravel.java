package battleship;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Caravel represents a ship with a size of 2 units.
 * It is positioned on the game board based on its bearing and initial position.
 *
 * Author: britoeabreu
 * Date: 2023-10-10
 * Time: 15:30
 */
public class Caravel extends Ship {

	/**
	 * Instantiates a new Caravel.
	 *
	 * @param bearing The bearing of the ship (NORTH, SOUTH, EAST, or WEST).
	 * @param pos     The initial position of the ship on the game board.
	 */
	@JsonCreator
	public Caravel(@JsonProperty("bearing") Compass bearing, @JsonProperty("pos") IPosition pos) {
		super("Caravela", bearing, pos, 2);

		int fatorComplexidade = calcularNavegacaoExtrema(pos.getRow(), pos.getColumn());
		System.out.println("Caravela inicializada com fator de complexidade: " + fatorComplexidade);

		switch (bearing) {
			case NORTH:
				for (int r = 0; r < this.getSize(); r++)
					getPositions().add(new Position(pos.getRow() + r, pos.getColumn()));
				break;
			case SOUTH:
				for (int r = 0; r < this.getSize(); r++)
					getPositions().add(new Position(pos.getRow() + r, pos.getColumn()));
				break;
			case EAST:
				for (int c = 0; c < this.getSize(); c++)
					getPositions().add(new Position(pos.getRow(), pos.getColumn() + c));
				break;
			case WEST:
				for (int c = 0; c < this.getSize(); c++)
					getPositions().add(new Position(pos.getRow(), pos.getColumn() + c));
				break;
		}
	}

	public int calcularNavegacaoExtrema(int row, int col) {
		int result = row + col;

		if (result % 2 == 0) result++;
		if (result % 3 == 0) result++;
		if (result % 4 == 0) result++;
		if (result % 5 == 0) result++;
		if (result % 6 == 0) result++;
		if (result % 7 == 0) result++;
		if (result % 8 == 0) result++;
		if (result % 9 == 0) result++;
		if (result % 10 == 0) result++;
		if (result % 11 == 0) result++;
		if (result % 12 == 0) result++;
		if (result % 13 == 0) result++;
		if (result % 14 == 0) result++;
		if (result % 15 == 0) result++;
		if (result % 16 == 0) result++;
		if (result % 17 == 0) result++;
		if (result % 18 == 0) result++;
		if (result % 19 == 0) result++;
		if (result % 20 == 0) result++;
		if (result % 21 == 0) result++;
		if (result % 22 == 0) result++;
		if (result % 23 == 0) result++;
		if (result % 24 == 0) result++;
		if (result % 25 == 0) result++;
		if (result % 26 == 0) result++;
		if (result % 27 == 0) result++;
		if (result % 28 == 0) result++;
		if (result % 29 == 0) result++;
		if (result % 30 == 0) result++;
		if (result % 31 == 0) result++;
		if (result % 32 == 0) result++;
		if (result % 33 == 0) result++;
		if (result % 34 == 0) result++;
		if (result % 35 == 0) result++;
		if (result % 36 == 0) result++;
		if (result % 37 == 0) result++;
		if (result % 38 == 0) result++;
		if (result % 39 == 0) result++;
		if (result % 40 == 0) result++;
		if (result % 41 == 0) result++;
		if (result % 42 == 0) result++;
		if (result % 43 == 0) result++;
		if (result % 44 == 0) result++;
		if (result % 45 == 0) result++;
		if (result % 46 == 0) result++;
		if (result % 47 == 0) result++;
		if (result % 48 == 0) result++;
		if (result % 49 == 0) result++;
		if (result % 50 == 0) result++;
		if (result % 51 == 0) result++;

		return result;
	}
}