package juego.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import juego.Game;
import juego.graphics.ui.UIActionListener;
import juego.graphics.ui.UIButton;
import juego.graphics.ui.UIButtonListener;
import juego.util.ImageUtils;
import juego.util.Vector2i;

public class PauseMenu {

	private ArrayList<UIButton> buttons = new ArrayList<>();

	private UIButton startGame;
	private UIButton exit;
	private UIButtonListener buttonListener;
	
	private BufferedImage buttonImg = null;
	
	private Font bitMadness;

	public PauseMenu() {
		bitMadness = new Font("8-Bit Madness", Font.PLAIN, 52);

		// We get our images

		try {
			buttonImg = ImageIO.read(getClass().getResource("/res/textures/menu_button.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// We declare our buttons and override the UIActionListener
		startGame = new UIButton(new Vector2i(263, 150), buttonImg, new UIActionListener() {
			public void perform() {
				Game.paused = false;
			}
		});

		exit = new UIButton(new Vector2i(263, 300), buttonImg, new UIActionListener() {
			public void perform() {
				System.exit(0);
			}
		});

		// We override the buttonListener
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

		// We set the overrided buttonListener as the new listener
		startGame.setButtonListener(buttonListener);
		exit.setButtonListener(buttonListener);

		// We set the labels for our buttons
		// startGame label -----------------
		startGame.setText("Continue");
		startGame.label.setFont(bitMadness);
		startGame.label.setColor(0xffffff);
		startGame.label.position = new Vector2i(305, 186);

		// exit label ----------------------
		exit.setText("Quit");
		exit.label.setFont(bitMadness);
		exit.label.setColor(0xffffff);
		exit.label.position = new Vector2i(348, 336);

		// We add the buttons to our button arraylist
		buttons.add(startGame);
		buttons.add(exit);
	}
	
	public void update(){
		for(UIButton button : buttons)
			button.update();
	}
	
	public void render(Graphics g){
		g.setColor(new Color(.0f, .0f, .0f, .35f));
		g.fillRect(0, 0, 900, 504);
		g.setColor(Color.WHITE);
		
		for(UIButton button : buttons)
			button.render(g);
	}
}
