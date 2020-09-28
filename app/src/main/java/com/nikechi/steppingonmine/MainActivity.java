package com.nikechi.steppingonmine;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.Handler;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView gameStatusTextView;
    private Button[][] boardButtons;
    private SteppingOnMineGame game;
    private TextView time;

    private Long startTime;
    private Handler handler = new Handler();
    private Long seconds;
    private Long topRecord;

    public static final String KEY = "com.my.package.app";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout tableLayout = findViewById(R.id.table_layout);
        Button replayButton = findViewById(R.id.replay_button);
        gameStatusTextView = findViewById(R.id.game_status_textView);
        time = findViewById(R.id.time_textView);
        SharedPreferences sharedPreferences = getSharedPreferences(KEY, Context.MODE_PRIVATE);
        //取得目前時間
        startTime = System.currentTimeMillis();
        //設定定時要執行的方法
        handler.removeCallbacks(updateTimer);
        //設定Delay的時間
        handler.postDelayed(updateTimer, 1000);

        //EXPERIMENT 1: CREATE THE DATABASE
        //Database database = new Database(this);

        replayButton.setOnClickListener(this);


        int columns = 5;
        int rows = 5;
        boardButtons = new Button[rows][columns];
        game = new SteppingOnMineGame(columns, rows);
        for (int i = 0; i < rows; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < columns; j++) {
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = 1.0f;
                Button button = new Button(this);
                button.setLayoutParams(layoutParams);

                int finalI = i;
                int finalJ = j;
                button.setOnClickListener(view -> {
                    if (!game.isEnd) {
                        game.clickCell(finalI, finalJ);

                        if (game.isEnd) {
                            if (game.isLose) {
                                gameStatusTextView.setText("You Lose!");
                            } else {
                                gameStatusTextView.setText("You Win!");
                                topRecord = getSharedPreferences(KEY, Context.MODE_PRIVATE)
                                        .getLong(KEY, seconds);
                                if (seconds <= topRecord) {
                                    sharedPreferences.edit()
                                            .putLong(KEY, seconds)
                                            .commit();
                                    topRecord = getSharedPreferences(KEY, Context.MODE_PRIVATE)
                                            .getLong(KEY, seconds);
                                }
                                time.setText("Top: " + topRecord + " Sec  You: " + seconds + " Sec");

                                Database database=new Database(this);
                                ArrayList<ToDo_Item> taskItemList = database.getAllTaskItems();

                                Intent intent = new Intent(this, InputName.class);
                                intent.putExtra("time",seconds);
                                if(taskItemList.size()<5){
                                    startActivity(intent);
                                }
                                else{
                                    taskItemList.sort(Comparator.comparing(ToDo_Item::getTime));
                                    Handler handler =new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(taskItemList.get(4).getTime()>seconds){
                                                startActivity(intent);
                                            }
                                        }
                                    },1000);

                                }
                            }
                        }
                        updateBoard();
                    }
                });

                tableRow.addView(button);
                boardButtons[i][j] = button;
            }
            tableLayout.addView(tableRow);
        }

        updateBoard();
    }

    private void updateBoard() {
        for (int i = 0; i < game.board.length; i++) {
            for (int j = 0; j < game.board[i].length; j++) {
                boardButtons[i][j].setText(game.getCell(i, j));
            }
        }
    }

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

    private Runnable updateTimer = new Runnable() {
        public void run() {
            if (!game.isEnd && !game.isLose) {
                Long spentTime = System.currentTimeMillis() - startTime;
                //計算目前已過秒數
                seconds = (spentTime / 1000) % 60;
                handler.postDelayed(this, 1000);
                gameStatusTextView.setText(seconds + " Sec");
            }
        }
    };
}