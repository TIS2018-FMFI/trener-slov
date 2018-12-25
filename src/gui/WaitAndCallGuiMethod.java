package gui;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import javafx.application.Platform;

public class WaitAndCallGuiMethod {
	
	public WaitAndCallGuiMethod(Integer waitDurationSeconds, Callable<Void> function) {
		new Timer().schedule( 
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
		        waitDurationSeconds * 1000
		);
	}
}
