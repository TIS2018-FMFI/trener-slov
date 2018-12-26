package modes;

import data.Group;
import data.Item;
import data.Lesson;
import java.util.ArrayList;
import java.util.HashMap;

public class Learning extends GameMode {
    Integer numOfRepeat;
    ArrayList<Group> skupiny;
    ArrayList<Group> rowOfG;
    ArrayList<Item> row;
    Integer actual=-1;
    Integer end=2;
    Boolean fazeOneDone=false;
    HashMap<Group,Integer> corrAnswers;

    public Learning(Lesson less, Integer num){
        numOfRepeat=num;
        this.lesson=less;
        reinitialize();
    }

    @Override
    public Item next(Boolean answerToPrevious) {
        if (answerToPrevious==null){
            return row.remove(0);
        }
        else if (answerToPrevious==true){
            if (row.isEmpty()) {
                Integer i=corrAnswers.get(rowOfG.get(actual));
                i++;
                corrAnswers.put(rowOfG.get(actual),i);
                printHash();
                addNextItems();
            }
            return row.remove(0);
        }
        else if (answerToPrevious==false){
            addActualGroup();
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
        corrAnswers=new HashMap<>();
        for (Group g:lesson.getGroupsInLesson()) {
            skupiny.add(g);
            corrAnswers.put(g,0);
        }
        rowOfG=new ArrayList<>();
        rowOfG.add(skupiny.get(0));
        rowOfG.add(skupiny.get(1));
        printGroups();
        printRow();
        addNextItems();

    }
    protected void addNextItems(){
        row.clear();
        actual++;
        if (actual==rowOfG.size()){
            actual=0;
            addNextGroup();
        }
        for (Item i:rowOfG.get(actual).getItemsInGroup()){ row.add(i);}
        System.out.println("Actual group is "+actual);
    }
    protected void addNextGroup(){
        if (end==skupiny.size()) end=0;
        rowOfG.add(skupiny.get(end));
        end++;

        printRow();
    }
    protected void addActualGroup(){
        rowOfG.add(skupiny.get(actual));
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
    protected Integer index(Group g){
        for (int i = 0; i < skupiny.size(); i++) {
            if (g==skupiny.get(i)) return i;
        }
        return null;
    }
    protected void printHash(){
        String v="{ ";
        for (Group g: corrAnswers.keySet()) {
            v+=g.getName()+":"+corrAnswers.get(g)+" ";
        }
        System.out.println(v+"}");
    }
}

