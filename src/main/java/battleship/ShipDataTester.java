package battleship;

/**
 * Classe executora para garantir o uso ativo do ShipDataHolder no projeto.
 */
public class ShipDataTester {

    public static void main(String[] args) {
        ShipDataHolder dataHolder = new ShipDataHolder("Battleship", 4);

        // Manipulacao direta de campos publicos (reforca o smell de Data Class)
        dataHolder.shipType = "Carrier";
        dataHolder.shipLength = 5;
        dataHolder.totalHitsReceived = 2;
        dataHolder.isOperational = false;

        System.out.println("Navio: " + dataHolder.shipType);
        System.out.println("Tamanho: " + dataHolder.shipLength);
        System.out.println("Danos sofridos: " + dataHolder.totalHitsReceived);
        System.out.println("Em operacao: " + dataHolder.isOperational);
    }
}