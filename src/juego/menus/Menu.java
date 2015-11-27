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

	private ArrayList<UIButton> menuButtons = new ArrayList<>();
	private ArrayList<UIButton> helpButtons = new ArrayList<>();

	private UIButton menuStartGame;
	private UIButton creatingCharStartGame;
	private UIButton help;
	private UIButton exit;
	private UIButton help_icons_b;
	private UIButton help_game_b;
	private UIButton help_enemies_b;
	private UIButton help_back_b;
	private UIButtonListener menuButtonListener;
	private UIButtonListener helpButtonListener;

	private BufferedImage buttonImg = null;
	private BufferedImage buttonImg_small = null;
	private BufferedImage icons_help = null;
	private BufferedImage enemies_help = null;
	private Image background = null;
	private Image background_clouds = null;

	private Font bitMadness;

	private boolean renderHelp = false;
	private boolean renderHelp_icons = false;
	private boolean renderHelp_game = false;
	private boolean renderHelp_enemies = false;
	private boolean creatingChar = false;

	private double background_clouds_x = 0;

	private UITextBox textBox;

	public Menu(Game game) {
		textBox = new UITextBox(new Vector2i(310, 250), new Vector2i(300, 35), game.key);

		bitMadness = new Font("8-Bit Madness", Font.PLAIN, 52);

		// Images
		// ==========================================================================
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

		try {
			buttonImg_small = ImageIO.read(getClass().getResource("/res/textures/menu_button_small.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			icons_help = ImageIO.read(getClass().getResource("/res/textures/icons_help.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			enemies_help = ImageIO.read(getClass().getResource("/res/textures/enemies_help.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// BUTTONS============================================================================
		// We declare our buttons and override the UIActionListener
		// Menu-buttons-----------------------------------------------------------------------
		menuStartGame = new UIButton(new Vector2i(50, 200), buttonImg, new UIActionListener() {
			public void perform() {
				creatingChar = true;
			}
		});

		help = new UIButton(new Vector2i(50, 300), buttonImg, new UIActionListener() {
			public void perform() {
				renderHelp = !renderHelp;
				renderHelp_icons = true;
			}
		});

		exit = new UIButton(new Vector2i(50, 400), buttonImg, new UIActionListener() {
			public void perform() {
				System.exit(0);
			}
		});

		// Help-buttons------------------------------------------------------------------------

		help_back_b = new UIButton(new Vector2i(50, 415), buttonImg_small, new UIActionListener() {
			public void perform() {
				renderHelp = !renderHelp;
				renderHelp_icons = false;
				renderHelp_game = false;
				renderHelp_enemies = false;
			}
		});

		help_icons_b = new UIButton(new Vector2i(250, 415), buttonImg_small, new UIActionListener() {
			public void perform() {
				renderHelp_icons = true;
				renderHelp_game = false;
				renderHelp_enemies = false;
			}
		});

		help_game_b = new UIButton(new Vector2i(450, 415), buttonImg_small, new UIActionListener() {
			public void perform() {
				renderHelp_game = true;
				renderHelp_icons = false;
				renderHelp_enemies = false;
			}
		});

		help_enemies_b = new UIButton(new Vector2i(650, 415), buttonImg_small, new UIActionListener() {
			public void perform() {
				renderHelp_icons = false;
				renderHelp_game = false;
				renderHelp_enemies = true;
			}
		});

		// CreatingChar-buttons----------------------------------------------------------------

		creatingCharStartGame = new UIButton(new Vector2i(335, 300), buttonImg, new UIActionListener() {
			public void perform() {
				creatingChar = false;
				renderHelp = false;
				Game.setGameState(STATE.Game);
				game.init(UITextBox.name);
			}
		});

		// We override the buttonListeners
		menuButtonListener = new UIButtonListener() {
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

		helpButtonListener = new UIButtonListener() {
			public void entered(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(buttonImg_small, 50));
			}

			public void exited(UIButton button) {
				button.setImage(buttonImg_small);
			}

			public void pressed(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(buttonImg_small, -20));
			}

			public void released(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(buttonImg_small, 50));
			}
		};

		// We set the overrided buttonListener as the new listener
		// Menu buttons-------------------------------------------
		menuStartGame.setButtonListener(menuButtonListener);
		help.setButtonListener(menuButtonListener);
		exit.setButtonListener(menuButtonListener);
		// Help buttons-------------------------------------------
		help_back_b.setButtonListener(helpButtonListener);
		help_icons_b.setButtonListener(helpButtonListener);
		help_game_b.setButtonListener(helpButtonListener);
		help_enemies_b.setButtonListener(helpButtonListener);
		// CreatingChar buttons-----------------------------------
		creatingCharStartGame.setButtonListener(menuButtonListener);

		// We set the labels for our buttons
		// Menu labels--------------------------------------------
		// startGame label -----------------
		menuStartGame.setText("Start");
		menuStartGame.label.setFont(bitMadness);
		menuStartGame.label.setColor(0xffffff);
		menuStartGame.label.position = new Vector2i(120, 236);

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

		// Help labels--------------------------------------------
		// back label --------------------
		help_back_b.setText("Back");
		help_back_b.label.setFont(bitMadness);
		help_back_b.label.setColor(0xffffff);
		help_back_b.label.position = new Vector2i(75, 451);

		// icons label --------------------
		help_icons_b.setText("Stats");
		help_icons_b.label.setFont(bitMadness);
		help_icons_b.label.setColor(0xffffff);
		help_icons_b.label.position = new Vector2i(270, 451);

		// game label --------------------
		help_game_b.setText("Game");
		help_game_b.label.setFont(bitMadness);
		help_game_b.label.setColor(0xffffff);
		help_game_b.label.position = new Vector2i(475, 451);

		// enemies label --------------------
		help_enemies_b.setText("Mobs");
		help_enemies_b.label.setFont(bitMadness);
		help_enemies_b.label.setColor(0xffffff);
		help_enemies_b.label.position = new Vector2i(675, 451);

		// CreatingChar labels--------------------------------------------
		// creatinCharStart label -----------------
		creatingCharStartGame.setText("Go!");
		creatingCharStartGame.label.setFont(bitMadness);
		creatingCharStartGame.label.setColor(0xffffff);
		creatingCharStartGame.label.position = new Vector2i(430, 335);

		// We add the buttons to our button arraylists, except for start, we'll
		// handle it separately
		// menuButtons -------------------------------------
		menuButtons.add(menuStartGame);
		menuButtons.add(help);
		menuButtons.add(exit);
		// helpButtons -------------------------------------
		helpButtons.add(help_back_b);
		helpButtons.add(help_enemies_b);
		helpButtons.add(help_game_b);
		helpButtons.add(help_icons_b);
	}

	public void update() {
		// Movement of the clouds in the background
		background_clouds_x -= .5;
		// So it loops again when it has already passed through all our screen
		// (width = 900)
		if (background_clouds_x == -900)
			background_clouds_x = 0;

		if (!creatingChar) {
			if (!renderHelp)
				for (UIButton button : menuButtons)
					button.update();
			else
				for (UIButton button : helpButtons)
					button.update();
		} else {
			creatingCharStartGame.update();
			textBox.update();
		}

	}

	public void render(Graphics g) {
		g.drawImage(background, 0, 0, null);
		// The cloud image gets rendered twice so the black parts one leaves
		// behind are covered by the other one
		g.drawImage(background_clouds, (int) background_clouds_x, 259, null);
		g.drawImage(background_clouds, 900 + (int) background_clouds_x, 259, null);
		g.setFont(bitMadness);

		if (!creatingChar) {
			if (!renderHelp)
				for (UIButton button : menuButtons)
					button.render(g);
			else {
				/*
				 * g.setColor(new Color(.28f, .0f, 1f, .15f)); g.fillRect(350,
				 * 250, 450, 160); g.setColor(Color.WHITE); g.drawRect(350, 250,
				 * 450, 160); g.drawString( "WASD/Arrows: Move", 355, 280);
				 * g.drawString("Left click: Shoot", 355, 320); g.drawString(
				 * "Right click: Shoot++" , 355, 360); g.drawString(
				 * "Spacebar: Sp Ability", 355, 400);
				 */
				for (UIButton button : helpButtons)
					button.render(g);

				g.setColor(new Color(.28f, .0f, 1f, .35f));
				g.fillRect(66, 210, 770, 183);
				g.setColor(Color.WHITE);

				if (renderHelp_icons) {
					g.drawImage(icons_help, 66, 210, null);
					g.drawString("Actual health", 126, 255);
					g.drawString("Actual mana", 126, 310);
					g.drawString("Attack speed", 126, 365);
					g.drawString("Damage", 476, 255);
					g.drawString("Cooldown", 476, 310);
					g.drawString("Movement speed", 476, 365);
				} else if (renderHelp_game) {
					g.drawString("WASD/Arrow keys: Move", 76, 250);
					g.drawString("Left click: Fireball", 76, 290);
					g.drawString("Right click: Fireball burst", 76, 330);
					g.drawString("Spacebar: Firestorm /w cooldown", 76, 370);
				} else if (renderHelp_enemies) {
					g.drawImage(enemies_help, 96, 210, null);
					g.setFont(new Font("8-Bit Madness", Font.PLAIN, 26));
					// Dummy
					g.drawString("Moves randomly", 76, 331);
					g.drawString("Almost no damage", 76, 351);
					g.drawString("Low health", 76, 371);
					g.drawString("Normal speed", 76, 391);
					// Chaser
					g.drawString("Follows you", 276, 331);
					g.drawString("Massive damage", 276, 351);
					g.drawString("High health", 276, 371);
					g.drawString("Low speed", 276, 391);
					// Star
					g.drawString("Finds you", 476, 331);
					g.drawString("Normal damage", 476, 351);
					g.drawString("Massive health", 476, 371);
					g.drawString("High speed", 476, 391);
					// Pokemon Trainer
					g.drawString("Follows/Shoots", 661, 331);
					g.drawString("High damage", 661, 351);
					g.drawString("Normal health", 661, 371);
					g.drawString("Normal speed", 661, 391);
				}
				g.drawRect(66, 210, 770, 183);
			}
		}

		else {
			creatingCharStartGame.render(g);
			textBox.render(g);
			g.setColor(Color.WHITE);
			g.drawString("Enter your name!", 284, 235);
		}
	}
}