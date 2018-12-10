package data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;


@XmlRootElement(namespace = "group")
@XmlAccessorType(XmlAccessType.FIELD)

public class Group implements Serializable {

    private String name;
    private Integer order;

    @XmlElementWrapper(name = "items")

    @XmlElement(name = "item")
    private ArrayList<Item> itemsInGroup;

    public Group() {
    }

    public Group(String name, Integer order, ArrayList<Item> itemsInGroup) {
        this.name = name;
        this.order = order;
        this.itemsInGroup = itemsInGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public ArrayList<Item> getItemsInGroup() {
        return itemsInGroup;
    }

    public void setItemsInGroup(ArrayList<Item> itemsInGroup) {
        this.itemsInGroup = itemsInGroup;
    }

    public void addItem(Item item) {
        itemsInGroup.add(item);
    }

    public void removeItem(Item item) {
        itemsInGroup.remove(item);
    }

}