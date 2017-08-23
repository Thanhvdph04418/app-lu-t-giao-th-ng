package Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vdthero.giaothong.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.CardVideo;

/**
 * Created by Thanh-PC on 7/14/2017.
 */

public class AdapVideo  extends ArrayAdapter<CardVideo> {
    Activity context; int resource; List<CardVideo> objects;ArrayList<CardVideo> arraylist;
    public AdapVideo(Activity context, int resource, List<CardVideo> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
        this.arraylist=new ArrayList<CardVideo>();
        this.arraylist.addAll(objects);
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);
        CardVideo cvideo=this.objects.get(position);
        ImageView img= (ImageView) row.findViewById(R.id.imgvideo);
        TextView txtVdeo1= (TextView) row.findViewById(R.id.txtvideo1);
        TextView txtVdeo2= (TextView) row.findViewById(R.id.txtvideo2);
        txtVdeo1.setText(cvideo.getTieude());
        txtVdeo2.setText(cvideo.getMota());
        Picasso.with(context).load(cvideo.getAnh()).into(img);
        return row;

    }
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        objects.clear();
        if(charText.length() == 0){
            objects.addAll(arraylist);
        } else {
            for (CardVideo it : arraylist){
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
                    List<CardVideo> founded = new ArrayList<CardVideo>();
                    for(CardVideo item: arraylist){
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
                for (CardVideo item : (List<CardVideo>) results.values){
                    add(item);
                }
                notifyDataSetChanged();
            }
        };

    }
}
