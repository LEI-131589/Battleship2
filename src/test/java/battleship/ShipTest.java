package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for abstract class Ship (instantiated via concrete subclasses).
 * Author: mariainesrodrigues
 * Date: 2026-11-14 14:25
 * Cyclomatic Complexity:
 * constructor: 1
 * buildShip(): 9
 * setPositionsForJackson(): 1
 * getCategory(): 1
 * getPositions(): 1
 * getAdjacentPositions(): 5
 * getPosition(): 1
 * getBearing(): 1
 * getSize(): 1
 * stillFloating(): 3
 * getTopMostPos(): 3
 * getBottomMostPos(): 3
 * getLeftMostPos(): 3
 * getRightMostPos(): 3
 * occupies(): 4
 * tooCloseTo(IShip): 4
 * tooCloseTo(IPosition): 4
 * isInsideBoard(): 5
 * shoot(): 5
 * sink(): 2
 * toString(): 1
 */
public class ShipTest {

    private Ship ship;
    private Ship shipBorder;

    @BeforeEach
    void setUp() {
        // Instantiate abstract class Ship using concrete subclass Barge
        ship = new Barge(Compass.NORTH, new Position(5, 5));
        shipBorder = new Barge(Compass.NORTH, new Position(0, 0));
    }

    @AfterEach
    void tearDown() {
        ship = null;
        shipBorder = null;
    }

    // --- constructor (CC = 1) ---

    @Test
    @DisplayName("Ship() constructor initializes fields and throws NullPointerException on null arguments")
    void Ship() {
        assertAll("Constructor field and null checks",
                () -> assertNotNull(ship, "Error: Ship instance should not be null"),
                () -> assertEquals("Barca", ship.getCategory(), "Error: Ship category should be 'Barca'"),
                () -> assertEquals(Compass.NORTH, ship.getBearing(), "Error: Ship bearing should be NORTH"),
                () -> assertEquals(1, ship.getSize(), "Error: Ship size should be 1"),
                () -> assertFalse(ship.getPositions().isEmpty(), "Error: Ship positions should not be empty"),
                () -> assertThrows(NullPointerException.class,
                        () -> new Barge(null, new Position(0, 0)),
                        "Error: expected NullPointerException when bearing is null"),
                () -> assertThrows(NullPointerException.class,
                        () -> new Barge(Compass.NORTH, null),
                        "Error: expected NullPointerException when position is null")
        );
    }

    // --- buildShip() (CC = 9) ---

    @Test
    @DisplayName("buildShip() path 1: throws AssertionError when shipKind is null")
    void buildShip1() {
        assertThrows(AssertionError.class,
                () -> Ship.buildShip(null, Compass.NORTH, new Position(2, 2)),
                "Error: expected AssertionError when shipKind is null");
    }

    @Test
    @DisplayName("buildShip() path 2: throws AssertionError when bearing is null")
    void buildShip2() {
        assertThrows(AssertionError.class,
                () -> Ship.buildShip("barca", null, new Position(2, 2)),
                "Error: expected AssertionError when bearing is null");
    }

    @Test
    @DisplayName("buildShip() path 3: throws AssertionError when position is null")
    void buildShip3() {
        assertThrows(AssertionError.class,
                () -> Ship.buildShip("barca", Compass.NORTH, null),
                "Error: expected AssertionError when position is null");
    }

    @Test
    @DisplayName("buildShip() path 4: creates Barge when shipKind is 'barca'")
    void buildShip4() {
        Ship s = Ship.buildShip("barca", Compass.NORTH, new Position(2, 2));
        assertInstanceOf(Barge.class, s, "Error: expected instance of Barge");
    }

    @Test
    @DisplayName("buildShip() path 5: creates Caravel when shipKind is 'caravela'")
    void buildShip5() {
        Ship s = Ship.buildShip("caravela", Compass.NORTH, new Position(2, 2));
        assertInstanceOf(Caravel.class, s, "Error: expected instance of Caravel");
    }

    @Test
    @DisplayName("buildShip() path 6: creates Carrack when shipKind is 'nau'")
    void buildShip6() {
        Ship s = Ship.buildShip("nau", Compass.NORTH, new Position(2, 2));
        assertInstanceOf(Carrack.class, s, "Error: expected instance of Carrack");
    }

    @Test
    @DisplayName("buildShip() path 7: creates Frigate when shipKind is 'fragata'")
    void buildShip7() {
        Ship s = Ship.buildShip("fragata", Compass.NORTH, new Position(2, 2));
        assertInstanceOf(Frigate.class, s, "Error: expected instance of Frigate");
    }

