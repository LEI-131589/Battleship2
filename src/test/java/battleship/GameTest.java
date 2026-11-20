package battleship;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Game.
 * Author: britoeabreu
 * Date: 2024-03-19
 * Time: 15:30
 * Cyclomatic Complexity for each method:
 * - Game (constructor): 1
 * - fire: 7
 * - getShots: 1
 * - getRepeatedShots: 1
 * - getInvalidShots: 1
 * - getHits: 1
 * - getSunkShips: 1
 * - getRemainingShips: 1
 * - validShot: 3
 * - repeatedShot: 2
 * - printBoard: 1
 * - printValidShots: 1
 * - printFleet: 1
 * - printShips: 4
 * - printBoardShots: 6
 * - printBoardFrame: 4
 * - jsonShots: 3
 * - getMyFleet: 1
 * - getAlienFleet: 1
 * - getMyMoves: 1
 * - getAlienMoves: 1
 * - randomEnemyFire: 11
 * - readEnemyFire: 22
 * - fireShots: 6
 * - fireSingleShot: 7
 * - printMyBoard: 1
 * - printAlienBoard: 1
 * - over: 1
 * - declareVictory: 1
 * - generatePdfReport: 5
 * - convertJsonToClassicFormat: 6
 * - isGameOver: 1
 * - metodoValido129866: 1
 */
public class GameTest {

	private Game game;

	@BeforeEach
	void setUp() {
		limparFicheiros();
		game = new Game(new Fleet()); // Assuming Fleet is a concrete implementation of IFleet
	}

	@AfterEach
	void tearDown() {
		game = null;
		limparFicheiros();
	}

	@AfterAll
	static void cleanUpAll(){
		limparFicheiros();
	}

	private static void limparFicheiros(){
		File alienPdf = new File("VictoryReport_Alien.pdf");
		if (alienPdf.exists()) {
			alienPdf.delete();
		}
		File playerPdf = new File("VictoryReport_Player.pdf");
		if (playerPdf.exists()) {
			playerPdf.delete();
		}
	}

	@Test
	@DisplayName("constructor: inicializa corretamente o estado do jogo")
	void constructor() {
		assertNotNull(game, "Game instance should not be null after construction.");
		assertNotNull(game.getAlienMoves(), "Shots list should not be null after initialization.");
		assertTrue(game.getAlienMoves().isEmpty(), "Shots list should be empty upon initialization.");
		assertEquals(0, game.getInvalidShots(), "Invalid shots count should be zero upon initialization.");
		assertEquals(0, game.getRepeatedShots(), "Repeated shots count should be zero upon initialization.");
		assertEquals(0, game.getHits(), "Hits count should be zero upon initialization.");
		assertEquals(0, game.getSunkShips(), "Sunk ships count should be zero upon initialization.");
	}

	@Test
	@DisplayName("fire2: contabiliza tiro inválido fora do tabuleiro")
	void fire2() {
		Position invalidPosition = new Position(-1, 5);
		game.fireSingleShot(invalidPosition, false);
		assertEquals(1, game.getInvalidShots(), "Invalid shots counter should increase for an invalid shot.");
	}

	@Test
	@DisplayName("fire3: contabiliza tiro repetido")
	void fire3() {
		Position position = new Position(2, 3);
		game.fireSingleShot(position, false);
		game.fireSingleShot(position, true);
		assertEquals(1, game.getRepeatedShots(), "Repeated shots counter should increase for a repeated shot.");
	}

	@Test
	@DisplayName("repeatedShot1: devolve true após disparar numa posição")
	void repeatedShot1() {
		List<IPosition> positions = List.of(new Position(2, 3), new Position(2, 4), new Position(2, 5));
		game.fireShots(positions);
		Position position = new Position(2, 3);
		assertTrue(game.repeatedShot(position), "Position (2,3) should be marked as repeated after firing.");
	}

	@Test
	@DisplayName("repeatedShot2: devolve false antes de disparar numa posição")
	void repeatedShot2() {
		Position position = new Position(2, 3);
		assertFalse(game.repeatedShot(position), "Position (2,3) should not be marked as repeated before firing.");
	}

