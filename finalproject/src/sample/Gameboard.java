package sample;

import java.util.*;
import javafx.scene.Parent;
import javafx.event.EventHandler;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


/**
 * creates boards for battleship
 */
public class Gameboard extends Parent {
    private VBox rows = new VBox();
    private boolean computer;
    public int ships = 5;

    /**
     *
     * @param computer
     * @param handler
     */
    public Gameboard(boolean computer, EventHandler<? super MouseEvent> handler) {
        this.computer = computer;
        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Cell c = new Cell(x, y, this);
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }
            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }

    public boolean placeShip(Ship ship, int x, int y) {
        if (canPlaceShip(ship, x, y)) {
            int length = ship.type;

            if (ship.vertical) {
                for (int i = y; i < length + y; i++) {
                    Cell cell = getCell(x, i);
                    cell.ship = ship;
                    if (!computer) {
                        cell.setFill(Color.GRAY);
                        cell.setStroke(Color.BLACK);
                    }
                }
            } else {
                for (int i = x; i < x + length; i++) {
                    Cell cell = getCell(i, y);
                    cell.ship = ship;
                    if (!computer) {
                        cell.setFill(Color.GRAY);
                        cell.setStroke(Color.BLACK);
                    }
                }
            }
            return true;
        }
        return false;
    }

    //returns cell
    public Cell getCell(int x, int y) {
        return (Cell)
                ((HBox) rows.getChildren().get(y)).getChildren().get(x);
    }

    //returns 'neighbors'
    private Cell[] getNearby(int x, int y) {
        Point2D[] points = new Point2D[]{
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };

        List<Cell> nearby = new ArrayList<>();

        for (Point2D p : points) {
            if (isOnBoard(p)) {
                nearby.add(getCell((int) p.getX(), (int) p.getY()));
            }
        }

        return nearby.toArray(new Cell[0]);
    }

    //determines if a ship can be placed in that spot
    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.type;

        if (ship.vertical) {
            for (int i = y; i < y + length; i++) {
                if (!isOnBoard(x, i))
                    return false;

                Cell cell = getCell(x, i);
                if (cell.ship != null)
                    return false;

                for (Cell nearby : getNearby(x, i)) {
                    if (!isOnBoard(x, i))
                        return false;

                    if (nearby.ship != null)
                        return false;
                }
            }
        } else {
            for (int i = x; i < x + length; i++) {
                if (!isOnBoard(i, y))
                    return false;

                Cell cell = getCell(i, y);
                if (cell.ship != null)
                    return false;

                for (Cell nearby : getNearby(i, y)) {
                    if (!isOnBoard(i, y))
                        return false;

                    if (nearby.ship != null)
                        return false;
                }
            }
        }
        return true;

    }

    private boolean isOnBoard(Point2D point) {
        return isOnBoard(point.getX(), point.getY());
    }

    //both of these determine if that point is valid

    private boolean isOnBoard(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

}
