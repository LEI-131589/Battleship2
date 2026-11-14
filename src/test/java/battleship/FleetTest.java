package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Fleet.
 * Author: mariainesrodrigues
 * Date: 2026-11-14 14:24
 * Cyclomatic Complexity:
 * constructor: 1
 * createRandom(): 4
 * getShips(): 1
 * addShip(): 5
 * getShipsLike(): 4
 * getFloatingShips(): 3
 * getSunkShips(): 3
 * shipAt(): 4
 * colisionRisk(): 4
 * printShips(): 3
 * printStatus(): 1
 * printShipsByCategory(): 2
 * printFloatingShips(): 1
 * printAllShips(): 1
 * areAllShipsSunk(): 2
 */
class FleetTest {

	private Fleet fleet;

	@BeforeEach
	void setUp() {
		fleet = new Fleet();
	}

	@AfterEach
	void tearDown() {
		fleet = null;
	}

	// --- constructor (CC = 1) ---

	@Test
	@DisplayName("Fleet() constructor initializes a non-null fleet with an empty ships list")
	void Fleet() {
		Fleet newFleet = new Fleet();
		assertAll("Constructor state verification",
				() -> assertNotNull(newFleet, "Error: expected Fleet instance not to be null"),
				() -> assertTrue(newFleet.getShips().isEmpty(), "Error: expected newly constructed fleet to be empty")
		);
	}

	// --- createRandom() (CC = 4) ---

	@Test
	@DisplayName("createRandom() path 1: generates a full valid fleet of FLEET_SIZE (11) ships")
	void createRandom1() {
		IFleet randomFleet = Fleet.createRandom();
		assertAll("Random fleet size and state",
				() -> assertNotNull(randomFleet, "Error: expected createRandom() to return non-null fleet"),
				() -> assertEquals(IFleet.FLEET_SIZE, randomFleet.getShips().size(),
						"Error: expected createRandom() to generate FLEET_SIZE ships")
		);
	}

	@Test
	@DisplayName("createRandom() path 2: places all generated ships inside board boundaries")
	void createRandom2() {
		IFleet randomFleet = Fleet.createRandom();
		for (IShip ship : randomFleet.getShips()) {
			assertTrue(ship.isInsideBoard(), "Error: expected ship " + ship + " to be inside board boundaries");
		}
	}

	@Test
	@DisplayName("createRandom() path 3: includes exact expected ship counts per category")
	void createRandom3() {
		IFleet randomFleet = Fleet.createRandom();
		assertAll("Ship counts by category",
				() -> assertEquals(1, randomFleet.getShipsLike("Galeao").size(), "Error: expected 1 Galeao"),
				() -> assertEquals(1, randomFleet.getShipsLike("Fragata").size(), "Error: expected 1 Fragata"),
				() -> assertEquals(2, randomFleet.getShipsLike("Nau").size(), "Error: expected 2 Naus"),
				() -> assertEquals(3, randomFleet.getShipsLike("Caravela").size(), "Error: expected 3 Caravelas"),
				() -> assertEquals(4, randomFleet.getShipsLike("Barca").size(), "Error: expected 4 Barcas")
		);
	}

	@Test
	@DisplayName("createRandom() path 4: ensures all generated ships are initially floating")
	void createRandom4() {
		IFleet randomFleet = Fleet.createRandom();
		assertEquals(IFleet.FLEET_SIZE, randomFleet.getFloatingShips().size(),
				"Error: expected all generated ships to be floating initially");
	}

	// --- getShips() (CC = 1) ---

	@Test
	@DisplayName("getShips() returns the internal list of added ships")
	void getShips() {
		assertTrue(fleet.getShips().isEmpty(), "Error: expected ships list to initially be empty");
		IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
		fleet.addShip(ship);
		assertAll("getShips verification",
				() -> assertEquals(1, fleet.getShips().size(), "Error: expected fleet size 1 after adding ship"),
				() -> assertEquals(ship, fleet.getShips().get(0), "Error: expected first ship to match added ship")
		);
	}

	// --- addShip() (CC = 5) ---

	@Test
	@DisplayName("addShip() path 1: throws AssertionError when ship is null")
	void addShip1() {
		assertThrows(AssertionError.class, () -> fleet.addShip(null),
				"Error: expected AssertionError when adding a null ship");
	}

