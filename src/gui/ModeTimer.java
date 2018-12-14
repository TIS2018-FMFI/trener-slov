package gui;

import java.util.Timer;
import java.util.TimerTask;

import gui.controllers.sceneControllers.ModeController;

public class ModeTimer {
	
	Timer timer;
	Integer seconds;
	Integer limit;
	ModeController controller;
	
	public ModeTimer(ModeController controller) {
		this.controller = controller;
		timer = new Timer();
		seconds = 0;
	}
	
	public ModeTimer(ModeController controller, Integer limit) {
		this(controller);
		this.limit = limit;
	}
	
	public void start() {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				seconds++;
			}
		}, 0, 1000);
	}
	
	public int time() {
		return seconds;
	}
}
