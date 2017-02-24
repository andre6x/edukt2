package mlearning.grupolink.com.mlearningandroid.Settings.adapters;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
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
import mlearning.grupolink.com.mlearningandroid.Settings.ItemNotifications;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCountries;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.ViewHolder> {

    private static final String TAG = "CountriesListAdapter";
    private Context context;
    private LayoutInflater inflater;
    private List<ItemNotifications> rows;
    private rowsCountries row;
    private String cdn;
    private int row_index = -1;


    public NotificationsListAdapter(Context context , List<ItemNotifications> rows ) {
        this.context = context;
        this.rows = rows;
        this.cdn = cdn;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.txt_title.setText(rows.get(position).getTitle());
        final int posicion = position;
        viewHolder.linear_layout_c.setId( rows.get(position).getId() );
        viewHolder.linear_layout_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
         return rows.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
        View v = li.inflate(R.layout.custom_notifications, viewGroup, false);
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