	@Test
	@DisplayName("getAlienMoves: devolve a lista de jogadas do inimigo após disparo")
	void getAlienMoves() {
		List<IPosition> positions = List.of(new Position(2, 3), new Position(2, 4), new Position(2, 5));
		game.fireShots(positions);
		assertEquals(1, game.getAlienMoves().size(), "Shots list should contain one shot after firing once.");
	}

	@Test
	@DisplayName("getRemainingShips: devolve o número de navios que ainda flutuam")
	void getRemainingShips() {
		IFleet fleet = game.getMyFleet();
		Ship ship1 = new Barge(Compass.NORTH, new Position(1, 1));
		Ship ship2 = new Frigate(Compass.EAST, new Position(5, 5));

		fleet.addShip(ship1);
		assertEquals(1, game.getRemainingShips(), "Just one ship was created!");
		fleet.addShip(ship2);
		assertEquals(2, game.getRemainingShips(), "Two ships were created!");
		ship2.sink();
		assertEquals(1, game.getRemainingShips(), "Remaining ships count should be 1 after sinking one of two ships.");
	}

	// =========================================================================
	// Estado do Jogo e Métodos Auxiliares
	// =========================================================================

	@Test
	@DisplayName("getMyMoves: devolve lista vazia inicialmente")
	void getMyMoves() {
		assertNotNull(game.getMyMoves(), "A lista das minhas jogadas não deve ser nula.");
		assertTrue(game.getMyMoves().isEmpty(), "A lista das minhas jogadas deve estar vazia após a inicialização.");
	}

	@Test
	@DisplayName("isGameOver1: devolve false no início do jogo")
	void isGameOver1() {
		assertFalse(game.isGameOver(), "O jogo não deve estar terminado após a inicialização.");
	}

