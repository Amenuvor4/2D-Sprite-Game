package Quests;

import entity.Entity;
import main.GamePanel;

public class Quest1 extends Entity {

    public static final String questName = "Lets See what you got";
    public Quest1(GamePanel gp) {
        super(gp);
        name = questName;
        level = 1;
        down1 = setup("/quests/quest1", gp.tileSize *7, gp.tileSize*4);
        description = "A journey of a thousand miles begins with a single step, you must begin yours. " +
                "You must slay 5 green slime monsters. Once that's done come back to the old man and he will give you" +
                "A reward";
    }
}
