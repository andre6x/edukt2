package mlearning.grupolink.com.mlearningandroid.Home.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mlearning.grupolink.com.mlearningandroid.CategoriesCourses.CategoriesCoursesActivity;
import mlearning.grupolink.com.mlearningandroid.FavoriteCourses.FavoriteCoursesActivity;
import mlearning.grupolink.com.mlearningandroid.Home.activities.MainActivity;
import mlearning.grupolink.com.mlearningandroid.Home.ItemMenu;
import mlearning.grupolink.com.mlearningandroid.Information.InformationActivity;
import mlearning.grupolink.com.mlearningandroid.MlearningApplicattion;
import mlearning.grupolink.com.mlearningandroid.MyCourses.MyCoursesActivity;
import mlearning.grupolink.com.mlearningandroid.Profile.ProfileActivity;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Settings.activities.SettingsActivity;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.constants.Config;
import mlearning.grupolink.com.mlearningandroid.entities.ICategories;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCategory;
import mlearning.grupolink.com.mlearningandroid.topTen.TopTenActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlidingMenuFragment extends Fragment {
	RecyclerView recyclerView;
	View rootView;
	LinearLayout containerMenu;

	//INCLUDE
	private LinearLayout linear_loading;
	private ProgressBar progress;
	private Button retry;

	private Call<ICategories.Category> call_0;
	private boolean isDestroyed = false;
	private static  final String TAG = "SlidingMenuFragment";

	public SlidingMenuFragment() {
		// Empty constructor required
	}

	public static SlidingMenuFragment newInstance() {
		return new SlidingMenuFragment();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fargment_sliding_menu, parent, false);


		// Instantiate image view object of the user avatar and attaching onClick listener to it.
		LinearLayout userAvatar = (LinearLayout) rootView.findViewById(R.id.menu_header);
		userAvatar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				callActivity(-1, 0 , "");
				((MainActivity)getActivity()).toggleMenu();
			}
		});

		formarListasPrimerNivel ();

		//si es falso no ah obtenido la data del json y vuelve consumir
		if (! MainActivity.FLAG_FEED ) {
			//false para q no muestre los botones de retry , loading, por qno estan instanciado aun
			restartLoading(false);
		}

		return rootView;
	}

	public void formarListasPrimerNivel (){

		containerMenu = (LinearLayout) rootView.findViewById(R.id.linear_listview_menu);
		containerMenu.removeAllViews();

		for (int i = 0; i < getData_Curso().size(); i++) {

			final int position = i ;
			Boolean isExpandableFlecha = false;
			final Boolean[] isTorneosExpanded = {false};
			View convertView;
			ImageView flechaFirstLevel = null;
			LinearLayout lytSecondLevel = null;
			LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			//tipo 1 cursos ,tipo 2  normal, tipo 3 favorito, tipo 4 submenu
			if ( getData_Curso().get(i).getTipo() == 1 ) {
				 convertView = inflater.inflate(R.layout.menu_row_white, null);
			}else { //tipo 2
				convertView = inflater.inflate(R.layout.menu_row, null);

				lytSecondLevel = (LinearLayout)convertView.findViewById(R.id.lytSecondLevel);
				flechaFirstLevel = (ImageView) convertView.findViewById(R.id.img_arrow);
				TextView title = (TextView) convertView.findViewById(R.id.title);

				if (getData_Curso().get(i).getTipo() == 2 )
					flechaFirstLevel.setBackgroundResource(R.mipmap.arrow_right_green);
				if (getData_Curso().get(i).getTipo() == 3 ) {
					flechaFirstLevel.setBackgroundResource(R.mipmap.arrow_right_magenta);
					title.setTextColor(getResources().getColor(R.color.purpleMenu));
				}
				if (getData_Curso().get(i).getTipo() == 4 ) {
					flechaFirstLevel.setBackgroundResource(R.mipmap.arrow_dropdown_green);
					isExpandableFlecha = true ;
				}

			}

			LinearLayout botonFlechaFirstLevel = (LinearLayout) convertView.findViewById(R.id.contenedorGrupos);
			TextView group = (TextView) convertView.findViewById(R.id.title);
			ImageView image_group = (ImageView) convertView.findViewById(R.id.icon);
			//NOMBRE GRUPO
			group.setText(getData_Curso().get(i).getTitle());
			image_group.setImageResource(getData_Curso().get(i).getIconRes());

			final int idMenu = getData_Curso().get(i).getId();
			containerMenu.addView(convertView);

			final Boolean finalIsExpandableFlecha = isExpandableFlecha;
			final ImageView finalFlechaFirstLevel = flechaFirstLevel;
			final LinearLayout finalLytSecondLevel = lytSecondLevel;
			botonFlechaFirstLevel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					//abre el submenu
					if (finalIsExpandableFlecha == true) {

						if (isTorneosExpanded[0] == true) {
							finalFlechaFirstLevel.setBackgroundResource(R.mipmap.arrow_dropdown_green);
							finalLytSecondLevel.setVisibility(View.GONE);
							isTorneosExpanded[0] = false;

						} else {
							finalFlechaFirstLevel.setBackgroundResource(R.mipmap.arrow_up_green);

							//formarListasSegundoNivel
							formarListasSegundoNivel(getData_Curso().get(position).getId() , finalLytSecondLevel);
							isTorneosExpanded[0] = true;
						}
					} else {

						Toast.makeText(getContext(), "Item Num: " + idMenu + " Clicked", Toast.LENGTH_SHORT).show();
						callActivity(idMenu, 0, "");
						// Hide the menu when item clicked
						((MainActivity)getActivity()).toggleMenu();

					}



				}
			});
		}
	}

	public void formarListasSegundoNivel(final int id, LinearLayout secondLevel) {
		secondLevel.removeAllViews();
		secondLevel.setVisibility(View.VISIBLE);

		//si ahi datos en el json consumo metod postexecute
		if ( MainActivity.FLAG_FEED ){

			//MainActivity.mCategories.getRows() +""+ MainActivity.mCategories.getCdn() ;
			int sixe = MainActivity.mCategories.getRows().size() ;
			for (int i= 0 ; i < sixe ; i++){

				LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View convertView = inflater.inflate(R.layout.fragment_navigation_second_level, null);

				LinearLayout botonSecondLevel = (LinearLayout) convertView.findViewById(R.id.contenedorGrupos);
				TextView expandedListTextView = (TextView) convertView.findViewById(R.id.title);
				ImageView teamImage = (ImageView) convertView.findViewById(R.id.icon);


				String url_img = "";
				if(MainActivity.mCategories.getCdn() != null)
					url_img = MainActivity.mCategories.getCdn();
				if(MainActivity.mCategories.getRows().get(i).getFolder() != null)
					url_img = url_img+"/"+MainActivity.mCategories.getRows().get(i).getFolder();
				if(MainActivity.mCategories.getRows().get(i).getUrl_cdn_small() != null)
					url_img = url_img+"/"+MainActivity.mCategories.getRows().get(i).getUrl_cdn_small();
				Utils.loadImage(url_img,teamImage);
				final String titleCategory = MainActivity.mCategories.getRows().get(i).getTitle();
				expandedListTextView.setText( titleCategory );

				final int idCategory = MainActivity.mCategories.getRows().get(i).getIdmed_categories();
				botonSecondLevel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						callActivity(-2 , idCategory , titleCategory);
						// Hide the menu when item clicked
						((MainActivity)getActivity()).toggleMenu();

					}
				});

				secondLevel.addView(convertView);

			}

		}else{
			LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View convertView = inflater.inflate(R.layout.fragment_navigation_third_level, null);

			linear_loading = (LinearLayout) convertView.findViewById(R.id.linear_loading);
			progress = (ProgressBar) convertView.findViewById(R.id.progress);
			retry = (Button) convertView.findViewById(R.id.retry);

			retry.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//true para mostrar los botones de retry , loading, instanciado en layout.fragment_navigation_third_level
					restartLoading(true);
				}
			});

			showRetry(true);

			secondLevel.addView(convertView);
		}
	}


	private List<ItemMenu> getData() {
		List<ItemMenu> menuList = new ArrayList<>();
		menuList.add(new ItemMenu("HOME", R.mipmap.menu_home));
		menuList.add(new ItemMenu("CURSOS FAVORITOS", R.mipmap.menu_favorites));
		menuList.add(new ItemMenu("MIS CURSOS", R.mipmap.menu_cursos));
		menuList.add(new ItemMenu("TOP TEN", R.mipmap.menu_topten));
		menuList.add(new ItemMenu("CATEGORÍAS", R.mipmap.menu_categories));
		menuList.add(new ItemMenu("EL RETO", R.mipmap.menu_reto));
		menuList.add(new ItemMenu("CONFIGURACIONES", R.mipmap.menu_configurations));
		menuList.add(new ItemMenu("INFORMACIÓN", R.mipmap.menu_information));

		return menuList;
	}

	//tipo 1 cursos ,tipo 2  normal, tipo 3 favorito, tipo 4 submenu
	private List<ItemMenu> getData_Curso() {
		List<ItemMenu> menuList = new ArrayList<>();
		menuList.add(new ItemMenu("5 Diplomas obtenidos",    R.mipmap.menu_diploma 	 , 1 , 1));
		menuList.add(new ItemMenu("2 Cursos en seguimiento", R.mipmap.menu_following , 2 , 1));

		menuList.add(new ItemMenu("HOME",             R.mipmap.menu_home 		   , 3 , 2));
		menuList.add(new ItemMenu("CURSOS FAVORITOS", R.mipmap.menu_favorites      , 4 , 3));
		menuList.add(new ItemMenu("MIS CURSOS",       R.mipmap.menu_cursos 		   , 5 , 2));
		menuList.add(new ItemMenu("TOP TEN",          R.mipmap.menu_topten    	   , 6 , 2));
		menuList.add(new ItemMenu("CATEGORÍAS",       R.mipmap.menu_categories 	   , 7 , 4));
		menuList.add(new ItemMenu("EL RETO", 		  R.mipmap.menu_reto 		   , 8 , 2));
		menuList.add(new ItemMenu("CONFIGURACIONES",  R.mipmap.menu_configurations , 9 , 2));
		menuList.add(new ItemMenu("INFORMACIÓN",      R.mipmap.menu_information    ,10 , 2));

		return menuList;
	}



	public void callActivity (int idMenu ,int  idCategory , String titleCategory){
		Log.e("Posicion", "--posicionfragment0:" + idMenu);
		Intent i = null;
		switch (idMenu) {

			case -1: // perfil
				i = new Intent(this.getActivity(),ProfileActivity.class);
				break;
			case -2: // Categorias Cursos
				i =  new Intent(getActivity(),CategoriesCoursesActivity.class);
				i.putExtra("idCategory",idCategory);
				i.putExtra("titleCategory",titleCategory);
				break;
			case 1: // Posiciones
				//i = new Intent(this.getActivity(),TablaPosicionActivities.class);
				break;
			case 2: //
				/*i =  new Intent(getActivity(),V2j_EquipoFavoritoActivity.class);
				i.putExtra("alertas","N");
				i.putExtra("cambiarEquipo",true);
				i.putExtra("main", true);
				i.putExtra("goneButtons", "S");*/
				break;
			case 3: //
				//i = new Intent(this.getActivity(),CabinaClaro3Activity.class);
				break;
			case 4: // CURSOS FAVORITOS
				i = new Intent(this.getActivity(),FavoriteCoursesActivity.class);
				break;
			case 5: // MIS CURSO
				i = new Intent(this.getActivity(),MyCoursesActivity.class);
				break;
			case 6: // Top Ten
				i = new Intent(this.getActivity(),TopTenActivity.class);
				break;
			case 7: //

				break;
			case 8: //

				break;
			case 9: //configuraciones
				i = new Intent(this.getActivity(),SettingsActivity.class);
				break;
			case 10:  // informacion
				i = new Intent(this.getActivity(),InformationActivity.class);
				break;
			default:
				break;
		}
		if(i!=null){
			startActivity(i);
        }

	}

	// Recycler view touch lister class
	class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
		private GestureDetector gestureDetector;
		private ClickListener clickListener;

		public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {

			this.clickListener = clickListener;

			gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
				@Override
				public boolean onSingleTapUp(MotionEvent e) {
					return true;
				}

				@Override
				public void onLongPress(MotionEvent e) {
					View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
					if (child != null && clickListener != null){
						clickListener.onLongClick(child, recyclerView.getChildPosition(child));
					}
				}
			});
		}

		@Override
		public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
			View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
			if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)){
				clickListener.onClick(child, rv.getChildPosition(child));
			}
			return false;
		}

		@Override
		public void onTouchEvent(RecyclerView rv, MotionEvent e) {

		}

		@Override
		public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

		}
	}

	public interface ClickListener {
		public void onClick(View view, int position);

		public void onLongClick(View view, int position);
	}


	private void restartLoading(boolean isVisible) {
		showLoading(isVisible);
		String CountryCode= "";
		if(Utils.getCountryCode(getActivity()) != null)
			CountryCode = Utils.getCountryCode(getActivity());
		restartLoadingCategories(CountryCode , isVisible);

	}

	private void restartLoadingCategories(String CountryCode , final boolean isVisible){
		Log.e(TAG, getString(R.string.base_path)+"getCategories?cod_servicio=" + CountryCode +  "&limit=" + Config.limit + "&offset=" + Config.offset);
		ICategories posiciones = MlearningApplicattion.getApplication().getRestAdapter().create(ICategories.class);
		call_0 = posiciones.getCategoryFrom(CountryCode, Config.limit, Config.offset);
		call_0.enqueue(new Callback<ICategories.Category>() {
			@Override
			public void onResponse(Call<ICategories.Category> call, Response<ICategories.Category> response) {
				if (response.isSuccess()) {
					Log.e(TAG, "Respuesta exitosa");
					MainActivity.mCategories = response.body();
					try {
						if (MainActivity.mCategories != null)
							if (MainActivity.mCategories.getSummary().getCdn() != null)
								MainActivity.mCategories.setCdn(MainActivity.mCategories.getSummary().getCdn());
						if (MainActivity.mCategories.getRows() != null)
							if (MainActivity.mCategories.getRows().size() > 0)
								MainActivity.mCategories.setNum_rows(MainActivity.mCategories.getRows().size());
						MlearningApplicattion.getApplication().CleanCategories();
						//Utils.DeleteCategories(MlearningApplicattion.getApplication().getCategoriesDao());
						Utils.DbsaveCategories(MainActivity.mCategories, MlearningApplicattion.getApplication().getCategoriesDao());
						if (MainActivity.mCategories.getRows() != null)
							if (MainActivity.mCategories.getRows().size() > 0) {
								MlearningApplicattion.getApplication().CleanRowsCategory();
								for (rowsCategory row : MainActivity.mCategories.getRows()) {
									if (!(MlearningApplicattion.getApplication().getRowsCategoryDao().queryBuilder().where().eq("idmed_categories", row.getIdmed_categories()).query().size() > 0))
										Utils.DbsaveRowsCategoryCategory(row, MlearningApplicattion.getApplication().getRowsCategoryDao());
								}
							}

						if (Utils.GetCategoriesFromDatabase(MlearningApplicattion.getApplication().getCategoriesDao()).size() > 0)
							MainActivity.mCategories = Utils.GetCategoriesFromDatabase(MlearningApplicattion.getApplication().getCategoriesDao(), MlearningApplicattion.getApplication().getRowsCategoryDao()).get(0);

					} catch (Exception e) {
						e.printStackTrace();
					}

					Postexecute(isVisible);

				} else {
					showRetry(isVisible);
					Log.e(TAG, "Error en la petición");
				}
			}

			@Override
			public void onFailure(Call<ICategories.Category> call, Throwable t) {
				Log.e(TAG, "Error en la petición onFailure");
				showRetry(isVisible);
				t.printStackTrace();
			}
		});
	}


	public  void Postexecute(boolean isVisible){

		if(MainActivity.mCategories != null){
			if(MainActivity.mCategories.getCode() == 0){
				if(MainActivity.mCategories.getNum_rows() > 0){

					if(MainActivity.mCategories.getRows() != null){
						if(MainActivity.mCategories.getRows().size() > 0){

							MainActivity.FLAG_FEED = true ;
							showLayout(isVisible);
							formarListasPrimerNivel();

						}else
							showRetry(isVisible);

					}else
						showRetry(isVisible);
				}else
					showRetry(isVisible);
			}else
				showRetry(isVisible);
		}else
			showRetry(isVisible);

	}


	private void showLayout(boolean visible) {

		if (getActivity() != null)
			if(visible)
				linear_loading.setVisibility(View.GONE);
	}

	private void showLoading(boolean visible) {

		if (getActivity() != null)
			if(visible) {
				linear_loading.setVisibility(View.VISIBLE);
				progress.setVisibility(View.VISIBLE);
				retry.setVisibility(View.GONE);
			}
	}

	private void showRetry(boolean visible) {

		if (getActivity() != null)
			if(visible){
				linear_loading.setVisibility(View.VISIBLE);
				progress.setVisibility(View.GONE);
				retry.setVisibility(View.VISIBLE);
			}
	}


}
