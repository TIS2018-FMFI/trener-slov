package gui;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import javafx.application.Platform;

public class WaitAndCallGuiMethod {
	
	private Timer timer;
	
	public WaitAndCallGuiMethod(Integer waitDurationSeconds, Callable<Void> function) {
		waitAndCallGuiMethod(waitDurationSeconds * 1000, function);
	}
	
	public WaitAndCallGuiMethod(Double waitDurationSeconds, Callable<Void> function) {
		waitAndCallGuiMethod((long) (waitDurationSeconds * 1000), function);
	}
	
	private void waitAndCallGuiMethod(long waitDurationMiliseconds, Callable<Void> function) {
		timer = new Timer();
		timer.schedule( 
		        new TimerTask() {
		            @Override
		            public void run() {
		    			Platform.runLater(() -> {
							try {
								function.call();
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
		            }
		        }, 
		        waitDurationMiliseconds
		);
	}

	public void stop() {
		timer.cancel();
	}
}
