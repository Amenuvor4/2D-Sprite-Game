package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        type = type_axe;
        name = "Normal Axe";
        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
        attackValue = 3;
        description = "[" + name + "]\nThis Axe is a basic one";
        attackArea.width = 30;
        attackArea.height = 30;
        price = 65;
        knockBackPower = 5;
    }
}
