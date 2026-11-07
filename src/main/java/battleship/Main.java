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

		GodClass godClass = new GodClass();
		godClass.gerirUtilizador("Novo Admin", "987654321");
		godClass.ligarBaseDados("Nova Rua, 456");
		godClass.enviarEmail("novo@jogo.com");
		godClass.processarPagamento(100.50);
		godClass.gerarRelatorioMensal("111222333");
		godClass.superMetodoComplexo(105, true);

		Tasks.menu();
    }
}
