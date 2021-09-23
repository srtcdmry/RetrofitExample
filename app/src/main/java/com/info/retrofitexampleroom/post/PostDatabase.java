package com.info.retrofitexampleroom.post;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.info.retrofitexampleroom.Post;

@Database(entities = {Post.class},version = 1)
public abstract class PostDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="PostDatabase";

    public abstract PostDao postDao();
    private static volatile PostDatabase INSTANCE;

    public static PostDatabase getInstance(Context context){
        if(INSTANCE==null){
            synchronized (PostDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context,PostDatabase.class,
                            DATABASE_NAME)
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsynTask(INSTANCE);
        }
    };
    static class PopulateAsynTask extends AsyncTask<Void,Void,Void>{
        private PostDao postDao;
        PopulateAsynTask(PostDatabase postDatabase){
            postDao=postDatabase.postDao();

        }
        @Override
        protected Void doInBackground(Void... voids) {
           // postDao.deleteAll();
            return null;
        }
    }
}
