package com.ngocbich.polyspinv1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ngocbich.polyspinv1.game_objects.Challenge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ngoc Bich on 7/24/2018.
 */

public class ChallengeDAO {
    public static ChallengeDAO challengeDAO=null;
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public ChallengeDAO(Context context){
        this.dbHelper=new DBHelper(context,DBHelper.TABLE_NAME,null,DBHelper.VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public static ChallengeDAO getInstence(Context context){
        if(challengeDAO==null){
            challengeDAO=new ChallengeDAO(context);
        }
        return challengeDAO;
    }

    public int update(Challenge... challenges){
        int count=0;
        ContentValues values=new ContentValues();

        for(Challenge challenge:challenges){
            values.clear();
            values.put(DBHelper.TABLE_COL_NAME,challenge.getName());
            values.put(DBHelper.TABLE_COL_DESCRIPTION,challenge.getDescription());
            values.put(DBHelper.TABLE_COL_REQUITMENT,challenge.getRequitment());
            values.put(DBHelper.TABLE_COL_PASS,challenge.getPass());

            count+=db.update(DBHelper.TABLE_NAME,values,DBHelper.TABLE_COL_ID+ " =? ",new String[]{challenge.getId()+""});
        }
        return count;
    }

    public List<Challenge> query(){
        Cursor cursor= db.query(DBHelper.TABLE_NAME,new String[]{DBHelper.TABLE_COL_ID,DBHelper.TABLE_COL_NAME,DBHelper.TABLE_COL_DESCRIPTION,
        DBHelper.TABLE_COL_REQUITMENT,DBHelper.TABLE_COL_PASS},null,null,null,null,null);

        List<Challenge> list=new ArrayList<>();

        while (cursor.moveToNext()){
            list.add(new Challenge(
                    cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_COL_ID)),
                    cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_COL_NAME)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COL_DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_COL_REQUITMENT)),
                    cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_COL_PASS))

            ));
        }
        return list;
    }

    public long insert(Challenge... challenges){
        int count = 0;
        ContentValues values = new ContentValues();

        for(Challenge challenge:challenges){
            values.clear();
            values.put(DBHelper.TABLE_COL_NAME,challenge.getName());
            values.put(DBHelper.TABLE_COL_DESCRIPTION,challenge.getDescription());
            values.put(DBHelper.TABLE_COL_REQUITMENT,challenge.getRequitment());
            values.put(DBHelper.TABLE_COL_PASS,challenge.getPass());

            if(db.insert(DBHelper.TABLE_NAME,null,values) !=-1){
                count++;
            }
        }
        return count;
    }
}
