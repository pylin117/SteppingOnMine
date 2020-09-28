package com.nikechi.steppingonmine;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class About  extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView view1=findViewById(R.id.textView3);
        view1.getDisplay();
        TextView view2=findViewById(R.id.textView4);
        view2.getDisplay();
    }
}
