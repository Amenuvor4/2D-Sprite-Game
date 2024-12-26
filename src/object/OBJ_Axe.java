package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {

    public static final String objName = "Normal Axe";
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        type = type_axe;
        name = objName;
        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
        attackValue = 3;
        description = "[" + name + "]\nThis Axe is a basic one";
        attackArea.width = 30;
        attackArea.height = 30;
        price = 65;
        knockBackPower = 5;
        motion1_direction = 20;
        motion2_direction = 40;
    }
}
