package sample;

import javafx.scene.Parent;

/**
 * creates ships to be used
 */

public class Ship extends Parent {
    public int type;
    public boolean vertical;

    private int health;

    /**
     * @param type
     * @param vertical
     */

    public Ship(int type, boolean vertical) {
        this.type = type;
        this.vertical = vertical;
        health = type;
    }

    //every time ship is hit, health decreases by 1
    public void hit() {
        health = health - 1;
    }

    //ship is still floating if it has any health left
    public boolean isFloating() {
        return health > 0;
    }
}

