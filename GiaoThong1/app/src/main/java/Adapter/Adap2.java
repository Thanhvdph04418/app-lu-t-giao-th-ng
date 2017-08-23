package Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vdthero.giaothong.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.bienbao;

/**
 * Created by Thanh-PC on 7/12/2017.
 */

public class Adap2 extends ArrayAdapter<bienbao> {
    Activity context;
    int resource;
    List<bienbao> objects;
    ArrayList<bienbao> arraylist;
    public Adap2(Activity context, int resource, List<bienbao> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.arraylist=new ArrayList<bienbao>();
        this.arraylist.addAll(objects);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        TextView txtnd = (TextView) row.findViewById(R.id.txtbienbao);
        ImageView img= (ImageView) row.findViewById(R.id.img1);
        bienbao bb=this.objects.get(position);

        txtnd.setText(bb.getNoidung());
        Picasso.with(context).load(bb.getAnh()).into(img);
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.up_from_bottom);
        row.startAnimation(animation);

        return row;
    }
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        objects.clear();
        if(charText.length() == 0){
            objects.addAll(arraylist);
        } else {
            for (bienbao it : arraylist){
                if(it.getNoidung().toLowerCase(Locale.getDefault()).contains(charText)){
                    objects.add(it);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();

                if(constraint != null && constraint.toString().length() > 0){
                    List<bienbao> founded = new ArrayList<bienbao>();
                    for(bienbao item: arraylist){
                        if(item.toString().toLowerCase().contains(constraint)){
                            founded.add(item);
                        }
                    }
                    result.values = founded;
                    result.count = founded.size();
                } else {
                    result.values = arraylist;
                    result.count = arraylist.size();
                }

                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                for (bienbao item : (List<bienbao>) results.values){
                    add(item);
                }
                notifyDataSetChanged();
            }
        };

    }

}