	@Test
	@DisplayName("isGameOver2: devolve true após todos os navios serem afundados")
	void isGameOver2() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		game.fireShots(List.of(new Position(0, 0), new Position(5, 5), new Position(5, 6)));
		assertTrue(game.isGameOver(), "O jogo deve terminar depois de todos os navios serem afundados.");
	}

	@Test
	@DisplayName("over: executa mensagem de fim de jogo sem erros")
	void over() {
		assertDoesNotThrow(() -> game.over(), "O método over() deve executar sem lançar exceções.");
	}

	@Test
	@DisplayName("metodoValido129866: executa método sem erros")
	void metodoValido129866() {
		assertDoesNotThrow(() -> game.metodoValido129866(), "O método metodoValido129866() deve executar sem lançar exceções.");
	}

	// =========================================================================
	// Disparos (fireSingleShot e fireShots) e Relatórios PDF
	// =========================================================================

	@Test
	@DisplayName("fireSingleShot1: acerta num navio sem o afundar")
	void fireSingleShot1() {
		game.getMyFleet().addShip(new Caravel(Compass.EAST, new Position(2, 2)));
		IGame.ShotResult result = game.fireSingleShot(new Position(2, 2), false);
		assertEquals(1, game.getHits(), "O contador de tiros certeiros deve aumentar após atingir um navio.");
		assertFalse(result.sunk(), "Um navio de tamanho 2 deve continuar a flutuar após 1 tiro.");
	}

	@Test
	@DisplayName("fireSingleShot2: acerta e afunda um navio")
	void fireSingleShot2() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		IGame.ShotResult result = game.fireSingleShot(new Position(0, 0), false);
		assertEquals(1, game.getHits(), "O contador de tiros certeiros deve aumentar após atingir um navio.");
		assertTrue(result.sunk(), "A barcaça deve ficar afundada após 1 tiro.");
	}

	@Test
	@DisplayName("fireShots1: lança exceção quando o número de tiros é inválido")
	void fireShots1() {
		List<IPosition> invalidShots = List.of(new Position(1, 1));
		assertThrows(IllegalArgumentException.class, () -> game.fireShots(invalidShots),
				"Deve lançar IllegalArgumentException quando o número de tiros difere de NUMBER_SHOTS.");
	}

	@Test
	@DisplayName("fireShots2: contabiliza tiro repetido dentro da mesma rajada")
	void fireShots2() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		game.fireShots(List.of(new Position(4, 4), new Position(4, 4), new Position(4, 5)));
		assertEquals(1, game.getRepeatedShots(), "Um tiro repetido na mesma rajada deve ser contabilizado.");
	}

	@Test
	@DisplayName("fireShots3: vitória do Alien e atualização de PDF já existente")
	void fireShots3() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		game.fireShots(List.of(new Position(0, 0), new Position(5, 5), new Position(5, 6)));
		assertTrue(new File("VictoryReport_Alien.pdf").exists(), "O ficheiro VictoryReport_Alien.pdf deve ser criado.");

		Game secondGame = new Game(new Fleet());
		secondGame.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(1, 1)));
		secondGame.fireShots(List.of(new Position(1, 1), new Position(6, 6), new Position(6, 7)));
		assertTrue(secondGame.isGameOver(), "O segundo jogo também deve terminar com a vitória do Alien.");
	}

	@Test
	@DisplayName("fireShots4: vitória do Player quando todos os navios da alienFleet estão afundados")
	void fireShots4() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		Ship alienShip = new Barge(Compass.NORTH, new Position(2, 2));
		game.getAlienFleet().addShip(alienShip);
		alienShip.shoot(new Position(2, 2));

		game.fireShots(List.of(new Position(9, 9), new Position(9, 8), new Position(9, 7)));
		assertTrue(game.isGameOver(), "O jogo deve terminar com a vitória do jogador.");
		assertTrue(new File("VictoryReport_Player.pdf").exists(), "O ficheiro VictoryReport_Player.pdf deve ser criado.");
	}

	@Test
	@DisplayName("generatePdfReportError: captura exceção quando não é possível criar o ficheiro PDF")
	void generatePdfReportError() {
		File dirAsPdf = new File("VictoryReport_Alien.pdf");
		if (dirAsPdf.exists()) {
			dirAsPdf.delete();
		}
		assertTrue(dirAsPdf.mkdir(), "Deve criar uma diretoria com o nome do PDF para forçar erro de escrita.");
		try {
			game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
			assertDoesNotThrow(() ->
							game.fireShots(List.of(new Position(0, 0), new Position(5, 5), new Position(5, 6))),
					"Deve capturar a exceção ao falhar a criação do PDF sem interromper o jogo."
			);
		} finally {
			dirAsPdf.delete();
		}
	}

	// =========================================================================
	// Impressão de Tabuleiros (printMyBoard e printAlienBoard)
	// =========================================================================

	@Test
	@DisplayName("printMyBoard1: imprime tabuleiro com navios afundados, tiros e legenda")
	void printMyBoard1() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		game.getMyFleet().addShip(new Caravel(Compass.EAST, new Position(3, 3)));
		game.fireShots(List.of(new Position(0, 0), new Position(7, 7), new Position(0, 1)));
		assertDoesNotThrow(() -> game.printMyBoard(true, true), "Imprimir o tabuleiro com tiros e legenda não deve lançar exceções.");
	}

	@Test
	@DisplayName("printMyBoard2: imprime tabuleiro sem tiros nem legenda")
	void printMyBoard2() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		assertDoesNotThrow(() -> game.printMyBoard(false, false), "Imprimir o tabuleiro sem tiros nem legenda não deve lançar exceções.");
	}

	@Test
	@DisplayName("printMyBoard3: cobre tiros fora do tabuleiro e tiros repetidos na mesma casa")
	void printMyBoard3() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		game.fireShots(List.of(new Position(-1, 0), new Position(4, 4), new Position(4, 4)));
		assertDoesNotThrow(() -> game.printMyBoard(true, false),
				"Deve ignorar tiros fora do tabuleiro e tiros sobre células já marcadas.");
	}

	@Test
	@DisplayName("printAlienBoard: imprime tabuleiro inimigo")
	void printAlienBoard() {
		assertDoesNotThrow(() -> game.printAlienBoard(true, true), "Imprimir o tabuleiro do inimigo não deve lançar exceções.");
	}

	// =========================================================================
	// Jogadas Aleatórias e Leitura de Input / JSON
	// =========================================================================

	@Test
	@DisplayName("randomEnemyFire1: gera rajada aleatória válida em JSON")
	void randomEnemyFire1() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		String json = game.randomEnemyFire();
		assertTrue(json.contains("\"row\""), "A string devolvida deve ser um JSON válido com as linhas dos tiros.");
	}

	@Test
	@DisplayName("randomEnemyFire2: gera rajada quando restam menos posições livres que NUMBER_SHOTS")
	void randomEnemyFire2() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		List<IPosition> almostAll = new ArrayList<>();
		for (int r = 0; r < Game.BOARD_SIZE; r++) {
			for (int c = 0; c < Game.BOARD_SIZE; c++) {
				if (!(r == 9 && c == 9)) {
					almostAll.add(new Position(r, c));
				}
			}
		}
		game.getAlienMoves().add(new Move(99, almostAll, new ArrayList<>()));
		String json = game.randomEnemyFire();
		assertNotNull(json, "Deve continuar a gerar tiros mesmo quando existem menos posições candidatas do que NUMBER_SHOTS.");
	}

	@Test
	@DisplayName("randomEnemyFire3: devolve {} quando o jogo já terminou")
	void randomEnemyFire3() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		game.fireShots(List.of(new Position(0, 0), new Position(8, 8), new Position(8, 9)));
		assertEquals("{}", game.randomEnemyFire(), "Deve devolver um objeto JSON vazio quando o jogo já terminou.");
	}

	@Test
	@DisplayName("readEnemyFire1: lê jogada válida em formato clássico com prefixo rajada e espaços")
	void readEnemyFire1() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		Scanner scanner = new Scanner("rajada 'D 4' E5 F6\n");
		String result = game.readEnemyFire(scanner);
		assertTrue(result.contains("\"row\""), "Deve interpretar coordenadas clássicas com o prefixo 'rajada'.");
	}

	@Test
	@DisplayName("readEnemyFire2: recupera de entradas inválidas antes de ler jogada válida")
	void readEnemyFire2() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		Scanner scanner = new Scanner("\n   \nA1 B2\nC\nD1 D2 D3\n");
		String result = game.readEnemyFire(scanner);
		assertTrue(result.contains("D"), "Deve recuperar de linhas inválidas e processar a entrada válida.");
	}

	@Test
	@DisplayName("readEnemyFire3: lê jogada em formato JSON sem parênteses retos")
	void readEnemyFire3() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		String jsonInput = "{\"row\": \"G\", \"column\": 1},\n{\"row\": \"G\", \"column\": 2},\n{\"row\": \"G\", \"column\": 3}\n";
		String result = game.readEnemyFire(new Scanner(jsonInput));
		assertTrue(result.contains("G"), "Deve interpretar entradas JSON sem parênteses retos exteriores.");
	}

	@Test
	@DisplayName("readEnemyFire4: devolve {} em EOF, linhas vazias ou após gameOver")
	void readEnemyFire4() {
		assertEquals("{}", game.readEnemyFire(new Scanner("")), "Deve devolver {} num scanner vazio.");
		assertEquals("{}", game.readEnemyFire(new Scanner("\n   \n")), "Deve devolver {} quando existem apenas linhas em branco.");

		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		game.fireShots(List.of(new Position(0, 0), new Position(7, 7), new Position(7, 8)));
		assertEquals("{}", game.readEnemyFire(new Scanner("H1 H2 H3\n")), "Deve devolver {} quando o jogo já terminou.");
	}

	@Test
	@DisplayName("readEnemyFire5: lê JSON completo com parênteses retos")
	void readEnemyFire5() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		String jsonWithBrackets = "[{\"row\": \"A\", \"column\": 2}, {\"row\": \"A\", \"column\": 3}, {\"row\": \"A\", \"column\": 4}]\n";
		String result = game.readEnemyFire(new Scanner(jsonWithBrackets));
		assertTrue(result.contains("A"), "Deve interpretar JSON completo numa só linha com parênteses retos.");
	}

	@Test
	@DisplayName("readEnemyFire6: cobre condições secundárias de deteção de JSON")
	void readEnemyFire6() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		String input = "prefixo \"row\" 'row' 'column' ]\nA1 A2 A3\n";
		String result = game.readEnemyFire(new Scanner(input));
		assertTrue(result.contains("A"), "Deve recuperar após falha na estrutura JSON e ler coordenadas válidas.");
	}

	@Test
	@DisplayName("readEnemyFire7: captura exceção genérica na leitura do stream e recupera")
	void readEnemyFire7() {
		game.getMyFleet().addShip(new Barge(Compass.NORTH, new Position(0, 0)));
		InputStream failingStream = new InputStream() {
			private boolean firstRead = true;
			private final byte[] validBytes = "\nA1 A2 A3\n".getBytes();
			private int pos = 0;

			@Override
			public int read() {
				if (firstRead) {
					firstRead = false;
					throw new RuntimeException("Falha simulada na leitura do stream");
				}
				return pos < validBytes.length ? validBytes[pos++] : -1;
			}
		};
		String result = game.readEnemyFire(new Scanner(failingStream));
		assertTrue(result.contains("A"), "Deve capturar a exceção genérica e ler a jogada seguinte.");
	}

	@Test
	@DisplayName("convertJsonToClassicFormat1: converte JSON válido com sufixo .0")
	void convertJsonToClassicFormat1() {
		String validJson = "[{\"row\":\"A\",\"column\":1.0},{\"row\":\"B\",\"column\":2}]";
		assertEquals(List.of("A1", "B2"), game.convertJsonToClassicFormat(validJson),
				"Deve converter objetos JSON para coordenadas clássicas removendo o sufixo .0.");
	}

	@Test
	@DisplayName("convertJsonToClassicFormat2: lança exceção quando falta o campo 'column'")
	void convertJsonToClassicFormat2() {
		assertThrows(IllegalArgumentException.class, () ->
						game.convertJsonToClassicFormat("[{\"row\":\"A\"}]"),
				"Deve lançar IllegalArgumentException quando falta o campo 'column'.");
	}

	@Test
	@DisplayName("convertJsonToClassicFormat3: lança exceção com JSON malformado")
	void convertJsonToClassicFormat3() {
		assertThrows(IllegalArgumentException.class, () ->
						game.convertJsonToClassicFormat("json_invalido"),
				"Deve lançar IllegalArgumentException perante um JSON malformado.");
	}

	@Test
	@DisplayName("convertJsonToClassicFormat4: lança exceção quando falta o campo 'row'")
	void convertJsonToClassicFormat4() {
		assertThrows(IllegalArgumentException.class, () ->
						game.convertJsonToClassicFormat("[{\"column\":1}]"),
				"Deve lançar IllegalArgumentException quando falta o campo 'row'.");
	}

	// =========================================================================
	// Verificação de Asserts (Ramos de Pré-condição)
	// =========================================================================

	@Test
	@DisplayName("assertionsNullArguments: verifica AssertionError quando argumentos obrigatórios são nulos")
	void assertionsNullArguments() {
		assertThrows(AssertionError.class, () -> Game.printBoard(null, List.of(), false, false),
				"Deve lançar AssertionError quando a frota em printBoard é nula.");
		assertThrows(AssertionError.class, () -> Game.printBoard(new Fleet(), null, false, false),
				"Deve lançar AssertionError quando a lista de jogadas em printBoard é nula.");
		assertThrows(AssertionError.class, () -> Game.jsonShots(null),
				"Deve lançar AssertionError quando a lista de tiros é nula.");
		assertThrows(AssertionError.class, () -> game.fireShots(null),
				"Deve lançar AssertionError quando a lista de tiros em fireShots é nula.");
		assertThrows(AssertionError.class, () -> game.fireSingleShot(null, false),
				"Deve lançar AssertionError quando a posição é nula.");
		assertThrows(AssertionError.class, () -> game.repeatedShot(null),
				"Deve lançar AssertionError quando a posição em repeatedShot é nula.");
	}

}