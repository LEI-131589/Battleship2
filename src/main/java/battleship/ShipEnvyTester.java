package battleship;

public class ShipEnvyTester {

    public static void main(String[] args) {
        Position pos = new Position(0, 0);
        Ship ship = new Caravel(Compass.NORTH, pos);

        ShipEnvyTester tester = new ShipEnvyTester();
        tester.printDetailedShipInfo(ship);
    }

    public void printDetailedShipInfo(Ship ship) {
        System.out.println("Category: " + ship.getCategory());
        System.out.println("Position: " + ship.getPosition());
        System.out.println("Bearing: " + ship.getBearing());
        System.out.println("Size: " + ship.getSize());
        System.out.println("Top: " + ship.getTopMostPos());
        System.out.println("Bottom: " + ship.getBottomMostPos());
        System.out.println("Left: " + ship.getLeftMostPos());
        System.out.println("Right: " + ship.getRightMostPos());
        System.out.println("Is Floating: " + ship.stillFloating());
        System.out.println("Is Inside: " + ship.isInsideBoard());
    }
}