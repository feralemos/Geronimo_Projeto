package com.example.aluno.geronimo_projeto.adapter;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

public class ListAdapter extends SimpleCursorAdapter {


    public ListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags){
        super(context, layout, c, from, to, flags);
    }
}//fim da classe
