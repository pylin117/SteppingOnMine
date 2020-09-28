package com.nikechi.steppingonmine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.nikechi.steppingonmine.Database;
import java.io.Serializable;

public class InputName extends AppCompatActivity implements View.OnClickListener {
    private EditText name;
    private int id;
    private Button button;
    private long seconds;
    //private Database database = new Database(this)/*null*/;
    private ToDo_Item task;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputname);
        Intent intent = getIntent();
        seconds = intent.getLongExtra("time", 9999);
        id=0;
        name=findViewById(R.id.editText);
        button=findViewById(R.id.button);
        name.addTextChangedListener(nameTextWatcher);
        button.setOnClickListener(this);
    }
    public void onClick(View v) {
        Intent intent=new Intent (this,Record.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("task", task);
        intent.putExtras(bundle);
        finish();
        startActivity(intent);
    }

    private TextWatcher nameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                task=new ToDo_Item(s+"", seconds);
                //database.addToDoItem(task);

            } catch (Exception e) {
                //database.addToDoItem(new ToDo_Item(0, "", 0));
                //task=new ToDo_Item(0, "", 0);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private void onDestory() {
        super.onDestroy();
    }
}
