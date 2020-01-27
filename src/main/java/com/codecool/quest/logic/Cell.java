package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.items.Hammer;
import com.codecool.quest.logic.items.Item;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private Item item;
    private GameMap gameMap;
    private int x, y;

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public void setItem(Item item) { this.item = item; }

    public Item getItem() { return item; }

    public Cell getNeighbor(int dx, int dy) {
        try {
            return gameMap.getCell(x + dx, y + dy);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public Cell getNeighbor(Direction direction) {
        return getNeighbor(direction.getDx(), direction.getDy());
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