	@Test
	@DisplayName("addShip() path 2: returns true and adds valid ship within board without collision")
	void addShip2() {
		IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
		assertAll("Valid addShip outcome",
				() -> assertTrue(fleet.addShip(ship), "Error: expected valid ship to be added successfully"),
				() -> assertEquals(1, fleet.getShips().size(), "Error: expected fleet size to be 1")
		);
	}

	@Test
	@DisplayName("addShip() path 3: returns false when ship is outside board boundaries")
	void addShip3() {
		IShip shipOutside = new Barge(Compass.NORTH, new Position(99, 99));
		assertAll("Out of bounds addShip outcome",
				() -> assertFalse(fleet.addShip(shipOutside), "Error: expected false for ship outside board"),
				() -> assertTrue(fleet.getShips().isEmpty(), "Error: expected fleet to remain empty")
		);
	}

	@Test
	@DisplayName("addShip() path 4: returns false when ship collides or is too close to existing ship")
	void addShip4() {
		IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
		IShip ship2 = new Barge(Compass.NORTH, new Position(1, 1));
		fleet.addShip(ship1);
		assertAll("Collision risk addShip outcome",
				() -> assertFalse(fleet.addShip(ship2), "Error: expected false when collision risk exists"),
				() -> assertEquals(1, fleet.getShips().size(), "Error: expected fleet size to remain 1")
		);
	}

	@Test
	@DisplayName("addShip() path 5: returns false when fleet exceeds maximum FLEET_SIZE")
	void addShip5() {
		for (int row = 0; row < 10; row += 2) {
			fleet.addShip(new Barge(Compass.NORTH, new Position(row, 0)));
			fleet.addShip(new Barge(Compass.NORTH, new Position(row, 2)));
		}
		fleet.addShip(new Barge(Compass.NORTH, new Position(0, 4)));
		fleet.addShip(new Barge(Compass.NORTH, new Position(2, 4)));

		IShip extraShip = new Barge(Compass.NORTH, new Position(4, 4));
		assertFalse(fleet.addShip(extraShip), "Error: expected false when ships.size() > FLEET_SIZE");
	}

	// --- getShipsLike() (CC = 4) ---

	@Test
	@DisplayName("getShipsLike() path 1: throws AssertionError when category is null")
	void getShipsLike1() {
		assertThrows(AssertionError.class, () -> fleet.getShipsLike(null),
				"Error: expected AssertionError when category is null");
	}

	@Test
	@DisplayName("getShipsLike() path 2: returns empty list when fleet is empty")
	void getShipsLike2() {
		assertTrue(fleet.getShipsLike("Barca").isEmpty(), "Error: expected empty list for empty fleet");
	}

	@Test
	@DisplayName("getShipsLike() path 3: returns empty list when no ships match category")
	void getShipsLike3() {
		fleet.addShip(new Barge(Compass.NORTH, new Position(1, 1)));
		assertTrue(fleet.getShipsLike("Galeao").isEmpty(),
				"Error: expected empty list when no ships match category Galeao");
	}

	@Test
	@DisplayName("getShipsLike() path 4: returns only ships matching the given category")
	void getShipsLike4() {
		IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
		IShip ship2 = new Caravel(Compass.NORTH, new Position(3, 1));
		fleet.addShip(ship1);
		fleet.addShip(ship2);

		List<IShip> barges = fleet.getShipsLike("Barca");
		assertAll("Filtered ships by category",
				() -> assertEquals(1, barges.size(), "Error: expected 1 ship of category Barca"),
				() -> assertEquals(ship1, barges.get(0), "Error: expected returned ship to match ship1")
		);
	}

	// --- getFloatingShips() (CC = 3) ---

	@Test
	@DisplayName("getFloatingShips() path 1: returns empty list when fleet has no ships")
	void getFloatingShips1() {
		assertTrue(fleet.getFloatingShips().isEmpty(), "Error: expected empty list for empty fleet");
	}

	@Test
	@DisplayName("getFloatingShips() path 2: returns only ships still floating after one sinks")
	void getFloatingShips2() {
		IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
		IShip ship2 = new Caravel(Compass.NORTH, new Position(4, 4));
		fleet.addShip(ship1);
		fleet.addShip(ship2);

		assertEquals(2, fleet.getFloatingShips().size(), "Error: expected all 2 ships floating initially");
		ship1.getPositions().get(0).shoot();
		List<IShip> floatingShips = fleet.getFloatingShips();
		assertAll("Floating ships after partial sink",
				() -> assertEquals(1, floatingShips.size(), "Error: expected 1 floating ship after sinking ship1"),
				() -> assertEquals(ship2, floatingShips.get(0), "Error: expected ship2 to be floating")
		);
	}