    @Test
    @DisplayName("buildShip() path 8: creates Galleon when shipKind is 'galeao'")
    void buildShip8() {
        Ship s = Ship.buildShip("galeao", Compass.NORTH, new Position(2, 2));
        assertInstanceOf(Galleon.class, s, "Error: expected instance of Galleon");
    }

    @Test
    @DisplayName("buildShip() path 9: returns null when shipKind is unknown")
    void buildShip9() {
        Ship s = Ship.buildShip("submarino", Compass.NORTH, new Position(2, 2));
        assertNull(s, "Error: expected null for unknown shipKind");
    }

    // --- setPositionsForJackson() (CC = 1) ---

    @Test
    @DisplayName("setPositionsForJackson() updates internal positions list from JSON")
    void setPositionsForJackson() {
        List<IPosition> customPositions = List.of(new Position(1, 1), new Position(1, 2));
        ship.setPositionsForJackson(customPositions);
        assertAll("Jackson positions update",
                () -> assertEquals(2, ship.getPositions().size(), "Error: expected 2 positions after update"),
                () -> assertEquals(new Position(1, 1), ship.getPositions().get(0), "Error: expected first position (1,1)")
        );
    }

    // --- getCategory() (CC = 1) ---

    @Test
    @DisplayName("getCategory() returns the category name of the ship")
    void getCategory() {
        assertEquals("Barca", ship.getCategory(), "Error: Ship category should be 'Barca'");
    }

    // --- getPositions() (CC = 1) ---

    @Test
    @DisplayName("getPositions() returns the list of occupied positions")
    void getPositions() {
        List<IPosition> positions = ship.getPositions();
        assertAll("getPositions verification",
                () -> assertNotNull(positions, "Error: Ship positions should not be null"),
                () -> assertEquals(1, positions.size(), "Error: Ship should have exactly one position"),
                () -> assertEquals(5, positions.get(0).getRow(), "Error: Position row should be 5"),
                () -> assertEquals(5, positions.get(0).getColumn(), "Error: Position column should be 5")
        );
    }

    // --- getAdjacentPositions() (CC = 5) ---

    @Test
    @DisplayName("getAdjacentPositions() path 1: returns 8 adjacent positions for a central single-cell ship")
    void getAdjacentPositions1() {
        assertEquals(8, ship.getAdjacentPositions().size(),
                "Error: expected 8 adjacent positions for a Barge at (5,5)");
    }

    @Test
    @DisplayName("getAdjacentPositions() path 2: returns 3 adjacent positions for a corner single-cell ship")
    void getAdjacentPositions2() {
        assertEquals(3, shipBorder.getAdjacentPositions().size(),
                "Error: expected 3 adjacent positions for a Barge at corner (0,0)");
    }

    @Test
    @DisplayName("getAdjacentPositions() path 3: excludes self positions and deduplicates neighbors on multi-cell ship")
    void getAdjacentPositions3() {
        Ship verticalCaravel = new Caravel(Compass.NORTH, new Position(2, 4));
        List<IPosition> adjacents = verticalCaravel.getAdjacentPositions();
        assertAll("Multi-cell adjacent filtering",
                () -> assertEquals(10, adjacents.size(), "Error: expected 10 unique adjacent positions for vertical Caravel"),
                () -> assertFalse(adjacents.contains(new Position(2, 4)), "Error: (2,4) belongs to ship and must be excluded"),
                () -> assertFalse(adjacents.contains(new Position(3, 4)), "Error: (3,4) belongs to ship and must be excluded")
        );
    }

    @Test
    @DisplayName("getAdjacentPositions() path 4: returns empty list when ship has no positions")
    void getAdjacentPositions4() {
        ship.setPositionsForJackson(List.of());
        assertTrue(ship.getAdjacentPositions().isEmpty(),
                "Error: expected empty list when ship has no positions");
    }

    @Test
    @DisplayName("getAdjacentPositions() path 5: includes valid diagonal and orthogonal neighbors")
    void getAdjacentPositions5() {
        List<IPosition> adjacents = ship.getAdjacentPositions();
        assertAll("Neighbor coordinates check",
                () -> assertTrue(adjacents.contains(new Position(4, 4)), "Error: expected diagonal neighbor (4,4)"),
                () -> assertTrue(adjacents.contains(new Position(5, 6)), "Error: expected orthogonal neighbor (5,6)")
        );
    }

    // --- getPosition() (CC = 1) ---

    @Test
    @DisplayName("getPosition() returns initial anchor position")
    void getPosition() {
        assertEquals(new Position(5, 5), ship.getPosition(), "Error: expected initial position (5,5)");
    }

    // --- getBearing() (CC = 1) ---

