/**
 * 
 */
package battleship;

public class Main
{
	/**
	 * Main.
	 *
	 * @param args the args
	 */
	public static void main(String[] args)
    {
		System.out.println("***  Battleship  ***");
		JogoGodClass jogo = new JogoGodClass();
		jogo.configurarDificuldade();
		jogo.iniciarJogo();
		jogo.desenharTabuleiro();
		jogo.validarMovimento();
		jogo.processarInputRato();
		jogo.reproduzirSomTiro();
		jogo.reproduzirSomAgua();
		jogo.atualizarPontuacao();
		jogo.mudarTurno();
		jogo.pausarJogo();
		jogo.retomarJogo();
		jogo.guardarHistorico();
		jogo.carregarJogoSalvo();
		jogo.verificarVencedor();
		jogo.calcularEstatisticas();
		jogo.exibirMenuVitoria();
		jogo.terminarJogo();

		Tasks.menu();

    }
}
