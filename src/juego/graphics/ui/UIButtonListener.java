package juego.graphics.ui;

public class UIButtonListener {

	public void entered(UIButton button) {
		button.setColor(0xcdcdcd);
	}

	public void exited(UIButton button) {
		button.setColor(0xaaaaaa);
		if (button.label != null)
			button.label.setColor(0x444444);
	}

	public void pressed(UIButton button) {
		button.setColor(0x404040);
		if (button.label != null)
			button.label.setColor(0xaaaaaa);
	}

	public void released(UIButton button) {
		button.setColor(0xcdcdcd);
		if (button.label != null)
			button.label.setColor(0x444444);
	}
}
