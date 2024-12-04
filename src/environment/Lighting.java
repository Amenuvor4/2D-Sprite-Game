package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {


    GamePanel gp;

    BufferedImage darknessFilter;

    public Lighting(GamePanel gp, double circleSize) {

        this.gp = gp;

        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        // CREATE A SCREEN-SIZED RECTANGLE AREA
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));

        // GET THE CENTER X AND Y OF THE LIGHT CIRCLE
        int centerX = gp.player.screenX + (gp.tileSize) / 2;
        int centerY = gp.player.screenY + (gp.tileSize) / 2;

        // GET THE TOP LEFT X AND Y OF THE LIGHT CIRCLE
        double x = centerX - (circleSize / 2);
        double y = centerY - (circleSize / 2);

        // CREATE A LIGHT CIRCLE SHAPE
        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);


        // Create a light circle area
        Area lightArea = new Area(circleShape);

        // Subtract the light circle form the screen rectangle
        screenArea.subtract((lightArea));

        // Set a color (black) to draw the rectangle
        g2.setColor(new Color(0, 0, 0.95f));

        // Draw the screen rectangle without the light circle area
        g2.fill(screenArea);

        g2.dispose();
    }

    public void draw(Graphics2D g2){

        g2.drawImage(darknessFilter, 0, 0, null);

    }
}
