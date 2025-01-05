package Quests;

import entity.Entity;
import main.GamePanel;

import java.util.ArrayList;

public class Quest extends Entity {
    public int progressCounter;
    public int progressGoal;
    public boolean completed;
    public String difficulty;
    public String task;
    public ArrayList<String> dialogues;

    // QUEST TYPES
    public int quest_type;
    public final int slayMonster_type = 0;
    public final int findObject_type = 1;
    public final int completeEvent_type = 2;
    public Entity reward;
// THis is a
    GamePanel gp;


    public Quest(GamePanel gp) {
        // Call the parent class constructor (Entity)
        super(gp); // Explicit call to the parent class constructor
        this.gp = gp;
    }

    public void update(int type) {
        checkCompletion();

        // Handle specific quest types
        if (type == slayMonster_type && gp.player.monsterKilled) {

            progressCounter++;
            gp.player.monsterKilled = false;
            checkCompletion();
        }

        // Add more logic for other quest types here if necessary
        // For example, if type == findObject_type and the player has found the object
        if (type == findObject_type && gp.player.hasFoundObject) {
            progressCounter++;
            checkCompletion();
        }

        // Add additional checks for other quest types as needed
    }

    protected void checkCompletion() {
        if (progressCounter >= progressGoal) {
            completed = true;
        }
    }
}

