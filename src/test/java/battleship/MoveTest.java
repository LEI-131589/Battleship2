package battleship;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Move.
 *
 * Author: Inês Marchante
 * Date: 2026-11-14 11:01
 *
 * Cyclomatic Complexity:
 * - constructor: 1
 * - toString(): 1
 * - getNumber(): 1
 * - getShots(): 1
 * - getShotResults(): 1
 * - processEnemyFire(boolean): 28 (estimated)
 */
class MoveTest {

    private Move move;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        move = new Move(1, new ArrayList<>(), new ArrayList<>());
    }

    @AfterEach
    void tearDown() {
        move = null;
    }

    // ---------------------------------------------------------
    // Auxiliary methods
    // ---------------------------------------------------------

    private IShip createShip(String category) {
        return new StubShip(category);
    }

    private IGame.ShotResult result(
            boolean valid,
            boolean repeated,
            IShip ship,
            boolean sunk) {

        return new IGame.ShotResult(valid, repeated, ship, sunk);
    }

    private IGame.ShotResult miss() {
        return result(true, false, null, false);
    }

    private IGame.ShotResult repeated() {
        return result(true, true, null, false);
    }

    private IGame.ShotResult invalid() {
        return result(false, false, null, false);
    }

    private IGame.ShotResult hit(String category) {
        return result(true, false, createShip(category), false);
    }

    private IGame.ShotResult sunk(String category) {
        return result(true, false, createShip(category), true);
    }

    private JsonNode execute(boolean verbose, IGame.ShotResult... results)
            throws Exception {

        move = new Move(1, new ArrayList<>(), List.of(results));

        String json = move.processEnemyFire(verbose);

        assertNotNull(json,
                "Error: processEnemyFire should return a non-null JSON string.");

        return mapper.readTree(json);
    }

    private void assertCounts(
            JsonNode json,
            int valid,
            int outside,
            int repeated,
            int missed) {

        assertEquals(valid, json.get("validShots").asInt(),
                "Error: incorrect number of valid shots.");

        assertEquals(outside, json.get("outsideShots").asInt(),
                "Error: incorrect number of outside shots.");

        assertEquals(repeated, json.get("repeatedShots").asInt(),
                "Error: incorrect number of repeated shots.");

        assertEquals(missed, json.get("missedShots").asInt(),
                "Error: incorrect number of missed shots.");
    }

    private int outside(int valid, int repeated) {
        return Game.NUMBER_SHOTS - valid - repeated;
    }

    // ---------------------------------------------------------
    // Constructor tests
    // ---------------------------------------------------------

    @Test
    @DisplayName("Constructor should initialize all attributes")
    void constructor() {

        List<IPosition> shots = new ArrayList<>();
        List<IGame.ShotResult> results = new ArrayList<>();

        Move newMove = new Move(5, shots, results);

        assertAll(
                () -> assertEquals(5, newMove.getNumber(),
                        "Error: move number should be 5."),

                () -> assertSame(shots, newMove.getShots(),
                        "Error: shots list should be the original list."),

                () -> assertSame(results, newMove.getShotResults(),
                        "Error: shot results should be the original list.")
        );
    }

    // ---------------------------------------------------------
    // toString() tests
    // ---------------------------------------------------------

    @Test
    @DisplayName("toString should return the move description")
    void toStringTest() {

        move = new Move(3,
                new ArrayList<>(List.of()),
                new ArrayList<>(List.of(miss())));

        assertEquals("Move{number=3, shots=0, results=1}",
                move.toString(),
                "Error: toString should return the expected move description.");
    }

    @Test
    @DisplayName("toString should throw NullPointerException when shots is null")
    void toStringNullShots() {

        move = new Move(1, null, new ArrayList<>());

        assertThrows(NullPointerException.class,
                () -> move.toString(),
                "Error: toString should throw NullPointerException when shots is null.");
    }

    @Test
    @DisplayName("toString should throw NullPointerException when results is null")
    void toStringNullResults() {

        move = new Move(1, new ArrayList<>(), null);

        assertThrows(NullPointerException.class,
                () -> move.toString(),
                "Error: toString should throw NullPointerException when results is null.");
    }

    // ---------------------------------------------------------
    // getNumber() tests
    // ---------------------------------------------------------

    @Test
    @DisplayName("getNumber should return the move number")
    void getNumber() {

        assertEquals(1, move.getNumber(),
                "Error: getNumber should return 1.");
    }

    // ---------------------------------------------------------
    // getShots() tests
    // ---------------------------------------------------------

    @Test
    @DisplayName("getShots should return the shots list")
    void getShots() {

        assertNotNull(move.getShots(),
                "Error: getShots should return a non-null list.");

        assertTrue(move.getShots().isEmpty(),
                "Error: shots list should initially be empty.");
    }

    // ---------------------------------------------------------
    // getShotResults() tests
    // ---------------------------------------------------------

    @Test
    @DisplayName("getShotResults should return the results list")
    void getShotResults() {

        assertNotNull(move.getShotResults(),
                "Error: getShotResults should return a non-null list.");

        assertTrue(move.getShotResults().isEmpty(),
                "Error: results list should initially be empty.");
    }

    // ---------------------------------------------------------
    // processEnemyFire() tests
    // ---------------------------------------------------------

    @Test
    @DisplayName("processEnemyFire1 - Empty results without verbose")
    void processEnemyFire1() throws Exception {

        JsonNode json = execute(false);

        assertCounts(json, 0, Game.NUMBER_SHOTS, 0, 0);

        assertEquals(0, json.get("sunkBoats").size(),
                "Error: there should be no sunk boats.");

        assertEquals(0, json.get("hitsOnBoats").size(),
                "Error: there should be no boat hits.");
    }

    @Test
    @DisplayName("processEnemyFire2 - Empty results with verbose")
    void processEnemyFire2() throws Exception {

        JsonNode json = execute(true);

        assertCounts(json, 0, Game.NUMBER_SHOTS, 0, 0);
    }

    @Test
    @DisplayName("processEnemyFire3 - Invalid shots should be ignored")
    void processEnemyFire3() throws Exception {

        JsonNode json = execute(true, invalid(), invalid());

        assertCounts(json, 0, Game.NUMBER_SHOTS, 0, 0);
    }

    @Test
    @DisplayName("processEnemyFire4 - Valid missed shot")
    void processEnemyFire4() throws Exception {

        JsonNode json = execute(true, miss());

        assertCounts(json, 1, outside(1, 0), 0, 1);
    }

    @Test
    @DisplayName("processEnemyFire5 - One repeated shot")
    void processEnemyFire5() throws Exception {

        JsonNode json = execute(true, repeated());

        assertCounts(json, 0, outside(0, 1), 1, 0);
    }

    @Test
    @DisplayName("processEnemyFire6 - Multiple repeated shots")
    void processEnemyFire6() throws Exception {

        JsonNode json = execute(true, repeated(), repeated());

        assertCounts(json, 0, outside(0, 2), 2, 0);
    }

    @Test
    @DisplayName("processEnemyFire7 - Valid and repeated shots")
    void processEnemyFire7() throws Exception {

        JsonNode json = execute(true, miss(), repeated());

        assertCounts(json, 1, outside(1, 1), 1, 1);
    }

    @Test
    @DisplayName("processEnemyFire8 - Multiple valid missed shots")
    void processEnemyFire8() throws Exception {

        JsonNode json = execute(true, miss(), miss());

        assertCounts(json, 2, outside(2, 0), 0, 2);
    }

    @Test
    @DisplayName("processEnemyFire9 - One hit on an unsunk ship")
    void processEnemyFire9() throws Exception {

        JsonNode json = execute(true, hit("Destroyer"));

        assertCounts(json, 1, outside(1, 0), 0, 0);

        assertEquals(1, json.get("hitsOnBoats").size(),
                "Error: one unsunk ship should appear in hitsOnBoats.");

        assertEquals("Destroyer",
                json.get("hitsOnBoats").get(0).get("type").asText(),
                "Error: the ship category should be Destroyer.");

        assertEquals(1,
                json.get("hitsOnBoats").get(0).get("hits").asInt(),
                "Error: the ship should have one hit.");
    }

    @Test
    @DisplayName("processEnemyFire10 - Multiple hits on the same unsunk ship")
    void processEnemyFire10() throws Exception {

        JsonNode json = execute(true,
                hit("Destroyer"),
                hit("Destroyer"));

        assertCounts(json, 2, outside(2, 0), 0, 0);

        assertEquals(2, json.get("hitsOnBoats").get(0).get("hits").asInt(),
                "Error: the Destroyer should have two hits.");
    }

    @Test
    @DisplayName("processEnemyFire11 - One sunk ship")
    void processEnemyFire11() throws Exception {

        JsonNode json = execute(true, sunk("Carrier"));

        assertCounts(json, 1, outside(1, 0), 0, 0);

        assertEquals(1, json.get("sunkBoats").size(),
                "Error: one sunk ship should be registered.");

        assertEquals("Carrier",
                json.get("sunkBoats").get(0).get("type").asText(),
                "Error: the sunk ship should be a Carrier.");

        assertEquals(1,
                json.get("sunkBoats").get(0).get("count").asInt(),
                "Error: the sunk ship count should be one.");

        assertEquals(0, json.get("hitsOnBoats").size(),
                "Error: sunk ships should not appear in hitsOnBoats.");
    }

    @Test
    @DisplayName("processEnemyFire12 - Multiple sunk ships of the same type")
    void processEnemyFire12() throws Exception {

        JsonNode json = execute(true,
                sunk("Carrier"),
                sunk("Carrier"));

        assertEquals(2,
                json.get("sunkBoats").get(0).get("count").asInt(),
                "Error: two Carriers should be registered as sunk.");
    }

    @Test
    @DisplayName("processEnemyFire13 - Multiple different sunk ship types")
    void processEnemyFire13() throws Exception {

        JsonNode json = execute(true,
                sunk("Carrier"),
                sunk("Destroyer"));

        assertEquals(2, json.get("sunkBoats").size(),
                "Error: two different sunk ship types should be registered.");
    }

    @Test
    @DisplayName("processEnemyFire14 - Sunk and unsunk ships together")
    void processEnemyFire14() throws Exception {

        JsonNode json = execute(true,
                sunk("Carrier"),
                hit("Destroyer"));

        assertEquals(1, json.get("sunkBoats").size(),
                "Error: one ship should be registered as sunk.");

        assertEquals(1, json.get("hitsOnBoats").size(),
                "Error: one unsunk ship should be registered.");

        assertEquals("Destroyer",
                json.get("hitsOnBoats").get(0).get("type").asText(),
                "Error: only the Destroyer should appear in hitsOnBoats.");
    }

    @Test
    @DisplayName("processEnemyFire15 - Sunk ship should not appear in boat hits")
    void processEnemyFire15() throws Exception {

        JsonNode json = execute(true,
                hit("Carrier"),
                sunk("Carrier"));

        assertEquals(1, json.get("sunkBoats").size(),
                "Error: Carrier should appear in sunkBoats.");

        assertEquals(0, json.get("hitsOnBoats").size(),
                "Error: Carrier should not appear in hitsOnBoats.");
    }

    @Test
    @DisplayName("processEnemyFire16 - Invalid and valid shots together")
    void processEnemyFire16() throws Exception {

        JsonNode json = execute(true,
                invalid(),
                miss(),
                invalid());

        assertCounts(json, 1, outside(1, 0), 0, 1);
    }

    @Test
    @DisplayName("processEnemyFire17 - Hit and missed shots together")
    void processEnemyFire17() throws Exception {

        JsonNode json = execute(true,
                hit("Destroyer"),
                miss());

        assertCounts(json, 2, outside(2, 0), 0, 1);

        assertEquals(1, json.get("hitsOnBoats").size(),
                "Error: the Destroyer should be registered.");

    }

    @Test
    @DisplayName("processEnemyFire18 - Repeated shots with valid hits")
    void processEnemyFire18() throws Exception {

        JsonNode json = execute(true,
                hit("Destroyer"),
                repeated(),
                repeated());

        assertCounts(json, 1, outside(1, 2), 2, 0);
    }

    @Test
    @DisplayName("processEnemyFire19 - Verbose disabled with sunk ship")
    void processEnemyFire19() throws Exception {

        JsonNode json = execute(false, sunk("Carrier"));

        assertEquals(1, json.get("sunkBoats").size(),
                "Error: sunk ship information should be returned even without verbose.");
    }

    @Test
    @DisplayName("processEnemyFire20 - Verbose disabled with repeated shots")
    void processEnemyFire20() throws Exception {

        JsonNode json = execute(false, repeated());

        assertCounts(json, 0, outside(0, 1), 1, 0);
    }

    @Test
    @DisplayName("processEnemyFire21 - Repeated shots and invalid shots")
    void processEnemyFire21() throws Exception {

        JsonNode json = execute(true,
                repeated(),
                invalid());

        assertCounts(json, 0, outside(0, 1), 1, 0);
    }

    @Test
    @DisplayName("processEnemyFire22 - Multiple hits on different ships")
    void processEnemyFire22() throws Exception {

        JsonNode json = execute(true,
                hit("Carrier"),
                hit("Destroyer"));

        assertEquals(2, json.get("hitsOnBoats").size(),
                "Error: both unsunk ships should be registered.");
    }

    @Test
    @DisplayName("processEnemyFire23 - Multiple hits and sunk ships")
    void processEnemyFire23() throws Exception {

        JsonNode json = execute(true,
                hit("Destroyer"),
                hit("Destroyer"),
                sunk("Carrier"),
                sunk("Carrier"));

        assertEquals(1, json.get("sunkBoats").size(),
                "Error: only Carrier should appear in sunkBoats.");

        assertEquals(1, json.get("hitsOnBoats").size(),
                "Error: only Destroyer should appear in hitsOnBoats.");

        assertEquals(2,
                json.get("hitsOnBoats").get(0).get("hits").asInt(),
                "Error: Destroyer should have two hits.");
    }

    @Test
    @DisplayName("processEnemyFire24 - Null shot result should throw exception")
    void processEnemyFire24() {

        move = new Move(1, new ArrayList<>(),
                java.util.Arrays.asList((IGame.ShotResult) null));

        assertThrows(NullPointerException.class,
                () -> move.processEnemyFire(false),
                "Error: null shot result should throw NullPointerException.");
    }

    @Test
    @DisplayName("processEnemyFire25 - Null shot results list should throw exception")
    void processEnemyFire25() {

        move = new Move(1, new ArrayList<>(), null);

        assertThrows(NullPointerException.class,
                () -> move.processEnemyFire(false),
                "Error: null shot results list should throw NullPointerException.");
    }

    @Test
    @DisplayName("processEnemyFire26 - No valid shots and no repeated shots")
    void processEnemyFire26() throws Exception {

        JsonNode json = execute(true,
                invalid(),
                invalid());

        assertCounts(json, 0, Game.NUMBER_SHOTS, 0, 0);
    }

    @Test
    @DisplayName("processEnemyFire27 - Valid shots without sunk boats")
    void processEnemyFire27() throws Exception {

        JsonNode json = execute(true,
                hit("Destroyer"),
                miss());

        assertEquals(0, json.get("sunkBoats").size(),
                "Error: no boats should be registered as sunk.");

        assertEquals(1, json.get("hitsOnBoats").size(),
                "Error: the unsunk Destroyer should be registered.");
    }

    @Test
    @DisplayName("processEnemyFire28 - Empty collections in JSON response")
    void processEnemyFire28() throws Exception {

        JsonNode json = execute(false, invalid());

        assertTrue(json.get("sunkBoats").isArray(),
                "Error: sunkBoats should always be a JSON array.");

        assertTrue(json.get("hitsOnBoats").isArray(),
                "Error: hitsOnBoats should always be a JSON array.");

        assertEquals(0, json.get("sunkBoats").size(),
                "Error: sunkBoats should be empty.");

        assertEquals(0, json.get("hitsOnBoats").size(),
                "Error: hitsOnBoats should be empty.");
    }

    @Test
    @DisplayName("processEnemyFire29 - Simula erro de serialização do Jackson sem usar Mockito")
    void processEnemyFire29() {
        // Criamos uma versão anónima da classe Move apenas para este teste
        Move badMove = new Move(1, new ArrayList<>(), new ArrayList<>()) {
            @Override
            protected ObjectMapper getObjectMapper() {
                // Injetamos um ObjectMapper defeituoso que falha propositadamente
                return new ObjectMapper() {
                    @Override
                    public String writeValueAsString(Object value) throws com.fasterxml.jackson.core.JsonProcessingException {
                        throw new com.fasterxml.jackson.core.JsonProcessingException("Erro forçado sem Mockito") {};
                    }
                };
            }
        };

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> badMove.processEnemyFire(false),
                "Error: processEnemyFire should throw RuntimeException when JSON serialization fails.");

        assertTrue(exception.getMessage().contains("Erro ao serializar"),
                "Error: unexpected exception message.");
    }

    // ===================================================================================
    // StubShip - Simulação da interface IShip para os testes
    // ===================================================================================

    private static class StubShip implements IShip {
        private final String category;

        public StubShip(String category) {
            this.category = category;
        }

        @Override public String getCategory() { return category; }
        @Override public Integer getSize() { return 0; }
        @Override public List<IPosition> getPositions() { return null; }
        @Override public List<IPosition> getAdjacentPositions() { return null; }
        @Override public IPosition getPosition() { return null; }
        @Override public Compass getBearing() { return null; }
        @Override public boolean stillFloating() { return false; }

        @Override public int getTopMostPos() { return 0; }
        @Override public int getBottomMostPos() { return 0; }
        @Override public int getLeftMostPos() { return 0; }
        @Override public int getRightMostPos() { return 0; }

        @Override public boolean occupies(IPosition pos) { return false; }
        @Override public boolean tooCloseTo(IShip other) { return false; }
        @Override public boolean tooCloseTo(IPosition pos) { return false; }
        @Override public boolean isInsideBoard() { return false; }

        @Override public void shoot(IPosition pos) {}
        @Override public void sink() {}
    }
}