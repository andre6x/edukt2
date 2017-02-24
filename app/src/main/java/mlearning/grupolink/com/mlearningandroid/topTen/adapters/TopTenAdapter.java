package mlearning.grupolink.com.mlearningandroid.topTen.adapters;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCountries;
import mlearning.grupolink.com.mlearningandroid.entities.rowsTopTen;

public class TopTenAdapter extends RecyclerView.Adapter<TopTenAdapter.ViewHolder> {

    private static final String TAG = "TopTenAdapter";
    private Context context;
    private LayoutInflater inflater;
    private List<rowsTopTen> rows;
    private String cdn;
    private ViewHolder.ClickListener clickListener;

    public TopTenAdapter(Context context , List<rowsTopTen> rows, String cdn , ViewHolder.ClickListener clickListener) {
        this.context = context;
        this.rows = rows;
        this.cdn = cdn;
        this.clickListener = clickListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.txt_title.setText(rows.get(position).getName());
        viewHolder.txt_sub_title.setText(rows.get(position).getDescription());

        String url_img = "";
        if(this.cdn != null)
            url_img = this.cdn;
        if(rows.get(position).getFolder() != null)
            url_img = url_img+"/"+rows.get(position).getFolder();
        if (rows.get(position).getUrl_cdn_small() != null)
            url_img = url_img+"/"+rows.get(position).getUrl_cdn_large();
        Utils.loadImage(url_img, viewHolder.img_cover );


        if (rows.get(position).getRanking() != null){
            if(rows.get(position).getRanking().length() > 0){
                viewHolder.ratingBar.setRating(0);
                try {
                    viewHolder.ratingBar.setRating(Float.parseFloat(rows.get(position).getRanking()));
                }catch (NumberFormatException rfd){
                    rfd.printStackTrace();
                }
            }else {
                viewHolder.txt_evaluaciones.setText(context.getString(R.string.txt_no_hay_evaluaciones));
                viewHolder.ratingBar.setVisibility(View.GONE);
            }
            viewHolder.txt_evaluaciones.setText(context.getString(R.string.txt_no_hay_evaluaciones));
            if(rows.get(position).getSubscribers() != null)
                if(rows.get(position).getSubscribers().length() > 0){
                    try {
                        int ev = Integer.parseInt(rows.get(position).getSubscribers());
                        if (ev == 1)
                            viewHolder.txt_evaluaciones.setText("("+ev+" evaluación)");
                        else
                            viewHolder.txt_evaluaciones.setText("("+ev+" evaluaciones)");
                    }catch (NumberFormatException rfd){
                        rfd.printStackTrace();
                    }
                }
        }else {
            viewHolder.txt_evaluaciones.setText(context.getString(R.string.txt_no_hay_evaluaciones));
            viewHolder.ratingBar.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
         return rows.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
        View v = li.inflate(R.layout.top_ten_adapter, viewGroup, false);
        return new ViewHolder(v , clickListener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private TextView txt_title;
        private TextView txt_sub_title;
        private TextView txt_evaluaciones;
        private ImageView img_cover;
        private TextView txt_title_header;
        private ImageView img_header;
        private RatingBar ratingBar;
        private ImageView  img_header_right;
        private CardView card;
        private LinearLayout linear_layout_c;
        private ClickListener listener;

        public ViewHolder(View itemView , ClickListener listener) {
            super(itemView);
            txt_title        = (TextView)  itemView.findViewById(R.id.txt_title);
            txt_sub_title    = (TextView)  itemView.findViewById(R.id.txt_sub_title);
            txt_evaluaciones = (TextView)  itemView.findViewById(R.id.txt_evaluaciones);
            ratingBar        = (RatingBar) itemView.findViewById(R.id.ratingBar);
            img_cover        = (ImageView) itemView.findViewById(R.id.img_cover);
            txt_title_header = (TextView)  itemView.findViewById(R.id.txt_title_header);
            img_header       = (ImageView) itemView.findViewById(R.id.img_header);
            img_header_right = (ImageView) itemView.findViewById(R.id.img_header_right);
            linear_layout_c  = (LinearLayout) itemView.findViewById(R.id.linear_layout_c);

            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(v,getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                return listener.onItemLongClicked(v,getLayoutPosition());
            }
            return false;
        }

        public interface ClickListener {
            void onItemClicked(View v, int position);
            boolean onItemLongClicked(View v, int position);
        }
    }

    //actulizar informacion del adapter
    public void updateData(List<rowsTopTen> listRows , String idcdn) {
        Log.e(TAG,"updateData");
        rows.clear();
        rows.addAll(listRows);
        cdn = idcdn;
        notifyDataSetChanged();
    }
}
