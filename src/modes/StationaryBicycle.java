package modes;

import data.Item;
import data.Lesson;

public class StationaryBicycle extends GameMode {

    private Integer numberOfAnswersPlay;
    private Integer pauseDurationInSecs;
    private Integer modeDurationInSecs;

    public StationaryBicycle(Lesson less, Integer number, Integer pause, Integer modeDur){
        this.lesson=less;
        this.numberOfAnswersPlay=number;
        this.pauseDurationInSecs=pause;
        this.pauseDurationInSecs=modeDur;
    }

    @Override
    public Item next(Boolean answerToPrevious) {
        return null;
    }

    @Override
    protected void randomize() {

    }
    
	@Override
	public void reinitialize() {
		// znovu pripravi polozky 
	}
}