package modes;

import data.Group;
import data.Item;
import data.Lesson;
import java.util.ArrayList;
import java.util.Collections;

public class StationaryBicycle extends GameMode {
    private ArrayList<Item> items;
    private ArrayList<Item> copiedItems;
    private Integer numberOfAnswersPlay;
    private Integer pauseDurationInSecs;
    private Integer modeDurationInSecs;

    public StationaryBicycle(Lesson less, Integer number, Integer pause, Integer modeDur){
        this.lesson=less;
        this.numberOfAnswersPlay=number;
        this.pauseDurationInSecs=pause;
        this.pauseDurationInSecs=modeDur;
        reinitialize();
    }

    @Override
    public Item next(Boolean answerToPrevious) {
        if (copiedItems.isEmpty()) copiedItems=cloneList(items);
        return copiedItems.remove(0);
    }

    @Override
    protected void randomize() { Collections.shuffle(items); }
    
	@Override
	public void reinitialize() {
        items = new ArrayList<>();
        for (Group group : lesson.getGroupsInLesson()) {
            for (Item item : group.getItemsInGroup()) {
                items.add(item);
            }
        }
        randomize();
        copiedItems=cloneList(items);
	}

    public static ArrayList<Item> cloneList(ArrayList <Item> list){
        ArrayList <Item> newL=new ArrayList<>();
        for (Item i:list) newL.add(i);
        return newL;
    }
}