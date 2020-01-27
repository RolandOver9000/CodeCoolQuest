package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Actor implements Drawable {
    private Cell cell;

    protected int health;
    protected int attackDamage;
    protected int armor;
    protected List<String> fixTiles = new ArrayList<>(Arrays.asList("wall", "bronze torch", "campfire", "chest open"));
    protected List<String> fixActors = new ArrayList<>(Arrays.asList("pot", "chest open", "chest closed"));


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {}

    protected void moveTo(Cell nextCell) {
        this.cell.setActor(null);
        nextCell.setActor(this);
        this.setCell(nextCell);
    }

    protected void attack(Actor target) {
        target.setHealth(target.getHealth() - this.getAttackDamage());
        if (target.getHealth() <= 0)
            target.terminate();
    }

    public int getHealth() {
        return this.health;
    }
    public void setHealth(int newHealth) {this.health = newHealth;}

    public int getAttackDamage() {
        return this.attackDamage;
    }

    public void setAttackDamage(int newAttackDamage) {this.attackDamage = newAttackDamage;}

    public int getArmor() {
        return this.armor;
    }

    public void setArmor(int newArmor) {this.armor = newArmor;}

    public Cell getCell() {
        return this.cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
        cell.setActor(this);
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public abstract void terminate();

}
