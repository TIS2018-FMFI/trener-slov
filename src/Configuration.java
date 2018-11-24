public class Configuration {

    private Integer fontSize;

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Configuration() {
        this.fontSize = 20;
    }

    public Configuration(Integer fontSize) {
        this.fontSize = fontSize;
    }
}
