package battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Test class for Ship.
 * Author: ${user.name}
 * Date: ${current_date}
 * Time: ${current_time}
 * Cyclomatic Complexity for each method:
 * - Constructor: 1
 * - getCategory: 1
 * - getSize: 1
 * - getBearing: 1
 * - getPositions: 1
 * - stillFloating: 2
 * - shoot: 2
 * - occupies: 2
 * - tooCloseTo (IShip): 2
 * - tooCloseTo (IPosition): 2
 * - getTopMostPos: 2
 * - getBottomMostPos: 2
 * - getLeftMostPos: 2
 * - getRightMostPos: 2
 */
public class ShipTest {

    private Ship ship, shipBorder;

    @BeforeEach
    void setUp() {
        // Since Ship is abstract, instantiate it with a concrete subclass (e.g., Barge)
        ship = new Barge(Compass.NORTH, new Position(5, 5));
        shipBorder = new Barge(Compass.NORTH, new Position(0, 0));
    }

    @AfterEach
    void tearDown() {
        ship = null;
    }

    /**
     * Test for the constructor.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testConstructor() {
        assertNotNull(ship, "Error: Ship instance should not be null.");
        assertEquals("Barca", ship.getCategory(), "Error: Ship category is incorrect.");
        assertEquals(Compass.NORTH, ship.getBearing(), "Error: Ship bearing is incorrect.");
        assertEquals(1, ship.getSize(), "Error: Ship size is incorrect.");
        assertFalse(ship.getPositions().isEmpty(), "Error: Ship positions should not be empty.");
    }

    /**
     * Test for the getCategory method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetCategory() {
        assertEquals("Barca", ship.getCategory(), "Error: Ship category should be 'Barca'.");
    }

    /**
     * Test for the getSize method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetSize() {
        assertEquals(1, ship.getSize(), "Error: Ship size should be 1.");
    }

    /**
     * Test for the getBearing method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetBearing() {
        assertEquals(Compass.NORTH, ship.getBearing(), "Error: Ship bearing should be NORTH.");
    }

    /**
     * Test for the getPositions method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetPositions() {
        List<IPosition> positions = ship.getPositions();
        assertNotNull(positions, "Error: Ship positions should not be null.");
        assertEquals(1, positions.size(), "Error: Ship should have exactly one position.");
        assertEquals(5, positions.get(0).getRow(), "Error: Position's row should be 5.");
        assertEquals(5, positions.get(0).getColumn(), "Error: Position's column should be 5.");
    }

    /**
     * Test for the stillFloating method (all positions intact).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testStillFloating1() {
        assertTrue(ship.stillFloating(), "Error: Ship should still be floating.");
    }

    /**
     * Test for the stillFloating method (all positions hit).
     */
    @Test
    void testStillFloating2() {
        ship.getPositions().get(0).shoot();
        assertFalse(ship.stillFloating(), "Error: Ship should no longer be floating after being hit.");
    }

    /**
     * Test for the shoot method (valid position).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testShoot1() {
        Position target = new Position(5, 5);
        ship.shoot(target);
        assertTrue(ship.getPositions().get(0).isHit(), "Error: Position should be marked as hit.");
    }

    /**
     * Test for the shoot method (invalid position).
     */
    @Test
    void testShoot2() {
        Position target = new Position(0, 0);
        ship.shoot(target); // No exception expected
        assertFalse(ship.getPositions().get(0).isHit(), "Error: Position should not be marked as hit for an invalid target.");
    }

    /**
     * Test for the occupies method (position occupied).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testOccupies1() {
        Position pos = new Position(5, 5);
        assertTrue(ship.occupies(pos), "Error: Ship should occupy position (5, 5).");
    }

    /**
     * Test for the occupies method (position not occupied).
     */
    @Test
    void testOccupies2() {
        Position pos = new Position(1, 1);
        assertFalse(ship.occupies(pos), "Error: Ship should not occupy position (1, 1).");
    }

    /**
     * Test for the tooCloseTo method with another IShip (ships too close).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testTooCloseToShip1() {
        Ship nearbyShip = new Barge(Compass.NORTH, new Position(5, 6));
        assertTrue(ship.tooCloseTo(nearbyShip), "Error: Ships should be too close.");
    }

    /**
     * Test for the tooCloseTo method with another IShip (ships not close).
     */
    @Test
    void testTooCloseToShip2() {
        Ship farShip = new Barge(Compass.NORTH, new Position(10, 10));
        assertFalse(ship.tooCloseTo(farShip), "Error: Ships should not be too close.");
    }

    /**
     * Test for the tooCloseTo method with an IPosition (positions adjacent).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testTooCloseToPosition1() {
        Position pos = new Position(5, 6); // Adjacent position
        assertTrue(ship.tooCloseTo(pos), "Error: Ship should be too close to the given position.");
    }

    /**
     * Test for the tooCloseTo method with an IPosition (positions not adjacent).
     */
    @Test
    void testTooCloseToPosition2() {
        Position pos = new Position(7, 7); // Non-adjacent position
        assertFalse(ship.tooCloseTo(pos), "Error: Ship should not be too close to the given position.");
    }

    /**
     * Test for the getTopMostPos method.
     * Cyclomatic Complexity: 2
     */
    @Test
    void testGetTopMostPos() {
        assertEquals(5, ship.getTopMostPos(), "Error: The topmost position should be 5.");
    }

    /**
     * Test for the getBottomMostPos method.
     * Cyclomatic Complexity: 2
     */
    @Test
    void testGetBottomMostPos() {
        assertEquals(5, ship.getBottomMostPos(), "Error: The bottommost position should be 5.");
    }

