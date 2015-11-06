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
import juego.graphics.Sprite;
import juego.input.Keyboard;
import juego.input.Mouse;
import juego.level.Level;
import juego.level.TileCoordinate;
import juego.sound.Sound;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    private static int width = 300;
    private static int height = width / 16 * 9;
    private static int scale = 3;
    public static String title = "The Adventures of Porki";

    private Thread thread;
    private JFrame frame;
    private Keyboard key;
    private Mouse mouse;
    private Level level;
    private Player player;
    private boolean running = false;
    private Screen screen;
    
    private TileCoordinate spawn_teleporter = new TileCoordinate(28, 24);
    private TileCoordinate playerSpawn_spawnLevel = new TileCoordinate(30, 40);
    private TileCoordinate playerSpawn_labyrinth = new TileCoordinate(30, 43);
    
    private int time = 0;
    private boolean teleporting = false;
    
    // Create the image
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    // Access the image
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    public Game() {
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);

        screen = new Screen(width, height);
        frame = new JFrame();
        key = new Keyboard();
        mouse = new Mouse();
        level = Level.spawn;
        
        player = new Player(playerSpawn_spawnLevel.getX(), playerSpawn_spawnLevel.getY() + 6, key);
        level.addLevelMobs();
        level.add(player);
        
        Sound.spawnMusic.loop();
        
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
        level.update();/*
        if(key.up){
        	if(Sound.spawnMusic.clip.isActive())
        		Sound.JOHNCENA.quickPlay(Sound.spawnMusic);
        	if(!Sound.JOHNCENA.clip.isActive())
        		Sound.spawnMusic.loop();
        }*/
        // If the player is in the spawnlevel on the teleporter from the left side he goes to the labyrinth
        if(level == Level.spawn && 
        player.getTileX() == spawn_teleporter.getTileX() && 
        player.getTileY() == spawn_teleporter.getTileY()){
        	time++;
        	teleporting = true;
        	if(time % 120 == 0){
        		changeLevel(Level.labyrinth);		
        		return;
        	}
        }
        
        // But if he is in the labyrinth and manages to get to the other side, he returns to the spawn
        else if(level == Level.labyrinth && player.getTileY() == 44){
        	time++;
        	teleporting = true;
        	if(time % 120 == 0){
        		changeLevel(Level.spawn);
        		return;
        	}
        }
        
        else{
        	time = 0;
        	teleporting = false;
        }
    }
    
    /**
     * In order to change level we remove all the entities we have from the current one to avoid null pointer
     * exceptions and then we proceed to go to the next level where we add the level entities we should.
     */
    public void changeLevel(Level level){
    	for (int i = 0; i < this.level.entities.size(); i++) {
    		this.level.entities.get(i).remove();
		}
    	
    	for (int i = 0; i < this.level.projectiles.size(); i++) {
    		this.level.projectiles.get(i).remove();
		}
    	
    	for (int i = 0; i < this.level.particles.size(); i++) {
    		this.level.particles.get(i).remove();
		}
    	
    	for (int i = 0; i < this.level.players.size(); i++) {
    		this.level.players.get(i).remove();
		}
    	
    	this.level = level;
    	player.level = level;
    	
    	if(level == Level.labyrinth){
    		player.setXY(playerSpawn_labyrinth.getX(), playerSpawn_labyrinth.getY());
    		Sound.spawnMusic.stop();
    		Sound.encounter.play();
    	} else if(level == Level.spawn){
    		player.setXY(playerSpawn_spawnLevel.getX(), playerSpawn_spawnLevel.getY());
    		Sound.encounter.stop();
    		Sound.spawnMusic.loop();
    	}
    	
    	level.update();
    	level.add(player);
    	player.unRemove();
    	level.addLevelMobs();
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
        
        //Shows the teleporting "energy" under our player when he's about to teleport
        if(teleporting)
        	screen.renderSprite((int)player.getX() - 2, (int)player.getY() - 23, Sprite.teleporter_particles, true);
        
        
        // Copy the pixels we have in Screen.java to the pixels array here/*
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        // Next comes all the graphics that should be displayed
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        
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
