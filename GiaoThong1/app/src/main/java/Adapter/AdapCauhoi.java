package Adapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vdthero.giaothong.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.ItemCauhoi;

/**
 * Created by Thanh-PC on 7/15/2017.
 */

public class AdapCauhoi extends ArrayAdapter<ItemCauhoi> {
     Activity context;  int resource;  List<ItemCauhoi> objects;ArrayList<ItemCauhoi> arraylist;

    public AdapCauhoi(@NonNull Activity context, @LayoutRes int resource, @NonNull List<ItemCauhoi> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
        this.arraylist=new ArrayList<ItemCauhoi>();
        this.arraylist.addAll(objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);
        ItemCauhoi itch=this.objects.get(position);
        ImageView imgcauhoi= (ImageView) row.findViewById(R.id.imgcauhoi);
        TextView txtsocau= (TextView) row.findViewById(R.id.txtsocau);
      final   TextView txtdapan= (TextView) row.findViewById(R.id.txtdapan);
      final   Button btnxem= (Button) row.findViewById(R.id.btnxemdapan);

        Picasso.with(context).load(itch.getAnh()).into(imgcauhoi);
        txtsocau.setText(itch.getSocau());
        txtdapan.setText(itch.getDapan());
        ///animation
       final Animation animation= AnimationUtils.loadAnimation(context,R.anim.up_from_bottom);
        row.startAnimation(animation);


        txtdapan.setVisibility(View.GONE);


            btnxem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(txtdapan.getVisibility()==View.VISIBLE){
                        txtdapan.setVisibility(View.GONE);
                        btnxem.setText("Hiện Đáp Án");
                    }else {
                        txtdapan.setVisibility(View.VISIBLE);
                        btnxem.setText("Ẩn Đáp Án");
                        txtdapan.startAnimation(animation);
                    }

                }
            });








        return row;
    }
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        objects.clear();
        if(charText.length() == 0){
            objects.addAll(arraylist);
        } else {
            for (ItemCauhoi it : arraylist){
                if(it.getSocau().toLowerCase(Locale.getDefault()).contains(charText)){
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
                    List<ItemCauhoi> founded = new ArrayList<ItemCauhoi>();
                    for(ItemCauhoi item: arraylist){
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
                for (ItemCauhoi item : (List<ItemCauhoi>) results.values){
                    add(item);
                }
                notifyDataSetChanged();
            }
        };

    }
}
