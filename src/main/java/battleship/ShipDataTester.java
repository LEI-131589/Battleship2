package battleship;

/**
 * Classe executora para testar o uso do ShipDataHolder com encapsulamento correto.
 */
public class ShipDataTester {

    public static void main(String[] args) {
        // Criacao do objeto utilizando o construtor
        ShipDataHolder dataHolder = new ShipDataHolder("Battleship", 4);

        // Manipulacao de atributos atraves dos metodos Setter (respeita o encapsulamento)
        dataHolder.setShipType("Carrier");
        dataHolder.setShipLength(5);
        dataHolder.setTotalHitsReceived(2);
        dataHolder.setOperational(false);
        dataHolder.setArmorRating(75.5); // Utiliza o setter do armorRating

        // Leitura de atributos atraves dos metodos Getter
        System.out.println("Navio: " + dataHolder.getShipType());
        System.out.println("Tamanho: " + dataHolder.getShipLength());
        System.out.println("Danos sofridos: " + dataHolder.getTotalHitsReceived());
        System.out.println("Em operacao: " + dataHolder.isOperational());
        System.out.println("Blindagem: " + dataHolder.getArmorRating()); // Utiliza o getter do armorRating
    }
}