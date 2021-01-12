package ro.ase.echipa_4;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activitate_adaugare_review extends AppCompatActivity {
    public static final String ADAUGARE_REVIEW = "adaugareReview";
    private ConstraintLayout ml;
    private TextView tv1,tv2;
    private EditText etNume,etReview;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        etNume = findViewById(R.id.etnumer);
        etReview = findViewById(R.id.etreview);
        tv1 = findViewById(R.id.tvnumer);
        tv2 = findViewById(R.id.tvreview);
        ml=findViewById(R.id.layout3);
        Button btnTrimitere = findViewById(R.id.button);
        final Intent intent = getIntent();
        database=FirebaseDatabase.getInstance();

        btnTrimitere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNume.getText().toString().isEmpty())
                    etNume.setError("Introduceti numele");
                else
                if (etReview.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Introduceti review", Toast.LENGTH_LONG).show();
                else
                {
                        String nume = etNume.getText().toString();
                        String continut = etReview.getText().toString();
                        Review review = new Review(nume, continut);
                        writeCVinFirebase(review);
                        intent.putExtra(ADAUGARE_REVIEW, review);
                        setResult(RESULT_OK, intent);
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
            Toast.makeText(getApplicationContext(), R.string.mesaj2, Toast.LENGTH_LONG).show();
        }
        if(chk_night){
            ml.setBackgroundColor(Color.parseColor("#222222"));
            tv1.setTextColor(Color.parseColor("#ffffff"));
            tv2.setTextColor(Color.parseColor("#ffffff"));
            etNume.setTextColor(Color.parseColor("#ffffff"));
            etReview.setTextColor(Color.parseColor("#ffffff"));
        }else{
            ml.setBackgroundColor(Color.parseColor("#ffffff"));
            tv1.setTextColor(Color.parseColor("#000000"));
            tv2.setTextColor(Color.parseColor("#000000"));
            etNume.setTextColor(Color.parseColor("#000000"));
            etReview.setTextColor(Color.parseColor("#000000"));
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
    @Override
    protected void onResume() {
        Load_setting();
        super.onResume();
    }
    private void writeCVinFirebase(final Review r){
        final DatabaseReference myref=database.getReference("proiectlicitatie-default-rtdb");
        myref.keepSynced(true);
        myref.child("proiectlicitatie-default-rtdb").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            r.setUid(myref.child("proiectlicitatie-default-rtdb").push().getKey());
            myref.child("proiectlicitatie-default-rtdb").child(r.getUid()).setValue(r);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}