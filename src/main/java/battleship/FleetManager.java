package battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class responsible for managing a fleet of Ships in the Battleship game.
 * Demonstrates dependency on the Ship class by calling its various methods.
 */
public class FleetManager {

    private final List<Ship> fleet;

    /**
     * Constructs a new empty FleetManager.
     */
    public FleetManager() {
        this.fleet = new ArrayList<>();
    }

    /**
     * Adds a new ship to the fleet after validating that it is not too close
     * to any existing ship in the fleet.
     *
     * @param newShip the ship to be added
     * @return true if the ship was successfully added, false if it is too close to another ship
     */
    public boolean addShip(Ship newShip) {
        Objects.requireNonNull(newShip, "Ship to add cannot be null");

        // Uses Ship#tooCloseTo(IShip) to verify positioning rules
        for (Ship existingShip : fleet) {
            if (existingShip.tooCloseTo(newShip)) {
                return false;
            }
        }
        return fleet.add(newShip);
    }

    /**
     * Fires a shot at a specific position on the board.
     * Passes the shot to all ships in the fleet.
     *
     * @param targetPos the position being attacked
     * @return true if any ship occupied that position, false otherwise
     */
    public boolean processShot(IPosition targetPos) {
        Objects.requireNonNull(targetPos, "Target position cannot be null");
        boolean hitAny = false;

        for (Ship ship : fleet) {
            // Uses Ship#occupies(IPosition) to check if the ship was hit
            if (ship.occupies(targetPos)) {
                // Uses Ship#shoot(IPosition) to apply damage
                ship.shoot(targetPos);
                hitAny = true;
            }
        }
        return hitAny;
    }

    /**
     * Checks if the entire fleet has been destroyed.
     *
     * @return true if all ships in the fleet are sunk, false if at least one is floating
     */
    public boolean isFleetDestroyed() {
        for (Ship ship : fleet) {
            // Uses Ship#stillFloating() to check operational status
            if (ship.stillFloating()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Counts how many ships of a specific category are currently floating.
     *
     * @param category the category name (e.g., "galeao", "fragata")
     * @return the count of active ships in that category
     */
    public int countActiveShipsByCategory(String category) {
        int count = 0;
        for (Ship ship : fleet) {
            // Uses Ship#getCategory() and Ship#stillFloating()
            if (ship.getCategory().equalsIgnoreCase(category) && ship.stillFloating()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Forces all ships in the fleet to sink immediately.
     */
    public void sinkAllShips() {
        for (Ship ship : fleet) {
            // Uses Ship#sink()
            ship.sink();
        }
    }

    /**
     * Returns an unmodifiable list of ships in the fleet.
     *
     * @return list of current ships
     */
    public List<Ship> getFleet() {
        return List.copyOf(fleet);
    }
}