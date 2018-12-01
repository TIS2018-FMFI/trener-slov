package modes;

import data.Item;

public class StationaryBicycle extends GameMode {

    private Integer numberOfAnswersPlay;
    private Integer pauseDurationInSecs;
    private Integer modeDurationInSecs;

    @Override
    public Item next(Boolean answerToPrevious) {
        return null;
    }

    @Override
    protected void randomize() {

    }
}