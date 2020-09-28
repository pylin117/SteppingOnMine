package com.nikechi.steppingonmine;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Record extends AppCompatActivity  {
    private TextView text;
    private Long topRecord;
    private ListView dataView;

    private ToDo_Item theTask ;
    Boolean isFind=false;
    Database database;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        text = findViewById(R.id.textView7);
        dataView=findViewById(R.id.listView2);
        database=new Database(this);

        Bundle bundle=this.getIntent().getExtras();
        theTask =(ToDo_Item) bundle.getSerializable("task");
        if(theTask !=null) {
            ArrayList<ToDo_Item> taskItemList = database.getAllTaskItems();
            taskItemList.sort(Comparator.comparing(ToDo_Item::getTime));
            for(int i=0;i<taskItemList.size();i++)
            {
                if(taskItemList.get(i)==theTask){
                    isFind=true;
                }
            }
            if(!isFind)
                database.addToDoItem(theTask);
        }

        List<String> taskItemList = database.getAllTaskItems()
                .stream()//變成流
                .sorted(Comparator.comparing(ToDo_Item::getTime))//照時間排序(小->大)
                .limit(5)//最多五筆
                .map(task -> task.getName() + " " + task.getTime() + " Sec")//轉成字串
                .collect(Collectors.toList());//流變成list
        ArrayAdapter adapter =new ArrayAdapter(this,android.R.layout.simple_list_item_1,taskItemList);
        dataView.setAdapter(adapter);

        //Log.v("",taskItemList);
    }
}
