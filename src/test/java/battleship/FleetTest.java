package battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Test class for Fleet.
 * Author: ${user.name}
 * Date: ${current_date}
 * Time: ${current_time}
 * Cyclomatic Complexity for each method:
 * - Constructor: 1
 * - addShip: 3
 * - getShips: 1
 * - getShipsLike: 2
 * - getFloatingShips: 2
 * - shipAt: 2
 * - isInsideBoard: 3
 * - colisionRisk: 2
 */
	public class FleetTest {

		private Fleet fleet;

		@BeforeEach
		void setUp() {
			fleet = new Fleet();
		}

		@AfterEach
		void tearDown() {
			fleet = null;
		}

		/**
		 * Test for the Fleet constructor.
		 * Cyclomatic Complexity: 1
		 */
		@Test
		void testConstructor() {
			assertNotNull(fleet, "Error: Instance of Fleet should not be null.");
			assertTrue(fleet.getShips().isEmpty(), "Error: Fleet should be initialized with empty ships list.");
		}

		@Test
		void testCreateRandom(){
				IFleet r = Fleet.createRandom();
				assertNotNull(r, "Error: createRandom should return a non-null fleet instance.");
				assertEquals(Fleet.FLEET_SIZE, r.getShips().size(), "Error: createRandom should create a fleet with the correct number of ships.");
		}

		/**
		 * Test for the addShip method (all conditions true).
		 * Cyclomatic Complexity: 3
		 */
		@Test
		void testAddShip1() {
			IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
			assertTrue(fleet.addShip(ship), "Error: Valid ship should be added successfully.");
			assertEquals(1, fleet.getShips().size(), "Error: Fleet should contain one ship after addition.");
		}

		/**
		 * Test for the addShip method (fleet size limit reached).
		 */
		@Test
		void testAddShip2() {
			for (int i = 0; i < Fleet.FLEET_SIZE; i++) {
				fleet.addShip(new Barge(Compass.NORTH, new Position(i, 0)));
			}
			IShip anotherShip = new Barge(Compass.NORTH, new Position(10, 10));
			assertFalse(fleet.addShip(anotherShip), "Error: Should not add ship when fleet size limit is reached.");
		}

		/**
		 * Test for the addShip method (ship outside the board).
		 */
		@Test
		void testAddShip3() {
			IShip shipOutside = new Barge(Compass.NORTH, new Position(99, 99));
			assertFalse(fleet.addShip(shipOutside), "Error: Should not add ship outside the board.");
		}

		/**
		 * Test for the addShip method (collision risk).
		 */
		@Test
		void testAddShip4() {
			IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
			IShip ship2 = new Barge(Compass.NORTH, new Position(1, 1));  // Overlapping position
			fleet.addShip(ship1);
			assertFalse(fleet.addShip(ship2), "Error: Should not add ship with a collision risk.");
		}

		/**
		 * Test for the getShips method.
		 * Cyclomatic Complexity: 1
		 */
		@Test
		void testGetShips() {
			assertTrue(fleet.getShips().isEmpty(), "Error: Fleet's ships list should initially be empty.");
			IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
			fleet.addShip(ship);
			assertEquals(1, fleet.getShips().size(), "Error: Fleet should have size 1 after adding a ship.");
			assertEquals(ship, fleet.getShips().get(0), "Error: Fleet's first ship should match the added ship.");
		}

		/**
		 * Test for the getShipsLike method (ships of specific category).
		 * Cyclomatic Complexity: 2
		 */
		@Test
		void testGetShipsLike() {
			IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
			IShip ship2 = new Caravel(Compass.NORTH, new Position(2, 1));
			fleet.addShip(ship1);
			fleet.addShip(ship2);

			List<IShip> barges = fleet.getShipsLike("Barca");
			assertEquals(1, barges.size(), "Error: There should be exactly one ship of category 'Barca'.");
			assertEquals(ship1, barges.get(0), "Error: The ship of category 'Barca' does not match.");
		}

		/**
		 * Test for the getFloatingShips method.
		 * Cyclomatic Complexity: 2
		 */
		@Test
		void testGetFloatingShips() {
			IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
			IShip ship2 = new Caravel(Compass.NORTH, new Position(4, 4));
			fleet.addShip(ship1);
			fleet.addShip(ship2);

			List<IShip> floatingShips = fleet.getFloatingShips();
			assertEquals(2, floatingShips.size(), "Error: All ships should be floating initially.");

			ship1.getPositions().get(0).shoot();  // Sink ship1
			floatingShips = fleet.getFloatingShips();
			assertEquals(1, floatingShips.size(), "Error: Only one ship should be floating after sinking one.");
			assertEquals(ship2, floatingShips.get(0), "Error: The floating ship should match the expected result.");
		}

		/**
		 * Test for the shipAt method.
		 * Cyclomatic Complexity: 2
		 */
		@Test
		void testShipAt() {
			IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
			fleet.addShip(ship);

			assertEquals(ship, fleet.shipAt(new Position(1, 1)), "Error: Should return the correct ship at the position.");
			assertNull(fleet.shipAt(new Position(5, 5)), "Error: Should return null for empty positions in the fleet.");
		}


		/**
		 * Test for private method colisionRisk.
		 * Cyclomatic Complexity: 2
		 */
		@Test
		void testColisionRisk() throws Exception {
			var method = Fleet.class.getDeclaredMethod("colisionRisk", IShip.class);
			method.setAccessible(true);

			IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
			IShip ship2 = new Barge(Compass.NORTH, new Position(1, 1));  // Overlapping position
			fleet.addShip(ship1);

			assertTrue((Boolean) method.invoke(fleet, ship2), "Error: Overlapping ships should be at collision risk.");
			assertFalse((Boolean) method.invoke(fleet, new Barge(Compass.NORTH, new Position(5, 5))),
					"Error: Ships at non-overlapping positions should not have a collision risk.");
		}

		/**
		 * Test for the printStatus method.
		 * Cyclomatic Complexity: 1
		 */
		@Test
		void testPrintStatus() {
			IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
			fleet.addShip(ship);
			assertDoesNotThrow(fleet::printStatus, "Error: printStatus should not throw any exceptions.");
		}

		@Test
		@DisplayName("Should return sunk ships accurately")
		void testGetSunkShips() {
			IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
			IShip ship2 = new Caravel(Compass.NORTH, new Position(4, 4));
			fleet.addShip(ship1);
			fleet.addShip(ship2);

			assertTrue(fleet.getSunkShips().isEmpty(), "Initially no ships should be sunk.");

			ship1.getPositions().get(0).shoot(); // Afunda a barca (tamanho 1)
			List<IShip> sunkShips = fleet.getSunkShips();
			assertEquals(1, sunkShips.size(), "One ship should be sunk.");
			assertEquals(ship1, sunkShips.get(0), "The sunk ship should be ship1.");
		}

		@Test
		@DisplayName("Should reject adding ship when fleet exceeds FLEET_SIZE")
		void testAddShipExceedingFleetSize() {
			// Preenche com 12 navios (FLEET_SIZE = 11, e a condição é ships.size() <= FLEET_SIZE)
			for (int row = 0; row < 10; row += 2) {
				fleet.addShip(new Barge(Compass.NORTH, new Position(row, 0)));
				fleet.addShip(new Barge(Compass.NORTH, new Position(row, 2)));
			}
			fleet.addShip(new Barge(Compass.NORTH, new Position(0, 4))); // 11º navio (size passa a 11)
			fleet.addShip(new Barge(Compass.NORTH, new Position(2, 4))); // 12º navio (size passa a 12)

			IShip extraShip = new Barge(Compass.NORTH, new Position(4, 4)); // 13º navio (12 <= 11 é false!)
			assertFalse(fleet.addShip(extraShip), "Should not add ship when size > FLEET_SIZE.");
		}

		@Test
		@DisplayName("Should check if all ships are sunk across empty, partial, and full sunk states")
		void testAreAllShipsSunk() {
			// Caso 1: Frota vazia
			assertFalse(fleet.areAllShipsSunk(), "Empty fleet should return false.");

			// Caso 2: Frota com navios ainda a flutuar
			IShip ship1 = new Barge(Compass.NORTH, new Position(1, 1));
			IShip ship2 = new Barge(Compass.NORTH, new Position(3, 3));
			fleet.addShip(ship1);
			fleet.addShip(ship2);
			assertFalse(fleet.areAllShipsSunk(), "Should return false when ships are still floating.");

			// Caso 3: Apenas um navio afundado
			ship1.getPositions().get(0).shoot();
			assertFalse(fleet.areAllShipsSunk(), "Should return false when only some ships are sunk.");

			// Caso 4: Todos os navios afundados
			ship2.getPositions().get(0).shoot();
			assertTrue(fleet.areAllShipsSunk(), "Should return true when all ships are sunk.");
		}

		@Test
		@DisplayName("Should execute all print methods without throwing exceptions")
		void testPrintMethods() {
			IShip ship = new Barge(Compass.NORTH, new Position(1, 1));
			fleet.addShip(ship);

			assertDoesNotThrow(() -> fleet.printShips(fleet.getShips()), "printShips should not throw.");
			assertDoesNotThrow(() -> fleet.printShipsByCategory("Barca"), "printShipsByCategory should not throw.");
			assertDoesNotThrow(fleet::printFloatingShips, "printFloatingShips should not throw.");
			assertDoesNotThrow(fleet::printAllShips, "printAllShips should not throw.");
		}

		@Test
		@DisplayName("Should throw AssertionError when null arguments are passed")
		void testNullAssertions() {
			assertThrows(AssertionError.class, () -> fleet.addShip(null));
			assertThrows(AssertionError.class, () -> fleet.getShipsLike(null));
			assertThrows(AssertionError.class, () -> fleet.shipAt(null));
			assertThrows(AssertionError.class, () -> fleet.printShips(null));
			assertThrows(AssertionError.class, () -> fleet.printShipsByCategory(null));
		}
	}