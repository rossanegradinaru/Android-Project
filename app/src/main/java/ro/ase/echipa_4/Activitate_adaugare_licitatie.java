package ro.ase.echipa_4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Activitate_adaugare_licitatie extends AppCompatActivity {

    public static final String ADAUGARE_LICITATIE = "adaugaLicitatie";
    private ConstraintLayout ml;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    private EditText etNume,etPrenume,etEmail,etSuma,etData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitate_adaugare_licitatie);

        final Spinner spinnerArt = findViewById(R.id.spinner);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.optiuni_spinner_formular, R.layout.support_simple_spinner_dropdown_item);

        spinnerArt.setAdapter(adapter);

        etData = findViewById(R.id.etdata);
        etNume = findViewById(R.id.etnume);
        etPrenume = findViewById(R.id.etprenume);
        etEmail = findViewById(R.id.etemail);
        etSuma = findViewById(R.id.etsuma);
         ml=findViewById(R.id.Layout2);
        final String DATE_FORMAT="dd/MM/yyyy";
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tvnume);
        tv3=findViewById(R.id.tvprenume);
        tv4=findViewById(R.id.tvEmail);
        tv5=findViewById(R.id.tvArticol);
        tv6=findViewById(R.id.tvsuma);
        tv7=findViewById(R.id.textView9);
        final Intent intent=getIntent();

        if(intent.hasExtra(Activitate_lista_licitatii.EDIT_LICITATIE))
        {
            Licitatie licitatie = (Licitatie) intent.getSerializableExtra(Activitate_lista_licitatii.EDIT_LICITATIE);
            etNume.setText(licitatie.getNume());
            etPrenume.setText(licitatie.getPrenume());
            etEmail.setText(licitatie.getEmail());
//            ArrayAdapter<String> adaptor = (ArrayAdapter<String>)spinnerArt.getAdapter();
//            for(int i=0;i<adaptor.getCount();i++)
//                if(adaptor.getItem(i).equals(licitatie.getArticol()))
//                {
//                    spinnerArt.setSelection(i);
//                    break;
//                }


            etSuma.setText(""+licitatie.getSumaLicitata());
            etData.setText(new SimpleDateFormat(DATE_FORMAT, Locale.US).format(licitatie.getData()));
        }



        Button btnTrimitere = findViewById(R.id.button);

        btnTrimitere.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
             //   SimpleDateFormat sdf=new SimpleDateFormat(DATE_FORMAT,Locale.US);
                Licitatie l=new Licitatie(etNume.getText().toString(),etPrenume.getText().toString(),etEmail.getText().toString(),spinnerArt.getSelectedItem().toString().toUpperCase(),Integer.parseInt(etSuma.getText().toString()),etData.getText().toString());
                LicitatiiDB licitatiiDB=LicitatiiDB.getInstanta(getApplicationContext());
                licitatiiDB.getLicitatieDao().insert(l);

                Random random=new Random();
                Articol articolC=new Articol(random.nextInt(99)+1,"Pantofi",10,1000);

                l.setId(articolC.getId());

                licitatiiDB.getArticolDao().insert(articolC);
                licitatiiDB.getLicitatieDao().insert(l);

                Licitatie l1=new Licitatie("Ionescu","Daniela","id@yahoo.com","Geanta",10000,"10/10/2019");
                Licitatie l2=new Licitatie("Tomescu","Maria","tm@yahoo.com","Pantofi",15000,"12/10/2019");
                Licitatie l3=new Licitatie("Ionescu","Constanta","ic@yahoo.com","Geanta",12000,"09/11/2018");

                Articol a1=new Articol(random.nextInt(99)+1,"Palarie",15,500);
                Articol a2=new Articol(random.nextInt(99)+1,"Pantofi",20,2000);

                licitatiiDB.getLicitatieDao().insert(l1);
                licitatiiDB.getLicitatieDao().insert(l2);
                licitatiiDB.getLicitatieDao().insert(l3);

                licitatiiDB.getLicitatieDao().updateLicitatii("Georgescu","Maria");

                licitatiiDB.getLicitatieDao().getAll();

                licitatiiDB.getLicitatieDao().deleteLicitatie(l3);


                licitatiiDB.getArticolDao().insert(a1);
                licitatiiDB.getArticolDao().insert(a2);
                licitatiiDB.getArticolDao().getAll();
                licitatiiDB.getArticolDao().deleteArticol(a2);
                licitatiiDB.getArticolDao().updateArticole(1500,"Pantofi");



                List<Licitatie> listaRoom=licitatiiDB.getLicitatieDao().getAll();
                if(listaRoom!=null)
                    for(Licitatie licitatie:listaRoom)
                        Toast.makeText(getApplicationContext(),l.toString(), Toast.LENGTH_LONG).show();


                if (etNume.getText().toString().isEmpty())
                    etNume.setError("Introduceti numele");
                else if(etPrenume.getText().toString().isEmpty())
                    etPrenume.setError("Introduceti prenumele");
                else if(etEmail.getText().toString().isEmpty())
                    etEmail.setError("Introduceti emailul");
                else if(etSuma.getText().toString().isEmpty())
                    etSuma.setError("Introduceti suma");
                else if(etData.getText().toString().isEmpty())
                {Toast.makeText(getApplicationContext(), R.string.eroare_data, Toast.LENGTH_LONG).show();}
                else
                    {


                        //sdf.parse(etData.getText().toString());

                        String nume=etNume.getText().toString();
                        String prenume=etPrenume.getText().toString();
                        String email=etEmail.getText().toString();
                        String articol=spinnerArt.getSelectedItem().toString().toUpperCase();
                        int suma=Integer.parseInt(etSuma.getText().toString());

                        String data=etData.getText().toString();


                        Licitatie licitatie=new Licitatie(nume,prenume,email,articol,suma,data);
                        SharedPreferences spf=getSharedPreferences("PreferinteAplicatie",0);
                        SharedPreferences.Editor ed=spf.edit();
                        ed.putString("nume",licitatie.getNume());
                        ed.putString("prenume",licitatie.getPrenume());
                        ed.putString("email",licitatie.getEmail());
                        ed.putString("articol", String.valueOf(licitatie.getArticol()));
                        ed.putInt("sumaLicitata",licitatie.getSumaLicitata());
                        ed.putString("data", String.valueOf(licitatie.getData()));
                        ed.apply();
                        intent.putExtra(ADAUGARE_LICITATIE,licitatie);
                        setResult(RESULT_OK,intent);
                        finish();


                    }
            }

        });
        Load_setting();
    }

    private void Load_setting(){
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        boolean chk_night=sp.getBoolean("NIGHT",false);
        boolean chk_mess=sp.getBoolean("MESSAGE",false);
        if(chk_mess){
            Toast.makeText(getApplicationContext(), R.string.mesaj1, Toast.LENGTH_LONG).show();
        }

        if(chk_night){
            ml.setBackgroundColor(Color.parseColor("#222222"));
            tv1.setTextColor(Color.parseColor("#ffffff"));
            tv2.setTextColor(Color.parseColor("#ffffff"));
            tv3.setTextColor(Color.parseColor("#ffffff"));
            tv4.setTextColor(Color.parseColor("#ffffff"));
            tv5.setTextColor(Color.parseColor("#ffffff"));
            tv6.setTextColor(Color.parseColor("#ffffff"));
            tv7.setTextColor(Color.parseColor("#ffffff"));
            etNume.setTextColor(Color.parseColor("#ffffff"));
            etPrenume.setTextColor(Color.parseColor("#ffffff"));
            etEmail.setTextColor(Color.parseColor("#ffffff"));
            etSuma.setTextColor(Color.parseColor("#ffffff"));
            etData.setTextColor(Color.parseColor("#ffffff"));

        }else{
            ml.setBackgroundColor(Color.parseColor("#ffffff"));
            tv1.setTextColor(Color.parseColor("#000000"));
            tv2.setTextColor(Color.parseColor("#000000"));
            tv3.setTextColor(Color.parseColor("#000000"));
            tv4.setTextColor(Color.parseColor("#000000"));
            tv5.setTextColor(Color.parseColor("#000000"));
            tv6.setTextColor(Color.parseColor("#000000"));
            tv7.setTextColor(Color.parseColor("#000000"));
            etNume.setTextColor(Color.parseColor("#000000"));
            etPrenume.setTextColor(Color.parseColor("#000000"));
            etEmail.setTextColor(Color.parseColor("#000000"));
            etSuma.setTextColor(Color.parseColor("#000000"));
            etData.setTextColor(Color.parseColor("#000000"));
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


}
