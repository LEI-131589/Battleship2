package battleship;

/**
 * Data Class criada para demonstrar a violacao de encapsulamento (PublicField inspection).
 */
public class ShipDataHolder {

    // Campos publicos expostos (disparam a inspecao PublicField no Qodana)
    public String shipType;
    public int shipLength;
    public boolean isOperational;
    public int totalHitsReceived;

    public ShipDataHolder(String shipType, int shipLength) {
        this.shipType = shipType;
        this.shipLength = shipLength;
        this.isOperational = true;
        this.totalHitsReceived = 0;
    }
}