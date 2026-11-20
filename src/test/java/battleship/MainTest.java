package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Deve iniciar a aplicação, imprimir o cabeçalho e sair do menu sem erros")
    void testMainExecutionAndExit() {
        // Simula o comando de saída no menu (se o menu usar outra palavra como "quit" ou "0", altera aqui)
        String simulatedInput = "desisto\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        assertDoesNotThrow(() -> Main.main(new String[]{}), "A execução do método main não deve lançar exceções");

        String output = outputStreamCaptor.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("***  Battleship  ***"), "Deve imprimir a mensagem inicial da aplicação");
    }
}