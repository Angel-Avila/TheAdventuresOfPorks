package juego.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import juego.Game;
import juego.Game.STATE;
import juego.graphics.ui.UIActionListener;
import juego.graphics.ui.UIButton;
import juego.graphics.ui.UIButtonListener;
import juego.graphics.ui.UITextBox;
import juego.util.ImageUtils;
import juego.util.Vector2i;

public class Menu {

	private ArrayList<UIButton> buttons = new ArrayList<>();

	private UIButton createChar;
	private UIButton startGame;
	private UIButton help;
	private UIButton exit;
	private UIButtonListener buttonListener;
	
	private BufferedImage buttonImg = null;
	private Image background = null;
	private Image background_clouds = null;
	
	private Font bitMadness;
	
	private boolean renderHelp = false;
	private boolean creatingChar = false;
	
	private double background_clouds_x = 0;
	
	
	private UITextBox textBox;

	public Menu(Game game) {
		textBox = new UITextBox(new Vector2i(310, 250), new Vector2i(300, 35), game.key);
		
		bitMadness = new Font("8-Bit Madness", Font.PLAIN, 52);
		
		// We get our images
		try {
			background = ImageIO.read(getClass().getResource("/res/textures/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			background_clouds = ImageIO.read(getClass().getResource("/res/textures/background_clouds.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			buttonImg = ImageIO.read(getClass().getResource("/res/textures/menu_button.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// We declare our buttons and override the UIActionListener
		createChar = new UIButton(new Vector2i(50, 200), buttonImg, new UIActionListener() {
			public void perform() {
				creatingChar = true;
			}
		});
		
		startGame = new UIButton(new Vector2i(335, 300), buttonImg, new UIActionListener() {
			public void perform() {
				creatingChar = false;
				renderHelp = false;
				Game.setGameState(STATE.Game);
				game.init(UITextBox.name);
			}
		});

		help = new UIButton(new Vector2i(50, 300), buttonImg, new UIActionListener() {
			public void perform() {
				renderHelp = !renderHelp;
			}
		});

		exit = new UIButton(new Vector2i(50, 400), buttonImg, new UIActionListener() {
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
		createChar.setButtonListener(buttonListener);
		startGame.setButtonListener(buttonListener);
		help.setButtonListener(buttonListener);
		exit.setButtonListener(buttonListener);

		// We set the labels for our buttons
		// startGame label -----------------
		createChar.setText("Start");
		createChar.label.setFont(bitMadness);
		createChar.label.setColor(0xffffff);
		createChar.label.position = new Vector2i(120, 236);
		
		// startGame label -----------------
		startGame.setText("Go!");
		startGame.label.setFont(bitMadness);
		startGame.label.setColor(0xffffff);
		startGame.label.position = new Vector2i(430, 335);

		// help label ----------------------
		help.setText("Help");
		help.label.setFont(bitMadness);
		help.label.setColor(0xffffff);
		help.label.position = new Vector2i(125, 336);

		// exit label ----------------------
		exit.setText("Exit");
		exit.label.setFont(bitMadness);
		exit.label.setColor(0xffffff);
		exit.label.position = new Vector2i(130, 436);

		// We add the buttons to our button arraylist, except for start, we'll handle it separately
		buttons.add(createChar);
		buttons.add(help);
		buttons.add(exit);
	}

	public void update() {
		// Movement of the clouds in the background
		background_clouds_x-=.5;
		// So it loops again when it has already passed through all our screen (width = 900)
		if(background_clouds_x == -900) 
			background_clouds_x = 0;
		
		if(!creatingChar)
			for (UIButton button : buttons)
				button.update();
		else{
			startGame.update();
			textBox.update();
		}
		
	}

	public void render(Graphics g) {
		g.drawImage(background, 0, 0, null);
		// The cloud image gets rendered twice so the black parts one leaves behind are covered by the other one
		g.drawImage(background_clouds, (int)background_clouds_x, 259, null);
		g.drawImage(background_clouds, 900 + (int)background_clouds_x, 259, null);

		if(!creatingChar)
			for (UIButton button : buttons)
				button.render(g);
		else{
			startGame.render(g);
			textBox.render(g);
			g.setColor(Color.WHITE);
			g.drawString("Enter your name!", 284, 235);
		}
		
		if(renderHelp && !creatingChar){
			g.setFont(bitMadness);
			g.setColor(new Color(.28f, .0f, 1f, .15f));
			g.fillRect(350, 250, 450, 120);
			g.setColor(Color.WHITE);
			g.drawRect(350, 250, 450, 120);
			g.drawString("WASD/Arrows: Move", 355, 280);
			g.drawString("Left click: Shoot", 355, 320);
			g.drawString("Spacebar: Sp Ability", 355, 360);
		}
	}

}