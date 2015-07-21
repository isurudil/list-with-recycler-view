package hms.ajuba.menu_designer_app.pojo;

import com.google.gson.internal.LinkedTreeMap;

public class Option {
    private String header;
    private boolean isLastOption;
    private String title;
    private LinkedTreeMap<String, LinkedTreeMap> optionsMap;

    public Option(String title, String header, LinkedTreeMap<String, LinkedTreeMap> optionsMap) {
        this.title = title;
        this.optionsMap = optionsMap;
        this.isLastOption = false;
        this.header = header;
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

    public boolean isLastOption() {
        return isLastOption;
    }

    public void setIsLastOption(boolean isLastOption) {
        this.isLastOption = isLastOption;
    }

    public String getHeader() {
        return header;
    }
}
