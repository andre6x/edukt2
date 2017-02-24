package mlearning.grupolink.com.mlearningandroid.Settings.adapters;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import mlearning.grupolink.com.mlearningandroid.Home.activities.MainActivity;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCountries;


import java.util.List;

public class CountriesListAdapter extends RecyclerView.Adapter<CountriesListAdapter.ViewHolder> {

    private static final String TAG = "CountriesListAdapter";
    private Context context;
    private LayoutInflater inflater;
    private List<rowsCountries> rows;
    private rowsCountries row;
    private String cdn;
    private int row_index = -1;


    public CountriesListAdapter(Context context , List<rowsCountries> rows, String cdn ) {
        this.context = context;
        this.rows = rows;
        this.cdn = cdn;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.txt_title.setText(rows.get(position).getNombre());


        String url_img = "";
        if(this.cdn != null)
            url_img = this.cdn;
        if(rows.get(position).getUrl_flag() != null)
            url_img = url_img+rows.get(position).getUrl_flag();
        Utils.loadImage(url_img, (viewHolder.img_cover));


        final int posicion = position;
        viewHolder.linear_layout_c.setId( rows.get(position).getCod_servicio() );
        viewHolder.linear_layout_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.countrySelectCLICK = ""+ v.getId();
                row_index=posicion;
                notifyDataSetChanged();
            }
        });
        if(row_index==position){
            viewHolder.linear_layout_c.setBackgroundColor(ContextCompat.getColor(context, R.color.greenSetting));
            //holder.tv1.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            viewHolder.linear_layout_c.setBackgroundColor(ContextCompat.getColor(context, R.color.silver_expandle));
            //holder.tv1.setTextColor(Color.parseColor("#000000"));
        }


    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
        viewHolder.itemView.clearAnimation();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder viewHolder) {
        super.onViewAttachedToWindow(viewHolder);
       // animateCircularReveal(viewHolder.itemView);
    }


    @Override
    public int getItemCount() {
         return rows.size();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
        View v = li.inflate(R.layout.custom_countries, viewGroup, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_title;
        private ImageView img_cover;
        private CardView card;
        private LinearLayout linear_layout_c;

        public ViewHolder(View v) {
            super(v);
            txt_title = (TextView) v.findViewById(R.id.txt_title);
            img_cover = (ImageView) v.findViewById(R.id.img_cover);
            linear_layout_c = (LinearLayout) v.findViewById(R.id.linear_layout_c);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int requestCode = getAdapterPosition();
                }
            });


        }
    }


}
