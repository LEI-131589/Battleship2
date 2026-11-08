package battleship;

public class ShipDataClassTester {

    // Campos públicos expostos (disparam a inspeção PublicField / Data Class)
    public String shipName;
    public int shipSize;
    public String shipCategory;
    public boolean isDestroyed;

    public static void main(String[] args) {
        // Uso da classe no main para garantir que está ativa e em execução
        ShipDataClassTester tester = new ShipDataClassTester();
        tester.shipName = "Caravel";
        tester.shipSize = 3;
        tester.shipCategory = "Warship";
        tester.isDestroyed = false;

        System.out.println("Testing data class: " + tester.shipName);
    }
}