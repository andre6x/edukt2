package mlearning.grupolink.com.mlearningandroid.Start.adapters;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mlearning.grupolink.com.mlearningandroid.Home.activities.MainActivity;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCategory;

public class FavCategoryListAdapter extends RecyclerView.Adapter<FavCategoryListAdapter.ViewHolder> {

    private static final String TAG = "CountriesListAdapter";
    private Context context;
    private LayoutInflater inflater;
    private List<rowsCategory> rows;
    private String cdn;
    private int row_index = -1;


    public FavCategoryListAdapter(Context context , List<rowsCategory> rows, String cdn ) {
        this.context = context;
        this.rows = rows;
        this.cdn = cdn;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        viewHolder.txt_title.setText(  rows.get(position).getTitle() );

        String url_img = "";
        if(cdn != null)
            url_img = cdn;
        if(rows.get(position).getFolder() != null)
            url_img = url_img+"/"+rows.get(position).getFolder();
        if(rows.get(position).getUrl_cdn_small() != null)
            url_img = url_img+"/"+rows.get(position).getUrl_cdn_small();

        Utils.loadImage(url_img,viewHolder.img_icon);

    }

    @Override
    public int getItemCount() {
         return rows.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
        View v = li.inflate(R.layout.fav_cate_list_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_title;
        private ImageView img_icon;
        private CardView card;

        public ViewHolder(View v) {
            super(v);
            txt_title = (TextView) v.findViewById(R.id.txt_title);
            img_icon = (ImageView) v.findViewById(R.id.img_icon);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int requestCode = getAdapterPosition();
                }
            });
        }
    }


}
