package gui;

import java.util.Timer;
import java.util.TimerTask;

public class ModeTimer {
	
	Timer timer;
	int seconds;
	
	public ModeTimer() {
		timer = new Timer();
		seconds = 0;
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
