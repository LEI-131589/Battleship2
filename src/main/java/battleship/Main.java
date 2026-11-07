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
		System.out.println(jogo.toString());

		Tasks.menu();

    }
}
