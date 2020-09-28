package com.nikechi.steppingonmine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Game_fragment extends Fragment{

    private TextView gameStatusTextView;
    private Button[][] boardButtons;
    private SteppingOnMineGame game;

    private TextView time;
    private ListViewControl listViewControl;
    private Long startTime;
    private Handler handler = new Handler();
    private Long seconds;
    private Long topRecord;
    public static final String KEY = "com.my.package.app";
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        TableLayout tableLayout = view.findViewById(R.id.table_layout);
        Button replayButton = view.findViewById(R.id.replay_button);
        gameStatusTextView = view.findViewById(R.id.game_status_textView);

        time = view.findViewById(R.id.time_textView);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(KEY, Context.MODE_PRIVATE);


        //取得目前時間
        startTime = System.currentTimeMillis();
        //設定定時要執行的方法
        handler.removeCallbacks(updateTimer);
        //設定Delay的時間
        handler.postDelayed(updateTimer, 1000);

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.replay_button) {
                    game.reset();
                    gameStatusTextView.setText("");
                    time.setText("");

                    //取得目前時間
                    startTime = System.currentTimeMillis();
                    //設定定時要執行的方法
                    handler.removeCallbacks(updateTimer);
                    //設定Delay的時間
                    handler.postDelayed(updateTimer, 1000);
                    updateBoard();
                }
            }
        });

        int columns = 5;
        int rows = 5;
        boardButtons = new Button[rows][columns];
        game = new SteppingOnMineGame(columns, rows);
        for (int i = 0; i < rows; i++) {
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < columns; j++) {
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = 1.0f;
                Button button = new Button(getActivity());
                button.setLayoutParams(layoutParams);

                int finalI = i;
                int finalJ = j;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!game.isEnd) {
                            game.clickCell(finalI, finalJ);

                            if(game.isEnd ){
                                if (game.isLose) {
                                    gameStatusTextView.setText("You Lose!");
                                } else {
                                    gameStatusTextView.setText("You Win!");
                                    topRecord=getActivity().getSharedPreferences(KEY, Context.MODE_PRIVATE)
                                            .getLong(KEY,seconds);
                                    if(seconds<=topRecord){
                                        sharedPreferences.edit()
                                                .putLong(KEY,seconds)
                                                .commit();
                                        topRecord=getActivity().getSharedPreferences(KEY, Context.MODE_PRIVATE)
                                                .getLong(KEY,seconds);
                                    }
                                    time.setText("Top: "+topRecord+" Sec  You: "+seconds+" Sec");

                                    InputName_fragment inputFragment =  new  InputName_fragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putLong("time", seconds);
                                    inputFragment.setArguments(bundle);


                                    Database database=new Database(getActivity());
                                    ArrayList<ToDo_Item> taskItemList = database.getAllTaskItems();

                                    if(taskItemList.size()<5){
                                        inputFragment.setListViewControl(listViewControl);
                                        getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment2, inputFragment).commit();
                                    }
                                    else{
                                        taskItemList.sort(Comparator.comparing(ToDo_Item::getTime));
                                        Handler handler =new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(taskItemList.get(4).getTime()>seconds){
                                                    inputFragment.setListViewControl(listViewControl);
                                                    getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment2, inputFragment).commit();
                                                }
                                            }
                                        },1000);
                                    }
                                }

                            }
                            updateBoard();
                        }
                    }
                });

                tableRow.addView(button);
                boardButtons[i][j] = button;
            }
            tableLayout.addView(tableRow);
        }

        updateBoard();

        return view;
    }

    private void updateBoard() {
        for (int i = 0; i < game.board.length; i++) {
            for (int j = 0; j < game.board[i].length; j++) {
                boardButtons[i][j].setText(game.getCell(i, j));
            }
        }
    }

    private Runnable updateTimer = new Runnable() {
        public void run() {
            if(!game.isEnd && !game.isLose){
                Long spentTime = System.currentTimeMillis() - startTime;
                //計算目前已過秒數
                seconds = (spentTime/1000) % 60;
                gameStatusTextView.setText(seconds+" Sec");
                handler.postDelayed(this, 1000);
            }
        }
    };
    public void setListViewControl(ListViewControl listViewControl) {
        this.listViewControl = listViewControl;
    }
}
