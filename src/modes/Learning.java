package modes;

import data.Group;
import data.Item;

import java.util.ArrayList;

public class Learning extends GameMode {

    ArrayList<Group> rowOfGroups;
    Integer numOfRepeat;
    Integer index; // po ktoru skupinu ma pridat
    Boolean allTaught;
    Integer actual;

    public Learning(Integer num){
        numOfRepeat=num;
        rowOfGroups=new ArrayList<Group>();
        rowOfGroups.add(lesson.getGroupsInLesson().get(0));
        rowOfGroups.add(lesson.getGroupsInLesson().get(1));
        index=1;
    }

    @Override
    public Item next(Boolean answerToPrevious) {


        return null;
    }

    @Override
    protected void randomize() {


    }
    protected Boolean fillRow(){
        if(index<lesson.getGroupsInLesson().size()){
            for (int i=0;i<=index;i++){
                rowOfGroups.add(lesson.getGroupsInLesson().get(i));
            }
            index++;
            return true;
        }
        else return false;
    }

}
