
package game;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

public class GameLogic {
    private Label player;
    private GridPane grid;
    private BorderPane borders;
    private Label header;
    private Button[][] buttons;
    
    public GameLogic(Stage window) {
        player = new Label("X");
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        borders = new BorderPane();
        borders.setCenter(grid);
        borders.setTop(createTopPanel());
        createButtons();

        Scene scene = new Scene(borders);
        window.setScene(scene);
        window.setTitle("Tic-Tac-Toe");
        window.show(); 
    }


    public void changeTurn(Label player) {
        if (player.getText().equals("X")) {
            player.setText("O");
        } else {
            player.setText("X");
        }
        header.setText("Vuoro: " + player.getText());
    }

    private Label createTopPanel() {
        player.setFont(Font.font("Monospaced", 30));
        header = new Label("Vuoro: " + player.getText());
        header.setFont(Font.font("Monospaced", 30));
        return header;
    }

    public void handleGridButton(Button button) {
        if (button.getText().equals(" ")) {
            button.setText(player.getText());
            changeTurn(player);
            checkWinningConditions();
        }
    }

    public void checkWinningConditions() {
        ArrayList<Integer> sums = new ArrayList<>();
        sums.addAll(rowSums());
        sums.addAll(columnSums());
        sums.addAll(diagonalSums());
        if (sums.contains(3) || sums.contains(30)) {
            win();
        }

        
    }

    public void win(){
        for (Node button : grid.getChildren()) {
            button.setDisable(true);
        }
        header.setText("Loppu!");
    }

    public void createButtons() {
        buttons = new Button[9][9];
        for (int column=0; column<3; column++) {
            for (int row=0; row<3; row++) {
                Button button = new Button(" ");
                button.setFont(Font.font("Monospaced", 40));
                button.setOnAction((event) -> handleGridButton(button));
                grid.add(button, column, row);
                buttons[row][column] = button;
            }
        }
    }

    public ArrayList<Integer> rowSums() {
        ArrayList<Integer> sums = new ArrayList<>();
        for (int row = 0; row<3; row++) {
            int sum = 0;
            for (int column = 0; column<3; column++) {
                sum += buttonToInt(buttons[row][column]);
            }
            sums.add(sum);
        }
        return sums;
    }

    public ArrayList<Integer> columnSums() {
        ArrayList<Integer> sums = new ArrayList<>();
        for (int column = 0; column<3; column++){
            int sum = 0;
            for (int row = 0; row<3; row++) {
                sum += buttonToInt(buttons[row][column]);
            }
            sums.add(sum);
        }
        return sums;
    }

    public ArrayList<Integer> diagonalSums() {
        ArrayList<Integer> sums = new ArrayList<>();
        int sum1 = 0;
        int sum2 = 0;
        for (int i=0; i<3; i++) {
            sum1 += buttonToInt(buttons[i][i]);
            sum2 += buttonToInt(buttons[buttons.length - i - 1][i]);
        }
        sums.add(sum1);
        sums.add(sum2);
        return sums;
    }

    private int buttonToInt(Button button){
        if (button == null) {
            return 0;
        } else if (button.getText().equals("X")) {
            return 1;
        } else if (button.getText().equals("O")) {
            return 10;
        } 
        return 0;
    }
}