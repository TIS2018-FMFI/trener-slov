package modes;

import data.Group;
import data.Item;
import data.Lesson;
import java.util.ArrayList;
import java.util.Collections;

public class Dictate extends GameMode {
    private ArrayList<Item> items;

    public Dictate(Lesson less){
        this.lesson=less;
        reinitialize();
        this.printItems();
    }
    @Override
    public Item next(Boolean answerToPrevious) {
        if (!items.isEmpty()){
            if (answerToPrevious==null){
                return items.get(0);
            }
            else if (answerToPrevious==true){
                items.remove(0);
                if (items.isEmpty()) return null;
                return items.get(0);
            }
            else if (answerToPrevious==false){
                this.printItems();
                items.add(items.get(0));
                items.remove(0);
                return items.get(0);
            }
        }
        return null;
    }

    @Override
    protected void randomize()  {
        Collections.shuffle(items);
    }
    protected void printItems(){
        String arr="[";
        for (Item i:items) arr += i.getQuestionText()+", ";
        arr+="]";
        System.out.println(arr);
    }

	@Override
	public void reinitialize() {
        items=new ArrayList<>();
        for (Group group : lesson.getGroupsInLesson()) {
            for (Item item : group.getItemsInGroup()) {
                if (item.getQuestionSound() != null && item.getAnswerText() != null) items.add(item);
            }
        }
        randomize();
	}
}