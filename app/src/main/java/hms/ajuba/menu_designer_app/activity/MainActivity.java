package hms.ajuba.menu_designer_app.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import hms.ajuba.menu_designer_app.R;
import hms.ajuba.menu_designer_app.adapter.MenuAdapter;
import hms.ajuba.menu_designer_app.exception.ConnectionFailureException;
import hms.ajuba.menu_designer_app.pojo.Option;
import hms.ajuba.menu_designer_app.util.ConnectionManager;
import hms.ajuba.menu_designer_app.util.OptionsUtil;
import hms.ajuba.menu_designer_app.util.PropertyLoader;


public class MainActivity extends Activity {

    private LinkedTreeMap optionsMap;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private MenuAdapter adapter;
    private TextView txtTitle;
    private String rootTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControls();
        initImageLoader();
        new GetJsonTask().execute();
    }

    private void initControls() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        txtTitle = (TextView) findViewById(R.id.txt_header);
    }

    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .defaultDisplayImageOptions(defaultOptions)
        .build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onBackPressed() {
        handleBackEvent();
    }

    private void handleBackEvent() {
        Stack<ArrayList<Option>> menuListStack = adapter.getMenuListStack();
        if (menuListStack.size() != 0) {
            adapter.setOptionList(menuListStack.pop());
            adapter.notifyDataSetChanged();
            if(menuListStack.size() == 1){
                txtTitle.setText(rootTitle);
            }
        } else {
            finish();
        }
    }

    private class GetJsonTask extends AsyncTask<Void, Void, LinkedTreeMap<String, LinkedTreeMap>> {

        @Override
        protected LinkedTreeMap<String, LinkedTreeMap> doInBackground(Void... voids) {
            String jsonResponse;
            ObjectMapper mapper = new ObjectMapper();
            try {
                jsonResponse = ConnectionManager.get(PropertyLoader.getString("service.url"), MainActivity.this);
                JsonNode rootNode = mapper.readTree(jsonResponse);
                JsonNode menuItemNode = rootNode.path("menuStructure").path("menuItem");
                JsonNode optionsNode = menuItemNode.path("options");
                rootTitle = menuItemNode.path("header").path("en").getTextValue();
                optionsMap = new Gson().fromJson(optionsNode.toString(), LinkedTreeMap.class);
            } catch (ConnectionFailureException | IOException e) {
                Log.e("MD", "Error occurred", e);
            }
            return optionsMap;
        }

        @Override
        protected void onPostExecute(LinkedTreeMap<String, LinkedTreeMap> optionsMap) {
            txtTitle.setText(rootTitle);
            progressBar.setVisibility(View.GONE);
            ArrayList<Option> nextList = OptionsUtil.getNextList(optionsMap);
            adapter = new MenuAdapter(MainActivity.this, nextList);
            recyclerView.setAdapter(adapter);
        }
    }
}
