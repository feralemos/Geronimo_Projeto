package com.example.aluno.geronimo_projeto.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by aluno on 20/05/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    //ATRIBUTOS E/OU CONSTANTES DA CLASSE

    private static DBHelper myHelper;



    public DBHelper(Context context) {
        super(context, Contract.BD_NOME, null, Contract.BD_VERSAO);

    }


    public static  DBHelper getInstance (Context context){
        if(myHelper == null){
            myHelper = new DBHelper(context);
            return myHelper;
        }
        return myHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    String sql= "create table  " + Contract.Lutador.TABELA_NOME + " ("
            + Contract.Lutador.COLUNA_ID + " integer primary key autoincrement,"
            + Contract.Lutador.COLUNA_NOME + " text not null,"
            + Contract.Lutador.COLUNA_PESO + " text not null,"
            + Contract.Lutador.COLUNA_SOBRENOME + " text not null,"
            + Contract.Lutador.COLUNA_CATEGORIA + " text not null);";

        db.execSQL(sql);
        Log.i("F4ra", "executou o scrip de criação de tabelas"
         + Contract.Lutador.TABELA_NOME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("F4ra", "Upgrade da versão" + oldVersion + "para" + newVersion + ", destruindo tudo");
        db.execSQL("DROP TABLE IF EXISTS" + Contract.Lutador.TABELA_NOME);
        onCreate(db); //chama OnCreate e recria o banco de dados
        Log.i("F4ra", "Executou o script de upgrade de tabela" + Contract.Lutador.TABELA_NOME);
    }
}
