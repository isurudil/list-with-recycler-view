package hms.ajuba.menu_designer_app.pojo;

import java.util.ArrayList;

public class MenuItem {

    private String header;
    private ArrayList<Option> option;

    public MenuItem(String header, ArrayList<Option> option) {
        this.header = header;
        this.option = option;
    }
}
