package mlearning.grupolink.com.mlearningandroid.Settings.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Settings.fragments.CountriesFragment;
import mlearning.grupolink.com.mlearningandroid.Settings.fragments.DesvinculaTelefono;
import mlearning.grupolink.com.mlearningandroid.Settings.fragments.NotificationsFragment;
import mlearning.grupolink.com.mlearningandroid.Settings.fragments.SuscriberFragment;
import mlearning.grupolink.com.mlearningandroid.Start.activities.FavoriteCategoryActivity;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;

/**
 * Created by User on 13/04/2015.
 */
public class SettingsActivity extends AppCompatActivity {

    private static SettingsActivity mContext;
    private Map<String, List<String>> map;
    private int lastExpandedPosition = -1;
    public int idCourse = 0;
    private String inicio ="";
    public  String CountryCode ;
    private  ExpandableListView expandableListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idCourse = extras.getInt("idCourse");
            inicio = extras.getString("inicio");
        }

        expandableListView = (ExpandableListView) findViewById(R.id.group_listview);
        map = GetGroupMap();
        SettingAdapter adapter = new SettingAdapter(this,map);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        Utils.SetStyleToolbarTitle(this, getString(R.string.txt_configuraciones));
        try{
            Utils.sendGoogleAnalyticsTracker("configuraciones", "");
        }catch (Exception e){e.printStackTrace();}

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }

    public void callCategoriesFavorites(){

    }

    public Map<String,List<String>> GetGroupMap()  {
        Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();

        List<String> cabecera = new ArrayList<String>();
        List<String> desvincular_telefono = new ArrayList<String>();
        List<String> notificaciones = new ArrayList<String>();
        List<String> categorias = new ArrayList<String>();

        cabecera.add("cabecera");
        desvincular_telefono.add("Validar Número");
        notificaciones.add("Validar Notificaciones");
        categorias.add(getString(R.string.category_fav) );

        map.put("cabecera",cabecera);
        map.put(getString(R.string.subscribe), desvincular_telefono );
        map.put(getString(R.string.notificaciones), notificaciones);
        map.put(getString(R.string.category_fav) ,categorias);

        return map;
    }


    public class SettingAdapter extends BaseExpandableListAdapter {

        private LayoutInflater mInflater;
        private Map<String, List<String>> classifications;
        private Context context;

        private Button button_ecuador, button_argentina, button_mexico, button_chile, button_nicaragua, button_salvador;
        private LinearLayout layout_select;

        public SettingAdapter(Context context, Map<String, List<String>> children) {
            this.context = context;
            this.classifications = children;
            mInflater = LayoutInflater.from(context);
        }

        public void setItems(Map<String, List<String>> children) {
            this.classifications = children;
            notifyDataSetChanged();
        }

        public boolean areAllItemsEnabled() {
            return true;
        }

        public Object getChild(int groupPosition, int childPosition) {
            return classifications.get(getGroup(groupPosition)).get(childPosition);
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return classifications.get(getGroup(groupPosition)).size();
        }

        public String getGroup(int groupPosition) {
            Iterator<String> keySet = classifications.keySet().iterator();
            int index = 0;
            String group = null;
            do {
                group = keySet.next();
            } while (keySet.hasNext() && groupPosition != index++);
            return group;
        }

        public int getGroupCount() {
            return classifications.size();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getChildView(int groupPosition, int position, boolean isLastChild, View convertView, ViewGroup parent) {
            String item = ((String) getChild(groupPosition, position));

            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (item.equals("Validar Número")) {
                convertView = mInflater.inflate(R.layout.fragment_suscripcion, parent, false);
                callFragmentDesvincularTelefono();

            } else if (item.equals("Validar Notificaciones") ) {
                convertView = mInflater.inflate(R.layout.fragment_suscripcion, parent, false);
                callFragmentNotifications();

            }

            return convertView;
        }


        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            String group = (String) getGroup(groupPosition);
            holder = new GroupHolder();
            if (group.equals("cabecera") )  {

                    convertView = mInflater.inflate(R.layout.setting_header, parent, false);
                    convertView.setTag(holder);

            }else {
                    //holder = new GroupHolder();
                    convertView = mInflater.inflate(R.layout.expandable_list_setting_group, parent, false);

                    holder.lyt_contenedor = (LinearLayout) convertView.findViewById(R.id.lyt_contenedor);
                    holder.main_card = (CardView) convertView.findViewById(R.id.main_card);
                    holder.group = (TextView) convertView.findViewById(R.id.grupoName);
                    holder.flag = (ImageView) convertView.findViewById(R.id.img_arrow_right);
                    holder.lyt_footer = (LinearLayout) convertView.findViewById(R.id.lyt_footer);
                    holder.lyt_icon = (LinearLayout) convertView.findViewById(R.id.lyt_icon);
                    holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
                    convertView.setTag(holder);

                holder.lyt_contenedor.setClickable(false);
                holder.group.setText(group);
                if (isExpanded == true) {
                    //holder.lyt_icon.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow_expandle));
                    holder.lyt_icon.setBackgroundResource( R.drawable.custom_setting_yellow);
                    holder.lyt_footer.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.silver_expandle));
                } else {
                    //holder.lyt_icon.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.silver_card_expandle));
                    holder.lyt_icon.setBackgroundResource( R.drawable.custom_setting_silver);
                    holder.lyt_footer.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
                }
                if (group.equals(getString(R.string.subscribe))) {
                    holder.img_icon.setBackgroundResource(R.mipmap.setting_suscripcion);
                } else if (group.equals(getString(R.string.notificaciones))) {
                    holder.img_icon.setBackgroundResource(R.mipmap.setting_notificaciones);
                } else if (group.equals(getString(R.string.category_fav))) {

                    holder.lyt_contenedor.setClickable(true);
                    holder.img_icon.setBackgroundResource(R.mipmap.setting_ayuda);
                    holder.flag.setImageResource(R.mipmap.arrow_drop_right_grey);
                    holder.main_card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SettingsActivity.this, FavoriteCategoryActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                            //expandableListView.expandGroup(1);
                        }
                    });
                }
            }
            convertView.setFocusable(false);
            convertView.setSelected(false);

            return convertView;
        }




        public boolean hasStableIds() {
            return true;
        }

        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }

        class GroupHolder {
            CardView main_card;
            TextView group;
            ImageView flag;
            ImageView img_icon;
            LinearLayout lyt_footer;
            LinearLayout lyt_icon;
            LinearLayout lyt_contenedor;

        }
    }

    public void callFragmentDesvincularTelefono(){
        DesvinculaTelefono mFragment = new DesvinculaTelefono();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_suscipcion, mFragment);
        fragmentTransaction.commit();
    }

    public void callFragmentCountries(){
        CountriesFragment mFragment = new CountriesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_suscipcion, mFragment);
        fragmentTransaction.commit();
    }

    public void callFragmentSuscriber(){
        SuscriberFragment mFragment = new SuscriberFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_suscipcion, mFragment);
        fragmentTransaction.commit();
    }

    public void callFragmentNotifications(){
        NotificationsFragment mFragment = new NotificationsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_suscipcion, mFragment);
        fragmentTransaction.commit();
    }


}
