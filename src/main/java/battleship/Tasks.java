package battleship;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Tasks.
 */
public class Tasks {
	/**
	 * The constant LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * The constant GOODBYE_MESSAGE.
	 */
	private static final String GOODBYE_MESSAGE = "Bons ventos!";

	/**
	 * Strings to be used by the user
	 */
	private static final String NOVO = "novo";
	private static final String CARREGAR = "carregar";
	private static final String GRAVAR = "gravar";
	private static final String AJUDA = "ajuda";
	private static final String GERAFROTA = "gerafrota";
	private static final String LEFROTA = "lefrota";
	private static final String DESISTIR = "desisto";
	private static final String RAJADA = "rajada";
	private static final String TIROS = "tiros";
	private static final String MAPA = "mapa";
	private static final String STATUS = "estado";
	private static final String SIMULA = "simula";
	private static final String EXIT = "exit";
	private static final String NAVIOS = "navios";

	/**
	 * This task also tests the fighting element of a round of three shots
	 */
	public static void menu() {

		IFleet myFleet = null;
		IGame game = null;
		Scanner in = new Scanner(System.in);

		menuHelp();
		System.out.print("> ");
		String command = in.next();

		while (!command.equals(DESISTIR)) {

			switch (command) {
				case NOVO:
					System.out.println("Inicie um novo jogo com '" + GERAFROTA + "' ou '" + LEFROTA + "'.");
					break;
				case CARREGAR:
					Game loadedGame = carregarJogoInterativo(in);
					if (loadedGame != null) {
						game = loadedGame;
						myFleet = game.getMyFleet();
						game.printMyBoard(true, true);
					}
					break;
				case GRAVAR:
					if (game != null) {
						gravarJogoInterativo((Game) game, in);
					} else {
						System.out.println("Não há jogo a decorrer para gravar.");
					}
					break;
				case GERAFROTA:
					myFleet = Fleet.createRandom();
					game = new Game(myFleet);
					game.printMyBoard(false, true);
					break;
				case LEFROTA:
					myFleet = buildFleet(in);
					game = new Game(myFleet);
					game.printMyBoard(false, true);
					break;
				case STATUS:
					if (myFleet != null)
						myFleet.printStatus();
					break;
				case NAVIOS:
					if (myFleet != null) {
						myFleet.printFloatingShips();
					} else {
						System.out.println("Ainda não existe uma frota.");
					}
					break;
				case MAPA:
					if (myFleet != null)
						game.printMyBoard(false, true);
					break;
				case RAJADA:
					if (game != null) {
						game.readEnemyFire(in);
						myFleet.printStatus();
						game.printMyBoard(true, false);

						if (game.getRemainingShips() == 0) {
							game.over();
							System.exit(0);
						}
					}
					break;
				case SIMULA:
					if (game != null) {
						while (game.getRemainingShips() > 0){
							game.randomEnemyFire();
							myFleet.printStatus();
							game.printMyBoard(true, false);
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt(); // Best practice: restore interrupt status
							}
						}

						if (game.getRemainingShips() == 0) {
							game.over();
							System.exit(0);
						}
					}
					break;
				case TIROS:
					if (game != null)
						game.printMyBoard(true, true);
					break;
                case AJUDA:
                    menuHelp();
                    break;
				default:
					System.out.println("Que comando é esse??? Repete ...");
			}
			System.out.print("> ");
			command = in.next();
		}
		System.out.println(GOODBYE_MESSAGE);
	}

	/**
	 * This function provides help information about the menu commands.
	 */
	public static void menuHelp() {
		System.out.println("======================= AJUDA DO MENU =========================");
		System.out.println("Digite um dos comandos abaixo para interagir com o jogo:");
		System.out.println("- " + NOVO + ": Inicia um novo jogo");
		System.out.println("- " + CARREGAR + ": Carrega um jogo existente na pasta data");
		System.out.println("- " + GRAVAR + ": Grava o jogo atual na pasta data");
		System.out.println("- " + GERAFROTA + ": Gera uma frota aleatória de navios.");
		System.out.println("- " + LEFROTA + ": Permite criar e carregar uma frota personalizada.");
		System.out.println("- " + STATUS + ": Mostra o status atual da frota.)");
		System.out.println("- " + NAVIOS + ": Consulta os navios disponíveis.");
		System.out.println("- " + MAPA + ": Exibe o mapa da frota.");
		System.out.println("- " + RAJADA + ": Realiza uma rajada de disparos.");
		System.out.println("- " + SIMULA + ": Simula um jogo completo.");
		System.out.println("- " + TIROS + ": Lista os tiros válidos realizados (* = tiro em navio, o = tiro na água)");
		System.out.println("- " + DESISTIR + ": Encerra o jogo.");
		System.out.println("===============================================================");
	}
	/**
	 * This operation allows the build up of a fleet, given user data
	 *
	 * @param in The scanner to read from
	 * @return The fleet that has been built
	 */
	public static Fleet buildFleet(Scanner in) {

		assert in != null;

		Fleet fleet = new Fleet();
		int i = 0; // i represents the total of successfully created ships
		while (i < Fleet.FLEET_SIZE) {
			IShip s = readShip(in);
			if (s != null) {
				boolean success = fleet.addShip(s);
				if (success)
					i++;
				else
					LOGGER.info("Falha na criacao de {} {} {}", s.getCategory(), s.getBearing(), s.getPosition());
			} else {
				LOGGER.info("Navio desconhecido!");
			}
		}
		LOGGER.info("{} navios adicionados com sucesso!", i);
		return fleet;
	}

	/**
	 * This operation reads data about a ship, build it and returns it
	 *
	 * @param in The scanner to read from
	 * @return The created ship based on the data that has been read
	 */
	public static Ship readShip(Scanner in) {

		assert in != null;

		String shipKind = in.next();
		Position pos = readPosition(in);
		char c = in.next().charAt(0);
		Compass bearing = Compass.charToCompass(c);
		return Ship.buildShip(shipKind, bearing, pos);
	}

	/**
	 * This operation allows reading a position in the map
	 *
	 * @param in The scanner to read from
	 * @return The position that has been read
	 */
	public static Position readPosition(Scanner in) {

		assert in != null;

		int row = in.nextInt();
		int column = in.nextInt();
		return new Position(row, column);
	}

	/**
	 * This operation allows reading a position in the map
	 *
	 * @param in The scanner to read from
	 * @return The classic position that has been read
	 */
	public static IPosition readClassicPosition(@NotNull Scanner in) {
		// Verifica se ainda há tokens disponíveis
		if (!in.hasNext()) {
			throw new IllegalArgumentException("Nenhuma posição válida encontrada!");
		}

		String part1 = in.next(); // Primeiro token
		String part2 = null;

		if (in.hasNextInt()) {
			part2 = in.next(); // Segundo token, se disponível
		}

		String input = (part2 != null) ? part1 + part2 : part1;

		// Normalizar o input para tratar letras maiúsculas e minúsculas
		input = input.toUpperCase();

		// Verificar os dois formatos possíveis: compactos e com espaço
		if (input.matches("[A-Z]\\d+")) {
			char column = input.charAt(0); // Extrair a coluna
			int row = Integer.parseInt(input.substring(1)); // Extrair a linha
			return new Position(column, row);
		} else if (part2 != null && part1.matches("[A-Z]") && part2.matches("\\d+")) {
			char column = part1.charAt(0); // Extrair a coluna
			int row = Integer.parseInt(part2); // Extrair a linha
			return new Position(column, row);
		} else {
			throw new IllegalArgumentException("Formato inválido. Use 'A3', 'A 3' ou similar.");
		}
	}

	// -------------------------------------------------------------------------
	// LÓGICA DE GRAVAÇÃO E CARREGAMENTO
	// -------------------------------------------------------------------------

	private static void gravarJogoInterativo(Game game, Scanner in) {
		while(true) {
			System.out.println("> Indique o nome do ficheiro JSON (caso pretenda sair escreva o comando \"exit\")");
			System.out.print("> nome : ");
			String nomeFicheiro = in.next();

			if (nomeFicheiro.equalsIgnoreCase(EXIT)) {
				System.out.println("Gravação cancelada.");
				return;
			}

			if (!nomeFicheiro.endsWith(".json")) {
				nomeFicheiro += ".json";
			}

			File dir = new File("data");
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File ficheiro = new File(dir, nomeFicheiro);
			if (ficheiro.exists()) {
				System.out.print("O ficheiro '" + nomeFicheiro + "' já existe. Deseja reescrever? (S/N): ");
				String resposta = in.next();
				if (!resposta.equalsIgnoreCase("S")) {
					System.out.println("Gravação cancelada.");
					continue;
				}
			}

			gravarJogo(game, ficheiro.getPath());
			break;
		}

	}

	private static Game carregarJogoInterativo(Scanner in) {
		Game game = null;
		while (game == null) {
			System.out.println("> Indique o nome do ficheiro JSON (caso pretenda sair escreva o comando \"exit\")");
			System.out.print("> nome : ");
			String nomeFicheiro = in.next();

			if (nomeFicheiro.equalsIgnoreCase(EXIT)) {
				break;
			}

			if (!nomeFicheiro.endsWith(".json")) {
				nomeFicheiro += ".json";
			}

			File ficheiro = new File("data", nomeFicheiro);
			if (!ficheiro.exists()) {
				System.out.println("Erro: Ficheiro não encontrado na pasta 'data'. Tente novamente.");
				continue;
			}

			game = carregarJogo(ficheiro.getPath());
			if (game == null) {
				System.out.println("Tente novamente com um ficheiro válido.");
			}
		}
		return game;
	}

	private static ObjectMapper getConfiguredMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

		PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
				.allowIfBaseType(Object.class)
				.build();
		mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);

		return mapper;
	}

	public static void gravarJogo(Game jogo, String caminho) {
		ObjectMapper mapper = getConfiguredMapper();
		try {
			mapper.writeValue(new File(caminho), jogo);
			System.out.println("Jogo gravado com sucesso em: " + caminho);
		} catch (IOException e) {
			LOGGER.error("Erro ao gravar o jogo: {}", e.getMessage());
			System.out.println("Erro ao gravar o jogo: " + e.getMessage());
		}
	}

	public static Game carregarJogo(String caminho) {
		ObjectMapper mapper = getConfiguredMapper();
		File file = new File(caminho);

		try {
			Game jogo = mapper.readValue(file, Game.class);

			if (jogo == null || jogo.getMyFleet() == null || jogo.getAlienFleet() == null) {
				System.err.println("Erro: Ficheiro JSON inválido ou com dados em falta.");
				return null;
			}

			System.out.println("Jogo carregado com sucesso!");
			return jogo;
		} catch (Exception e) {
			System.err.println("Erro: Não foi possível ler o JSON. O formato é inválido ou está corrompido.");
			return null;
		}
	}
}