    /**
     * Test for the getLeftMostPos method.
     * Cyclomatic Complexity: 2
     */
    @Test
    void testGetLeftMostPos() {
        assertEquals(5, ship.getLeftMostPos(), "Error: The leftmost position should be 5.");
    }

    /**
     * Test for the getRightMostPos method.
     * Cyclomatic Complexity: 2
     */
    @Test
    void testGetRightMostPos() {
        assertEquals(5, ship.getRightMostPos(), "Error: The rightmost position should be 5.");
    }

    /**
     * Test for the getRightMostPos method.
     * Cyclomatic Complexity: 2
     */
    @Test
    void testGetAdjacentPositions() {
        assertEquals(8, ship.getAdjacentPositions().size(), "Error: The number of adjacent positions of a barge in the middle of the tray should be 8.");
        assertEquals(3, shipBorder.getAdjacentPositions().size(), "Error: The number of adjacent positions of a barge in the middle of the tray should be 8.");
    }

    @Test
    @DisplayName("Should build all valid ship types and return null for invalid kind")
    void testBuildShip() {
        Position pos = new Position(2, 2);
        assertInstanceOf(Barge.class, Ship.buildShip("barca", Compass.NORTH, pos));
        assertInstanceOf(Caravel.class, Ship.buildShip("caravela", Compass.NORTH, pos));
        assertInstanceOf(Carrack.class, Ship.buildShip("nau", Compass.NORTH, pos));
        assertInstanceOf(Frigate.class, Ship.buildShip("fragata", Compass.NORTH, pos));
        assertInstanceOf(Galleon.class, Ship.buildShip("galeao", Compass.NORTH, pos));
        assertNull(Ship.buildShip("submarino", Compass.NORTH, pos));

        assertThrows(AssertionError.class, () -> Ship.buildShip(null, Compass.NORTH, pos));
        assertThrows(AssertionError.class, () -> Ship.buildShip("barca", null, pos));
        assertThrows(AssertionError.class, () -> Ship.buildShip("barca", Compass.NORTH, null));
    }

    @Test
    @DisplayName("Should return initial anchor position and allow setting positions for Jackson")
    void testGetPositionAndSetPositionsForJackson() {
        assertEquals(new Position(5, 5), ship.getPosition());

        List<IPosition> customPositions = List.of(new Position(1, 1), new Position(1, 2));
        ship.setPositionsForJackson(customPositions);
        assertEquals(2, ship.getPositions().size());
        assertEquals(new Position(1, 1), ship.getPositions().get(0));
    }

    @Test
    @DisplayName("Should calculate extreme coordinates and adjacent positions for multi-cell ships")
    void testMultiCellShipExtremesAndAdjacents() {
        // Navio vertical (linhas 2 e 3, coluna 4)
        Ship verticalCaravel = new Caravel(Compass.NORTH, new Position(2, 4));
        assertEquals(2, verticalCaravel.getTopMostPos());
        assertEquals(3, verticalCaravel.getBottomMostPos());
        assertEquals(4, verticalCaravel.getLeftMostPos());
        assertEquals(4, verticalCaravel.getRightMostPos());

        // Cobre também getAdjacentPositions com sobreposição de adjacentes e posições do próprio navio
        List<IPosition> adjacents = verticalCaravel.getAdjacentPositions();
        assertEquals(10, adjacents.size());

        // Navio horizontal (linha 4, colunas 2, 3 e 4)
        Ship horizontalCarrack = new Carrack(Compass.EAST, new Position(4, 2));
        assertEquals(4, horizontalCarrack.getTopMostPos());
        assertEquals(4, horizontalCarrack.getBottomMostPos());
        assertEquals(2, horizontalCarrack.getLeftMostPos());
        assertEquals(4, horizontalCarrack.getRightMostPos());
    }

    @Test
    @DisplayName("Should verify if ship is completely inside the board across all boundaries")
    void testIsInsideBoard() {
        assertTrue(ship.isInsideBoard());

        Ship outsideTop = new Barge(Compass.NORTH, new Position(-1, 5));
        Ship outsideBottom = new Barge(Compass.NORTH, new Position(Game.BOARD_SIZE, 5));
        Ship outsideLeft = new Barge(Compass.NORTH, new Position(5, -1));
        Ship outsideRight = new Barge(Compass.NORTH, new Position(5, Game.BOARD_SIZE));

        assertFalse(outsideTop.isInsideBoard());
        assertFalse(outsideBottom.isInsideBoard());
        assertFalse(outsideLeft.isInsideBoard());
        assertFalse(outsideRight.isInsideBoard());
    }

    @Test
    @DisplayName("Should sink ship completely and format toString properly")
    void testSinkAndToString() {
        Ship caravel = new Caravel(Compass.NORTH, new Position(2, 2));
        assertTrue(caravel.stillFloating());

        caravel.sink();
        assertFalse(caravel.stillFloating());

        assertEquals("[Barca n F6]", ship.toString());
    }

    @Test
    @DisplayName("Should throw AssertionError on invalid/null arguments")
    void testAssertions() {
        assertThrows(AssertionError.class, () -> ship.occupies(null));
        assertThrows(AssertionError.class, () -> ship.tooCloseTo((IShip) null));
        assertThrows(AssertionError.class, () -> ship.tooCloseTo((IPosition) null));
        assertThrows(AssertionError.class, () -> ship.shoot(null));
        assertThrows(AssertionError.class, () -> ship.shoot(new Position(-1, -1)));
    }
}