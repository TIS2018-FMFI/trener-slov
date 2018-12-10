package gui;

public enum Scenes {
	
	MAIN_MENU("mainMenu", "Hlavné menu"),
    LESSON_LIST("lessonList", "Zoznam lekcií"),
    LESSON("lesson", "Lekcia"),
    GROUP("group", "Skupina"),
    ITEM("item", "Položka"),
    EXPORT_LESSONS("exportLessons", "Export lekcií"), 
    START_LESSON("startLesson", "Výber lekcie"), 
    START_MODE("startMode", "Výber módu"), 
    MODE("mode", "")
    ;

    private final String fxmlName;
    private String title;

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

    public void setTitle(String title) {
    	this.title = title;
    }
}
