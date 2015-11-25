package juego;
/*
 * Disclaimer* The pig and zombie pig sprites where made by me, and the other ones
 * were ripped from pokemon emerald and uploaded to 
 * http://www.spriters-resource.com/game_boy_advance/pokeem/
 */
import java.awt.Canvas;
import java.awt.Color;
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
import juego.menus.GameOverScreen;
import juego.menus.Menu;
import juego.menus.PauseMenu;
import juego.sound.Sound;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    private boolean running = false;

    private static int width = 300 - 60;
    private static int height = (width + 60) / 16 * 9;
    private static int miniMapWidth = 50;
    private static int miniMapHeight = 50;
    private static int scale = 3;
    public static String title = "The Adventures of Porki";

    private Thread thread;
    private JFrame frame;
    public Keyboard key;
    private Mouse mouse;
    
    public Level level;
    private Player player;
    private Screen screen;
    private Menu menu;
    private PauseMenu pauseMenu;
    private GameOverScreen gameOverScreen;
    //private Font font;
    
    public static boolean paused = false;
    
    private boolean dead_resetMM = false;
    
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
    
    // MiniMap stuff
    private BufferedImage miniMapImage = new BufferedImage(miniMapWidth, miniMapHeight, BufferedImage.TYPE_INT_RGB);
    private int[] miniMapPixels = ((DataBufferInt) miniMapImage.getRaster().getDataBuffer()).getData();

    public enum STATE {
    	Menu,
    	CreateChar,
    	Game,
    	End
    };
    
    public static STATE gameState = STATE.Menu;
    
    public Game() {
        Dimension size = new Dimension(width * scale + 60 * 3, height * scale);
        setPreferredSize(size);

        screen = new Screen(width, height, miniMapWidth, miniMapHeight);
        frame = new JFrame();
        key = new Keyboard();
        mouse = new Mouse();
        menu = new Menu(this);
        pauseMenu = new PauseMenu();
        gameOverScreen = new GameOverScreen();
        //font = new Font();
        uiManager = new UIManager();
        
        addKeyListener(key);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }
    
    public void init(String name){
    	level = Level.spawn;
    	player = new Player(name, playerSpawn_spawnLevel.getX(), playerSpawn_spawnLevel.getY() + 6, key);
        level.add(player);
        level.addLevelMobs();
        dead_resetMM = false;
        if(name.toUpperCase().equals("JOHN CENA"))
        	Sound.JOHNCENA.loop();
        else
        	Sound.spawnMusic.loop();
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
    
    public static STATE getGameState(){
    	return gameState;
    }
    
    public static void setGameState(STATE state){
    	gameState = state;
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
        if(gameState == STATE.Game){
        	if(!paused){
        		level.update(this, player, screen);
	        	uiManager.update();
        	}
        	else
        		pauseMenu.update();
        } else if(gameState == STATE.Menu){
        	menu.update();
        } else if(gameState == STATE.End){
        	gameOverScreen.update();
        	level.removeAll();
        }
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
        Graphics g = bs.getDrawGraphics();
        
        if(gameState == STATE.Game){
	        double xScroll = player.getX() - screen.width / 2;
	        double yScroll = player.getY() - screen.height / 2;
	        level.render((int)xScroll, (int)yScroll, screen);
	        //font.render(-5, 120, "Welcum to a new\nadventure.", screen);
	        
	        // Copy the pixels we have in Screen.java to the pixels array here/*
	        for (int i = 0; i < pixels.length; i++) {
	            pixels[i] = screen.pixels[i];
	        }
	        // Next comes all the graphics that should be displayed
	        g.drawImage(image, 0, 0, width * scale, height * scale, null);
	        // We render all the uicomponents
	        uiManager.render(g);
	        // If it's paused, we render over the game
	        if(paused)
	        	pauseMenu.render(g);
	        
	        g.setColor(Color.BLACK);
	        g.fillRect(735, 15, 150, 150);
	    
	        int xMMScroll = player.getTileX() - miniMapWidth / 2;
	        int yMMScroll = player.getTileY() - miniMapHeight / 2;
	        
	        level.renderMiniMap(xMMScroll, yMMScroll, screen);
	        
	        for(int i = 0; i < miniMapPixels.length; i++)
	        	miniMapPixels[i] = screen.miniMapPixels[i];
	        
	        g.drawImage(miniMapImage, 735, 15, miniMapWidth * scale, miniMapHeight * scale, null);
	        
	        g.setColor(Color.WHITE);
	        g.drawRect(735, 15, 150, 150);
	        
        } else if(gameState == STATE.Menu){
        	g.drawImage(image, 0, 0, width * scale + 180, height * scale, null);
        	menu.render(g);
//        	g.setColor(Color.white);
//        	g.drawString("Menu", 100, 100);
        } else if(gameState == STATE.End) {
        	if(!dead_resetMM){
        		for(int i = 0; i < miniMapPixels.length; i++)
    	        	screen.miniMapPixels[i] = 0;
        		dead_resetMM = true;
        	}
        	
        	g.drawImage(image, 0, 0, width * scale + 180, height * scale, null);
        	gameOverScreen.render(g);
        }
	
	 
	        
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
