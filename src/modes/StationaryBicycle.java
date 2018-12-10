package modes;

import data.Item;
import data.Lesson;

public class StationaryBicycle extends GameMode {

    private Integer numberOfAnswersPlay;
    private Integer pauseDurationInSecs;
    private Integer modeDurationInSecs;
    
    public StationaryBicycle(Lesson less, Integer numOfPlay, Integer pause, Integer modeDur){
        this.lesson=less;
        this.numberOfAnswersPlay=numOfPlay;
        this.pauseDurationInSecs=pause;
        this.modeDurationInSecs=modeDur;
    }
    @Override
    public Item next(Boolean answerToPrevious) {
        return null;
    }

    @Override
    protected void randomize() {

    }
}