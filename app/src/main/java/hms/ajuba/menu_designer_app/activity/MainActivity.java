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
    private TextView txtHeader;
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

        txtHeader = (TextView) findViewById(R.id.txt_header);
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
        OptionsUtil.handleBackEvent(adapter, this);
        ArrayList<String> headerList = adapter.getHeaderList();
        if (headerList.size() > 1) {
            txtHeader.setText(headerList.get(headerList.size() - 2));
            headerList.remove(headerList.size() - 2);
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
            txtHeader.setText(rootTitle);
            progressBar.setVisibility(View.GONE);
            ArrayList<Option> nextList = OptionsUtil.getNextList(optionsMap);
            adapter = new MenuAdapter(MainActivity.this, nextList, txtHeader);
            adapter.getHeaderList().add(rootTitle);
            recyclerView.setAdapter(adapter);
        }
    }
}
