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

        currentDialogue = " ";
        dialogueSet = 0;

        getImage();
    }


    public void setDialogue() {
        dialogues[0][0] = "Hello, lad";
        dialogues[0][1] = "My name is young lord Bartholomeus";
        dialogues[0][2] = "I have a very important job for you";
        dialogues[0][3] = "I want to test out your skills and make sure you\nare not a bum";
        dialogues[0][4] = "Go slay 5 green slime monsters and come back\nand I'll give you something special";
        questDialogues.add(dialogues[0][4]);

        if (!gp.ui.declinedQuest) {
            dialogues[0][5] = "A journey of a thousand miles begins with a single step";
            dialogues[0][6] = "I wish you luck";
        } else {
            dialogues[0][5] = "Get away from my sight...\n you disgust me";
            dialogues[0][6] = "pathetic weakling";
        }

        if (gp.player.currentQuest != null) {
            dialogues[1][0] = "Why are you here?";
            dialogues[1][1] = "Go finish your task";
            dialogues[1][2] = "Remember, you have to\n" + gp.player.currentQuest.task;
        }

        if (gp.player.currentQuest != null && gp.player.currentQuest.completed) {
            dialogues[2][0] = "Well done, lad!";
            dialogues[2][1] = "You’ve completed the task, and I’m impressed.";
            dialogues[2][2] = "Take this as a reward!";
            if(gp.player.canObtainItem(gp.player.currentQuest.reward)){
                gp.playSE(4);
            }
        }

        if (gp.player.life < gp.player.maxLife / 2) {
            dialogues[3][0] = "You look weak...";
            dialogues[3][1] = "Be careful, lad. Rest if you need to.";
        }
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


    public void setAction() {

        if (onPath) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;
            if (actionLockCounter == 100) {
                Random random = new Random();
                int directionChoice = random.nextInt(4);  // Generates 0 to 3
                switch (directionChoice) {
                    case 0: direction = "up"; break;
                    case 1: direction = "down"; break;
                    case 2: direction = "left"; break;
                    case 3: direction = "right"; break;
                }
                actionLockCounter = 0;
            }
        }
    }

    public void speak() {
        setDialogue();
        // Face player before starting dialogue
        facePlayer();

        // Update the dialogue set based on specific conditions
        updateDialogueSet();

        // Display the dialogue if it exists
        if (dialogues[dialogueSet][dialogueIndex] != null) {
            startDialogue(this, dialogueSet);
        }else {
            System.out.println("This isn't working");
        }


    }


    private void updateDialogueSet() {

        // Check if the player has accepted the quest
        if(gp.player.currentQuest != null && !gp.player.currentQuest.completed){
            System.out.println(gp.player.currentQuest.task);
            dialogueSet = 1;
        }

        //Check if the player has completed the quest
        if(gp.player.currentQuest != null && gp.player.currentQuest.completed){
            dialogueSet = 2;
        }

        // Check if teh player's health is low

        if(gp.player.life < gp.player.maxLife /2){
            dialogueSet = 3;
        }
    }


}
