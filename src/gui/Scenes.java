package gui;

public enum Scenes {
	
	MAIN_MENU("mainMenu", "Hlavné menu"),
    LESSON_LIST("lessonList", "Zoznam lekcií"),
    LESSON("lesson", "Lekcia"),
    GROUP("group", "Skupina")
    ;

    private final String fxmlName;
    private final String title;

    Scenes(final String fxmlName, final String title) {
        this.fxmlName = fxmlName;
        this.title = title;
    }

    @Override
    public String toString() {
        return fxmlName;
    }
    
    public String getTitle() {
    	return title;
    }

}
