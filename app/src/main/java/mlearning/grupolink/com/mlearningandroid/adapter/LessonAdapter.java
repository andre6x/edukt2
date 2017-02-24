package mlearning.grupolink.com.mlearningandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.activities.DetalleLessonActivity;
import mlearning.grupolink.com.mlearningandroid.entities.rowsLesson;

/**
 * Created by Andres on 23/12/2016.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {

    private Context context;
    private List<rowsLesson> rows;
    private String cdn;
    private LayoutInflater mLayoutInflater;

    public int position_current;
    private static final int HEADERVIEWITEM = 2;

    public LessonAdapter(Context c, List<rowsLesson> rows, String cdn) {
        this.context = c;
        this.rows = rows;
        this.cdn = cdn;

        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        FoundCurrent();
    }

    public int getPosition_current() {
        return position_current;
    }

    public void setPosition_current(int position_current) {
        this.position_current = position_current;
    }

    public void FoundCurrent()
    {
        rowsLesson row;
        setPosition_current(-1);

        if(this.rows != null)
            if(this.rows.size() > 0)
            {
                for(int x = 0 ; x < this.rows.size() ; x++)
                {
                    row = this.rows.get(x);
                    Log.e("TAG", "ESTADO ADAPTADPRD: " + row.getCurrent_status()+"  QUIZ: "+row.getHas_quiz_mandatory());
                    if(row.getCurrent_status() == -1){
                        setPosition_current(x); break;}
                    else if((row.getCurrent_status() == 0) && (row.getHas_quiz_mandatory() == 1)){
                        setPosition_current(x);break;}
                    else if((row.getCurrent_status() == 1) && (row.getHas_quiz_mandatory() == 1)){
                        setPosition_current(x); break;}
                }
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
    public int getItemViewType(int position) {
        int viewType = 0; //Default is 0
        if (position == getPosition_current() ) {
            viewType = HEADERVIEWITEM;
        }
        return viewType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v;

        switch (viewType) {
            case HEADERVIEWITEM:
                v = mLayoutInflater.inflate(R.layout.custom_lesson_current, viewGroup, false);
                ViewHolder mvh = new ViewHolder(v);
                return mvh;
            default:
                v = mLayoutInflater.inflate(R.layout.custom_lesson, viewGroup, false);
                ViewHolder mvh2 = new ViewHolder(v);
                return mvh2;
        }
    }

    @Override
    public void onBindViewHolder(LessonAdapter.ViewHolder myViewHolder, final int position) {


        try {

            switch (myViewHolder.getItemViewType()) {
                case HEADERVIEWITEM:
                        ////

                    break;

                default:
                    /*if(rows.get(position).getCurrent_status() == -1)
                        myViewHolder.imageView.setVisibility(View.VISIBLE);
                    else {
                        myViewHolder.imageView.setImageResource(R.mipmap.right);
                        myViewHolder.imageView.setBackgroundResource(R.color.transparent);
                    }*/

                    break;
            }

            myViewHolder.txtCountView.setText(convertTwoDigit(position));
            if(rows.get(position).getDescription() != null)
                myViewHolder.txtnameView.setText( rows.get(position).getDescription() );

            myViewHolder.main_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, DetalleLessonActivity.class);

                    Bundle bundle = new Bundle();
                    //bundle.putSerializable("car", rows.get(position) ) ;
                    bundle.putInt("idLeccion", rows.get(position).getIdmed_lesson() );
                    bundle.putInt("idCourse", rows.get(position).getIdmed_courses() );
                    intent.putExtras(bundle);

                    context.startActivity(intent);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView txtnameView;
        private ImageView imageView;
        private TextView txtCountView;
        private CardView main_card;

        public ViewHolder(View v) {
            super(v);

            main_card = (CardView) v.findViewById(R.id.main_card);
            txtCountView = (TextView) v.findViewById(R.id.txt_count);
            imageView = (ImageView) v.findViewById(R.id.img_lock);
            txtnameView = (TextView) v.findViewById(R.id.txt_name);


        }

        @Override
        public void onClick(View v) {

        }
    }

    private String convertTwoDigit(int position)
    {
        position = position + 1;
        if(position < 10)
            return ""+position;
        else
            return ""+position;
    }

}