    @Test
    @DisplayName("getBearing() returns ship orientation bearing")
    void getBearing() {
        assertEquals(Compass.NORTH, ship.getBearing(), "Error: Ship bearing should be NORTH");
    }

    // --- getSize() (CC = 1) ---

    @Test
    @DisplayName("getSize() returns number of cells occupied by ship")
    void getSize() {
        assertEquals(1, ship.getSize(), "Error: Ship size should be 1");
    }

    // --- stillFloating() (CC = 3) ---

    @Test
    @DisplayName("stillFloating() path 1: returns true when all positions are intact")
    void stillFloating1() {
        assertTrue(ship.stillFloating(), "Error: Ship should still be floating when intact");
    }

    @Test
    @DisplayName("stillFloating() path 2: returns true when multi-cell ship is only partially hit")
    void stillFloating2() {
        Ship caravel = new Caravel(Compass.NORTH, new Position(2, 4));
        caravel.shoot(new Position(2, 4));
        assertTrue(caravel.stillFloating(), "Error: partially hit Caravel should still be floating");
    }

    @Test
    @DisplayName("stillFloating() path 3: returns false when all positions are hit")
    void stillFloating3() {
        ship.getPositions().get(0).shoot();
        assertFalse(ship.stillFloating(), "Error: Ship should no longer float after all cells are hit");
    }

    // --- getTopMostPos() (CC = 3) ---

    @Test
    @DisplayName("getTopMostPos() path 1: returns single row for single-cell ship")
    void getTopMostPos1() {
        assertEquals(5, ship.getTopMostPos(), "Error: topmost position should be 5");
    }

    @Test
    @DisplayName("getTopMostPos() path 2: updates minimum row when subsequent cell has smaller row")
    void getTopMostPos2() {
        Ship caravel = new Caravel(Compass.NORTH, new Position(4, 2));
        caravel.setPositionsForJackson(List.of(new Position(4, 2), new Position(2, 2)));
        assertEquals(2, caravel.getTopMostPos(), "Error: topmost position should be 2");
    }

    @Test
    @DisplayName("getTopMostPos() path 3: keeps initial row when subsequent cells have larger rows")
    void getTopMostPos3() {
        Ship verticalCaravel = new Caravel(Compass.NORTH, new Position(2, 4));
        assertEquals(2, verticalCaravel.getTopMostPos(), "Error: topmost position should be 2");
    }

    // --- getBottomMostPos() (CC = 3) ---

    @Test
    @DisplayName("getBottomMostPos() path 1: returns single row for single-cell ship")
    void getBottomMostPos1() {
        assertEquals(5, ship.getBottomMostPos(), "Error: bottommost position should be 5");
    }

    @Test
    @DisplayName("getBottomMostPos() path 2: updates maximum row when subsequent cell has larger row")
    void getBottomMostPos2() {
        Ship verticalCaravel = new Caravel(Compass.NORTH, new Position(2, 4));
        assertEquals(3, verticalCaravel.getBottomMostPos(), "Error: bottommost position should be 3");
    }

    @Test
    @DisplayName("getBottomMostPos() path 3: keeps initial row when subsequent cells have smaller rows")
    void getBottomMostPos3() {
        ship.setPositionsForJackson(List.of(new Position(4, 2), new Position(2, 2)));
        assertEquals(4, ship.getBottomMostPos(), "Error: bottommost position should be 4");
    }

    // --- getLeftMostPos() (CC = 3) ---

    @Test
    @DisplayName("getLeftMostPos() path 1: returns single column for single-cell ship")
    void getLeftMostPos1() {
        assertEquals(5, ship.getLeftMostPos(), "Error: leftmost position should be 5");
    }

    @Test
    @DisplayName("getLeftMostPos() path 2: updates minimum column when subsequent cell has smaller column")
    void getLeftMostPos2() {
        Ship caravel = new Caravel(Compass.EAST, new Position(4, 4));
        caravel.setPositionsForJackson(List.of(new Position(4, 4), new Position(4, 2)));
        assertEquals(2, caravel.getLeftMostPos(), "Error: leftmost position should be 2");
    }

    @Test
    @DisplayName("getLeftMostPos() path 3: keeps initial column when subsequent cells have larger columns")
    void getLeftMostPos3() {
        Ship horizontalCarrack = new Carrack(Compass.EAST, new Position(4, 2));
        assertEquals(2, horizontalCarrack.getLeftMostPos(), "Error: leftmost position should be 2");
    }

    // --- getRightMostPos() (CC = 3) ---

