package modes;

import data.Group;
import data.Item;
import data.Lesson;
import java.util.ArrayList;

public class Learning extends GameMode {
    Integer numOfRepeat;

    public Learning(Lesson less, Integer num){
        numOfRepeat=num;
        this.lesson=less;
    }

    @Override
    public Item next(Boolean answerToPrevious) {


        return null;
    }

    @Override
    protected void randomize() {

    }
    protected Boolean fillRow(){
        return null;
    }
    
	@Override
	public void reinitialize() {
		// znovu pripravi polozky 
	}

}
