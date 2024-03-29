package juego.entity.mob;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import juego.Game;
import juego.entity.projectile.WizardProjectile;
import juego.graphics.Screen;
import juego.graphics.Sprite;
import juego.graphics.ui.UIActionListener;
import juego.graphics.ui.UIButton;
import juego.graphics.ui.UIButtonListener;
import juego.graphics.ui.UILabel;
import juego.graphics.ui.UIManager;
import juego.graphics.ui.UIPanel;
import juego.graphics.ui.UIProgressBar;
import juego.graphics.ui.UIStat;
import juego.input.Keyboard;
import juego.input.Mouse;
import juego.sound.Sound;
import juego.util.ImageUtils;
import juego.util.Vector2i;

public class Player extends Mob {

	private String name;
	private Keyboard input;
	private int anim = 0;

	private BufferedImage pig_icon = null;
	private BufferedImage health_icon = null;
	private BufferedImage damage_icon = null;
	private BufferedImage mana_icon = null;
	private BufferedImage cooldown_icon = null;
	private BufferedImage attackSpeed_icon = null;
	private BufferedImage speed_icon = null;

	private UIStat health_stat;
	private UIStat damage_stat;
	private UIStat mana_stat;
	private UIStat cooldown_stat;
	private UIStat attackSpeed_stat;
	private UIStat speed_stat;

	private double actualMana, maxMana, manaRegen;
	private double healthRegen;
	private final double COOLDOWN = 60;
	private double cooldown = 60;
	private int fireRate = 0;
	private double speed;
	public int score = 0;
	public int killCount = 0;
	
	private int sinceHit = 90;

	private Vector2i usedAt;

	public Vector2i position;

	private UIManager ui;
	private UIProgressBar uiHealthBar, uiManaBar;
	private UILabel scoreLabel;
	private UILabel killCountLabel;
//	private UIButton button;
	
	public static boolean displayStats = true;

	@Deprecated
	public Player(Keyboard input) {
		this.input = input;
		sprite = Sprite.player_forward;
	}

	public Player(String name, int x, int y, Keyboard input) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.input = input;
		position = new Vector2i(x, y);
		usedAt = new Vector2i();
		dir = Direction.DOWN;
		sprite = Sprite.player_forward;
		fireRate = WizardProjectile.FIRE_RATE;

		/* ------------- STATS FOR NOW SINCE HE'S A WIZARD ------------- */
		actualHealth = maxHealth = 60;
		actualMana = maxMana = 120;
		manaRegen = 0.12;
		healthRegen = .012;
		damage = 15;
		speed = 1.25;

		// ================================ HERE WE START ALL THE UI STUFF
		// ================================
		ui = Game.getUIManager();

		/*
		 * UI
		 * PANEL----------------------------------------------------------------
		 * ---------------------
		 */
		// This creates the gray panel on the right side of the screen
		UIPanel panel = (UIPanel) new UIPanel(new Vector2i(240 * 3, 0), new Vector2i(60 * 3, 168 * 3))
				.setColor(0x4f4f4f);
		// And this adds it to our UI
		ui.addPanel(panel);
		// Then we add a label with our name to our panel
		panel.addComponent(new UILabel(new Vector2i(28, 200), name).setFont(new Font("Helvetica", Font.BOLD, 20))
				.setColor(0xbbbbbb));

		/*
		 * HEALTH AND MANA PROGRESS BARS
		 * ---------------------------------------------------------------
		 */
		// We create our healthbar, set its colors and add it to our panel
		uiHealthBar = new UIProgressBar(new Vector2i(10, 210), new Vector2i(160, 15));
		uiHealthBar.setColor(0x6a6a6a); // Dark gray
		uiHealthBar.setForegroundColor(0xee3030); // Red
		panel.addComponent(uiHealthBar);
		// We just put a label to it so the player knows it is his HP
		UILabel HPLabel = new UILabel(new Vector2i(uiHealthBar.position).add(new Vector2i(2, 13)), "HP");
		HPLabel.setColor(0xffffff);
		HPLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.addComponent(HPLabel);