    @Test
    @DisplayName("getRightMostPos() path 1: returns single column for single-cell ship")
    void getRightMostPos1() {
        assertEquals(5, ship.getRightMostPos(), "Error: rightmost position should be 5");
    }

    @Test
    @DisplayName("getRightMostPos() path 2: updates maximum column when subsequent cell has larger column")
    void getRightMostPos2() {
        Ship horizontalCarrack = new Carrack(Compass.EAST, new Position(4, 2));
        assertEquals(4, horizontalCarrack.getRightMostPos(), "Error: rightmost position should be 4");
    }

    @Test
    @DisplayName("getRightMostPos() path 3: keeps initial column when subsequent cells have smaller columns")
    void getRightMostPos3() {
        ship.setPositionsForJackson(List.of(new Position(4, 4), new Position(4, 2)));
        assertEquals(4, ship.getRightMostPos(), "Error: rightmost position should be 4");
    }

    // --- occupies() (CC = 4) ---

    @Test
    @DisplayName("occupies() path 1: throws AssertionError when position is null")
    void occupies1() {
        assertThrows(AssertionError.class, () -> ship.occupies(null),
                "Error: expected AssertionError when position is null");
    }

    @Test
    @DisplayName("occupies() path 2: returns true when position matches first cell")
    void occupies2() {
        assertTrue(ship.occupies(new Position(5, 5)), "Error: Ship should occupy position (5, 5)");
    }

    @Test
    @DisplayName("occupies() path 3: returns true when position matches later cell on multi-cell ship")
    void occupies3() {
        Ship horizontalCarrack = new Carrack(Compass.EAST, new Position(4, 2));
        assertTrue(horizontalCarrack.occupies(new Position(4, 4)),
                "Error: Carrack should occupy third cell (4, 4)");
    }

    @Test
    @DisplayName("occupies() path 4: returns false when position is not occupied by ship")
    void occupies4() {
        assertFalse(ship.occupies(new Position(1, 1)), "Error: Ship should not occupy position (1, 1)");
    }

    // --- tooCloseTo(IShip) (CC = 4) ---

    @Test
    @DisplayName("tooCloseTo(IShip) path 1: throws AssertionError when other ship is null")
    void tooCloseToShip1() {
        assertThrows(AssertionError.class, () -> ship.tooCloseTo((IShip) null),
                "Error: expected AssertionError when other ship is null");
    }

    @Test
    @DisplayName("tooCloseTo(IShip) path 2: returns true when ships are adjacent")
    void tooCloseToShip2() {
        Ship nearbyShip = new Barge(Compass.NORTH, new Position(5, 6));
        assertTrue(ship.tooCloseTo(nearbyShip), "Error: Ships at (5,5) and (5,6) should be too close");
    }

    @Test
    @DisplayName("tooCloseTo(IShip) path 3: returns false when ships are far apart")
    void tooCloseToShip3() {
        Ship farShip = new Barge(Compass.NORTH, new Position(9, 9));
        assertFalse(ship.tooCloseTo(farShip), "Error: Ships at (5,5) and (9,9) should not be too close");
    }

    @Test
    @DisplayName("tooCloseTo(IShip) path 4: returns false when other ship has no positions")
    void tooCloseToShip4() {
        Ship emptyShip = new Barge(Compass.NORTH, new Position(9, 9));
        emptyShip.setPositionsForJackson(List.of());
        assertFalse(ship.tooCloseTo(emptyShip), "Error: expected false when other ship has empty positions");
    }

    // --- tooCloseTo(IPosition) (CC = 4) ---

    @Test
    @DisplayName("tooCloseTo(IPosition) path 1: throws AssertionError when position is null")
    void tooCloseToPosition1() {
        assertThrows(AssertionError.class, () -> ship.tooCloseTo((IPosition) null),
                "Error: expected AssertionError when position is null");
    }

    @Test
    @DisplayName("tooCloseTo(IPosition) path 2: returns true when position is adjacent to first cell")
    void tooCloseToPosition2() {
        assertTrue(ship.tooCloseTo(new Position(5, 6)),
                "Error: Ship at (5,5) should be too close to adjacent position (5,6)");
    }

    @Test
    @DisplayName("tooCloseTo(IPosition) path 3: returns true when position is adjacent to a later cell")
    void tooCloseToPosition3() {
        Ship horizontalCarrack = new Carrack(Compass.EAST, new Position(4, 2));
        assertTrue(horizontalCarrack.tooCloseTo(new Position(4, 5)),
                "Error: Carrack should be too close to position (4,5) adjacent to its last cell");
    }

