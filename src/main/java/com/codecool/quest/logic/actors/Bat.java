package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.AutoTarget;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;

public class Bat extends Actor implements Combative {

    AutoTarget autotarget = new AutoTarget(MONSTER_ATTACK_RANGE, this);

    private static final int INITIAL_HEALTH = 6;
    private static final int INITIAL_ATTACK_DAMAGE = 1;
    private static final int INITIAL_ARMOR = 0;
    private static final int MONSTER_ATTACK_RANGE = 3;

    private int[] direction = new int[]{1,1};
    // Boti's version: private Direction direction = new Direction(1, 1);


    public Bat(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setArmor(INITIAL_ARMOR);
        this.setAttackDamage(INITIAL_ATTACK_DAMAGE);
        this.setMonsterAttackRange(MONSTER_ATTACK_RANGE);
    }

    @Override
    public void act() {

        int dx = direction[0];
        int dy = direction[1];

        Cell nextCell;

        //if player is near it will search for the next closest possible cell
        if(isPlayerNear()) {
            nextCell= autotarget.pathFinding();

            //step on nextCell if possible
            if (!nextCell.isBlocking() && !isPlayerNexToIt()) {
                this.moveTo(nextCell);
                //if there is a player, then attack
            } else if(isPlayerNexToIt()){
                Actor target = getPlayerCurrentPosition().getActor();
                this.attack(target);
                //if something blocking the player's side then just stand and wait
            } else if(!nextCell.isBlocking()){
                this.moveTo(this.getCell());
            }
         } else {
            nextCell = wallBounceCheck(dx, dy);
            this.moveTo(nextCell);
        }
    }


    public Cell wallBounceCheck(int dx, int dy) {
        Cell nextCell;
        Cell otherSideCell;
        boolean rightLeftSideCheck;
        boolean topBottomSideCheck;

        //check if it hits the wall
        nextCell = super.getCell().getNeighbor(dx, dy);
        if (fixTiles.contains(nextCell.getTileName()) || fixActors.contains(nextCell.getTileName())) {

            //check if it hits wall on right or left side
            nextCell = super.getCell().getNeighbor(dx, 0);
            if(fixTiles.contains(nextCell.getTileName()) || fixActors.contains(nextCell.getTileName())) {

                direction[0] *= -1;
                dx = direction[0];
                rightLeftSideCheck = true;
            } else {
                rightLeftSideCheck = false;
            }

            //check if it hits wall on bottom or top
            nextCell = super.getCell().getNeighbor(0, dy);
            if(fixTiles.contains(nextCell.getTileName()) || fixActors.contains(nextCell.getTileName())) {

                direction[1] *= -1;
                dy = direction[1];
                topBottomSideCheck = true;
            } else {
                topBottomSideCheck = false;
            }

            //check if stuck between right and left walls
            nextCell = super.getCell().getNeighbor(0, dy);
            otherSideCell = super.getCell().getNeighbor(dx, 0);
            if (rightLeftSideCheck &&
                    !fixTiles.contains(nextCell.getTileName()) &&
                    nextCell.getActor() == null &&
                    !fixActors.contains(nextCell.getTileName()) &&
                    (fixTiles.contains(otherSideCell.getTileName()) ||
                    fixActors.contains(otherSideCell.getTileName())) &&
                    otherSideCell.getActor() == null) {

                dx = 0;
            }

            //check if stuck between top and bottom walls
            nextCell = super.getCell().getNeighbor(dx, 0);
            otherSideCell = super.getCell().getNeighbor(0, dy);
            if (topBottomSideCheck &&
                    !fixTiles.contains(nextCell.getTileName()) &&
                    nextCell.getActor() == null &&
                    !fixActors.contains(nextCell.getTileName()) &&
                    (fixTiles.contains(otherSideCell.getTileName()) ||
                    fixActors.contains(otherSideCell.getTileName())) &&
                    otherSideCell.getActor() == null){

                dy = 0;
            }

            //check if hits corner
            if (topBottomSideCheck && rightLeftSideCheck) {
                Cell rightLeftContinueCheck = super.getCell().getNeighbor(dx * -1, dy);
                Cell topDownContinueCheck = super.getCell().getNeighbor(dx, dy * -1);

                //check if it can escape right or left
                if (!fixTiles.contains(rightLeftContinueCheck.getTileName()) &&
                        !fixActors.contains(rightLeftContinueCheck.getTileName())) {

                    direction[0] *= -1;
                    dx = direction[0];
                }

                //check if it can escape top or bottom
                if (!fixTiles.contains(topDownContinueCheck.getTileName()) &&
                        !fixActors.contains(topDownContinueCheck.getTileName())) {

                    direction[1] *= -1;
                    dy = direction[1];
                }
            }

            //check if it hits wall on inner corner
            nextCell = super.getCell().getNeighbor(dx, dy);
            if(fixTiles.contains(nextCell.getTileName()) || fixActors.contains(nextCell.getTileName())) {

                direction[0] *= -1;
                direction[1] *= -1;
                dx = direction[0];
                dy = direction[1];

                //check if it hits a wall and the next step is inner corner wall, it goes back, instead of waiting one turn
                if(topBottomSideCheck) {
                    direction[1] *= -1;
                    dy = direction[1];
                }

                if(rightLeftSideCheck) {
                    direction[0] *= -1;
                    dx = direction[0];
                }

            }
            nextCell = super.getCell().getNeighbor(dx, dy);
        }
        return nextCell;
    }

    /* Boti's version:

            Cell nextCell = this.getCell().getNeighbor(direction);

        if (nextCell.getActor() instanceof Player) {
            attack(nextCell.getActor());
        } else {
            changeDirection(nextCell);
            this.moveTo(this.getCell().getNeighbor(direction));
        }
    }

    private void changeDirection(Cell defaultCell) {
        Cell currentCell = this.getCell();
        Direction calculatedDirection = direction;
        Cell yNeighbor = currentCell.getNeighbor(direction.yComponent());
        Cell xNeighbor = currentCell.getNeighbor(direction.xComponent());

        if (yNeighbor.isBlocking() && xNeighbor.isBlocking())
            calculatedDirection = direction.reversed();
        else if (xNeighbor.isBlocking())
            calculatedDirection = direction.xFlipped();
        else if (yNeighbor.isBlocking())
            calculatedDirection = direction.yFlipped();
        else if (defaultCell.isBlocking())
            calculatedDirection = direction.reversed();

        if (currentCell.getNeighbor(calculatedDirection).isBlocking())
            calculatedDirection = direction.reversed();

        direction = calculatedDirection;
    }
     */

    public void terminate() {
        this.getCell().setActor(null);
        this.getCell().setType(CellType.BONE);
        this.getCell().getGameMap().removeCombativeActor(this);
    }

    @Override
    public String getTileName() {
        return "bat";
    }

}
