package Quests;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;

import javax.swing.*;

public class Quest1 extends Quest {


    public static final String questName = "Lets See what you got";

    public Quest1(GamePanel gp) {
        super(gp);
        name = questName;
        completed = false;
        difficulty = "Very Easy";
        task = "Kill 5 monsters";
        quest_type = slayMonster_type;
        progressCounter = 0;
        exp = 5;
        reward = new OBJ_Shield_Blue(gp);
        progressGoal = 5;
        level = 1;
        image = setup("/quests/quest1", gp.tileSize * 7, gp.tileSize * 4);
        description =  """
    A journey of a thousand miles begins with 
    a single step. You must begin yours. 
    Slay 5 green slime monsters. Once that's
    done, return to the old man to receive
    your reward.
    """;

    }



//    public void setDialogue() {
//        dialogues[0][0] = "You have completed the quest.";
//        dialogues[0][1] = "Go back to old man and let him know";
//
//    }
}
