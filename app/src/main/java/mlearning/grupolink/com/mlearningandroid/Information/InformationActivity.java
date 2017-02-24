package mlearning.grupolink.com.mlearningandroid.Information;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;

public class InformationActivity extends AppCompatActivity {


    private Map<String, List<String>> map;
    private int lastExpandedPosition = -1;
    public int idCourse = 0;
    private String inicio ="";
    public  String CountryCode ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);


        final ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.group_listview);
        map = GetGroupMap();
        InformationActivity.SettingAdapter adapter = new InformationActivity.SettingAdapter(this,map);
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


        Utils.SetStyleToolbarTitle(this, getString(R.string.txt_informacion));
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

    public Map<String,List<String>> GetGroupMap()  {
        Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();


        List<String> acerca = new ArrayList<String>();
        List<String> terminos_condiciones = new ArrayList<String>();
        List<String> ayuda = new ArrayList<String>();


        acerca.add( getString(R.string.about) );
        terminos_condiciones.add(getString(R.string.terms));
        ayuda.add(getString(R.string.help));


        map.put(getString(R.string.about), acerca );
        map.put(getString(R.string.terms), terminos_condiciones);
        map.put(getString(R.string.help), ayuda);


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

            convertView = mInflater.inflate(R.layout.setting_two, parent, false);
            layout_select = (LinearLayout) convertView.findViewById(R.id.layout_select);
            button_ecuador = (Button) convertView.findViewById(R.id.button_ec);
            button_argentina = (Button) convertView.findViewById(R.id.button_ar);
            button_mexico = (Button) convertView.findViewById(R.id.button_mx);
            button_chile = (Button) convertView.findViewById(R.id.button_chl);
            button_nicaragua = (Button) convertView.findViewById(R.id.button_ni);
            button_salvador = (Button) convertView.findViewById(R.id.button_sv);
            layout_select.setVisibility(View.GONE);

            button_ecuador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Utils.generarAlerta(context, context.getResources().getString(R.string.txt_atencion), "CLICK EN BUTTOM ECUADOR");
                    Intent intent = new Intent(context, TermsConditionsActivity.class);
                    intent.putExtra("pais", "ec");
                    context.startActivity(intent);
                }
            });
            button_argentina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Utils.generarAlerta(context, context.getResources().getString(R.string.txt_atencion), "CLICK EN BUTTOM ARGENTINA");
                    Intent intent = new Intent(context, TermsConditionsActivity.class);
                    intent.putExtra("pais", "ar");
                    context.startActivity(intent);

                }
            });

            button_mexico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Utils.generarAlerta(context, context.getResources().getString(R.string.txt_atencion), "CLICK EN BUTTOM ARGENTINA");
                    Intent intent = new Intent(context, TermsConditionsActivity.class);
                    intent.putExtra("pais", "mx");
                    context.startActivity(intent);

                }
            });

            button_chile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Utils.generarAlerta(context, context.getResources().getString(R.string.txt_atencion), "CLICK EN BUTTOM ARGENTINA");
                    Intent intent = new Intent(context, TermsConditionsActivity.class);
                    intent.putExtra("pais", "chl");
                    context.startActivity(intent);

                }
            });

            button_nicaragua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Utils.generarAlerta(context, context.getResources().getString(R.string.txt_atencion), "CLICK EN BUTTOM ARGENTINA");
                    Intent intent = new Intent(context, TermsConditionsActivity.class);
                    intent.putExtra("pais", "ni");
                    context.startActivity(intent);

                }
            });

            button_salvador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Utils.generarAlerta(context, context.getResources().getString(R.string.txt_atencion), "CLICK EN BUTTOM ARGENTINA");
                    Intent intent = new Intent(context, TermsConditionsActivity.class);
                    intent.putExtra("pais", "sv");
                    context.startActivity(intent);

                }
            });

            final WebView webTextSettings = (WebView) convertView.findViewById(R.id.webTextSettings);
            WebSettings webSettings = webTextSettings.getSettings();
            webSettings.setTextZoom(100);
            //webTextSettings.setBackgroundColor(0);
            webTextSettings.setBackgroundColor(0x00000000);
            webTextSettings.getSettings().setJavaScriptEnabled(true);
            webTextSettings.requestFocus(View.FOCUS_DOWN);
            webTextSettings.clearHistory();
            webTextSettings.clearCache(true);
            webTextSettings.getSettings().setDomStorageEnabled(true);
            webTextSettings.getSettings().setUserAgentString("ua");
            webTextSettings.getSettings().setLoadWithOverviewMode(true);
            webTextSettings.getSettings().setAllowFileAccess(true);
            webTextSettings.getSettings().setPluginState(WebSettings.PluginState.ON);
            webTextSettings.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);

            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);

            String m_data ="";
            if (groupPosition == 0) {
                m_data = getStringFromAssets("acerca.html", context);
                //webTextSettings.loadUrl("file:///android_asset/acerca.html");
            }else if (groupPosition == 1) {
                m_data = getStringFromAssets("ayuda.html", context);
                webTextSettings.loadUrl("file:///android_asset/ayuda.html");
            }else if (groupPosition == 2) {
                m_data = getStringFromAssets("terminosYCondicionesGeneral.html", context);
                //webTextSettings.loadUrl("file:///android_asset/terminosYCondicionesGeneral.html");
                layout_select.setVisibility(View.VISIBLE);
            }

            webTextSettings.loadDataWithBaseURL("file:///android_asset/", m_data, "text/html", "utf-8", null);
            webTextSettings.setWebChromeClient(new WebChromeClient());
            webTextSettings.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    view.loadUrl(url);

                    return true;
                }
                public void onPageFinished(WebView view, String url) {
                    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                    if (currentapiVersion == 14) {
                        webTextSettings.loadUrl("javascript:document.body.style.setProperty(\"color\", \"black\");");
                    }

                }
            });


            return convertView;
        }


        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            InformationActivity.SettingAdapter.GroupHolder holder;
            String group = (String) getGroup(groupPosition);
            if (convertView == null) {
                holder = new InformationActivity.SettingAdapter.GroupHolder();
                convertView = mInflater.inflate(R.layout.expandable_list_setting_group, parent, false);
                holder.group = (TextView) convertView.findViewById(R.id.grupoName);
                holder.flag = (ImageView) convertView.findViewById(R.id.img_arrow_right);
                holder.lyt_footer = (LinearLayout) convertView.findViewById(R.id.lyt_footer);
                holder.lyt_icon = (LinearLayout) convertView.findViewById(R.id.lyt_icon);
                holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);



                convertView.setTag(holder);
            } else
                holder = (InformationActivity.SettingAdapter.GroupHolder) convertView.getTag();

            if (isExpanded == true) {
                holder.lyt_icon.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow_expandle));
                holder.lyt_footer.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.silver_expandle));

            } else {
                holder.lyt_icon.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.silver_card_expandle));
                holder.lyt_footer.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));

            }


            if(group.equals(getString(R.string.about))){
                holder.img_icon.setBackgroundResource(R.mipmap.setting_acerca);

            }else if (group.equals(getString(R.string.terms))){
                holder.img_icon.setBackgroundResource(R.mipmap.setting_terminos);

            }else if (group.equals(getString(R.string.help))){
                holder.img_icon.setBackgroundResource(R.mipmap.setting_ayuda);
            }

            convertView.setFocusable(false);
            convertView.setSelected(false);
            holder.group.setText(group);

            return convertView;
        }

        public boolean hasStableIds() {
            return true;
        }

        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }

        class GroupHolder {
            TextView group;
            ImageView flag;
            ImageView img_icon;
            LinearLayout lyt_footer;
            LinearLayout lyt_icon;

        }
    }

    public static String getStringFromAssets(String name, Context p_context)
    {
        try
        {
            InputStream is = p_context.getAssets().open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String bufferString = new String(buffer);
            return bufferString;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;

    }


}
