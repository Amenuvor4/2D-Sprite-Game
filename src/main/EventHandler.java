package main;

import entity.Entity;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect[][][] eventRect;

    int previousEventX = 0;
    int previousEventY = 0;


    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;
    public EventHandler(GamePanel gp){

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){

            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;

                if(row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }

        }




        this.gp = gp;
    }



    public void checkEvent() {

        // Check if the player character is more than 2 tile away from the last event

        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }
        if(canTouchEvent){
            if(hit(0,27, 16, "right")){damagePit( gp.dialogueState);}
            else if (hit(0,23, 21, "down")){healFountain(gp.dialogueState);}
            else if (hit(0,13,38, "any")){teleport(1,12, 13);}
            else if (hit(1,12, 13, "down")){teleport(0,13,38);}
            else if (hit(1,12,9,"up")){speak(gp.npc[1][0]);}
        }


    }

    public boolean hit(int map, int col, int row, String reqDirection){
        boolean hit = false;
        if(map == gp.currentMap){
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            // getting event rects position
            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;
            // If the solid area of the event is touching the player at the right spots then hit = true
            if(gp.player.solidArea.intersects(eventRect[map][col][row])){ //&& eventRect[col][row].eventDone == false){
                if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;

                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;

        }

        return  hit;

    }


    public void damagePit(int gameState){

        gp.gameState = gameState;
        gp.playSE(7);
        gp.ui.currentDialogue = "You've fallen into a pit";
        gp.player.life--;
        //eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healFountain(int gameState){

        if(gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.playSE(3);
            gp.player.attackCancel = true;
            gp.ui.currentDialogue = "You have found the healing fountain";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
        }

        gp.keyH.enterPressed = false;



    }


    // You can also make a teleport function but its optional.
    public void teleport(int map, int col, int row){
        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;

    }

    public void speak(Entity entity){
        if (gp.keyH.enterPressed){
            gp.gameState = gp.dialogueState;
            gp.player.attackCancel = true;
            entity.speak();
        }
    }


}
