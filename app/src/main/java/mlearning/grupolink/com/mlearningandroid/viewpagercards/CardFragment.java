package mlearning.grupolink.com.mlearningandroid.viewpagercards;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.activities.DetalleLessonActivity;


public class CardFragment extends Fragment {

    private CardView cardView;

    private static ArrayList webWindow;
    private String cadena;
    private boolean flag;
    //private WebView webView;
    private Button button_quiz;
    private Button button_next_lesson;
    private Button button_view_diploma ;
    private RelativeLayout pag_web_Layout;
    private int quiz;
    private float status;
    private int idLeccion;
    private String title;
    private int Progress;
    private int IdlastLesson ;
    private int idCourse;
    public static final int USER_MOBILE  = 0;
    public static final int USER_DESKTOP = 1;
    private View nonVideoLayout ;
    private ViewGroup videoLayout;
    private View loadingView;
    private DetalleLessonActivity detail;

    public static WebView webView;


    public static Fragment getInstance(int position , String cadena, DetalleLessonActivity detail, boolean flag ,
                                       int quiz , Float status , int idLeccion , String title,
                                       int Progress , int IdlastLesson , int idCourse ) {


        CardFragment f = new CardFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("cadena", cadena);
        args.putBoolean("flag",flag);
        args.putInt("quiz", quiz);
        args.putFloat("status", status);
        args.putInt("idLeccion", idLeccion);
        args.putString("title", title);
        args.putInt("Progress", Progress);
        args.putInt("IdlastLesson",IdlastLesson);
        args.putInt("idCourse", idCourse);
        f.setArguments(args);

        return f;
    }

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_viewpager, container, false);
        cardView = (CardView) v.findViewById(R.id.cardView);
        cardView.setMaxCardElevation(cardView.getCardElevation() * CardAdapter.MAX_ELEVATION_FACTOR);


        flag = false;
        //title.setText(String.format("Card %d", getArguments().getInt("position")));
        Bundle extras = this.getArguments();
        if (extras != null) {
            cadena = extras.getString("cadena");
            flag = extras.getBoolean("flag");
            quiz = extras.getInt("quiz");
            status = extras.getFloat("status");
            idLeccion = extras.getInt("idLeccion");
            title = extras.getString("title");
            Progress = extras.getInt("Progress");
            IdlastLesson =  extras.getInt("IdlastLesson");
            idCourse  =  extras.getInt("idCourse");

        }

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        webView = (WebView) v .findViewById(R.id.webView);
        button_quiz = (Button) v.findViewById(R.id.button_quiz);
        button_next_lesson = (Button) v.findViewById(R.id.button_next_lesson);
        button_view_diploma = (Button) v.findViewById(R.id.button_view_diploma);

        button_next_lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail.nextLesson();
            }
        });

        button_view_diploma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                detail.goDiploma();
            }
        });

        if(flag){
            button_quiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*Intent intent = new Intent(getActivity(), QuizActivity.class);
                    intent.putExtra("idLeccion", idLeccion);
                    intent.putExtra("title", title);
                    intent.putExtra("IdlastLesson", IdlastLesson);
                    intent.putExtra("idCourse", idCourse);
                    startActivity(intent);*/

                }
            });

            if ( Progress == 100 && IdlastLesson == idLeccion){

                button_quiz.setVisibility(View.GONE);
                button_next_lesson.setVisibility(View.GONE);
                button_view_diploma.setVisibility(View.VISIBLE);

                //if(detail != null)
                //  button_view_diploma = DetalleLessonActivity.button_view_diploma;
                //else
                //  Log.e("TAG","BUTTON NULOO DIPLOMNA");

            }else if(quiz == 1 && status >=-1 && status <=1)
            {
                button_quiz.setVisibility(View.VISIBLE);
                button_next_lesson.setVisibility(View.GONE);
                button_view_diploma.setVisibility(View.GONE);
            }else
            {
                button_quiz.setVisibility(View.GONE);
                button_next_lesson.setVisibility(View.VISIBLE);
                button_view_diploma.setVisibility(View.GONE);

                //if(detail != null)
                // button_next_lesson = DetalleLessonActivity.button_next_lesson;
                //else
                //    Log.e("TAG","BUTTON NULOO NEXT LESSON");
            }

        }else{
            button_quiz.setVisibility(View.GONE);
            button_next_lesson.setVisibility(View.GONE);
            button_view_diploma.setVisibility(View.GONE);

        }


        if(cadena != null) {
            try {

                String justify = "<html><body style=\"text-align:left\"> <style> h2 {text-align:center} </style> %s</body></Html>";
                //webView.loadData(String.format(justify, cadena), "text/html; charset=UTF-8", null);
                webView.loadDataWithBaseURL("file:///android_asset/", String.format(justify, cadena), "text/html", "utf-8", null);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.requestFocus(View.FOCUS_DOWN);
                webView.setWebChromeClient(new WebChromeClient());
                //webView.getSettings().setDefaultFontSize(20);

                webView.setWebViewClient(new WebViewClient() {

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });


            }catch (Exception e){
                e.printStackTrace();
            }
        }



        return v;
    }

    public CardView getCardView() {
        return cardView;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        if(webView != null){
            webView.onPause();
            webView.destroy();

        }

    }


    @Override
    public void onStop() {
        if(webView != null) {
            webView.loadUrl("javascript:playPause()");
            //webView.onPause();

        }
        super.onStop();
    }

    @Override
    public void onResume() {
        /*if(webView != null) {
            //webView.loadUrl("javascript:playPause()");
            //webView.onResume();
        }*/
        super.onResume();
    }

}
