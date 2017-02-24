package mlearning.grupolink.com.mlearningandroid.Settings.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Settings.ItemNotifications;
import mlearning.grupolink.com.mlearningandroid.Settings.adapters.NotificationsListAdapter;


public class NotificationsFragment extends Fragment {

    RecyclerView recyclerView;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.notifications_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity() ));
        NotificationsListAdapter menuAdapter = new NotificationsListAdapter( getActivity() ,getData_Notification());
        recyclerView.setAdapter(menuAdapter);

        return view;
    }


    private List<ItemNotifications> getData_Notification() {
        List<ItemNotifications> menuList = new ArrayList<>();
        menuList.add(new ItemNotifications("Cursos Pendientes",    R.mipmap.ic_launcher , 1 ));
        menuList.add(new ItemNotifications("Avisos Generales",     R.mipmap.ic_launcher , 2 ));

        return menuList;
    }


}
