package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {

    GamePanel gp;

    public static final String objName = "Mana Crystal";

    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        type = type_pickupOnly;
        value = 1;
        down1 = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
        down1 = image;
    }
    public boolean use(Entity entity){
        gp.playSE(3);
        gp.ui.addMessage("Mana +" + value);
        gp.player.mana += value;
        return true;
    }
}