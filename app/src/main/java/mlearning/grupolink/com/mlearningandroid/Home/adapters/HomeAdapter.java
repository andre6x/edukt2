package mlearning.grupolink.com.mlearningandroid.Home.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//import mlearning.grupolink.com.mlearningandroid.Home.fragments.HomeFragment;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.activities.LessonActivity;
import mlearning.grupolink.com.mlearningandroid.entities.ICountries;
import mlearning.grupolink.com.mlearningandroid.entities.rows;
import mlearning.grupolink.com.mlearningandroid.entities.rowsHome;
import mlearning.grupolink.com.mlearningandroid.interfaces.RecyclerViewOnClickListenerHack;

/**
 * Created by Andres on 18/01/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>  {

    private String TAG = "HomeAdapter";
    private Context mContext;
    private List<rowsHome> rows ;
    private String cdn;
    private LayoutInflater mLayoutInflater;
    private int roundPixels;
    private float scale;
    private static final int HEADERVIEWITEM = 2;
    private MyViewHolder.ClickListener clickListener;

    public HomeAdapter(Context c, List<rowsHome> rows, String cdn , MyViewHolder.ClickListener clickListener) {
        this.mContext = c;
        this.rows = rows;
        this.cdn = cdn;
        this.clickListener = clickListener;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        scale = mContext.getResources().getDisplayMetrics().density;
        roundPixels = (int)(2 * scale + 0.5f);
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0; //Default is 0
        if (position % 3 == 0) {
            viewType = HEADERVIEWITEM;
        }
        return viewType;
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.MyViewHolder myViewHolder, final int position) {
        //validacion para posicion: fila 1 columnas 1 , fila 2 columnas 2 , fila 3 columnas 1 etc..
        boolean line ;
        if(position % 3 == 0){
            line = true;
        }else
            line = false;
        String url_imgBase = "";
        String url_imgHeader = "";
        String url_imgCover = "";

        switch (myViewHolder.getItemViewType()) {
            case HEADERVIEWITEM:

                if(this.cdn != null)
                    url_imgBase = this.cdn;
                if(rows.get(position).getFolder() != null)
                    url_imgBase = url_imgBase+"/"+rows.get(position).getFolder();
                //IMAGEN NORMAL
                if (rows.get(position).getUrl_cdn_large() != null)
                    url_imgCover = url_imgBase + "/" + rows.get(position).getUrl_cdn_large();
                Utils.loadImage(url_imgCover, myViewHolder.img_cover  );
                //IMAGEN ICONO HEADER
                if (rows.get(position).getIcono() != null)
                    url_imgHeader = url_imgBase + "/" + rows.get(position).getIcono() ;
                Utils.loadImage(url_imgHeader, myViewHolder.img_header  );

                myViewHolder.txt_title.setText( rows.get(position).getName() );
                myViewHolder.txt_sub_title.setText( rows.get(position).getDescription() );
                myViewHolder.txt_title_header.setText(rows.get(position).getTitle2() );



                break;

            default:

                if(this.cdn != null)
                    url_imgBase = this.cdn;
                if(rows.get(position).getFolder() != null)
                    url_imgBase = url_imgBase+"/"+rows.get(position).getFolder();
                //IMAGEN NORMAL
                if (rows.get(position).getUrl_cdn_large() != null)
                    url_imgCover = url_imgBase + "/" + rows.get(position).getUrl_cdn_large();
                Utils.loadImage(url_imgCover, myViewHolder.img_cover  );
                //IMAGEN ICONO HEADER
                if (rows.get(position).getIcono() != null)
                    url_imgHeader = url_imgBase + "/" + rows.get(position).getIcono() ;
                Utils.loadImage(url_imgHeader, myViewHolder.img_header  );

                myViewHolder.txt_title.setText( rows.get(position).getName() );
                myViewHolder.txt_sub_title.setText( rows.get(position).getDescription() );
                myViewHolder.txt_title_header.setText(rows.get(position).getTitle2() );



                break;
        }

        //dividir la pantalla cuando sea true line
        // Span the item if active
        final ViewGroup.LayoutParams lp = myViewHolder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;
            sglp.setFullSpan(line /*item.isActive()*/);
            myViewHolder.itemView.setLayoutParams(sglp);
        }


    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        switch (viewType) {
            case HEADERVIEWITEM:
                v = mLayoutInflater.inflate(R.layout.custom_list_home_large, viewGroup, false);
                HomeAdapter.MyViewHolder mvh = new HomeAdapter.MyViewHolder(v, clickListener);
                return mvh ;
            default:
                v = mLayoutInflater.inflate(R.layout.custom_list_home_small, viewGroup, false);
                HomeAdapter.MyViewHolder mvh2 = new HomeAdapter.MyViewHolder(v, clickListener);
                return mvh2;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public ImageView img_cover;
        public TextView txt_title;
        public TextView txt_sub_title;
        private TextView txt_title_header;
        public ImageView img_header;
        private ClickListener listener;

        public MyViewHolder(View itemView , ClickListener listener) {
            super(itemView);
            img_cover = (ImageView) itemView.findViewById(R.id.img_cover);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_sub_title = (TextView) itemView.findViewById(R.id.txt_sub_title);
            txt_title_header = (TextView) itemView.findViewById(R.id.txt_title_header);
            img_header = (ImageView) itemView.findViewById(R.id.img_header);
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
    public void updateData(List<rowsHome> listRows , String idcdn) {
        Log.e(TAG,"updateData");
        rows.clear();
        rows.addAll(listRows);
        cdn = idcdn;
        notifyDataSetChanged();
    }
}
