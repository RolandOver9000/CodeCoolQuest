package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.Bat;
import com.codecool.quest.logic.actors.Duck;
import com.codecool.quest.logic.actors.Golem;
import com.codecool.quest.logic.actors.Skeleton;
import com.codecool.quest.logic.items.Key;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Button pickUpButton = new Button("Pick up");
    ListView<String> inventory = new ListView<>();

    Label characterNameLabel = new Label("hackerman");
    ScheduledExecutorService botActuator = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = createUI();
        BorderPane borderPane = createBorderPane(ui);
        Scene scene = createScene(borderPane);
        prepareStage(primaryStage, scene);
        refresh();
        primaryStage.show();
        borderPane.requestFocus();
        setCharacterName();
        activateBots();
    }

    private void prepareStage(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.setTitle("Codecool Quest");
        primaryStage.setOnCloseRequest(windowEvent -> botActuator.shutdown());
    }

    private Scene createScene(BorderPane borderPane) {
        // All of the list that you work with in javafx has a type of ObservableList
        ObservableList<String> items = FXCollections.observableArrayList();
        pickUpButton.setOnAction(actionEvent -> {
            addItemToInventory(map, "hammer", items);
            addItemToInventory(map, "key", items);
            addItemToInventory(map, "coins", items);
            borderPane.requestFocus();
        });

        inventory.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                String selectedItem = inventory.getSelectionModel().getSelectedItem();
                if (selectedItem.equals("key") && map.getPlayer().isDoorInNeighbourCell()) {
                    map.getPlayer().openDoorInNeighbourCell();
                    int indexOfKey = inventory.getSelectionModel().getSelectedIndex();
                    inventory.getItems().remove(indexOfKey);

                }

            }
        });

        Scene scene = new Scene(borderPane);
        scene.setOnKeyPressed(this::onKeyPressed);
        return scene;
    }

    private BorderPane createBorderPane(GridPane ui) {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
        return borderPane;
    }

    private GridPane createUI() {
        GridPane ui = new GridPane();

        ColumnConstraints col1 = new ColumnConstraints(85);
        ui.getColumnConstraints().add(col1);

        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Epic name: "), 0, 0);
        ui.add(characterNameLabel, 1, 0);
        ui.add(new Label("Health: "), 0, 1);
        ui.add(healthLabel, 1, 1);

        ui.add(pickUpButton, 0, 2);
        ui.add(inventory, 0, 3);
        inventory.setPrefWidth(30);
        inventory.setPrefHeight(70);

        return ui;
    }

    private void addItemToInventory(GameMap map, String itemToBeAdd, ObservableList<String> items) {
        try {
            if (map.getPlayer().getCell().getItem().pickUpItem(map, itemToBeAdd)) {
                items.add(itemToBeAdd);
                inventory.setItems(items);
            }
        } catch (NullPointerException ignored) {
        }
    }

    private TextInputDialog createCharacterNameDialog() {
        TextInputDialog nameDialog = new TextInputDialog("hackerman");
        nameDialog.setTitle("Character setup");
        nameDialog.setHeaderText("Choose an epic name for your character!");
        nameDialog.setContentText("Epic name:");
        nameDialog.setGraphic(null);

        Button cancelButton = (Button) nameDialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setVisible(false);

        TextField nameInputField = nameDialog.getEditor();
        Button OKButton = (Button) nameDialog.getDialogPane().lookupButton(ButtonType.OK);
        nameInputField.textProperty().addListener((observable, oldValue, newValue) -> {
            OKButton.setDisable(!(newValue.length() > 0 && newValue.length() <= 9));
        });

        return nameDialog;
    }

    private void setCharacterName() {
        TextInputDialog dialog = createCharacterNameDialog();
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> this.characterNameLabel.setText(name));
    }

    private void activateBots() {
        Runnable actuate = () -> Platform.runLater(() -> {
            Skeleton.getSkeletons().forEach(Skeleton::move);
            Bat.getBats().forEach(Bat::move);
            Duck.getDucks().forEach(Duck::move);
            Golem.getGolems().forEach(Golem::attackIfPlayerNextToIt);
            refresh();
        });
        botActuator.scheduleAtFixedRate(actuate, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void onKeyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                break;
        }
        refresh();
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
    }
}
