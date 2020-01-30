package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.util.Direction;
import com.codecool.quest.util.GameEvent;
import com.codecool.quest.util.GameEventHandler;
import com.codecool.quest.util.GameOverEvent;

public class Player extends Actor {

    private static Player player;
    private static final int INITIAL_HEALTH = 20;
    private static final int INITIAL_ATTACK_DAMAGE = 5;
    private static final int INITIAL_ARMOR = 0;

    private GameEvent gameOverEvent;

    public Player(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
        player = this;
    }

    public static Player getInstance() {
        return player;
    }

    public void move(int dx, int dy) {
        this.move(new Direction(dx, dy));
    }

    public void move(Direction direction) {
        Cell nextCell = this.getCell().getNeighbor(direction);
        if (nextCell == null) return;

        if (!nextCell.isBlocking())
            this.moveTo(nextCell);
        else if (nextCell.hasActor())
            this.attack(nextCell.getActor());
    }

    public void terminate() {
        this.getCell().setActor(null);
        gameOverEvent = new GameOverEvent();
    }

    public boolean isDead() {
        return health == 0;
    }

    public GameEvent getGameOverEvent() {
        return gameOverEvent;
    }

    public String getTileName() {
        return "player";
    }

    public boolean isDoorInNeighbourCell() {
        return Direction.MAIN_DIRECTIONS.stream().anyMatch(
                direction -> getCell().getNeighbor(direction).getType() == CellType.DOOR_CLOSED
        );
    }

    public void openDoorInNeighbourCell() {
        Cell neighbourCell;
        for (Direction direction : Direction.MAIN_DIRECTIONS) {
            neighbourCell = getCell().getNeighbor(direction);
            if (neighbourCell.getType() == CellType.DOOR_CLOSED) {
                neighbourCell.setType(CellType.DOOR_OPENED);
                return;
            }
        }

    }
}
