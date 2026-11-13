package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TasksTest {

	private final InputStream originalIn = System.in;
	private File tempJsonFile;
	private File dataTestFile;

	@BeforeEach
	void setUp() {
		tempJsonFile = new File("temp_test_game.json");
		dataTestFile = new File("data", "test_menu_save.json");
	}

	@AfterEach
	void tearDown() {
		System.setIn(originalIn);
		if (tempJsonFile != null && tempJsonFile.exists()) {
			tempJsonFile.delete();
		}
		if (dataTestFile != null && dataTestFile.exists()) {
			dataTestFile.delete();
		}
	}

	private void provideInput(String data) {
		System.setIn(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
	}

	@Test
	@DisplayName("Should read numeric position from Scanner and throw AssertionError on null")
	void testReadPosition() {
		Scanner sc = new Scanner("3 7");
		Position pos = Tasks.readPosition(sc);
		assertEquals(new Position(3, 7), pos);

		assertThrows(AssertionError.class, () -> Tasks.readPosition(null));
	}

	@Test
	@DisplayName("Should read classic positions in compact and spaced formats and reject invalid inputs")
	void testReadClassicPosition() {
		// Formato compacto (ex: A3 -> coluna 'A'=0, linha 3 -> row=2, col=0)
		IPosition p1 = Tasks.readClassicPosition(new Scanner("A3"));
		assertEquals(new Position('A', 3), p1);

		// Formato com espaço e minúsculas (ex: b 5)
		IPosition p2 = Tasks.readClassicPosition(new Scanner("b 5"));
		assertEquals(new Position('B', 5), p2);

		// Scanner vazio
		assertThrows(IllegalArgumentException.class, () -> Tasks.readClassicPosition(new Scanner("")));

		// Formatos inválidos
		assertThrows(IllegalArgumentException.class, () -> Tasks.readClassicPosition(new Scanner("123")));
		assertThrows(IllegalArgumentException.class, () -> Tasks.readClassicPosition(new Scanner("AB 12")));
	}

	@Test
	@DisplayName("Should read valid and invalid ship descriptions from Scanner")
	void testReadShip() {
		Ship validShip = Tasks.readShip(new Scanner("barca 2 4 n"));
		assertNotNull(validShip);
		assertInstanceOf(Barge.class, validShip);
		assertEquals(new Position(2, 4), validShip.getPosition());

		Ship unknownShip = Tasks.readShip(new Scanner("submarino 1 1 s"));
		assertNull(unknownShip);

		assertThrows(AssertionError.class, () -> Tasks.readShip(null));
	}

	@Test
	@DisplayName("Should build a complete fleet while ignoring invalid ships and collisions")
	void testBuildFleet() {
		// Inclui 1 navio desconhecido, 1 colisão e 11 navios válidos (FLEET_SIZE = 11)
		String fleetInput = String.join("\n",
				"desconhecido 0 0 n", // navio desconhecido (s == null)
				"barca 0 0 n",        // 1º válido
				"barca 0 0 n",        // colisão (success == false)
				"barca 0 2 n",        // 2º
				"barca 0 4 n",        // 3º
				"barca 0 6 n",        // 4º
				"barca 2 0 n",        // 5º
				"barca 2 2 n",        // 6º
				"barca 2 4 n",        // 7º
				"barca 2 6 n",        // 8º
				"barca 4 0 n",        // 9º
				"barca 4 2 n",        // 10º
				"barca 4 4 n"         // 11º
		);

		Fleet fleet = Tasks.buildFleet(new Scanner(fleetInput));
		assertNotNull(fleet);
		assertEquals(Fleet.FLEET_SIZE, fleet.getShips().size());

		assertThrows(AssertionError.class, () -> Tasks.buildFleet(null));
	}

	@Test
	@DisplayName("Should save and load a Game to/from JSON and handle invalid files gracefully")
	void testGravarECarregarJogo() throws IOException {
		Game game = new Game(Fleet.createRandom());
		String path = tempJsonFile.getPath();

		// Gravar com sucesso
		Tasks.gravarJogo(game, path);
		assertTrue(tempJsonFile.exists());

		// Carregar com sucesso
		Game loaded = Tasks.carregarJogo(path);
		assertNotNull(loaded);
		assertNotNull(loaded.getMyFleet());

		// Erro ao gravar num caminho inválido (diretoria inexistente/inválida)
		assertDoesNotThrow(() -> Tasks.gravarJogo(game, ""));

		// Carregar ficheiro inexistente
		assertNull(Tasks.carregarJogo("ficheiro_que_nao_existe_12345.json"));

		// Carregar ficheiro com JSON corrompido
		try (FileWriter fw = new FileWriter(tempJsonFile)) {
			fw.write("{ invalid json ");
		}
		assertNull(Tasks.carregarJogo(path));

		// Carregar ficheiro com JSON válido mas campos nulos (jogo == null ou fleets null)
		try (FileWriter fw = new FileWriter(tempJsonFile)) {
			fw.write("null");
		}
		assertNull(Tasks.carregarJogo(path));
	}

	@Test
	@DisplayName("Should print menu help without errors")
	void testMenuHelp() {
		assertDoesNotThrow(Tasks::menuHelp);
	}

	@Test
	@DisplayName("Should execute menu commands before and after game creation")
	void testMenuCommands() throws IOException {
		// Prepara um ficheiro corrompido na pasta data para testar o ramo de erro no carregarJogoInterativo
		File dataDir = new File("data");
		if (!dataDir.exists()) {
			dataDir.mkdirs();
		}
		File badFile = new File(dataDir, "corrupted_test.json");
		Files.writeString(badFile.toPath(), "{ bad json");

		try {
			// Sequência de comandos cobrindo estados sem jogo e com jogo ativo:
			String commands = String.join("\n",
					"comandoinvalido",              // default
					"novo",                         // NOVO
					"ajuda",                        // AJUDA
					"gravar",                       // GRAVAR sem jogo
					"estado",                       // STATUS sem frota
					"navios",                       // NAVIOS sem frota
					"mapa",                         // MAPA sem frota
					"rajada",                       // RAJADA sem jogo
					"simula",                       // SIMULA sem jogo
					"tiros",                        // TIROS sem jogo
					"carregar", "inexistente", "corrupted_test", "exit", // CARREGAR falhado + exit
					"gerafrota",                    // GERAFROTA cria frota e jogo
					"estado",                       // STATUS com frota
					"navios",                       // NAVIOS com frota
					"mapa",                         // MAPA com frota
					"tiros",                        // TIROS com jogo
					"gravar", "test_menu_save",     // GRAVAR novo ficheiro
					"gravar", "test_menu_save", "N",// GRAVAR ficheiro existente -> N ->
					"exit",                         // -> cancelar gravação
					"gravar", "test_menu_save", "S",// GRAVAR ficheiro existente -> S (reescrever)
					"carregar", "test_menu_save",   // CARREGAR com sucesso
					"desisto"                       // DESISTIR termina o loop
			);

			provideInput(commands);
			assertDoesNotThrow(Tasks::menu);
		} finally {
			if (badFile.exists()) {
				badFile.delete();
			}
		}
	}

	@Test
	@DisplayName("Should execute lefrota and rajada commands in menu without sinking entire fleet")
	void testMenuLefrotaAndRajada() {
		// 11 barcas espalhadas + comando rajada (3 tiros na mesma linha separados por espaço)
		String commands = String.join("\n",
				"lefrota",
				"barca 0 0 n", "barca 0 2 n", "barca 0 4 n", "barca 0 6 n",
				"barca 2 0 n", "barca 2 2 n", "barca 2 4 n", "barca 2 6 n",
				"barca 4 0 n", "barca 4 2 n", "barca 4 4 n",
				"rajada",
				"J10 J9 J8",
				"desisto"
		);

		provideInput(commands);
		assertDoesNotThrow(Tasks::menu);
	}
}