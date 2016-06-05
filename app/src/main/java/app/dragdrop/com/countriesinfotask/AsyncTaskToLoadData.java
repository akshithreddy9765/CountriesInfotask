package app.dragdrop.com.countriesinfotask;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class AsyncTaskToLoadData extends AsyncTask<Void, Void, String> {

    public Context mContext;
    private ProgressDialog pDialog;
    public String url = "http://restcountries.eu/rest/v1/region/americas";

    public AsyncTaskToLoadData(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        JSONParser jparser = new JSONParser();
        String json = jparser.getJSONFromUrl(url);
        return json;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (pDialog.isShowing()) {
            pDialog.hide();
//            Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
        }
    }
}
