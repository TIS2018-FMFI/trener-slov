package gui;

public enum Scenes {
	
	MAIN_MENU("mainMenu", "Hlavn� menu"),
    LESSON_LIST("lessonList", "Zoznam lekci�"),
    LESSON("lesson", "Lekcia")
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
