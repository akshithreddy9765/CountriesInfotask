package app.dragdrop.com.countriesinfotask;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ListView listView;
    public String url = "https://restcountries.eu/rest/v1/region/americas";
    // Progress dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        makeJsonArrayRequest();
        showpDialog();


        ArrayList<Object> globalArrayList = parseJSONData();
        if (globalArrayList.size() > 0) {
            hidepDialog();

            CustomAdapter adapter = new CustomAdapter(MainActivity.this, globalArrayList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(listviewOnItemClickListener);

        }

    }

    AdapterView.OnItemClickListener listviewOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);

            ArrayList<String> languagesArray = (ArrayList<String>) nameTextView.getTag();

            String languages = "";
            for (int k = 0; k < languagesArray.size(); k++) {
                languages = languages + languagesArray.get(k) + " , ";
            }

            if (languages.length() > 0) {
                languages = languages.substring(0, languages.length() - 2);
                Toast.makeText(getApplicationContext(), "Languages: " + languages, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "No languages available", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public ArrayList<Object> parseJSONData() {

        ArrayList<Object> regionArrayList = new ArrayList<Object>();
        try {
            String tempdata = loadJSONFromAsset();

            JSONArray jsonArray = new JSONArray(tempdata);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Region region = new Region();
                String name = jsonObject.getString("name");
                region.setName(name);
                String capital = jsonObject.getString("capital");
                region.setCapital(capital);
                String population = jsonObject.getString("population");
                region.setPopulation(population);
                JSONArray languagesArray = jsonObject.getJSONArray("languages");

                ArrayList<String> langArrayList = new ArrayList<String>();
                for (int j = 0; j < languagesArray.length(); j++) {
                    String language = languagesArray.getString(j);
                    langArrayList.add(language);

                }

                region.setLanguages(langArrayList);

                regionArrayList.add(region);

            }

        } catch (Exception e) {
            Log.d("MainActivity", e.getMessage());
        }

        return regionArrayList;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("test.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void makeJsonArrayRequest() {

        AsyncTaskToLoadData async = new AsyncTaskToLoadData(MainActivity.this);
        async.execute();


    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
