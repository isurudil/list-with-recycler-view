package hms.ajuba.menu_designer_app.pojo;

import java.util.Map;

public class Option {
    private String title;
    private Map<String, Map> optionsMap;

    public Option(String title, Map<String, Map> optionsMap) {
        this.title = title;
        this.optionsMap = optionsMap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Map<String, Map> getOptionsMap() {
        return optionsMap;
    }
}
