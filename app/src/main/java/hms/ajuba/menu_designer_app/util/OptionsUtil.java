package hms.ajuba.menu_designer_app.util;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import hms.ajuba.menu_designer_app.pojo.Option;

public class OptionsUtil {

    public static ArrayList<Option> getNextList(Map<String, Map> optionsMap) {
        ArrayList<Option> menuList = new ArrayList<>();
        Iterator iterator = optionsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry next = (Map.Entry) iterator.next();
            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) next.getValue();
            LinkedTreeMap nameTree = (LinkedTreeMap) linkedTreeMap.values().toArray()[0];
            String name = (String) nameTree.values().toArray()[0];
            Map<String, Map> nextOptionsMap = (LinkedTreeMap) linkedTreeMap.get("options");
            Option option = new Option(name, nextOptionsMap);
            menuList.add(option);
        }
        return menuList;
    }
}
