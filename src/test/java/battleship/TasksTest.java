package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Tasks.
 * Author: mariainesrodrigues
 * Date: 2026-11-14 14:24
 * Cyclomatic Complexity:
 * constructor: 1
 * menu(): 23
 * menuHelp(): 1
 * buildFleet(): 4
 * readShip(): 1
 * readPosition(): 1
 * readClassicPosition(): 7
 * gravarJogoInterativo(): 5 (tested via menu)
 * carregarJogoInterativo(): 5 (tested via menu)
 * getConfiguredMapper(): 1 (tested via gravarJogo/carregarJogo)
 * gravarJogo(): 2
 * carregarJogo(): 5
 */
class TasksTest {

	private InputStream originalIn;
	private PrintStream originalOut;
	private PrintStream originalErr;
	private ByteArrayOutputStream outContent;
	private ByteArrayOutputStream errContent;

	@BeforeEach
	void setUp() {
		originalIn = System.in;
		originalOut = System.out;
		originalErr = System.err;
		outContent = new ByteArrayOutputStream();
		errContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@AfterEach
	void tearDown() {
		System.setIn(originalIn);
		System.setOut(originalOut);
		System.setErr(originalErr);
		outContent = null;
		errContent = null;
	}

	private void provideInput(String data) {
		System.setIn(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
	}

	private String validFleetInput() {
		return String.join("\n",
				"galeao 1 1 n",
				"fragata 0 5 e",
				"nau 3 5 e",
				"nau 5 0 e",
				"caravela 5 5 e",
				"caravela 7 0 e",
				"caravela 7 4 e",
				"barca 7 8 n",
				"barca 9 0 n",
				"barca 9 3 n",
				"barca 9 6 n"
		) + "\n";
	}

	// --- constructor (CC = 1) ---

	@Test
	@DisplayName("Tasks() constructor creates a valid Tasks instance")
	void Tasks() {
		Tasks tasks = new Tasks();
		assertNotNull(tasks, "Error: expected Tasks instance not to be null");
	}

	// --- menuHelp() (CC = 1) ---

	@Test
	@DisplayName("menuHelp() prints all available game commands to standard output")
	void menuHelp() {
		Tasks.menuHelp();
		String output = outContent.toString();
		assertAll("menuHelp output verification",
				() -> assertTrue(output.contains("AJUDA DO MENU"), "Error: expected header AJUDA DO MENU"),
				() -> assertTrue(output.contains("gerafrota"), "Error: expected command gerafrota"),
				() -> assertTrue(output.contains("desisto"), "Error: expected command desisto")
		);
	}

	// --- readPosition() (CC = 1) ---

	@Test
	@DisplayName("readPosition() reads row and column integers and creates a Position")
	void readPosition() {
		assertThrows(AssertionError.class, () -> Tasks.readPosition(null),
				"Error: expected AssertionError when Scanner is null");

		Scanner scanner = new Scanner("4 7");
		Position pos = Tasks.readPosition(scanner);
		assertAll("readPosition coordinates",
				() -> assertEquals(4, pos.getRow(), "Error: expected row 4"),
				() -> assertEquals(7, pos.getColumn(), "Error: expected column 7")
		);
	}

	// --- readShip() (CC = 1) ---

	@Test
	@DisplayName("readShip() parses ship kind, coordinates, and bearing into a Ship instance")
	void readShip() {
		assertThrows(AssertionError.class, () -> Tasks.readShip(null),
				"Error: expected AssertionError when Scanner is null");

		Scanner scanner = new Scanner("caravela 2 3 e");
		Ship ship = Tasks.readShip(scanner);
		assertAll("readShip result",
				() -> assertNotNull(ship, "Error: expected non-null Ship"),
				() -> assertEquals("Caravela", ship.getCategory(), "Error: expected category Caravela"),
				() -> assertEquals(Compass.EAST, ship.getBearing(), "Error: expected bearing EAST"),
				() -> assertEquals(new Position(2, 3), ship.getPosition(), "Error: expected position (2,3)")
		);
	}

	// --- buildFleet() (CC = 4) ---

	@Test
	@DisplayName("buildFleet() path 1: throws AssertionError when Scanner is null")
	void buildFleet1() {
		assertThrows(AssertionError.class, () -> Tasks.buildFleet(null),
				"Error: expected AssertionError when Scanner is null");
	}

	@Test
	@DisplayName("buildFleet() path 2: builds full fleet when 11 valid non-overlapping ships are provided")
	void buildFleet2() {
		Scanner scanner = new Scanner(validFleetInput());
		Fleet fleet = Tasks.buildFleet(scanner);
		assertEquals(IFleet.FLEET_SIZE, fleet.getShips().size(),
				"Error: expected built fleet to contain FLEET_SIZE (11) ships");
	}

	@Test
	@DisplayName("buildFleet() path 3: ignores unknown ship kinds and continues until 11 valid ships are added")
	void buildFleet3() {
		String inputWithUnknown = "submarino 0 0 n\n" + validFleetInput();
		Scanner scanner = new Scanner(inputWithUnknown);
		Fleet fleet = Tasks.buildFleet(scanner);
		assertEquals(IFleet.FLEET_SIZE, fleet.getShips().size(),
				"Error: expected fleet size 11 even after encountering an unknown ship");
	}

	@Test
	@DisplayName("buildFleet() path 4: rejects colliding/out-of-bounds ships and retries until 11 valid ships are added")
	void buildFleet4() {
		String inputWithCollision = "barca 0 0 n\n" + validFleetInput();
		Scanner scanner = new Scanner(inputWithCollision);
		Fleet fleet = Tasks.buildFleet(scanner);
		assertEquals(IFleet.FLEET_SIZE, fleet.getShips().size(),
				"Error: expected fleet size 11 after skipping colliding ship");
	}

	// --- readClassicPosition() (CC = 7) ---

	@Test
	@DisplayName("readClassicPosition() path 1: throws IllegalArgumentException when scanner has no tokens")
	void readClassicPosition1() {
		Scanner emptyScanner = new Scanner("");
		assertThrows(IllegalArgumentException.class, () -> Tasks.readClassicPosition(emptyScanner),
				"Error: expected IllegalArgumentException when input is empty");
	}

	@Test
	@DisplayName("readClassicPosition() path 2: parses compact uppercase coordinate like 'A3'")
	void readClassicPosition2() {
		IPosition pos = Tasks.readClassicPosition(new Scanner("A3"));
		assertEquals(new Position('A', 3), pos, "Error: expected Position('A', 3)");
	}

	@Test
	@DisplayName("readClassicPosition() path 3: parses compact lowercase coordinate like 'c5'")
	void readClassicPosition3() {
		IPosition pos = Tasks.readClassicPosition(new Scanner("c5"));
		assertEquals(new Position('C', 5), pos, "Error: expected Position('C', 5)");
	}

	@Test
	@DisplayName("readClassicPosition() path 4: parses space-separated coordinate like 'B 4'")
	void readClassicPosition4() {
		IPosition pos = Tasks.readClassicPosition(new Scanner("B 4"));
		assertEquals(new Position('B', 4), pos, "Error: expected Position('B', 4)");
	}

	@Test
	@DisplayName("readClassicPosition() path 5: throws IllegalArgumentException for invalid single-token format like '123'")
	void readClassicPosition5() {
		assertThrows(IllegalArgumentException.class, () -> Tasks.readClassicPosition(new Scanner("123")),
				"Error: expected IllegalArgumentException for numeric-only token");
	}

	@Test
	@DisplayName("readClassicPosition() path 6: throws IllegalArgumentException for invalid two-token format like 'AB 3'")
	void readClassicPosition6() {
		assertThrows(IllegalArgumentException.class, () -> Tasks.readClassicPosition(new Scanner("AB 3")),
				"Error: expected IllegalArgumentException when first token is not a single letter");
	}

	@Test
	@DisplayName("readClassicPosition() path 7: throws IllegalArgumentException for invalid alphanumeric mix like 'A3B'")
	void readClassicPosition7() {
		assertThrows(IllegalArgumentException.class, () -> Tasks.readClassicPosition(new Scanner("A3B")),
				"Error: expected IllegalArgumentException for malformed coordinate 'A3B'");
	}

	// --- gravarJogo() (CC = 2) ---

	@Test
	@DisplayName("gravarJogo() path 1: serializes Game object into a valid JSON file")
	void gravarJogo1(@TempDir Path tempDir) {
		Game game = new Game(Fleet.createRandom());
		File jsonFile = tempDir.resolve("saved_game.json").toFile();

		Tasks.gravarJogo(game, jsonFile.getAbsolutePath());

		assertAll("gravarJogo success state",
				() -> assertTrue(jsonFile.exists(), "Error: expected JSON file to be created"),
				() -> assertTrue(jsonFile.length() > 0, "Error: expected JSON file not to be empty"),
				() -> assertTrue(outContent.toString().contains("Jogo gravado com sucesso"),
						"Error: expected confirmation message in output")
		);
	}

	@Test
	@DisplayName("gravarJogo() path 2: handles IOException gracefully when target path is a directory")
	void gravarJogo2(@TempDir Path tempDir) {
		Game game = new Game(Fleet.createRandom());
		assertDoesNotThrow(() -> Tasks.gravarJogo(game, tempDir.toAbsolutePath().toString()),
				"Error: expected gravarJogo to catch IOException without throwing");
		assertTrue(outContent.toString().contains("Erro ao gravar o jogo"),
				"Error: expected error message in standard output");
	}

	// --- carregarJogo() (CC = 5) ---

	@Test
	@DisplayName("carregarJogo() path 1: loads a valid serialized Game from JSON file")
	void carregarJogo1(@TempDir Path tempDir) {
		Game originalGame = new Game(Fleet.createRandom());
		File jsonFile = tempDir.resolve("valid_game.json").toFile();
		Tasks.gravarJogo(originalGame, jsonFile.getAbsolutePath());

		Game loaded = Tasks.carregarJogo(jsonFile.getAbsolutePath());
		assertAll("carregarJogo valid outcome",
				() -> assertNotNull(loaded, "Error: expected loaded Game not to be null"),
				() -> assertNotNull(loaded.getMyFleet(), "Error: expected loaded myFleet not to be null"),
				() -> assertTrue(outContent.toString().contains("Jogo carregado com sucesso!"),
						"Error: expected success message in output")
		);
	}

	@Test
	@DisplayName("carregarJogo() path 2: returns null when JSON literal is 'null'")
	void carregarJogo2(@TempDir Path tempDir) throws Exception {
		Path nullJson = tempDir.resolve("null_game.json");
		Files.writeString(nullJson, "null");

		Game loaded = Tasks.carregarJogo(nullJson.toAbsolutePath().toString());
		assertAll("carregarJogo null JSON outcome",
				() -> assertNull(loaded, "Error: expected null when JSON content is 'null'"),
				() -> assertTrue(errContent.toString().contains("Ficheiro JSON inválido"),
						"Error: expected validation error on System.err")
		);
	}

	@Test
	@DisplayName("carregarJogo() path 3: returns null when Game JSON has missing fleets")
	void carregarJogo3(@TempDir Path tempDir) throws Exception {
		Path incompleteJson = tempDir.resolve("incomplete_game.json");
		Files.writeString(incompleteJson, "{\"@class\":\"battleship.Game\"}");

		Game loaded = Tasks.carregarJogo(incompleteJson.toAbsolutePath().toString());
		assertNull(loaded, "Error: expected null when myFleet or alienFleet is missing");
	}

	@Test
	@DisplayName("carregarJogo() path 4: returns null when JSON syntax is corrupted")
	void carregarJogo4(@TempDir Path tempDir) throws Exception {
		Path brokenJson = tempDir.resolve("broken.json");
		Files.writeString(brokenJson, "{ invalid json !!!");

		Game loaded = Tasks.carregarJogo(brokenJson.toAbsolutePath().toString());
		assertAll("carregarJogo corrupted JSON outcome",
				() -> assertNull(loaded, "Error: expected null for corrupted JSON"),
				() -> assertTrue(errContent.toString().contains("Não foi possível ler o JSON"),
						"Error: expected parse error message on System.err")
		);
	}

	@Test
	@DisplayName("carregarJogo() path 5: returns null when file does not exist")
	void carregarJogo5(@TempDir Path tempDir) {
		String nonExistent = tempDir.resolve("does_not_exist.json").toString();
		Game loaded = Tasks.carregarJogo(nonExistent);
		assertNull(loaded, "Error: expected null for non-existent file path");
	}

	// --- menu() (CC = 23: covering all command branches and null-guards safely) ---

	@Test
	@DisplayName("menu() path 1: immediately exits with goodbye message on 'desisto'")
	void menu1() {
		provideInput("desisto\n");
		Tasks.menu();
		assertTrue(outContent.toString().contains("Bons ventos!"),
				"Error: expected goodbye message on 'desisto'");
	}

	@Test
	@DisplayName("menu() path 2: handles 'novo', 'ajuda', and unknown default commands")
	void menu2() {
		provideInput("novo\najuda\ncomandoinvalido\ndesisto\n");
		Tasks.menu();
		String output = outContent.toString();
		assertAll("Basic menu commands",
				() -> assertTrue(output.contains("Inicie um novo jogo"), "Error: expected 'novo' prompt"),
				() -> assertTrue(output.contains("Que comando é esse???"), "Error: expected default unknown message")
		);
	}

	@Test
	@DisplayName("menu() path 3: handles commands safely when no game or fleet is initialized yet")
	void menu3() {
		provideInput("gravar\nestado\nnavios\nmapa\nrajada\nsimula\ntiros\ndesisto\n");
		assertDoesNotThrow(Tasks::menu, "Error: expected menu commands before initialization not to throw");
		String output = outContent.toString();
		assertAll("Uninitialized guard messages",
				() -> assertTrue(output.contains("Não há jogo a decorrer para gravar."),
						"Error: expected warning on 'gravar' without game"),
				() -> assertTrue(output.contains("Ainda não existe uma frota."),
						"Error: expected warning on 'navios' without fleet")
		);
	}

	@Test
	@DisplayName("menu() path 4: executes 'gerafrota', 'estado', 'navios', 'mapa', 'tiros', and 'rajada'")
	void menu4() {
		String input = String.join("\n",
				"gerafrota",
				"estado",
				"navios",
				"mapa",
				"tiros",
				"rajada",
				"A1 A2 A3",
				"desisto"
		) + "\n";
		provideInput(input);
		assertDoesNotThrow(Tasks::menu, "Error: expected full active game commands to run without error");
		assertTrue(outContent.toString().contains("Estado da Frota:"),
				"Error: expected fleet status output after 'estado'");
	}

	@Test
	@DisplayName("menu() path 5: builds custom fleet via 'lefrota' and exits cleanly")
	void menu5() {
		String input = "lefrota\n" + validFleetInput() + "desisto\n";
		provideInput(input);
		assertDoesNotThrow(Tasks::menu, "Error: expected 'lefrota' command to build fleet cleanly");
	}

	@Test
	@DisplayName("menu() path 6: cancels 'gravar' and 'carregar' interactive prompts via 'exit'")
	void menu6() {
		String input = String.join("\n",
				"gerafrota",
				"gravar",
				"exit",
				"carregar",
				"exit",
				"desisto"
		) + "\n";
		provideInput(input);
		Tasks.menu();
		assertTrue(outContent.toString().contains("Gravação cancelada."),
				"Error: expected 'Gravação cancelada.' when typing 'exit' in gravar");
	}

	@Test
	@DisplayName("menu() path 7: saves and reloads a game interactively via 'gravar' and 'carregar'")
	void menu7() {
		String tempName = "junit_temp_save_129866.json";
		File savedFile = new File("data", tempName);
		if (savedFile.exists()) {
			savedFile.delete();
		}
		try {
			String input = String.join("\n",
					"gerafrota",
					"gravar",
					"junit_temp_save_129866",
					"gravar",
					tempName,
					"N",
					"exit",
					"carregar",
					"ficheiro_inexistente_xyz",
					"exit",
					"desisto"
			) + "\n";
			provideInput(input);
			Tasks.menu();
			String output = outContent.toString();
			assertAll("Interactive save/load flow",
					() -> assertTrue(output.contains("Jogo gravado com sucesso"), "Error: expected save confirmation"),
					() -> assertTrue(output.contains("Deseja reescrever?"), "Error: expected overwrite prompt"),
					() -> assertTrue(output.contains("Ficheiro não encontrado"), "Error: expected missing file message")
			);
		} finally {
			if (savedFile.exists()) {
				savedFile.delete();
			}
		}
	}
}