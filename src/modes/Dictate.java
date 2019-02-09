package modes;

import data.Group;
import data.Item;
import data.Lesson;
import java.util.ArrayList;
import java.util.Collections;

public class Dictate extends GameMode {
    private ArrayList<Item> items;


    public Dictate(Lesson less) throws UnsatisfactoryLessonException {
        this.lesson=less;
        if (less.getGroupsInLesson().size()<1) throw new UnsatisfactoryLessonException("Lekcia má málo skupín.");
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
    }

	@Override
	public void reinitialize() throws UnsatisfactoryLessonException {
        items=new ArrayList<>();
        for (Group group : lesson.getGroupsInLesson()) {
            if (group.getItemsInGroup().size()<3){
                throw new UnsatisfactoryLessonException("Skupina v tejto lekcii obsahuje nedostatočný počet položiek.");
            }
                for (Item item : group.getItemsInGroup()) {
                if (item.getQuestionSound() != null && item.getAnswerText() != null) items.add(item);
            }
        }
        randomize();
	}
}