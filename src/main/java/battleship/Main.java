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

		Tasks.menu();
		GodClass godClass = new GodClass();
		int resultado = godClass.rebentarMetricas(10);
		System.out.println("Resultado: " + resultado);
    }
}
