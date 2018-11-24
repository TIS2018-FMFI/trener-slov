package data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(namespace = "lesson")

public class Lesson {

    private String name;

    @XmlElementWrapper(name = "groups")
    @XmlElement(name = "group")
    private ArrayList<Group> groupsInLesson;

    public Lesson(String name, ArrayList<Group> groupsInLesson) {
        this.name = name;
        this.groupsInLesson = groupsInLesson;
    }

    public Lesson() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Group> getGroupsInLesson() {
        return groupsInLesson;
    }

    public void addGroup(Group group) {
        groupsInLesson.add(group);
    }

    public void removeGroup(Group group) {
        groupsInLesson.remove(group);
    }
}
