package modes;

import data.*;

public abstract class GameMode {

    Lesson lesson;

    public abstract Item next(Boolean answerToPrevious);
    protected abstract void randomize();

}
