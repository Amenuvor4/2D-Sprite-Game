package interactiveTile;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class DestructibleWall extends InteractiveTile {


    public DestructibleWall(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize*row;



        down1 = setup("/interactive_Tile/destructibleWall", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 4;
    }

    public void playSE(){
        gp.playSE(18);
    }

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;

        return tile;
    }

    public boolean isCorrectItem(Entity entity){
        boolean isCorrectedItem = false;
        if(entity.currentWeapon.type == type_pickaxe){
            isCorrectedItem = true;
        }
        return isCorrectedItem;
    }


    public Color getParticleColor(){
        Color color = new Color(51,51,53);
        return color;
    }

    public int getParticleSize(){
        int size = 6;
        return size;
    }

    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }


    // If you want to add some mining methods you could add the check drop function
}
