package main;

import data.Progress;
import entity.Entity;

public class EventHandler {
    GamePanel gp;
    EventRect[][][] eventRect;
    Entity eventMaster;

    int previousEventX = 0;
    int previousEventY = 0;


    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;
    public EventHandler(GamePanel gp){

        this.gp = gp;

        eventMaster = new Entity(gp);

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

        setDialogue();
    }


    public void setDialogue() {

        eventMaster.dialogues[0][0] = "You fall into a pit!";
        eventMaster.dialogues[1][0] = "You have found the healing fountain\n" + "The progress has been saved";
        eventMaster.dialogues[1][1] = "Damn this is good Water";
        eventMaster.dialogues[2][0] = "You have completed the quest.";
        eventMaster.dialogues[2][1] = "Go back to old man and let him know";

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
            else if (hit(0,13,38, "any")){teleport(1,12, 13, gp.indoor);} // To the merchant's house
            else if (hit(1,12, 13, "down")){teleport(0,13,38, gp.outside);} // to outside
            else if (hit(1,12,9,"up")){speak(gp.npc[1][0]);}
            else if (hit(0,10,9,"any")){teleport(2, 9, 41, gp.dungeon);} // To the dungeon
            else if (hit(2,9,41,"any")){teleport(0, 10, 9, gp.outside);} // To outside
            else if (hit(2,8,7,"any")){teleport(3, 26, 41, gp.dungeon);} // to B2
            else if (hit(3,26,41,"any")){teleport(2, 8, 7, gp.dungeon);} // to B1
            else if (hit(3,25,27,"any")){skullKing();}
        }
        if(gp.keyH.teleport){
            if(gp.currentMap == 0){
                teleport(2, 9, 41, gp.dungeon);
            }
            if(gp.currentMap == 2){
                teleport(3, 26, 41, gp.dungeon);
            }
            gp.keyH.teleport = false;
        }
        if(gp.player.currentQuest != null && gp.player.currentQuest.completed && !gp.player.questFinished){
            questCompleted(gp.dialogueState);
            gp.player.questFinished = true;

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
        eventMaster.startDialogue(eventMaster, 0);
        gp.player.life--;
        //eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healFountain(int gameState){

        if(gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.playSE(3);
            gp.player.attackCancel = true;
            eventMaster.startDialogue(eventMaster, 1);
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
            gp.saveLoad.save();
        }

        gp.keyH.enterPressed = false;

    }

    public void questCompleted(int gameState){

        gp.gameState = gameState;
        gp.playSE(3);
        gp.player.attackCancel = true;
        eventMaster.startDialogue(eventMaster, 2);



    }


    // You can also make a teleport function but its optional.
    public void teleport(int map, int col, int row, int area){
        gp.gameState = gp.transitionState;
        gp.nextArea = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gp.playSE(15);

    }

    public void speak(Entity entity){
        if (gp.keyH.enterPressed){
            gp.gameState = gp.dialogueState;
            gp.player.attackCancel = true;
            entity.speak();
        }
    }

    public void skullKing(){

        if(!gp.bossBattleOn && !Progress.skeletonDefeated){
            gp.gameState = gp.cutSceneState;
            gp.csManager.sceneNum = gp.csManager.skullKing;
        }

    }


}
