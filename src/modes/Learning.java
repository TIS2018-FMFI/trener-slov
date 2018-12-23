package modes;

import data.Group;
import data.Item;
import data.Lesson;
import java.util.ArrayList;

public class Learning extends GameMode {
    Integer numOfRepeat;
    ArrayList<Group> skupiny;
    ArrayList<Group> rowOfG;
    ArrayList<Item> row;
    Integer actual=0;
    Integer end=2;
    Boolean fazeOneDone=false;

    public Learning(Lesson less, Integer num){
        numOfRepeat=num;
        this.lesson=less;
        reinitialize();
    }

    @Override
    public Item next(Boolean answerToPrevious) {
        if (row.isEmpty()) {
            addNextItems();
        }
        return row.remove(0);
    }

    @Override
    protected void randomize() { }
    
	@Override
	public void reinitialize() {
        skupiny=new ArrayList<>();
        row=new ArrayList<>();
        for (Group g:lesson.getGroupsInLesson()) { skupiny.add(g); }
        rowOfG=new ArrayList<>();
        rowOfG.add(skupiny.get(0));
        rowOfG.add(skupiny.get(1));
        printRow();
        addNextItems();

    }
    protected void addNextItems(){
        row.clear();
        if (actual==rowOfG.size()){
            actual=0;
            addNextGroup();
        }
        for (Item i:rowOfG.get(actual).getItemsInGroup()){ row.add(i);}
        actual++;
    }
    protected void addNextGroup(){
        if (end==skupiny.size()) end=0;
        rowOfG.add(skupiny.get(end));
        end++;
        printRow();
    }
    protected void printRow(){
        String vysl="{ ";
        for (Group g : rowOfG){ vysl+=g.getName()+", ";}
        vysl+="}";
        System.out.println(vysl);
    }
    protected void printGroups(){
        String vysl="{ ";
        for (Group g : skupiny){ vysl+=g.getName()+", ";}
        vysl+="}";
        System.out.println(vysl);
    }
    protected int count(Group g){
        int i=0;
        for (Group gg:rowOfG) {
            if (g==gg) i++;
        }
        return i;
    }
}
