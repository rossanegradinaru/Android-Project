package ro.ase.echipa_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdaptorLicitatie extends ArrayAdapter<Licitatie>{
    private Context context;
    private int resource;
    private List<Licitatie> listaLicitatii;
    private LayoutInflater layoutInflater;

    public AdaptorLicitatie(@NonNull Context context, int resource, List<Licitatie> listaLicitatii, LayoutInflater layoutInflater) {
        super(context, resource, listaLicitatii);
        this.context = context;
        this.resource = resource;
        this.listaLicitatii = listaLicitatii;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = layoutInflater.inflate(resource, parent, false);
        Licitatie licitatie = listaLicitatii.get(position);

        if (licitatie!=null)
        {
            TextView tv1 = view.findViewById(R.id.nume);
            tv1.setText(licitatie.getNume());

            TextView tv2 = view.findViewById(R.id.prenume);
            tv2.setText(licitatie.getPrenume());

            TextView tv3 = view.findViewById(R.id.email);
            tv3.setText(licitatie.getEmail());

            TextView tv4 = view.findViewById(R.id.articol);
            tv4.setText(licitatie.getArticol().toString());

            TextView tv5 = view.findViewById(R.id.suma);
            tv5.setText(String.valueOf(licitatie.getSumaLicitata()));

            TextView tv6 = view.findViewById(R.id.data);
            tv6.setText(licitatie.getData().toString());
        }

        return view;
    }
}