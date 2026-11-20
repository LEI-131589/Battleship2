package battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class IMoveTest {

    @Test
    @DisplayName("Deve ler corretamente uma jogada com vários tiros")
    void testReadMoveMultipleShots() {
        String input = "3 1 2 4 5 7 8";
        Scanner scanner = new Scanner(input);

        Move move = IMove.readMove(1, scanner);

        assertNotNull(move, "O objeto Move criado não deve ser nulo");
        assertEquals(1, move.getNumber(), "O número da jogada deve ser 1");

        List<IPosition> shots = move.getShots();
        assertEquals(3, shots.size(), "Deve conter exatamente 3 tiros");
        assertEquals(new Position(1, 2), shots.get(0));
        assertEquals(new Position(4, 5), shots.get(1));
        assertEquals(new Position(7, 8), shots.get(2));
        assertTrue(move.getShotResults().isEmpty(), "A lista de resultados deve começar vazia");
    }

    @Test
    @DisplayName("Deve ler corretamente uma jogada com zero tiros")
    void testReadMoveZeroShots() {
        String input = "0";
        Scanner scanner = new Scanner(input);

        Move move = IMove.readMove(5, scanner);

        assertNotNull(move, "O objeto Move criado não deve ser nulo");
        assertEquals(5, move.getNumber(), "O número da jogada deve ser 5");
        assertTrue(move.getShots().isEmpty(), "A lista de tiros deve estar vazia quando numShots é 0");
        assertTrue(move.getShotResults().isEmpty(), "A lista de resultados deve começar vazia");
    }

    @Test
    @DisplayName("Deve lançar NoSuchElementException quando faltam coordenadas no Scanner")
    void testReadMoveIncompleteInput() {
        String input = "2 3 4 5"; // Indica 2 tiros (4 números), mas só fornece 3 números
        Scanner scanner = new Scanner(input);

        assertThrows(NoSuchElementException.class, () -> IMove.readMove(2, scanner));
    }
}