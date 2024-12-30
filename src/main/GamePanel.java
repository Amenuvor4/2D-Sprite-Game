package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;

import javax.swing.JPanel;


import Quests.QuestManager;
import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import tile.Map;
import tile.TileManager;
import interactiveTile.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 20; // Left to right
    public final int maxScreenRow = 12; // Top to bottom
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;

    // FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    // FPS
    int FPS = 60;

    // SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker checker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    Map map = new Map(this);
    SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    public CutsceneManager csManager = new CutsceneManager(this);
    public QuestManager questManager = new QuestManager(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity[][] obj = new Entity[maxMap][20];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][20];
    public Entity[][] quest = new Entity[maxMap][20];
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];
    public Entity[][] projectile = new Entity[maxMap][20];
    // public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;
    public final int cutSceneState = 11;
    public final int questState = 12;

    // OTHERS
    public boolean bossBattleOn = false;


    // AREA
    public int currentArea;
    public int nextArea;
    public final int outside = 0;
    public final int indoor = 1;
    public final int dungeon = 2;

    public GamePanel() {
        // Size of panel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        // Background Color to black
        this.setBackground(Color.black);

        // Add rendering performance
        this.setDoubleBuffered(true);

        // Add keyboard recognition
        this.addKeyListener(keyH);

        // Make GamePanel focused to receive input
        this.setFocusable(true);
    }

    public void setupGame() {

        currentArea = outside;
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        eManager.setUp();

        gameState = titleState;
        currentArea = outside;


        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn) {
            setFullScreen();
        }
    }

    public void resetGame(boolean restart){
        //player.restoreStatus();
        stopMusic();
        currentArea = outside;
        removeTemp();
        bossBattleOn = false;
        player.resetCounter();
        aSetter.setNPC();
        aSetter.setMonster();
        player.setDefaultPositions();
        if(restart) {
            player.setDefaultValues();
            aSetter.setObject();
            aSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }

    }

    public void setFullScreen() {
        if (Main.window == null) {
            System.err.println("Error: Main.window is not initialized.");
            return;
        }

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                // 1. UPDATE: update the information such as character position
                update();

                // 2. DRAW: draw the screen with updated information
                drawToTempScreen();
                drawToScreen();

                delta--;
                drawCount++;
            }

            if (timer >= 1_000_000_000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            // PLAYER
            player.update();

            // NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            // MONSTER
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive  && !monster[currentMap][i].dying) {
                        monster[currentMap][i].update();
                    }
                    if (!monster[currentMap][i].alive) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            // PROJECTILE
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].alive) {
                        projectile[currentMap][i].update();
                    }
                    if (!projectile[currentMap][i].alive) {
                        projectile[currentMap][i] = null;
                    }
                }
            }

            // PARTICLE
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive) {
                        particleList.get(i).update();
                    }
                    if (!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }
            // INTERACTIVE TILE
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }

            // EVENT MANAGER
            eManager.update();




        }

        if (gameState == pauseState) {
        }
    }

    public void drawToTempScreen() {

        // Clear the screen to prevent trails
        g2.setColor(Color.BLACK);  // Clear the screen with a black background
        g2.fillRect(0, 0, screenWidth2, screenHeight2);

        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }
        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }
        // MAP SCREEN
        else if(gameState == mapState){
            map.drawFullMapScreen(g2);
        }
        // Other game states
        else {
            // TILE
            tileM.draw(g2);

            // INTERACTIVE TILE
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }


            // ADD ENTITIES TO THE LIST
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }
            for (Entity value : particleList) {
                if (value != null) {
                    entityList.add(value);
                }
            }

            // SORT
            entityList.sort(new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }

            });

            // DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }



            // Clear entity list before adding new entities
            entityList.clear();

            // ENVIRONMENT
            eManager.draw(g2);

            // MINI MAP
            map.drawMiniMap(g2);

            // CUTSCENE
            csManager.draw(g2);

            // UI overlay, if applicable
            ui.draw(g2);
        }


        if(keyH.checkDrawTime){
            long drawEnd = System.nanoTime();
            long passed =  drawEnd - drawStart;
            g2.setColor(Color.white);
            int x = 10;
            int y = 380;
            int lineHeight = 30;
            g2.drawString("Players Coordinates:", x, y); y += lineHeight;
            g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
            g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
            g2.drawString("Col: " + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;

            g2.drawString("Draw Time: " + passed, x, y);
        }

        if(keyH.godMode){
            g2.setColor(Color.white);
            int x = 10;
            int y = 380;
            g2.drawString("GOD MODE ON", x, y);

        }
    }


    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }

    public void changeArea(){

        if(nextArea != currentArea){
            stopMusic();

            if(nextArea == outside){
                playMusic(0);
            }
            if(nextArea == indoor){
                playMusic(16);
            }
            if(nextArea == dungeon){
                playMusic(17);
            }

            // To rest the bolders
            aSetter.setNPC();
        }
        currentArea = nextArea;
    }
    public void removeTemp(){
        for(int mapNum = 0; mapNum < maxMap; mapNum++ ){
            for(int i =0; i < obj[1].length; i++){
                if(obj[mapNum][i] != null && obj[mapNum][i].temp){
                    obj[mapNum][i] = null;
                }
            }
        }
    }
}