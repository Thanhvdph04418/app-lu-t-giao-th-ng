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
import android.widget.TextView;

import com.vdthero.giaothong.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.coban;

/**
 * Created by Thanh-PC on 7/12/2017.
 */

public class Adap1 extends ArrayAdapter<coban>  {
    Activity context;
    int resource;
    List<coban> objects;
    ArrayList<coban> arraylist;
    public Adap1(Activity context, int resource, List<coban> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.arraylist=new ArrayList<coban>();
        this.arraylist.addAll(objects);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        TextView txtTieude = (TextView) row.findViewById(R.id.txt1);
        coban cb = this.objects.get(position);
        txtTieude.setText(cb.getTieude());
        ///animatiom
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
            for (coban it : arraylist){
                if(it.getTieude().toLowerCase(Locale.getDefault()).contains(charText)){
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
                    List<coban> founded = new ArrayList<coban>();
                    for(coban item: arraylist){
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
                for (coban item : (List<coban>) results.values){
                    add(item);
                }
                notifyDataSetChanged();
            }
        };

    }

}
