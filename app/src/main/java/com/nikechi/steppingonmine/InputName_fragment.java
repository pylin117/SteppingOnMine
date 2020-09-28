package com.nikechi.steppingonmine;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class InputName_fragment extends Fragment {
    private EditText name;
    private int id;
    private Button button;
    private long seconds;
    private ToDo_Item task;
    private ListViewControl listViewControl;
    Record_fragment recordFragment =  new  Record_fragment();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inputname, container, false);
        //Intent intent = getActivity().getIntent();
        //seconds = intent.getLongExtra("time", 9999);
        seconds=(Long)getArguments().getSerializable("time");
        id=0;
        name=view.findViewById(R.id.editText);
        button=view.findViewById(R.id.button);
        if(listViewControl!=null) listViewControl.setListViewEnable(false);

        button.setOnClickListener( new View.OnClickListener() {public void onClick(View v) {
            Record_fragment recordFragment =  new  Record_fragment();
            task=new ToDo_Item(name.getEditableText().toString(), seconds);
            Bundle bundle=new Bundle();
            bundle.putSerializable("task", task);
            recordFragment.setArguments(bundle);
            getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment2, recordFragment).commit();
            if(listViewControl!=null) listViewControl.setListViewEnable(true);
        }});
        return view;
    }
    public void setListViewControl(ListViewControl listViewControl) {
        this.listViewControl = listViewControl;
    }

}
