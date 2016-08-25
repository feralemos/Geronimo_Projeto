package com.example.aluno.geronimo_projeto.dao;

import android.provider.BaseColumns;

/**
 * Created by aluno on 20/05/16.
 */
public final class Contract {
    public static final String BD_NOME = "lutado.db";
    public static final int BD_VERSAO = 1;

    private Contract(){
    }

    public static abstract class Lutador implements BaseColumns{
        public static final String TABELA_NOME = "lutador";
        public static final String COLUNA_ID = "_id";
        public static final String COLUNA_NOME = "nome";
        public static final String COLUNA_SOBRENOME= "sobrenome";
        public static final String COLUNA_PESO= "peso";
        public static final String COLUNA_CATEGORIA="categoria";
    }
}
