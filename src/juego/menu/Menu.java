package juego.menu;

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
import juego.util.ImageUtils;
import juego.util.Vector2i;

public class Menu {

	private ArrayList<UIButton> buttons = new ArrayList<>();

	private UIButton startGame;
	private UIButton help;
	private UIButton exit;
	private UIButtonListener buttonListener;
	private BufferedImage startGameButtonImg;
	private BufferedImage helpButtonImg;
	private BufferedImage exitButtonImg;
	private Image background;

	public Menu() {
		background = null;
		startGameButtonImg = null;
		helpButtonImg = null;
		exitButtonImg = null;

		// We get our images
		try {
			background = ImageIO.read(getClass().getResource("/res/textures/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			startGameButtonImg = ImageIO.read(getClass().getResource("/res/textures/pig_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			helpButtonImg = ImageIO.read(getClass().getResource("/res/textures/pig_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			exitButtonImg = ImageIO.read(getClass().getResource("/res/textures/pig_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// We declare our buttons and override the UIActionListener
		startGame = new UIButton(new Vector2i(100, 100), startGameButtonImg, new UIActionListener() {
			public void perform() {
				Game.setGameState(STATE.Game);
			}
		});

		help = new UIButton(new Vector2i(100, 200), helpButtonImg, new UIActionListener() {
			public void perform() {

			}
		});

		exit = new UIButton(new Vector2i(100, 300), exitButtonImg, new UIActionListener() {
			public void perform() {
				System.exit(0);
			}
		});

		// We override the buttonListener
		buttonListener = new UIButtonListener() {
			public void entered(UIButton button) {
				if (button == startGame)
					button.setImage(ImageUtils.changeBrightness(startGameButtonImg, 80));
				else if(button == help)
					button.setImage(ImageUtils.changeBrightness(helpButtonImg, 80));
				else if(button == exit)
					button.setImage(ImageUtils.changeBrightness(exitButtonImg, 80));
			}

			public void exited(UIButton button) {
				if (button == startGame)
					button.setImage(startGameButtonImg);
				else if(button == help)
					button.setImage(helpButtonImg);
				else if(button == exit)
					button.setImage(exitButtonImg);
			}

			public void pressed(UIButton button) {
				if (button == startGame)
					button.setImage(ImageUtils.changeBrightness(startGameButtonImg, -20));
				else if(button == help)
					button.setImage(ImageUtils.changeBrightness(helpButtonImg, -20));
				else if(button == exit)
					button.setImage(ImageUtils.changeBrightness(exitButtonImg, -20));
			}

			public void released(UIButton button) {
				if (button == startGame)
					button.setImage(ImageUtils.changeBrightness(startGameButtonImg, 80));
				else if(button == help)
					button.setImage(ImageUtils.changeBrightness(helpButtonImg, 80));
				else if(button == exit)
					button.setImage(ImageUtils.changeBrightness(exitButtonImg, 80));
			}
		};
		
		// We set the overrided buttonListener as the new listener
		startGame.setButtonListener(buttonListener);
		help.setButtonListener(buttonListener);
		exit.setButtonListener(buttonListener);

		// We add the buttons to our button arraylist
		buttons.add(startGame);
		buttons.add(help);
		buttons.add(exit);
	}

	public void update() {
		for (UIButton button : buttons)
			button.update();
	}

	public void render(Graphics g) {
		g.drawImage(background, 0, 0, null);

		for (UIButton button : buttons)
			button.render(g);
	}

}