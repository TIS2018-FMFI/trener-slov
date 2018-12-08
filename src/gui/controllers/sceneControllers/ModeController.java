package gui.controllers.sceneControllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.controllers.ControllerBase;
import modes.GameMode;

public class ModeController extends ControllerBase {
	
	GameMode mode;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO  

	}

	@Override
	protected void setFontSizeToTexts() {

	}

	public void setMode(GameMode mode) {
		this.mode = mode;
	}
}
