package mlearning.grupolink.com.mlearningandroid.Start.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCategory;

public class Adapter extends SelectableAdapter<Adapter.ViewHolder> {
	@SuppressWarnings("unused")
	private static final String TAG = Adapter.class.getSimpleName();

	private static final int TYPE_INACTIVE = 0;
	private static final int TYPE_ACTIVE = 1;

	private static final int ITEM_COUNT = 10;
	private List<rowsCategory> rows;
	private String cdn ;

	private ViewHolder.ClickListener clickListener;

	public Adapter(ViewHolder.ClickListener clickListener, List<rowsCategory> rowsCategory , String cdn) {
		super();
		this.clickListener = clickListener;
		this.rows = rowsCategory;
		this.cdn = cdn;

	}



	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final int layout = viewType == TYPE_INACTIVE ? R.layout.fav_cate_grid_adapter : R.layout.fav_cate_grid_adapter;

		View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
		return new ViewHolder(v, clickListener);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

		holder.txt_title.setText(  rows.get(position).getTitle() );

		String url_img = "";
		if(cdn != null)
			url_img = cdn;
		if(rows.get(position).getFolder() != null)
			url_img = url_img+"/"+rows.get(position).getFolder();
		if(rows.get(position).getUrl_cdn_small() != null)
			url_img = url_img+"/"+rows.get(position).getUrl_cdn_small();
		Utils.loadImage(url_img,holder.img_cover);

		if(isSelected(position) ){
			holder.main_card.setCardBackgroundColor(ContextCompat.getColor((Context) clickListener, R.color.orange_star));
		}else{
			holder.main_card.setCardBackgroundColor(ContextCompat.getColor((Context) clickListener, R.color.white));
		}


	}

	@Override
	public int getItemCount() {
		return rows.size();
	}


	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,	View.OnLongClickListener {
		@SuppressWarnings("unused")
		private static final String TAG = ViewHolder.class.getSimpleName();

		CardView main_card;
		TextView txt_title;
		ImageView img_cover;

		private ClickListener listener;

		public ViewHolder(View itemView, ClickListener listener) {
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
