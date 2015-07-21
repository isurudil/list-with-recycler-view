package hms.ajuba.menu_designer_app.pojo;

import com.google.gson.internal.LinkedTreeMap;

public class Option {
    private String title;
    private LinkedTreeMap<String, LinkedTreeMap> optionsMap;

    public Option(String title, LinkedTreeMap<String, LinkedTreeMap> optionsMap) {
        this.title = title;
        this.optionsMap = optionsMap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public LinkedTreeMap<String, LinkedTreeMap> getOptionsMap() {
        return optionsMap;
    }
}
