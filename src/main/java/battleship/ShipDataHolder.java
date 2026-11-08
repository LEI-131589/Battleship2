package battleship;

/**
 * Classe com o Code Smell "Data Class" (provoca a regra de inspeção do Qodana).
 */
public class ShipDataHolder {

    // Campos públicos sem encapsulamento (Smell de Data Class)
    public String shipType;
    public int shipLength;
    public boolean isOperational;
    public int totalHitsReceived;
    public double armorRating;

    public ShipDataHolder(String shipType, int shipLength) {
        this.shipType = shipType;
        this.shipLength = shipLength;
        this.isOperational = true;
        this.totalHitsReceived = 0;
        this.armorRating = 100.0;
    }
}