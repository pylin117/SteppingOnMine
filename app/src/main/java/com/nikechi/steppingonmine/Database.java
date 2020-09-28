package com.nikechi.steppingonmine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_TABLE = "myTable";
    public static final String DATABASE_NAME = "data.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;
    // 資料庫物件，固定的欄位變數
    private static SQLiteDatabase database;
    private static final String KEY_ID = "_id";
    private static final String NAME_COLUMN = "name";
    private static final String TIME_COLUMN = "time";

    private int taskCount;

    public Database(Context context) {
        super (context, DATABASE_NAME, null, VERSION);

    }

    // 資料庫名稱


    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new Database(context).getWritableDatabase();
        }

        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 建立應用程式需要的表格
        // 待會再回來完成它
        taskCount=0;
        String instruction = "CREATE TABLE " + " myTable " + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COLUMN+ " TEXT, "
                + TIME_COLUMN+ " INTEGER) ";
        db.execSQL (instruction);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 刪除原有的表格
        // 待會再回來完成它
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        // 呼叫onCreate建立新版的表格
        onCreate(db);
    }

    public void addToDoItem(ToDo_Item task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//ADD KEY-VALUE PAIR INFORMATION FOR THE TASK DESCRIPTION
//        values.put(KEY_ID, task.getId());
        values.put(NAME_COLUMN, task.getName()); // task name
        values.put(TIME_COLUMN, task.getTime());
// INSERT THE ROW IN THE TABLE
        long id = db.insert(DATABASE_TABLE, null, values);
        task.setId((int)id);
        taskCount++;
        db.close();
    }

    public void editTaskItem(ToDo_Item task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COLUMN, task.getName());
        values.put(TIME_COLUMN, task.getTime());
        db.update(DATABASE_TABLE, values, KEY_ID + " = ?",
                new String[]{
                        String.valueOf(task.getId())
                });
        db.close();
    }

    public ToDo_Item getToDo_Task(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                DATABASE_TABLE,
                new String[]{KEY_ID, NAME_COLUMN, TIME_COLUMN}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null );
        if (cursor != null)
            cursor.moveToFirst();
        ToDo_Item task = new ToDo_Item();
        task.setId(cursor.getInt(0));
        task.setName(cursor.getString(1));
        task.setTime(cursor.getLong(2));
        db.close();
        return task;
    }

    public void deleteTaskItem (ToDo_Item task){
        SQLiteDatabase database = this.getReadableDatabase();
// DELETE THE TABLE ROW
        database.delete(DATABASE_TABLE, KEY_ID + " = ?", new String[]{String.valueOf(task.getId())});
        database.close();
    }
    // return number of items in database; we keep track of this in an instance var
    public int getTaskCount() {
        return taskCount;
    }

    public ArrayList<ToDo_Item> getAllTaskItems() {
        ArrayList<ToDo_Item> taskList = new ArrayList<>();
        String queryList = "SELECT * FROM " + DATABASE_TABLE;
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery(queryList, null);
//COLLECT EACH ROW IN THE TABLE
        if (cursor!= null && cursor.moveToFirst()){
            do {
                ToDo_Item task = new ToDo_Item();
                task.setId(cursor.getInt(0));
                task.setName(cursor.getString(1));
                task.setTime(cursor.getLong(2));
                //ADD TO THE QUERY LIST
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }
}
