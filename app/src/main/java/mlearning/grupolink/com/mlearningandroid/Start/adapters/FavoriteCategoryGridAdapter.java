package mlearning.grupolink.com.mlearningandroid.Start.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.List;

import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCategory;

//e
public class FavoriteCategoryGridAdapter extends SelectableAdapter<FavoriteCategoryGridAdapter.ViewHolder> {
    private static final String TAG = "FavoriteCategoryGridAdapter";

    public Context context;
    private List<rowsCategory> rows;
    private String cdn;

    private ViewHolder.ClickListener clickListener;

    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public FavoriteCategoryGridAdapter(Context context , ViewHolder.ClickListener clickListener , List<rowsCategory> rowsCategory , String cdn ) {
        this.context = context;
        this.rows = rowsCategory;
        this.cdn = cdn;
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder myViewHolder, final int position) {

        myViewHolder.txt_title.setText(  rows.get(position).getTitle() );

        String url_img = "";
        if(cdn != null)
            url_img = cdn;
        if(rows.get(position).getFolder() != null)
            url_img = url_img+"/"+rows.get(position).getFolder();
        if(rows.get(position).getUrl_cdn_small() != null)
            url_img = url_img+"/"+rows.get(position).getUrl_cdn_small();

        Utils.loadImage(url_img,myViewHolder.img_cover);

        if(isSelected(position) ){
            myViewHolder.main_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_star));
        }else{
            myViewHolder.main_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }


    }

    @Override
    public int getItemCount() {
         return rows.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
        View v = li.inflate(R.layout.fav_cate_grid_adapter, viewGroup, false);
        return new ViewHolder(v, clickListener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private CardView main_card;
        private TextView txt_title;
        private ImageView img_cover;
        private ClickListener listener;

        public ViewHolder(View itemView , ClickListener listener) {
            super(itemView);
            main_card = (CardView) itemView.findViewById(R.id.main_card);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            img_cover = (ImageView) itemView.findViewById(R.id.img_cover);

            this.listener = listener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                return listener.onItemLongClicked(getLayoutPosition());
            }

            return false;
        }

        public interface ClickListener {
            void onItemClicked(int position);
            boolean onItemLongClicked(int position);
        }
    }




}
