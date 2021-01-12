package ro.ase.echipa_4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Activitate_lista_licitatii extends AppCompatActivity {
    public static final int OPTIUNE1 = 0;
    public static final int OPTIUNE2 = 1;
    public static final int OPTIUNE3 = 2;
    public static final int OPTIUNE4 = 3;
    private FloatingActionButton floatingActionButton;

    public static final int REQUEST_CODE=200;

    public static final int REQUEST_CODE_EDIT = 300;

    public static final String EDIT_LICITATIE = "editLicitatie";

    public int poz;

    private ListView listView;
    List<Licitatie> licitatieList=new ArrayList<Licitatie>();
    private Intent intent;
    private LinearLayout ml;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_licitatii);
        floatingActionButton=findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), Activitate_adaugare_licitatie.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        listView = findViewById(R.id.listView);
        tv=findViewById(R.id.tvIstoricComenzi);
        ml=findViewById(R.id.Layout1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                poz = position;
                intent = new Intent(getApplicationContext(), Activitate_adaugare_licitatie.class);
                intent.putExtra(EDIT_LICITATIE, licitatieList.get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                final Licitatie licitatie = licitatieList.get(position);


                final AdaptorLicitatie adapter = (AdaptorLicitatie) listView.getAdapter();


                AlertDialog dialog = new AlertDialog.Builder(Activitate_lista_licitatii.this)
                        .setTitle("Confirmare stergere")
                        .setMessage("Sigur doriti stergerea?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "Nu s-a sters nimic!",
                                        Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                licitatieList.remove(licitatie);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "S-a sters licitatia: "+licitatie.toString(),
                                        Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        }).create();

                dialog.show();

                return true;
            }
        });
        Load_setting();
    }

    private void Load_setting(){
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        boolean chk_night=sp.getBoolean("NIGHT",false);
        boolean chk_mess=sp.getBoolean("MESSAGE",false);
        if(chk_mess){
            Toast.makeText(getApplicationContext(), R.string.mesaj3, Toast.LENGTH_LONG).show();
        }

        if(chk_night){
            ml.setBackgroundColor(Color.parseColor("#222222"));
            tv.setTextColor(Color.parseColor("#ffffff"));
        }else{
            ml.setBackgroundColor(Color.parseColor("#ffffff"));
            tv.setTextColor(Color.parseColor("#000000"));
        }

        String or=sp.getString("ORIENTARE","false");
        if("1".equals(or)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);

        }
        else if("2".equals(or)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
        else if("3".equals(or)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, OPTIUNE1, 0, R.string.optiune1);
        menu.add(0, OPTIUNE2, 1, R.string.optiune2);
        menu.add(0, OPTIUNE3, 2, R.string.optiune3);
        menu.add(0, OPTIUNE4, 3,R.string.optiune4);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case OPTIUNE1:
                Intent intent = new Intent(getApplicationContext(), Activitate_adaugare_review.class);
                startActivity(intent);
                break;
            case OPTIUNE2:
                Intent intent2 = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent2);
                break;
            case OPTIUNE3:
                Intent intent3 = new Intent(getApplicationContext(), JSONActivity.class);
                startActivity(intent3);
                break;
            case OPTIUNE4:
                Intent intent4 = new Intent(getApplicationContext(), AfisareFirebase.class);
                startActivity(intent4);
                return true;

        }

        return false;
    }

    @Override
    protected void onResume() {
        Load_setting();
        super.onResume();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Licitatie licitatie = (Licitatie) data.getSerializableExtra(Activitate_adaugare_licitatie.ADAUGARE_LICITATIE);

            if (licitatie != null) {
                licitatieList.add(licitatie);

                AdaptorLicitatie adapter = new AdaptorLicitatie(getApplicationContext(), R.layout.elemlistview,
                        licitatieList, getLayoutInflater()){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        Licitatie licitatie =  licitatieList.get(position);
                        return view;
                    }
                };
                listView.setAdapter(adapter);
            }
        }
        else
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
            Licitatie licitatie = (Licitatie) data.getSerializableExtra(Activitate_adaugare_licitatie.ADAUGARE_LICITATIE);
            {
                if (licitatie!=null)
                {
                    licitatieList.get(poz).setNume(licitatie.getNume());
                    licitatieList.get(poz).setPrenume(licitatie.getPrenume());
                    licitatieList.get(poz).setEmail(licitatie.getEmail());
                    licitatieList.get(poz).setArticol(licitatie.getArticol());
                    licitatieList.get(poz).setSumaLicitata(licitatie.getSumaLicitata());
                    licitatieList.get(poz).setData(licitatie.getData());

                    AdaptorLicitatie adapter = new AdaptorLicitatie(getApplicationContext(), R.layout.elemlistview,
                            licitatieList, getLayoutInflater()){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);

                            Licitatie movie =  licitatieList.get(position);
                            return view;
                        }
                    };
                    listView.setAdapter(adapter);
                }
            }
        }
    }
}