		// The same thing but for mana
		uiManaBar = new UIProgressBar(new Vector2i(10, 230), new Vector2i(160, 15));
		uiManaBar.setColor(0x6a6a6a); // Dark gray
		uiManaBar.setForegroundColor(0xff0026FF); // Blue
		panel.addComponent(uiManaBar);
		// We just put a label to it so the player knows it is his Mana
		UILabel ManaLabel = new UILabel(new Vector2i(uiManaBar.position).add(new Vector2i(2, 13)), "MP");
		ManaLabel.setColor(0xffffff);
		ManaLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.addComponent(ManaLabel);

		panel.addComponent(new UILabel(new Vector2i(10,270), "Stats").setColor(0xffffff));
		/*
		 * THE
		 * BUTTON---------------------------------------------------------------
		 * ----------------------
		 
		// We initialize our button
		button = new UIButton(new Vector2i(10, 260), new Vector2i(45, 15), new UIActionListener() {
			// and we override the perform() action to specify what the button
			// does
			public void perform() {
				displayStats = !displayStats;
			}
		});

		// We can override the normal UIButtonListener actions here with the
		// setButtonL with an anonymous class
		button.setButtonListener(new UIButtonListener() {
			public void pressed(UIButton button) {
				super.pressed(button);
				button.performAction();
				button.ignoreNextPress();
			}
		});

		button.setText("Stats");
		panel.addComponent(button);

		/*
		 * THE BUTTON WITH AN
		 * IMAGE----------------------------------------------------------------
		 * ------------
		 */

