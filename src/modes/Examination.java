package modes;

import data.Group;
import data.Item;
import data.Lesson;

import java.util.ArrayList;
import java.util.Collections;

public class Examination extends GameMode {
    ArrayList<Item> items;
    Integer actual;

    public Examination(Lesson less){
        this.lesson=less;
        actual=0;
        for (Group group : lesson.getGroupsInLesson()) {
            for (Item item : group.getItemsInGroup()) {
                items.add(item);
            }
        }
        randomize();
    }
    @Override
    public Item next(Boolean answerToPrevious) {// ak prva polozka tak predchadzajuca odpoved bude null
        if (!items.isEmpty()){
            if (answerToPrevious==true){
                actual++;
                return items.get(actual);
            }
            else if(answerToPrevious==null){
                return items.get(0);
            }
            else {
                items.add(items.get(actual));
                actual++;
                return items.get(actual);
            }
        }
        return null;
    }

    @Override
    protected void randomize() {
        Collections.shuffle(items);
    }
}