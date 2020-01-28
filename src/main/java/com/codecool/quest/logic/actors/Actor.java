package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.HandleAttack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Actor implements Drawable {
    private Cell cell;
    HandleAttack handleAttack = new HandleAttack();

    protected int health;
    protected int attackDamage;
    protected int armor;
    protected List<String> fixTiles = new ArrayList<>(Arrays.asList("wall", "bronze torch", "campfire", "chest open"));
    protected List<String> fixActors = new ArrayList<>(Arrays.asList("pot", "chest open", "chest closed"));
    protected static Cell playerCurrentPosition;
    private static final int MONSTER_ATTACK_RANGE = 3;


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
    };

    public int attack(int targetHealth) {
        return targetHealth - this.attackDamage;
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

    public void setCell(Cell cell) { this.cell = cell;}

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void setPlayerCurrentPosition(Cell playerCurrentPosition) {
        Actor.playerCurrentPosition = playerCurrentPosition;
    }


    public Cell getClosestCellToPlayer() {

        int playerCurrentXPosition = playerCurrentPosition.getX();
        int playerCurrentYPosition = playerCurrentPosition.getY();
        int attackerCurrentXPosition = this.getCell().getX();
        int attackerCurrentYPosition = this.getCell().getY();

        int[] upDownDirections = new int[2];
        int[] rightLeftDirections = new int[2];
        String[] directions  = new String[] {"left", "right", "up", "down"};
        int closestNumber = MONSTER_ATTACK_RANGE * 2;
        int horizontalIndex = 0;
        int verticalIndex = 0;

        Cell closestCellToPlayer = null;

        //left
        rightLeftDirections[0] = Math.abs((attackerCurrentXPosition - 1) - playerCurrentXPosition);
        //right
        rightLeftDirections[1] = Math.abs((attackerCurrentXPosition + 1) - playerCurrentXPosition);

        //up
        upDownDirections[0] = Math.abs((attackerCurrentYPosition - 1) - playerCurrentYPosition);
        //down
        upDownDirections[1] = Math.abs((attackerCurrentYPosition + 1) - playerCurrentYPosition);


        //get the lowest distance from the lists
        for(int indexUpDown = 0; indexUpDown < upDownDirections.length; indexUpDown++) {
            for(int indexRightLeft = 0; indexRightLeft < rightLeftDirections.length; indexRightLeft++) {
                if (closestNumber > (upDownDirections[indexUpDown] + rightLeftDirections[indexRightLeft])) {
                    closestNumber = (upDownDirections[indexUpDown] + rightLeftDirections[indexRightLeft]);
                    horizontalIndex = indexRightLeft;
                    verticalIndex = indexUpDown;
                }
            }
        }

        if(rightLeftDirections[horizontalIndex] <= upDownDirections[verticalIndex] &&
                rightLeftDirections[horizontalIndex] != 0) {
            closestCellToPlayer = getDirectionOfClosestCell(directions[horizontalIndex]);
        } else if(upDownDirections[verticalIndex] < rightLeftDirections[horizontalIndex] &&
                upDownDirections[verticalIndex] != 0) {
            closestCellToPlayer = getDirectionOfClosestCell(directions[verticalIndex + 2]);
        } else if(rightLeftDirections[horizontalIndex] == 0 && upDownDirections[verticalIndex] == 1) {
            closestCellToPlayer = getDirectionOfClosestCell(directions[verticalIndex + 2]);
        } else if(rightLeftDirections[horizontalIndex] == 1 && upDownDirections[verticalIndex] == 0) {
            closestCellToPlayer = getDirectionOfClosestCell(directions[horizontalIndex]);
        } else if(rightLeftDirections[horizontalIndex] == 0) {
            closestCellToPlayer = getDirectionOfClosestCell(directions[verticalIndex + 2]);
        } else if(upDownDirections[verticalIndex] == 0) {
            closestCellToPlayer = getDirectionOfClosestCell(directions[horizontalIndex]);
        }

        System.out.println("lowest difference rightleft" + horizontalIndex + " updown " + verticalIndex);
        System.out.println("player    x " + playerCurrentPosition.getX() + "y " + playerCurrentPosition.getY());
        System.out.println(" rightleft " + Arrays.toString(rightLeftDirections) + " updown " + Arrays.toString(upDownDirections));
        //System.out.println("x to player " + closestCellToPlayer.getX() + " y to player " + closestCellToPlayer.getY());
        System.out.println("x enemy " + this.getX() + "y enemy " + this.getY());
        return closestCellToPlayer;
    }

    private Cell getDirectionOfClosestCell(String finalDirection) {
        //search for the closest direction and returns the cell

        System.out.println(finalDirection);
        switch(finalDirection) {
            case "left":
                return this.getCell().getNeighbor(-1 ,0);
            case "right":
                return this.getCell().getNeighbor(1 ,0);
            case "up":
                return this.getCell().getNeighbor(0, -1);
            case "down":
                return this.getCell().getNeighbor(0, 1);

            default:
                throw new IllegalStateException("Unexpected value: " + finalDirection);
        }
    }

    public boolean isPlayerNexToIt() {
        int[] xDirections = {1, 0, -1, 0};
        int[] yDirections = {0, 1, 0, -1};

        for (int index = 0; index < xDirections.length; index++) {
            Cell nextCell = this.getCell().getNeighbor(xDirections[index], yDirections[index]);

            if (nextCell.getActor() != null && nextCell.getActor().getTileName().equals("player")) {
                return true;
            }
        }
        return false;
    }

    public abstract void terminate();

    public boolean isPlayerNear() {
        return Math.abs(this.getX() - playerCurrentPosition.getX()) <= MONSTER_ATTACK_RANGE &&
                Math.abs(this.getY() - playerCurrentPosition.getY()) <= MONSTER_ATTACK_RANGE;
    }



}
