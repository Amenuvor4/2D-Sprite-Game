package Quests;

import entity.Entity;
import main.GamePanel;

import javax.swing.*;

public class Quest1 extends Entity {


    public static final String questName = "Lets See what you got";
    public Quest1(GamePanel gp) {
        super(gp);
        name = questName;
        completed = false;
        difficulty = "Very Easy";
        task = "Kill 5 monsters";
        progressCounter = 0;
        level = 1;
        image = setup("/quests/quest1", gp.tileSize *7, gp.tileSize*4);
        description = "A journey of a thousand miles begins with\n" +
                      " a single step, you must begin yours.\n" +
                      "You must slay" + " 5 green slime monsters. \n" +
                      "Once that's" +
                      " done come back to\n the old man and he" +
                      " will give\n you A reward";
    }


    public void update(GamePanel gp){
        int monsterIndex = gp.checker.checkEntity(this, gp.monster);
        if(gp.monster[gp.currentMap][monsterIndex].dying){
            progressCounter++;
        }
        if(progressCounter >= 5){
            completed = true;
        }
    }
}
