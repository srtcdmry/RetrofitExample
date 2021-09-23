package com.info.retrofitexampleroom.comment;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.info.retrofitexampleroom.Comment;
import com.info.retrofitexampleroom.comment.CommentDao;
import com.info.retrofitexampleroom.comment.CommentDatabase;


    
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.info.retrofitexampleroom.Comment;

    @Database(entities = {Comment.class},version = 1)
    public abstract class CommentDatabase extends RoomDatabase {
        private static final String DATABASE_NAME="CommentDatabase";

        public abstract CommentDao commentDao();
        private static volatile com.info.retrofitexampleroom.comment.CommentDatabase INSTANCE;

        public static com.info.retrofitexampleroom.comment.CommentDatabase getInstance(Context context1){
            if(INSTANCE==null){
                synchronized (com.info.retrofitexampleroom.comment.CommentDatabase.class){
                    if(INSTANCE==null){
                        INSTANCE= Room.databaseBuilder(context1, com.info.retrofitexampleroom.comment.CommentDatabase.class,
                                DATABASE_NAME)
                                .addCallback(callback1)
                                .build();
                    }
                }
            }
            return INSTANCE;
        }

        static Callback callback1 = new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new com.info.retrofitexampleroom.comment.CommentDatabase.PopulateAsynTask(INSTANCE);
            }
        };
        static class PopulateAsynTask extends AsyncTask<Void,Void,Void> {
            private CommentDao commentDao;
            PopulateAsynTask(com.info.retrofitexampleroom.comment.CommentDatabase commentDatabase){
                commentDao=commentDatabase.commentDao();

            }
            @Override
            protected Void doInBackground(Void... voids) {
                // commentDao.deleteAll();
                return null;
            }
        }
    }