	@Test
	@DisplayName("getFloatingShips() path 3: returns empty list when all ships are sunk")
	void getFloatingShips3() {
		IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
		fleet.addShip(ship1);
		ship1.sink();
		assertTrue(fleet.getFloatingShips().isEmpty(), "Error: expected no floating ships when all are sunk");
	}

	// --- getSunkShips() (CC = 3) ---

	@Test
	@DisplayName("getSunkShips() path 1: returns empty list when fleet is empty")
	void getSunkShips1() {
		assertTrue(fleet.getSunkShips().isEmpty(), "Error: expected empty list for empty fleet");
	}

	@Test
	@DisplayName("getSunkShips() path 2: returns empty list when all ships are floating")
	void getSunkShips2() {
		fleet.addShip(new Barge(Compass.NORTH, new Position(1, 1)));
		assertTrue(fleet.getSunkShips().isEmpty(), "Error: expected no sunk ships initially");
	}

	@Test
	@DisplayName("getSunkShips() path 3: returns only sunk ships")
	void getSunkShips3() {
		IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
		IShip ship2 = new Caravel(Compass.NORTH, new Position(4, 4));
		fleet.addShip(ship1);
		fleet.addShip(ship2);

		ship1.getPositions().get(0).shoot();
		List<IShip> sunkShips = fleet.getSunkShips();
		assertAll("Sunk ships filtering",
				() -> assertEquals(1, sunkShips.size(), "Error: expected 1 sunk ship"),
				() -> assertEquals(ship1, sunkShips.get(0), "Error: expected ship1 to be in sunk ships")
		);
	}

	// --- shipAt() (CC = 4) ---

	@Test
	@DisplayName("shipAt() path 1: throws AssertionError when position is null")
	void shipAt1() {
		assertThrows(AssertionError.class, () -> fleet.shipAt(null),
				"Error: expected AssertionError when position is null");
	}

	@Test
	@DisplayName("shipAt() path 2: returns null when fleet is empty")
	void shipAt2() {
		assertNull(fleet.shipAt(new Position(1, 1)), "Error: expected null when fleet is empty");
	}

	@Test
	@DisplayName("shipAt() path 3: returns null for unoccupied positions")
	void shipAt3() {
		fleet.addShip(new Barge(Compass.NORTH, new Position(1, 1)));
		assertNull(fleet.shipAt(new Position(5, 5)), "Error: expected null for unoccupied position (5,5)");
	}

	@Test
	@DisplayName("shipAt() path 4: returns the ship occupying the given position")
	void shipAt4() {
		IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
		fleet.addShip(ship);
		assertEquals(ship, fleet.shipAt(new Position(1, 1)),
				"Error: expected ship at position (1,1)");
	}

	// --- colisionRisk() (CC = 4, tested via reflection) ---

	@Test
	@DisplayName("colisionRisk() path 1: throws AssertionError wrapped in InvocationTargetException when ship is null")
	void colisionRisk1() throws Exception {
		Method method = Fleet.class.getDeclaredMethod("colisionRisk", IShip.class);
		method.setAccessible(true);
		InvocationTargetException ex = assertThrows(InvocationTargetException.class,
				() -> method.invoke(fleet, (Object) null),
				"Error: expected InvocationTargetException wrapping AssertionError");
		assertInstanceOf(AssertionError.class, ex.getCause(),
				"Error: expected cause to be AssertionError when ship is null");
	}

	@Test
	@DisplayName("colisionRisk() path 2: returns false when fleet has no ships")
	void colisionRisk2() throws Exception {
		Method method = Fleet.class.getDeclaredMethod("colisionRisk", IShip.class);
		method.setAccessible(true);
		boolean risk = (Boolean) method.invoke(fleet, new Barge(Compass.NORTH, new Position(1, 1)));
		assertFalse(risk, "Error: expected false when fleet is empty");
	}

	@Test
	@DisplayName("colisionRisk() path 3: returns true when ships overlap or are adjacent")
	void colisionRisk3() throws Exception {
		Method method = Fleet.class.getDeclaredMethod("colisionRisk", IShip.class);
		method.setAccessible(true);
		fleet.addShip(new Barge(Compass.NORTH, new Position(1, 1)));
		boolean risk = (Boolean) method.invoke(fleet, new Barge(Compass.NORTH, new Position(1, 1)));
		assertTrue(risk, "Error: expected true when overlapping ship is checked");
	}

