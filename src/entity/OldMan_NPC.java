package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class OldMan_NPC extends Entity{

    /*NOTE TO FUTURE SELF: you still need to fix the collision detector for this npc because he still walks past
    The first trees when you run*/


    public OldMan_NPC(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        currentDialogue = null;
        dialogueSet = -1;

        getImage();
        setDialogue();
    }


    public void setDialogue(){
        dialogues[0][0] = "Hello, lad";
        dialogues[0][1] = "My name is young lord Bartholomeus";
        dialogues[0][2] = "I have a very important job for you";
        dialogues[0][3] = "I want to test out you skills and to make sure you\n are not a bum";
        dialogues[0][4] = "Go slay 5 green slime monsters and come back\n and I'll give you something special";
        questDialogues.add(dialogues[0][4]);



        dialogues[1][0]= "If you become tired, rest at then water.";
        dialogues[1][1] = "However, the monsters reappear if you rest.\nI don't know why but that's how it works";
        dialogues[1][2] = "In any case, don't push yourself too hard";

        dialogues[2][0] = "i wonder how to open that door...";
    }


    public void getImage() {

        up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
    }


    public void setAction(){

        if(onPath){

            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);

        }else{

            actionLockCounter++;
            if(actionLockCounter == 100){
                Random random = new Random();
                int i = random.nextInt(100)+1;

                if(i <= 25){
                    direction = "up";
                }
                if(i > 25 && i <= 50){
                    direction = "down";
                }
                if(i > 50 && i <= 75){
                    direction = "left";
                }
                if(i > 75){
                    direction = "right";
                }

                actionLockCounter = 0;
            }

        }


    }

    public void speak(){

        // Do character specific stuff
        facePlayer();
        startDialogue(this,dialogueSet);


        dialogueSet++;
        if(dialogues[dialogueSet][0] == null){

            dialogueSet--;
        }



        if(gp.player.life < gp.player.maxLife/2){
            dialogueSet = 1;
        }

        // IF you complete a quest

    }


}
