package battleship;

/**
 * Classe executora a consumir todos os getters e setters de forma correta.
 */
public class ShipDataTester {

    public static void main(String[] args) {
        ShipDataHolder dataHolder = new ShipDataHolder("Battleship", 4);

        dataHolder.setShipType("Carrier");
        dataHolder.setShipLength(5);
        dataHolder.setTotalHitsReceived(2);
        dataHolder.setOperational(false);
        dataHolder.setArmorRating(75.5);

        System.out.println("Navio: " + dataHolder.getShipType());
        System.out.println("Tamanho: " + dataHolder.getShipLength());
        System.out.println("Danos sofridos: " + dataHolder.getTotalHitsReceived());
        System.out.println("Em operacao: " + dataHolder.isOperational());
        System.out.println("Blindagem: " + dataHolder.getArmorRating());
    }
}