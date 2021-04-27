package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/** defines each cell to be used in gameboard
 *
 */

public class Cell extends Rectangle {
    public int x, y;
    public Ship ship = null;
    public boolean wasShot = false;

    private Gameboard gameboard;

    /**
     *
     * @param x
     * @param y
     * @param gameboard
     * sets size and color of cells
     */
    public Cell(int x, int y, Gameboard gameboard) {
        super(40, 40);
        this.x = x;
        this.y = y;
        this.gameboard = gameboard;
        setFill(Color.ROYALBLUE);
        setStroke(Color.BLACK);
    }

    //determines what happens when each cell is 'shot'
    public boolean shoot() {
        wasShot = true;
        if (ship == null) {
            setFill(Color.BLACK);  //if cell is not ship
        }

        if (ship != null) {
            ship.hit();
            setFill(Color.TOMATO); //if ship is hit
            if (!ship.isFloating()) {
                gameboard.ships--; //if ship 'sinks'
            } return true;
        } return false;
    }
}