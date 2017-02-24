package mlearning.grupolink.com.mlearningandroid.MyCourses;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.entities.rowsFavoriteCourses;
import mlearning.grupolink.com.mlearningandroid.entities.rowsMyCourses;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.ViewHolder> {

    private static final String TAG = "MyCoursesAdapter";
    private Context context;
    private LayoutInflater inflater;
    private List<rowsMyCourses> rows;
    private String cdn;
    private ViewHolder.ClickListener clickListener;

    public MyCoursesAdapter(Context context , List<rowsMyCourses> rows, String cdn , ViewHolder.ClickListener clickListener) {
        this.context = context;
        this.rows = rows;
        this.cdn = cdn;
        this.clickListener = clickListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        if(position== 0){
            viewHolder.lyt_header.setVisibility(View.VISIBLE);
            if(rows.get(position).getProgress() != 1) {
                viewHolder.img_header.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.menu_following));
                viewHolder.txt_header.setText( R.string.text_header_segui );
            }else{
                viewHolder.img_header.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.home_finalizado));
                viewHolder.txt_header.setText( R.string.text_header_final );
            }
        }else {
            viewHolder.lyt_header.setVisibility(View.GONE);
        }


        viewHolder.txt_title.setText(rows.get(position).getName());
        int porcentage = 0 ;
        if(rows.get(position).getProgress() > 0)
            porcentage = ((int) (rows.get(position).getProgress() * 100));
        if(porcentage >0) {
            viewHolder.txt_percentage.setText(porcentage + " %");
            viewHolder.prb_porgress.setProgress(porcentage );
        }else{
            viewHolder.txt_percentage.setText("0%");
            viewHolder.prb_porgress.setProgress(0);
        }

        if(rows.get(position).getProgress() != 1)
            viewHolder.img_diploma.setVisibility(View.GONE);
        else
            viewHolder.img_diploma.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
         return rows.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
        View v = li.inflate(R.layout.my_courses_adapter, viewGroup, false);
        return new ViewHolder(v , clickListener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private ProgressBar prb_porgress;
        private TextView txt_title;
        private TextView txt_percentage;
        private ImageView img_diploma ;
        private LinearLayout lyt_header;
        private ImageView img_header;
        private TextView txt_header;
        private ClickListener listener;

        public ViewHolder(View itemView , ClickListener listener) {
            super(itemView);

            prb_porgress = (ProgressBar) itemView.findViewById(R.id.prb_porgress);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_percentage = (TextView) itemView.findViewById(R.id.txt_percentage);
            img_diploma = (ImageView) itemView.findViewById(R.id.img_diploma);
            lyt_header = (LinearLayout) itemView.findViewById(R.id.lyt_header);
            img_header = (ImageView) itemView.findViewById(R.id.img_header);
            txt_header = (TextView) itemView.findViewById(R.id.txt_header);
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
    public void updateData(List<rowsMyCourses> listRows , String idcdn) {
        Log.e(TAG,"updateData");
        rows.clear();
        rows.addAll(listRows);
        cdn = idcdn;
        notifyDataSetChanged();
    }
}
