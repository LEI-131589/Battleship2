package battleship;

public class ShipDataHolder {

    // 1. Tornar os campos privados (corrige o code smell de Data Class / Public Field)
    private String shipType;
    private int shipLength;
    private boolean isOperational;
    private int totalHitsReceived;
    private double armorRating;

    // Construtor
    public ShipDataHolder(String shipType, int shipLength) {
        this.shipType = shipType;
        this.shipLength = shipLength;
        this.isOperational = true;
        this.totalHitsReceived = 0;
        this.armorRating = 100.0;
    }

    // 2. Adicionar Getters e Setters para garantir o encapsulamento
    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public int getShipLength() {
        return shipLength;
    }

    public void setShipLength(int shipLength) {
        this.shipLength = shipLength;
    }

    public boolean isOperational() {
        return isOperational;
    }

    public void setOperational(boolean operational) {
        isOperational = operational;
    }

    public int getTotalHitsReceived() {
        return totalHitsReceived;
    }

    public void setTotalHitsReceived(int totalHitsReceived) {
        this.totalHitsReceived = totalHitsReceived;
    }

    public double getArmorRating() {
        return armorRating;
    }

    public void setArmorRating(double armorRating) {
        this.armorRating = armorRating;
    }
}