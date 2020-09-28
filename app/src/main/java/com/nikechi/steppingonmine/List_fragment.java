package com.nikechi.steppingonmine;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

interface ListViewControl {
    void  setListViewEnable(boolean enable);
}

public class List_fragment extends Fragment implements ListViewControl {

    private OnItemSelectedListener listener;
    private String information;
    private Boolean landscape=false;
    private ToDo_Item task;
    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_listview, container, false);
        listView = view.findViewById(R.id.listview);
        //ListView 要顯示的內容
        String[] str = {"Start the Game","Record","Help","About the Game"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, str);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onClickListView);

        return view;
    }

    //判斷螢幕方向
    private void getConfigurationInfo(){
        Configuration configuration=getResources().getConfiguration();
        if (configuration.orientation==configuration.ORIENTATION_LANDSCAPE) {
            landscape=true;
        }
        if (configuration.orientation==configuration.ORIENTATION_PORTRAIT) {
            landscape=false;
        }
    }

    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            getConfigurationInfo();

            if (position == 0) {
                Game_fragment gameFragment = new Game_fragment();
                gameFragment.setListViewControl(List_fragment.this);
                if (landscape) {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment2, gameFragment).commit();
                } else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            } else if (position == 1) {
                Record_fragment recordFragment = new Record_fragment();
                if (landscape) {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("task", task);
                    recordFragment.setArguments(bundle);
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment2, recordFragment).commit();
                } else {
                    Intent intent = new Intent(getActivity(), Record.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("task", task);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            } else if (position == 2) {
                Help_fragment helpFragment = new Help_fragment();
                if (landscape) {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment2, helpFragment).commit();
                } else {
                    Intent intent = new Intent(getActivity(), Help.class);
                    startActivity(intent);
                }
            } else if (position == 3) {
                About_fragment aboutFragment = new About_fragment();
                if (landscape) {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment2, aboutFragment).commit();
                } else {
                    Intent intent = new Intent(getActivity(), About.class);
                    startActivity(intent);
                }
            }
            }

    };

    @Override
    public void setListViewEnable(boolean enable) {
        listView.setEnabled(enable);
    }

    public interface OnItemSelectedListener {
        public void onShadeItemSelected(String link);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        }
    }

    public void updateDetail() {
        listener.onShadeItemSelected(information);
    }

}
