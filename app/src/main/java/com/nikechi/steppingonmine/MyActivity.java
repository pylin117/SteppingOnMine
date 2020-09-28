package com.nikechi.steppingonmine;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


public class MyActivity extends Activity implements
        List_fragment.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

    }

    public void onShadeItemSelected(String link) {

        //TASK 1: CHECK IF THE INFORMATION FRAGMENT EXISTS IN THIS LAYOUT
        //Game_fragment gameFragment=(Game_fragment)getFragmentManager().findFragmentById(R.id.fragment2);

        //TASK 2: IS A TWO PANE CONFIGURATION BEING DISPLAYED?
        //if (gameFragment != null && gameFragment.isInLayout()) {
            //gameFragment.setText(link);
        //}
        //A SINGLE-PANE CONFIGURATION -
        //  IF THE INFORMATION FRAGMENT DOES NOT EXIST IN THIS LAYOUT,
        //  ACTIVATE THE INFORMATION ACTIVITY
        //else {
        //    Intent intent = new Intent (this, MainActivity.class);
        //    intent.putExtra("Game", link);
        //    startActivity (intent);
        //}
    }
}