		try {
			pig_icon = ImageIO.read(getClass().getResource("/res/textures/porki_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		UIButton imageButton = new UIButton(new Vector2i(6, 182), pig_icon, new UIActionListener() {
			// and we override the perform() action to specify what
			// the button does
			public void perform() {
				System.out.println("Pig Pressed!");
			}
		});

		// We override the buttonListener normal actions to make the image
		// brighter if hovered or make it darker if
		// pressed.
		imageButton.setButtonListener(new UIButtonListener() {
			public void entered(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(pig_icon, 120));
			}

			public void exited(UIButton button) {
				button.setImage(pig_icon);
			}

			public void pressed(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(pig_icon, -20));
			}

			public void released(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(pig_icon, 120));
			}
		});

		panel.addComponent(imageButton);

		// Stats ============================================================

		UIPanel statPanel = (UIPanel) new UIPanel(new Vector2i(240 * 3 + 5, 280), 
				new Vector2i(170, 67)).setColor(0x4C687C);
		
		ui.addPanel(statPanel);
		
		// Health-stat-------------------------------------------------------

		try {
			health_icon = ImageIO.read(getClass().getResource("/res/textures/health_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		health_stat = new UIStat(new Vector2i(5, 5), health_icon);
		health_stat.setText(String.valueOf(actualHealth));
		statPanel.addComponent(health_stat);

		// Dmg-stat-------------------------------------------------------

		try {
			damage_icon = ImageIO.read(getClass().getResource("/res/textures/damage_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		damage_stat = new UIStat(new Vector2i(90, 5), damage_icon);
		damage_stat.setText(String.valueOf(damage));
		statPanel.addComponent(damage_stat);

		// Mana-stat-------------------------------------------------------

		try {
			mana_icon = ImageIO.read(getClass().getResource("/res/textures/mana_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		mana_stat = new UIStat(new Vector2i(5, 25), mana_icon);
		mana_stat.setText(String.valueOf(actualMana));
		statPanel.addComponent(mana_stat);

		// Cooldown-stat-------------------------------------------------------

		try {
			cooldown_icon = ImageIO.read(getClass().getResource("/res/textures/cooldown_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		cooldown_stat = new UIStat(new Vector2i(90, 25), cooldown_icon);
		cooldown_stat.setText(String.valueOf(1.0 - (cooldown / COOLDOWN)) + "s");
		statPanel.addComponent(cooldown_stat);

		// AttackSpeed-stat-------------------------------------------------------

		try {
			attackSpeed_icon = ImageIO.read(getClass().getResource("/res/textures/attackSpeed_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		attackSpeed_stat = new UIStat(new Vector2i(5, 45), attackSpeed_icon);
		attackSpeed_stat.setText(String.valueOf(60.0 / WizardProjectile.FIRE_RATE));
		statPanel.addComponent(attackSpeed_stat);

		// Speed-stat-------------------------------------------------------

		try {
			speed_icon = ImageIO.read(getClass().getResource("/res/textures/speed_icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		speed_stat = new UIStat(new Vector2i(90, 45), speed_icon);
		speed_stat.setText(String.valueOf(speed));
		statPanel.addComponent(speed_stat);
		
		// PlayerScore========================================================================
		UIPanel scorePanel = (UIPanel) new UIPanel(new Vector2i(240 * 3 + 5, 375), 
				new Vector2i(170, 30)).setColor(0xff262626);
		ui.addPanel(scorePanel);
	
		panel.addComponent((UILabel) new UILabel(new Vector2i(10, 370), "Score: ").setColor(0xffffff));
		
		scoreLabel = ((UILabel) new UILabel(new Vector2i(6, 22), String.valueOf(score)).setColor(0xffffff))
				.setFont(new Font("8-bit Madness", Font.PLAIN, 32));
		scorePanel.addComponent(scoreLabel);
		
		//PlayerKillCount=====================================================================
		UIPanel killCountPanel = (UIPanel) new UIPanel(new Vector2i(240 * 3 + 5, 435), 
				new Vector2i(170, 30)).setColor(0xff262626);
		ui.addPanel(killCountPanel);
		
		panel.addComponent((UILabel) new UILabel(new Vector2i(10, 428), "Kill Count: ").setColor(0xffffff));
		
		killCountLabel = ((UILabel) new UILabel(new Vector2i(6, 22), String.valueOf(score)).setColor(0xffffff))
				.setFont(new Font("8-bit Madness", Font.PLAIN, 32));
		killCountPanel.addComponent(killCountLabel);
		
	}

	// =======================================================================================================
	// =======================================================================================================
	// =======================================================================================================

	public void update() {/*
							 * int xi = (int)((x + 7) / 16); int yi = (int)((y +
							 * 6) / 16); System.out.println(xi + ", " + yi +
							 * ", " + level.getTile(xi, yi).walkable());
							 */
		checkHit();

		if (hit)
			sinceHit = 0;
		if (!hit && sinceHit < 90)
			sinceHit++;

		// After 1.5 seconds from being hit, the player starts to regen health
		if (sinceHit == 90 && actualHealth < maxHealth) {
			if (maxHealth - actualHealth < healthRegen)
				actualHealth = maxHealth;
			else
				actualHealth += healthRegen;
		}

		if (anim < 7500)
			anim++;
		else
			anim = 0;

		// Cooldown for the space bar special ability
		if (cooldown < COOLDOWN)
			cooldown++;
		
		// Mana regen
		if (actualMana < maxMana){
			if (maxMana - actualMana < manaRegen)
				actualMana = maxMana;
			else
				actualMana += manaRegen;
		}
		double xa = 0, ya = 0;

		// Checking user input
		if (input.up)
			ya -= speed;
		if (input.down)
			ya += speed;
		if (input.left)
			xa -= speed;
		if (input.right)
			xa += speed;
		if (input.spA) {
			if (this.actualMana >= 30 && cooldown >= COOLDOWN) {
				specialAbility();
				cooldown = 0;
			}
		}

		// Moving
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else
			walking = false;

		position.set(getTileX(), getTileY());
		uiHealthBar.setProgress((actualHealth * 1.0) / maxHealth);
		uiManaBar.setProgress((actualMana * 1.0) / maxMana);

		// Takes 1 from firerate because the player only shoots in the
		// updateShooting() method if fireRate is <= 0
		// and after shooting it resets the fireRate int value to the declared
		// FIRE_RATE value in WizardProjectile
		if (fireRate > 0)
			fireRate--;

		updateShooting();

		if (actualHealth <= 0) {
			Game.setGameState(Game.STATE.End);
			Sound.gameOver.loop();
		}
		
		// Setters for the stats from the UIPanel
		health_stat.setText(String.format("%.2f", actualHealth));
		damage_stat.setText(String.format("%.2f", damage));
		mana_stat.setText(String.format("%.2f", actualMana));
		cooldown_stat.setText(String.format("%.2f", 1.0 - (cooldown / COOLDOWN)) + "s");
		attackSpeed_stat.setText(String.format("%.2f", 60.0 / WizardProjectile.FIRE_RATE));
		speed_stat.setText(String.format("%.2f", speed));
		scoreLabel.setText(String.format("%010d", score));
		killCountLabel.setText(String.format("%010d", killCount));
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	// Fully heals player and regens all of his mana
	public void heal(){
		actualHealth = maxHealth;
		actualMana = maxMana;
	}

	private void updateShooting() {
		// If the player is in the UIPanel, don't shoot
		if (Mouse.getX() >= 720)
			return;

		// If the player is clicking the left click and the fireRate is <= 0
		if (Mouse.getB() == 1 && fireRate <= 0) {
			// Gets the x and y coordinates from where the user wants to shoot
			// relative to the window, not the map
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			// Uses the atan function of y / x to get the angle. The - 10 is an
			// offset I did to center the projectile
			double dir = Math.atan2(dy, dx - 10);
			// Shoots to the desired direction
			shoot(x, y, dir, damage);
			// Resets the fireRate so it can shoot again
			fireRate = WizardProjectile.FIRE_RATE;
		}

		if (Mouse.getB() == 3 && actualMana > 4) {
			// Gets the x and y coordinates from where the user wants to shoot
			// relative to the window, not the map
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			// Uses the atan function of y / x to get the angle. The - 10 is an
			// offset I did to center the projectile
			double dir = Math.atan2(dy, dx - 10);
			// Shoots to the desired direction
			shoot(x, y, dir, damage);

			actualMana -= 5;
			actualMana = actualMana < 0 ? 0 : actualMana;
		}
	}

	// Damages all enemies within 3 tiles radius from the player
	private void specialAbility() {
		int damage = 40;
		this.actualMana -= 30;
		usedAt.set((int) x, (int) y);
		List<Integer> entities = level.getEntitiesIndex(this, 48);
		for (Integer i : entities)
			level.damageMobAt(i, damage);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Player getClientPlayer() {
		return this;
	}

	private double getDistance(Vector2i v1, Vector2i v2) {
		double dx = v1.getX() - v2.getX();
		double dy = v1.getY() - v2.getY();
		return Math.sqrt((dx * dx) + (dy * dy));
	}

	public Color getColor() {
		return new Color(0xFFD800);
	}

	// The anim % 20 > 10 is just an animation to alternate between the 2
	// walking sprites, one with the right leg
	// and one with the left leg
	public void render(Screen screen) {
		if (dir == Direction.UP) {
			sprite = Sprite.player_backward;
			if (walking) {
				if (anim % 20 > 10)
					sprite = Sprite.player_backward_1;
				else
					sprite = Sprite.player_backward_2;
			}
		}
		if (dir == Direction.RIGHT) {
			sprite = Sprite.player_right;
			if (walking) {
				if (anim % 20 > 10)
					sprite = Sprite.player_right_1;
				else
					sprite = Sprite.player_right_2;
			}
		}
		if (dir == Direction.DOWN) {
			sprite = Sprite.player_forward;
			if (walking) {
				if (anim % 20 > 10)
					sprite = Sprite.player_forward_1;
				else
					sprite = Sprite.player_forward_2;
			}
		}
		if (dir == Direction.LEFT) {
			sprite = Sprite.player_left;
			if (walking) {
				if (anim % 20 > 10)
					sprite = Sprite.player_left_1;
				else
					sprite = Sprite.player_left_2;
			}
		}

		// If the cooldown is <= 30, it means the ability was used at maximum half a second ago, so...
		if (cooldown <= 30) {
			// we render the flames in the radius that affects the mobs
			for (int xtemp = (int) usedAt.x - 48; xtemp < (int) usedAt.x + 49; xtemp += 12) {
				for (int ytemp = (int) usedAt.y - 48; ytemp < (int) usedAt.y + 49; ytemp += 12) {
					if (getDistance(new Vector2i(xtemp, ytemp), usedAt) < 41)
						screen.renderSprite(xtemp - 2, (int) ytemp - 23, Sprite.fire_specialAbility, true);
				}
			}
		}

		// Renders the player with a slight offset
		screen.renderMob((int) (x), (int) (y - 19), this);
	}

	public void renderMiniMap(Screen screen) {
		screen.renderPlayerMinimap(this);
	}
}