	@Test
	@DisplayName("colisionRisk() path 4: returns false when ships are far apart")
	void colisionRisk4() throws Exception {
		Method method = Fleet.class.getDeclaredMethod("colisionRisk", IShip.class);
		method.setAccessible(true);
		fleet.addShip(new Barge(Compass.NORTH, new Position(1, 1)));
		boolean risk = (Boolean) method.invoke(fleet, new Barge(Compass.NORTH, new Position(5, 5)));
		assertFalse(risk, "Error: expected false when ships are at non-adjacent positions");
	}

	// --- printShips() (CC = 3) ---

	@Test
	@DisplayName("printShips() path 1: throws AssertionError when list is null")
	void printShips1() {
		assertThrows(AssertionError.class, () -> fleet.printShips(null),
				"Error: expected AssertionError when ships list is null");
	}

	@Test
	@DisplayName("printShips() path 2: prints nothing for an empty list")
	void printShips2() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream original = System.out;
		System.setOut(new PrintStream(out));
		try {
			fleet.printShips(List.of());
			assertEquals("", out.toString(), "Error: expected empty output for empty list");
		} finally {
			System.setOut(original);
		}
	}

	@Test
	@DisplayName("printShips() path 3: prints string representation of each ship")
	void printShips3() {
		IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream original = System.out;
		System.setOut(new PrintStream(out));
		try {
			fleet.printShips(List.of(ship));
			assertTrue(out.toString().contains(ship.toString()),
					"Error: expected output to contain ship string representation");
		} finally {
			System.setOut(original);
		}
	}

	// --- printStatus() (CC = 1) ---

	@Test
	@DisplayName("printStatus() outputs current floating and sunk ship counts without throwing")
	void printStatus() {
		IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
		fleet.addShip(ship);
		assertDoesNotThrow(fleet::printStatus, "Error: printStatus should not throw any exceptions");
	}

	// --- printShipsByCategory() (CC = 2) ---

	@Test
	@DisplayName("printShipsByCategory() path 1: throws AssertionError when category is null")
	void printShipsByCategory1() {
		assertThrows(AssertionError.class, () -> fleet.printShipsByCategory(null),
				"Error: expected AssertionError when category is null");
	}

	@Test
	@DisplayName("printShipsByCategory() path 2: prints ships matching the given category")
	void printShipsByCategory2() {
		IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
		fleet.addShip(ship);
		assertDoesNotThrow(() -> fleet.printShipsByCategory("Barca"),
				"Error: printShipsByCategory should not throw for valid category");
	}

	// --- printFloatingShips() (CC = 1) ---

	@Test
	@DisplayName("printFloatingShips() prints floating ships without throwing")
	void printFloatingShips() {
		fleet.addShip(new Barge(Compass.NORTH, new Position(1, 1)));
		assertDoesNotThrow(fleet::printFloatingShips, "Error: printFloatingShips should not throw");
	}

	// --- printAllShips() (CC = 1) ---

	@Test
	@DisplayName("printAllShips() prints all ships in the fleet without throwing")
	void printAllShips() {
		fleet.addShip(new Barge(Compass.NORTH, new Position(1, 1)));
		assertDoesNotThrow(fleet::printAllShips, "Error: printAllShips should not throw");
	}

	// --- areAllShipsSunk() (CC = 2) ---

	@Test
	@DisplayName("areAllShipsSunk() path 1: returns false when fleet is empty")
	void areAllShipsSunk1() {
		assertFalse(fleet.areAllShipsSunk(), "Error: empty fleet should return false");
	}

	@Test
	@DisplayName("areAllShipsSunk() path 2: returns false while ships float and true when all sink")
	void areAllShipsSunk2() {
		IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
		IShip ship2 = new Barge(Compass.NORTH, new Position(3, 3));
		fleet.addShip(ship1);
		fleet.addShip(ship2);
		assertFalse(fleet.areAllShipsSunk(), "Error: should return false when ships are still floating");

		ship1.getPositions().get(0).shoot();
		assertFalse(fleet.areAllShipsSunk(), "Error: should return false when only some ships are sunk");

		ship2.getPositions().get(0).shoot();
		assertTrue(fleet.areAllShipsSunk(), "Error: should return true when all ships are sunk");
	}
}