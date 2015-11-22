package juego.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import juego.Game;
import juego.graphics.ui.UITextBox;

public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[300];
	public boolean up, down, left, right, pause, spA;

	public void update() {
		// True if the desired key is pressed
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		pause = keys[KeyEvent.VK_P];
		spA = keys[KeyEvent.VK_SPACE];

	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;

		if (e.getKeyCode() == KeyEvent.VK_P) {
			if (Game.STATE.Game == Game.getGameState()) {
				if (Game.paused)
					Game.paused = false;
				else
					Game.paused = true;
			}
		}

		if (UITextBox.active) {
			getInput(e);
		}
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {

	}

	public void getInput(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
			if (UITextBox.name.length() > 0) {
				UITextBox.name = UITextBox.name.substring(0, UITextBox.name.length() - 1);
				return;
			} else
				return;
		}
		if (e.getKeyCode() != KeyEvent.VK_SHIFT && UITextBox.name.length() < 12) {
			UITextBox.name += String.valueOf(e.getKeyChar());
		}
	}

}
