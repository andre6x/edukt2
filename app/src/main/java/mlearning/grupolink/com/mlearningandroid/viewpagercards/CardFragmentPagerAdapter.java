package mlearning.grupolink.com.mlearningandroid.viewpagercards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mlearning.grupolink.com.mlearningandroid.activities.DetalleLessonActivity;

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private List<CardFragment> fragments;
    private float baseElevation;

    private List<String> datos;
    private int quiz;
    private float status;
    private int idLeccion;
    private String title;
    private int Progress ;
    private int IdlastLesson ;
    private int idCourse ;
    private DetalleLessonActivity detail;


    public CardFragmentPagerAdapter(FragmentManager fm, float baseElevation , DetalleLessonActivity detail , List<String> datos, int quiz , float status, int idLeccion , String title, int Progress, int IdlastLesson, int idCourse) {
        super(fm);
        fragments = new ArrayList<>();
        this.baseElevation = baseElevation;

        this.detail = detail;
        this.datos = datos;
        this.quiz = quiz;
        this.status =status;
        this.idLeccion = idLeccion;
        this.title = title;
        this.Progress = Progress;
        this.IdlastLesson = IdlastLesson ;
        this.idCourse = idCourse;

        int size = datos.size();
        for(int i = 0; i< size; i++){
            addCardFragment(new CardFragment());
        }
    }

    @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return fragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        boolean flag ;
        if(position == (getCount()-1))
           flag = true;
        else
           flag = false;


        return CardFragment.getInstance(position ,
                                        datos.get(position),
                                        detail,
                                        flag,
                                        quiz ,
                                        status,
                                        idLeccion,
                                        title,
                                        Progress,
                                        IdlastLesson,
                                        idCourse);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        fragments.set(position, (CardFragment) fragment);
        return fragment;
    }

    public void addCardFragment(CardFragment fragment) {
        fragments.add(fragment);
    }

}
