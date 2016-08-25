package com.example.aluno.geronimo_projeto.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aluno.geronimo_projeto.modelo.Lutador;

/**
 * Created by aluno on 02/06/16.
 */
public class LutadorDAO {
    public static LutadorDAO lutDAO; //objeto da própria classe
    private final Context context; //o context da aplicação
    private DBHelper myHelper; //objeto que auxilia na criação e/ou upgrade do banco de dados
    private SQLiteDatabase database; //obejto de conexão com o banco de dados
    private Cursor listaByNome;

    private LutadorDAO(Context context){
        this.context = context; //recebe o contexto da Activity que o chamou
    }

    public static LutadorDAO getInstance(Context context){
        if(lutDAO == null){
            lutDAO = new LutadorDAO(context);
            return lutDAO;
        }
        return lutDAO;
    }

    /***
     * Método responsável por abrir uma conexão com o banco
     */

    public void open(){
        myHelper = DBHelper.getInstance(context); //instancia um objeto que auxilia na criação e/ou atualização
        database=myHelper.getWritableDatabase(); //devolve uma conexão para o banco de dados
    }

    /**
     * Método responsável por liberar a conexão com o banco de dados
     */
    public void close(){database.close();/**Libera o recurso */}

    /**
     * Método para salvar um lutador (o que inclui as operações Inseririr e Alterar)
     */
    public boolean salvar(Lutador lut){
        if(lut.getId_lutador() == null){
            return inserir(lut); //se não tem id
        }
        else{
            return alterar(lut); //se tem id
        }
    }
    /**
     * Método para inserir um lutador
     */
    public Boolean inserir(Lutador lut){
        ContentValues valores = new ContentValues(); //objeto tupla(label da coluna valor)

        valores.put(Contract.Lutador.COLUNA_NOME, lut.getNome());
        valores.put(Contract.Lutador.COLUNA_SOBRENOME, lut.getSobrenome());
        valores.put(Contract.Lutador.COLUNA_PESO, lut.getPeso());
        valores.put(Contract.Lutador.COLUNA_CATEGORIA, lut.getCategoria());

        database.insert(Contract.Lutador.TABELA_NOME, null, valores); //insere no banco de dados

        return true; //se inseriu
    }

    public Boolean alterar(Lutador lut){
        ContentValues valores = new ContentValues(); //objeto tupla(label da coluna valor)

        valores.put(Contract.Lutador.COLUNA_NOME, lut.getNome());
        valores.put(Contract.Lutador.COLUNA_SOBRENOME,lut.getSobrenome());
        valores.put(Contract.Lutador.COLUNA_PESO, lut.getPeso());
        valores.put(Contract.Lutador.COLUNA_CATEGORIA, lut.getCategoria());

        database.update(Contract.Lutador.TABELA_NOME, valores, Contract.Lutador.COLUNA_ID + "=" + lut.getId_lutador(), null);
        return true; //se alterou
    }

    /**
     * Métodod para excluir um contato do banco de dados
     */

    public Integer excluir(Lutador lut){
        //exclui do banco de dados
        return database.delete(Contract.Lutador.TABELA_NOME, Contract.Lutador.COLUNA_ID + "=" +lut.getId_lutador(), null);
    }

    /**
     * Método para buscar a lista de contatos sem filtro
     */

    public Cursor getLista(){
        //retorna o cursor para os registros contidos no banco de dados
        return database.rawQuery("SELECT * FROM " + Contract.Lutador.TABELA_NOME, null);
    }

    /**
     * Método para obter a lista de contatos, baseado no critério nome
     */

    public Cursor getListaByNome(String nome){
        //retorna o cursor para os registros contidos no banco de dados baseado no critério nomme
        return database.rawQuery("SELECT * FROM " + Contract.Lutador.TABELA_NOME + " WHERE nome LIKE " + nome + "%", null);
    }
}
