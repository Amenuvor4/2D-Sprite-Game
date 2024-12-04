package entity;

import main.GamePanel;

import java.awt.*;
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

        getImage();
        setDialogue();
    }


    public void setDialogue(){
        dialogue[0] = "Hello, lad";
        dialogue[1] = "HOWDY";
        dialogue[2] = "WHATS UP";
        dialogue[3] = "My name is young lord Bartholomeus";
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
        super.speak();
        onPath =  true;

    }


}
