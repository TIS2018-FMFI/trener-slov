package gui;

import java.util.Timer;
import java.util.TimerTask;

import gui.controllers.sceneControllers.ModeController;

public class ModeTimer {
	
	Timer timer;
	Integer seconds;
	Integer questionDuration;
	
	Integer limit;
	Integer questionDurationLimit;
	
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
				update();
			}
		}, 0, 1000);
	}
	
	public int time() {
		return seconds;
	}
	
	public void afterNSecondsShowAnswer(Integer seconds) {
		this.questionDuration = 0;
		this.questionDurationLimit = seconds;
	}
	
	public void quitModeAfterLimit(Integer limit) {
		this.limit = limit;
	}
	
	private void update() {
		seconds++;
		if (seconds >= limit) {
			controller.quit();
		}
		if (questionDuration != null && questionDurationLimit != null && questionDuration >= questionDurationLimit) {
			controller.showAnswer();
			questionDuration = null;
			questionDurationLimit = null;
		}
	}
}
