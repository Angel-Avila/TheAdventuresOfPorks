package juego;
/*
 * Disclaimer* The pig and zombie pig sprites where made by me, and the other ones
 * were ripped from pokemon emerald and uploaded to 
 * http://www.spriters-resource.com/game_boy_advance/pokeem/
 */
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import juego.entity.mob.Player;
import juego.graphics.Screen;
import juego.graphics.ui.UIManager;
import juego.input.Keyboard;
import juego.input.Mouse;
import juego.level.Level;
import juego.level.TileCoordinate;
import juego.sound.Sound;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    private static int width = 300 - 60;
    private static int height = width / 16 * 9;
    private static int scale = 3;
    public static String title = "The Adventures of Porki";

    private Thread thread;
    private JFrame frame;
    private Keyboard key;
    private Mouse mouse;
    public Level level;
    private Player player;
    private boolean running = false;
    private Screen screen;
    //private Font font;
    
    private static UIManager uiManager;
    
    //private TileCoordinate spawn_teleporter = new TileCoordinate(28, 24);
    public TileCoordinate playerSpawn_spawnLevel = new TileCoordinate(30, 40);
    public TileCoordinate playerSpawn_labyrinth = new TileCoordinate(17, 10);
    
    //private int time = 0;
    //private boolean teleporting = false;
    
    // Create the image
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    // Access the image
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    public Game() {
        Dimension size = new Dimension(width * scale + 60 * 3, height * scale);
        setPreferredSize(size);

        screen = new Screen(width, height);
        frame = new JFrame();
        key = new Keyboard();
        mouse = new Mouse();
        level = Level.spawn;
        //font = new Font();
        uiManager = new UIManager();
        
        player = new Player("Ragnar�k", playerSpawn_spawnLevel.getX(), playerSpawn_spawnLevel.getY() + 6, key);
        level.add(player);
        level.addLevelMobs();
        
        //Sound.spawnMusic.loop();
        
        addKeyListener(key);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }
    
    public static int getWindowWidth(){
    	return width * scale;
    }
    
    public static int getWindowHeight(){
    	return height * scale;
    }

    public static UIManager getUIManager(){
    	return uiManager;
    }
    
    //Synchronized avoids some problems, boosts performance of the threads
    // This method just starts the game
    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    // A method to rejoin the threads and stop them at once
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        // For the FPS count
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;

        // So the screen is focused as soon as we run the game
        requestFocus();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update(); // Notch calls it tick
                updates++;
                delta--;
            }
            render();
            frames++;

            // FPS counter
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frame.setTitle(title + " | " + updates + " ups " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
    }

    public void update() {
        key.update();
        level.update(this, player);
        uiManager.update();
        /*
        if(key.up){
        	if(Sound.spawnMusic.clip.isActive())
        		Sound.JOHNCENA.quickPlay(Sound.spawnMusic);
        	if(!Sound.JOHNCENA.clip.isActive())
        		Sound.spawnMusic.loop();
        }*/
    }
     
     public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            // Triple buffering so we don't wait to display the next img 
            // This starts buffering the next image; simply it's faster
            createBufferStrategy(3);
            return;
        }
        
        screen.clear();
        double xScroll = player.getX() - screen.width / 2;
        double yScroll = player.getY() - screen.height / 2;
        level.render((int)xScroll, (int)yScroll, screen);
        //font.render(-5, 120, "Welcum to a new\nadventure.", screen);
        
        // Copy the pixels we have in Screen.java to the pixels array here/*
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        // Next comes all the graphics that should be displayed
        g.drawImage(image, 0, 0, width * scale, height * scale, null);
        uiManager.render(g);
        
        /* g.fillRect(Mouse.getX() - 32, Mouse.getY() - 32, 64, 64);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana",0,50));
        g.drawString("Coord: " + Mouse.mouseX + " " + Mouse.mouseY, 80, 80);
        g.drawString("solid: " + level.getTile(29, 45).solid(), 80, 80);
        */
        // Dispose of all the graphics that aren't gonna be used
        g.dispose();

        // Buffer swapping or "blitting", shows the next buffer visible
        bs.show();
    }

     
    public static void main(String[] args) {
        Game game = new Game();

        // To avoid graphical errors
        game.frame.setResizable(false);

        // Title of our game
        game.frame.setTitle(Game.title);

        // It fills the window with something(parameter)
        game.frame.add(game);

        // Sizes the frame to be of our desired size
        game.frame.pack();

        // Closes the app when you click the close button
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centers our window
        game.frame.setLocationRelativeTo(null);

        // Shows our window
        game.frame.setVisible(true);

        // Starts our game
        game.start();
    }
}
