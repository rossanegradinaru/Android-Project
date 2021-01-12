package ro.ase.echipa_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AfisareFirebase extends AppCompatActivity {
private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afisare_firebase);
        lv=findViewById(R.id.lvfirebase);
        final List<Review> reviewuri=new ArrayList<>();
        final List<String> listaString=new ArrayList<>();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference myref=database.getReference("proiectlicitatie-default-rtdb");
        myref.keepSynced(true);
        ValueEventListener messageListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    reviewuri.clear();
                    for(DataSnapshot dn: snapshot.getChildren()){
                        Review r=dn.getValue(Review.class);
                        reviewuri.add(r);
                    }
                }

                listaString.clear();
                for(Review r:reviewuri)
                    listaString.add(r.toString());

                ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        listaString);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        myref.child("proiectlicitatie-default-rtdb").addValueEventListener(messageListener);

    }
    private void Load_setting(){
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        boolean chk_night=sp.getBoolean("NIGHT",false);
        boolean chk_mess=sp.getBoolean("MESSAGE",false);
        if(chk_mess){
            Toast.makeText(getApplicationContext(), R.string.mesaj3, Toast.LENGTH_LONG).show();
        }

        if(chk_night){
            lv.setBackgroundColor(Color.parseColor("#222222"));
        }else{
            lv.setBackgroundColor(Color.parseColor("#ffffff"));
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