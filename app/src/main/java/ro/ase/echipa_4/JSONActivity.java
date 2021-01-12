package ro.ase.echipa_4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONActivity extends ListActivity {
    public static final String  TAG_OFERTE = "oferte";
    public static final String TAG_NUME = "nume";
    public static final String TAG_PRENUME = "prenume";
    public static final String TAG_TELEFON = "telefon";
    public static final String TAG_PRODUS = "produs";
    public static final String TAG_PRET = "pretminim";
    private ProgressDialog pDialog;
    JSONArray oferte = null;
    ArrayList<HashMap<String, String>> Listaoferte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        Listaoferte = new ArrayList<>();
        URL url = null;
        try {
            url = new URL("https://pastebin.com/raw/Qz0WgYA3");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        GetOferte m = new GetOferte();
        m.setOnTaskFinishedEvent(new OnTaskExecutionFinished() {
            @Override
            public void onTaskFinishedEvent(String result) {

                if(pDialog.isShowing())
                {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pDialog.dismiss();
                }


                ListAdapter adapter = new SimpleAdapter(JSONActivity.this,
                        Listaoferte,
                        R.layout.list_item,
                        new String[]{TAG_NUME, TAG_PRENUME, TAG_TELEFON,TAG_PRODUS,TAG_PRET},
                        new int[]{R.id.numejson, R.id.prenumejson, R.id.telefonjson,R.id.produsjson,R.id.pretminimjson}){

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        HashMap<String, String> currentRow = Listaoferte.get(position);

                        return view;
                    }
                };

                setListAdapter(adapter);
            }
        });
        m.execute(url);
    }

    public interface OnTaskExecutionFinished
    {
        void onTaskFinishedEvent(String result);
    }
    public class GetOferte extends AsyncTask<URL, Void, String>
    {
        private OnTaskExecutionFinished event;

        public void setOnTaskFinishedEvent(OnTaskExecutionFinished event)
        {
            if (event!=null)
                this.event = event;
        }

        @Override
        protected String doInBackground(URL... urls) {
            HttpURLConnection conn = null;

            try{
                conn = (HttpURLConnection)urls[0].openConnection();
                conn.setRequestMethod("GET");
                InputStream ist = conn.getInputStream();

                InputStreamReader isr = new InputStreamReader(ist);
                BufferedReader br = new BufferedReader(isr);
                String linie = null;
                String sbuf = "";
                while ((linie = br.readLine())!=null)
                {
                    sbuf +=linie;
                }
                loadJSON(sbuf);
                return sbuf;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(this.event!=null)
            this.event.onTaskFinishedEvent(s);
        else
            Log.e("GetOferte", "event is null");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(JSONActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }
        public void loadJSON(String jsonStr)
        {
            if(jsonStr!=null)
            {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    oferte = jsonObject.getJSONArray(TAG_OFERTE);

                    for(int i=0;i<oferte.length();i++)
                    {
                        JSONObject c = oferte.getJSONObject(i);
                        String nume = c.getString(TAG_NUME);
                        String prenume = c.getString(TAG_PRENUME);
                        String telefon = c.getString(TAG_TELEFON);
                        String produs = c.getString(TAG_PRODUS);
                        String pret = c.getString(TAG_PRET);

                        HashMap<String, String> oferta  = new HashMap<>();
                        oferta.put(TAG_NUME,nume);
                        oferta.put(TAG_PRENUME, prenume);
                        oferta.put(TAG_TELEFON, telefon);
                        oferta.put(TAG_PRODUS, produs);
                        oferta.put(TAG_PRET, pret);

                        Listaoferte.add(oferta);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
                Log.e("loadJSON", "JSON object is null");
        }
    }
}