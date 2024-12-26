package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_potion_red extends Entity {

    GamePanel gp;
    public static final String objName = "Red Potion";
    public OBJ_potion_red(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name = objName;
        stackable = true;
        value = 2;
        price = 5;
        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nThis Serum will help heal you up";

        setDialogue();
    }

    public void setDialogue() {

        dialogues[0][0] = "You drink the " + name + "!\n" +
                "Your have restored " + value + " health!";
    }
    public boolean use(Entity entity){
        startDialogue(this,0);
        entity.life += value;
        gp.playSE(2);
        return  true;

    }
}
