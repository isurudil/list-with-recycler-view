package hms.ajuba.menu_designer_app.util;

import android.app.Activity;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import hms.ajuba.menu_designer_app.adapter.MenuAdapter;
import hms.ajuba.menu_designer_app.pojo.Option;

public class OptionsUtil {

    private static boolean isLastOption = false;

    public static ArrayList<Option> getNextList(LinkedTreeMap<String, LinkedTreeMap> optionsMap) {
        ArrayList<Option> menuList = new ArrayList<>();
        Iterator iterator = optionsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry next = (Map.Entry) iterator.next();
            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) next.getValue();
            LinkedTreeMap nameTree = (LinkedTreeMap) linkedTreeMap.values().toArray()[0];
            String name = (String) nameTree.values().toArray()[0];
            Option option = new Option(name, (LinkedTreeMap<String, LinkedTreeMap>) linkedTreeMap.get("options"));
            menuList.add(option);
        }
        return menuList;
    }

    public static void handleBackEvent(MenuAdapter adapter, Activity activity) {
        Stack<ArrayList<Option>> menuListStack = adapter.getMenuListStack();
        if (menuListStack.size() != 0) {
            adapter.setOptionList(menuListStack.pop());
            adapter.notifyDataSetChanged();
        } else {
            activity.finish();
        }
    }
}
