package src.juego;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import src.juego.entity.mob.Player;
import src.juego.graphics.Screen;
import src.juego.input.Keyboard;
import src.juego.input.Mouse;
import src.juego.level.Level;
import src.juego.level.TileCoordinate;

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
        TileCoordinate playerSpawn = new TileCoordinate(30, 43);
        player = new Player(playerSpawn.getX(), playerSpawn.getY() + 6, key);
        player.init(level);
        
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
        player.update();
        level.update();
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
        int xScroll = player.x - screen.width / 2;
        int yScroll = player.y - screen.height / 2;
        level.render(xScroll, yScroll, screen);
        player.render(screen);

        // Copy the pixels we have in Screen.java to the pixels array here/*
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        // Next comes all the graphics that should be displayed
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        /*
        g.fillRect(Mouse.getX() - 32, Mouse.getY() - 32, 64, 64);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana",0,50));
        g.drawString("Button: " + Mouse.getB(), 80, 80);
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
