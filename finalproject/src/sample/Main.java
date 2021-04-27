package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.*;
import javafx.geometry.Pos;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

/**
 * Battleship game to play against the computer
 * @author Kassidy Ashworth
 *
 */

public class Main extends Application {

    private boolean game = false;
    private Gameboard computerBoard;
    private Gameboard myBoard;
    private int shipsToPlace = 5;

    private boolean computerTurn = false;

    private Random random = new Random();

    private Parent createGame() {
        BorderPane root = new BorderPane();

        root.setPrefSize(200, 450);
        root.setLeft(new Text("Computer Board"));
        root.setRight(new Text("My Board"));

        computerBoard = new Gameboard(true, event -> {
            if (!game) {
                return;
            }

            Cell cell = (Cell) event.getSource();
            if (cell.wasShot) {
                return;
            }

            computerTurn = (!cell.shoot());

            //if there are no computer ships left, will send win popup message
            if (computerBoard.ships == 1) {
                Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
                winAlert.setTitle("YOU WIN");
                winAlert.setHeaderText(null);
                winAlert.setContentText("WINNER WINNER CHICKEN DINNER!!!");
                winAlert.showAndWait();
                System.exit(0);
            }

            if (computerTurn) {
                computerMove();
            }
        });

        myBoard = new Gameboard(false, event -> {
            if (game) {
                return;
            }

            Cell cell = (Cell) event.getSource();
            if (myBoard.placeShip(new Ship(shipsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
                if (--shipsToPlace == 1) {
                    startGame();
                }
            }
        });

        HBox hbox = new HBox(50, computerBoard, myBoard);
        hbox.setAlignment(Pos.BOTTOM_CENTER);

        root.setCenter(hbox);

        return root;
    }

    private void computerMove() {
        while (computerTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Cell cell = myBoard.getCell(x, y);
            if (cell.wasShot) {
                continue;    //get another turn if computer hits ship
            }

            computerTurn = cell.shoot();

            //if computer wins, displays losing popup message
            if (myBoard.ships == 1) {
                Alert loseAlert = new Alert(Alert.AlertType.INFORMATION);
                loseAlert.setTitle("YOU LOSE!!!");
                loseAlert.setHeaderText(null);
                loseAlert.setContentText("LOSER LOSER CHICKEN BRUISER");
                loseAlert.showAndWait();
                System.exit(0);
            }
        }
    }

    private void startGame() {
        int type = 5;

        while (type > 1) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (computerBoard.placeShip(new Ship(type, Math.random() < 0), x, y)) { //places 4 computer ships
                type = type - 1;
            }
        }

    game = true;
}

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createGame());
        primaryStage.setTitle("Kassidy's Battleship Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
