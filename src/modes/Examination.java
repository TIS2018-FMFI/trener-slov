package modes;

import data.Group;
import data.Item;
import data.Lesson;

import java.util.ArrayList;
import java.util.Collections;

public class Examination extends GameMode {
    ArrayList<Item> items;
    public Examination(Lesson less) throws UnsatisfactoryLessonException {
        this.lesson=less;
        if (less.getGroupsInLesson().size()<1) throw new UnsatisfactoryLessonException("Lekcia má málo skupín.");
        reinitialize();
        this.printItems();
    }

    @Override
    public Item next(Boolean answerToPrevious) {// ak prva polozka tak predchadzajuca odpoved bude null
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
                items.add(items.get(0));
                items.remove(0);
                return items.get(0);
            }
        }
        this.printItems();
        return null;
    }

    @Override
    protected void randomize() {
        Collections.shuffle(items);
    }
    protected void printItems(){
        String arr="[";
        for (Item i:items) arr += i.getQuestionText()+", ";
        arr+="]";
        System.out.println(arr);
    }
	@Override
	public void reinitialize() throws UnsatisfactoryLessonException {
        items = new ArrayList<>();
        for (Group group : lesson.getGroupsInLesson()) {
            if (group.getItemsInGroup().size()<3){
                throw new UnsatisfactoryLessonException("Skupina v tejto lekcii obsahuje nedostatočný počet položiek.");
            }
            for (Item item : group.getItemsInGroup()) {
                items.add(item);
            }
        }
        randomize();
	}
}