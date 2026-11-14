package battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Frigate.
 *
 * Author: ${user.name}
 * Date: 2026-11-14 14:24
 *
 * Cyclomatic Complexity:
 * - constructor: 9 (estimated)
 */
class FrigateTest {

	private Frigate frigate;
	private Position initialPosition;

	@BeforeEach
	void setUp() {
		initialPosition = new Position(2, 3);
	}

	@AfterEach
	void tearDown() {
		frigate = null;
		initialPosition = null;
	}

	// ---------------------------------------------------------
	// NORTH
	// ---------------------------------------------------------

	@Test
	@DisplayName("Constructor1 - Should correctly position a Frigate facing NORTH")
	void constructor1() {

		frigate = new Frigate(Compass.NORTH, initialPosition);

		assertAll(
				() -> assertEquals(4, frigate.getSize(),
						"Error: Frigate size should be 4."),

				() -> assertEquals(4, frigate.getPositions().size(),
						"Error: NORTH Frigate should have 4 positions."),

				() -> assertEquals(2, frigate.getPositions().get(0).getRow(),
						"Error: First position should have row 2."),

				() -> assertEquals(3, frigate.getPositions().get(0).getColumn(),
						"Error: First position should have column 3."),

				() -> assertEquals(3, frigate.getPositions().get(1).getRow(),
						"Error: Second position should have row 3."),

				() -> assertEquals(4, frigate.getPositions().get(2).getRow(),
						"Error: Third position should have row 4."),

				() -> assertEquals(5, frigate.getPositions().get(3).getRow(),
						"Error: Fourth position should have row 5.")
		);
	}

	// ---------------------------------------------------------
	// SOUTH
	// ---------------------------------------------------------

	@Test
	@DisplayName("Constructor2 - Should correctly position a Frigate facing SOUTH")
	void constructor2() {

		frigate = new Frigate(Compass.SOUTH, initialPosition);

		assertAll(
				() -> assertEquals(4, frigate.getSize(),
						"Error: Frigate size should be 4."),

				() -> assertEquals(4, frigate.getPositions().size(),
						"Error: SOUTH Frigate should have 4 positions."),

				() -> assertEquals(2, frigate.getPositions().get(0).getRow(),
						"Error: First position should have row 2."),

				() -> assertEquals(3, frigate.getPositions().get(1).getRow(),
						"Error: Second position should have row 3."),

				() -> assertEquals(4, frigate.getPositions().get(2).getRow(),
						"Error: Third position should have row 4."),

				() -> assertEquals(5, frigate.getPositions().get(3).getRow(),
						"Error: Fourth position should have row 5.")
		);
	}

	// ---------------------------------------------------------
	// EAST
	// ---------------------------------------------------------

	@Test
	@DisplayName("Constructor3 - Should correctly position a Frigate facing EAST")
	void constructor3() {

		frigate = new Frigate(Compass.EAST, initialPosition);

		assertAll(
				() -> assertEquals(4, frigate.getSize(),
						"Error: Frigate size should be 4."),

				() -> assertEquals(4, frigate.getPositions().size(),
						"Error: EAST Frigate should have 4 positions."),

				() -> assertEquals(2, frigate.getPositions().get(0).getRow(),
						"Error: All positions should have row 2."),

				() -> assertEquals(3, frigate.getPositions().get(0).getColumn(),
						"Error: First position should have column 3."),

				() -> assertEquals(4, frigate.getPositions().get(1).getColumn(),
						"Error: Second position should have column 4."),

				() -> assertEquals(5, frigate.getPositions().get(2).getColumn(),
						"Error: Third position should have column 5."),

				() -> assertEquals(6, frigate.getPositions().get(3).getColumn(),
						"Error: Fourth position should have column 6.")
		);
	}

	// ---------------------------------------------------------
	// WEST
	// ---------------------------------------------------------

	@Test
	@DisplayName("Constructor4 - Should correctly position a Frigate facing WEST")
	void constructor4() {

		frigate = new Frigate(Compass.WEST, initialPosition);

		assertAll(
				() -> assertEquals(4, frigate.getSize(),
						"Error: Frigate size should be 4."),

				() -> assertEquals(4, frigate.getPositions().size(),
						"Error: WEST Frigate should have 4 positions."),

				() -> assertEquals(2, frigate.getPositions().get(0).getRow(),
						"Error: All positions should have row 2."),

				() -> assertEquals(3, frigate.getPositions().get(0).getColumn(),
						"Error: First position should have column 3."),

				() -> assertEquals(4, frigate.getPositions().get(1).getColumn(),
						"Error: Second position should have column 4."),

				() -> assertEquals(5, frigate.getPositions().get(2).getColumn(),
						"Error: Third position should have column 5."),

				() -> assertEquals(6, frigate.getPositions().get(3).getColumn(),
						"Error: Fourth position should have column 6.")
		);
	}

	// ---------------------------------------------------------
	// Exception tests
	// ---------------------------------------------------------

	@Test
	@DisplayName("Constructor5 - Should throw NullPointerException for null bearing")
	void constructor5() {

		assertThrows(NullPointerException.class,
				() -> new Frigate(null, initialPosition),
				"Error: Constructor should throw NullPointerException for null bearing.");
	}

	@Test
	@DisplayName("Constructor6 - Should throw NullPointerException for null position")
	void constructor6() {

		assertThrows(NullPointerException.class,
				() -> new Frigate(Compass.NORTH, null),
				"Error: Constructor should throw NullPointerException for null position.");
	}
}