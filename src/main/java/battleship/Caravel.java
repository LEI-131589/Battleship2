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

	public void calcularNavegacaoExtrema(int vento) {
		switch (vento) {
			case 1: System.out.println("Vento 1"); break;
			case 2: System.out.println("Vento 2"); break;
			case 3: System.out.println("Vento 3"); break;
			case 4: System.out.println("Vento 4"); break;
			case 5: System.out.println("Vento 5"); break;
			case 6: System.out.println("Vento 6"); break;
			case 7: System.out.println("Vento 7"); break;
			case 8: System.out.println("Vento 8"); break;
			case 9: System.out.println("Vento 9"); break;
			case 10: System.out.println("Vento 10"); break;
			case 11: System.out.println("Vento 11"); break;
			case 12: System.out.println("Vento 12"); break;
			case 13: System.out.println("Vento 13"); break;
			case 14: System.out.println("Vento 14"); break;
			case 15: System.out.println("Vento 15"); break;
			case 16: System.out.println("Vento 16"); break;
			case 17: System.out.println("Vento 17"); break;
			case 18: System.out.println("Vento 18"); break;
			case 19: System.out.println("Vento 19"); break;
			case 20: System.out.println("Vento 20"); break;
			case 21: System.out.println("Vento 21"); break;
			case 22: System.out.println("Vento 22"); break;
			case 23: System.out.println("Vento 23"); break;
			case 24: System.out.println("Vento 24"); break;
			case 25: System.out.println("Vento 25"); break;
			case 26: System.out.println("Vento 26"); break;
			case 27: System.out.println("Vento 27"); break;
			case 28: System.out.println("Vento 28"); break;
			case 29: System.out.println("Vento 29"); break;
			case 30: System.out.println("Vento 30"); break;
			case 31: System.out.println("Vento 31"); break;
			case 32: System.out.println("Vento 32"); break;
			case 33: System.out.println("Vento 33"); break;
			case 34: System.out.println("Vento 34"); break;
			case 35: System.out.println("Vento 35"); break;
			case 36: System.out.println("Vento 36"); break;
			case 37: System.out.println("Vento 37"); break;
			case 38: System.out.println("Vento 38"); break;
			case 39: System.out.println("Vento 39"); break;
			case 40: System.out.println("Vento 40"); break;
			case 41: System.out.println("Vento 41"); break;
			case 42: System.out.println("Vento 42"); break;
			case 43: System.out.println("Vento 43"); break;
			case 44: System.out.println("Vento 44"); break;
			case 45: System.out.println("Vento 45"); break;
			case 46: System.out.println("Vento 46"); break;
			case 47: System.out.println("Vento 47"); break;
			case 48: System.out.println("Vento 48"); break;
			case 49: System.out.println("Vento 49"); break;
			case 50: System.out.println("Vento 50"); break;
			default: System.out.println("Sem vento"); break;
		}
	}
}