package juego.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import juego.Game;
import juego.Game.STATE;
import juego.graphics.ui.UIActionListener;
import juego.graphics.ui.UIButton;
import juego.graphics.ui.UIButtonListener;
import juego.util.ImageUtils;
import juego.util.Vector2i;

public class GameOverScreen {
	
	private UIButton returnToMenuB;
	private UIButton exitB;
	private UIButtonListener buttonListener;
	private BufferedImage buttonImg = null;
	private Font bitMadness;
	
	public GameOverScreen(){
		bitMadness = new Font("8-Bit Madness", Font.PLAIN, 52);
		
		// We load our image
		try {
			buttonImg = ImageIO.read(getClass().getResource("/res/textures/menu_button.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// We create our buttons and set the performed action
		returnToMenuB = new UIButton(new Vector2i(323, 300), buttonImg, new UIActionListener() {
			public void perform() {
				Game.setGameState(STATE.Menu);
			}
		});
		
		exitB = new UIButton(new Vector2i(323, 400), buttonImg, new UIActionListener() {
			public void perform() {
				System.exit(0);
			}
		});
		
		// We override the buttonlistener
		buttonListener = new UIButtonListener() {
			public void entered(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(buttonImg, 50));
			}

			public void exited(UIButton button) {
				button.setImage(buttonImg);
			}

			public void pressed(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(buttonImg, -20));
			}

			public void released(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(buttonImg, 50));
			}
		};
		
		// We set the button listener to our buttons
		returnToMenuB.setButtonListener(buttonListener);
		exitB.setButtonListener(buttonListener);
		
		// We set the button labels 70,36
		returnToMenuB.setText("Menu");
		returnToMenuB.label.setFont(bitMadness);
		returnToMenuB.label.setColor(0xffffff);
		returnToMenuB.label.position = new Vector2i(403, 336);
		
		exitB.setText("Quit");
		exitB.label.setFont(bitMadness);
		exitB.label.setColor(0xffffff);
		exitB.label.position = new Vector2i(408, 436);
	}
	
	public void update(){
		returnToMenuB.update();
		exitB.update();
	}
	
	public void render(Graphics g){
		//g.setColor(Color.BLACK);
		//g.fillRect(0, 0, 900, 504);
		g.setColor(Color.WHITE);
		g.setFont(bitMadness.deriveFont(85f));
		g.drawString("YOU AND YOUR FRIENDS", 70, 70);
		g.drawString("ARE DEAD.", 285, 130);
		g.drawString("GAME OVER", 270, 250);
		
		returnToMenuB.render(g);
		exitB.render(g);
	}
}
