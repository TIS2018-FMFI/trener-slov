package TSTypes;

import java.util.ArrayList;

public class Group {

    private String name;
    private Integer order;
    private ArrayList<Item> itemsInGroup;

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

    }

    public void removeItem(Item item) {

    }

}