    @Test
    @DisplayName("tooCloseTo(IPosition) path 4: returns false when position is not adjacent")
    void tooCloseToPosition4() {
        assertFalse(ship.tooCloseTo(new Position(7, 7)),
                "Error: Ship at (5,5) should not be too close to (7,7)");
    }

    // --- isInsideBoard() (CC = 5) ---

    @Test
    @DisplayName("isInsideBoard() path 1: returns true when all four boundary conditions hold")
    void isInsideBoard1() {
        assertTrue(ship.isInsideBoard(), "Error: Ship at (5,5) should be inside board");
    }

    @Test
    @DisplayName("isInsideBoard() path 2: returns false when getLeftMostPos() < 0")
    void isInsideBoard2() {
        Ship outsideLeft = new Barge(Compass.NORTH, new Position(5, -1));
        assertFalse(outsideLeft.isInsideBoard(), "Error: Ship at column -1 should be outside board");
    }

    @Test
    @DisplayName("isInsideBoard() path 3: returns false when getRightMostPos() > BOARD_SIZE - 1")
    void isInsideBoard3() {
        Ship outsideRight = new Barge(Compass.NORTH, new Position(5, Game.BOARD_SIZE));
        assertFalse(outsideRight.isInsideBoard(), "Error: Ship at column BOARD_SIZE should be outside board");
    }

    @Test
    @DisplayName("isInsideBoard() path 4: returns false when getTopMostPos() < 0")
    void isInsideBoard4() {
        Ship outsideTop = new Barge(Compass.NORTH, new Position(-1, 5));
        assertFalse(outsideTop.isInsideBoard(), "Error: Ship at row -1 should be outside board");
    }

    @Test
    @DisplayName("isInsideBoard() path 5: returns false when getBottomMostPos() > BOARD_SIZE - 1")
    void isInsideBoard5() {
        Ship outsideBottom = new Barge(Compass.NORTH, new Position(Game.BOARD_SIZE, 5));
        assertFalse(outsideBottom.isInsideBoard(), "Error: Ship at row BOARD_SIZE should be outside board");
    }

    // --- shoot() (CC = 5) ---

    @Test
    @DisplayName("shoot() path 1: throws AssertionError when target position is null")
    void shoot1() {
        assertThrows(AssertionError.class, () -> ship.shoot(null),
                "Error: expected AssertionError when target position is null");
    }

    @Test
    @DisplayName("shoot() path 2: throws AssertionError when target position is outside board")
    void shoot2() {
        assertThrows(AssertionError.class, () -> ship.shoot(new Position(-1, -1)),
                "Error: expected AssertionError when target position is outside board");
    }

    @Test
    @DisplayName("shoot() path 3: marks matching position as hit")
    void shoot3() {
        ship.shoot(new Position(5, 5));
        assertTrue(ship.getPositions().get(0).isHit(), "Error: Position (5,5) should be marked as hit");
    }

    @Test
    @DisplayName("shoot() path 4: leaves positions unhit when shot misses ship")
    void shoot4() {
        ship.shoot(new Position(0, 0));
        assertFalse(ship.getPositions().get(0).isHit(), "Error: Position (5,5) should not be hit when shooting (0,0)");
    }

    @Test
    @DisplayName("shoot() path 5: handles empty positions list without error")
    void shoot5() {
        ship.setPositionsForJackson(List.of());
        assertDoesNotThrow(() -> ship.shoot(new Position(5, 5)),
                "Error: shoot() should not throw when positions list is empty");
    }

    // --- sink() (CC = 2) ---

    @Test
    @DisplayName("sink() path 1: hits all cells of a multi-cell ship until it is no longer floating")
    void sink1() {
        Ship caravel = new Caravel(Compass.NORTH, new Position(2, 2));
        assertTrue(caravel.stillFloating(), "Error: Caravel should initially be floating");
        caravel.sink();
        assertAll("Sunk multi-cell ship state",
                () -> assertFalse(caravel.stillFloating(), "Error: Caravel should not float after sink()"),
                () -> assertTrue(caravel.getPositions().get(0).isHit(), "Error: first cell should be hit"),
                () -> assertTrue(caravel.getPositions().get(1).isHit(), "Error: second cell should be hit")
        );
    }

    @Test
    @DisplayName("sink() path 2: completes cleanly when positions list is empty")
    void sink2() {
        ship.setPositionsForJackson(List.of());
        assertDoesNotThrow(() -> ship.sink(), "Error: sink() should not throw on empty positions list");
    }

    // --- toString() (CC = 1) ---

    @Test
    @DisplayName("toString() formats category, bearing, and classic position")
    void testToString() {
        assertEquals("[Barca n F6]", ship.toString(), "Error: expected formatted string '[Barca n F6]'");
    }
}