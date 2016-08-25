package com.example.aluno.geronimo_projeto.control;


import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.SupportActionModeWrapper;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.Toast;

import com.example.aluno.geronimo_projeto.R;
import com.example.aluno.geronimo_projeto.adapter.ListAdapter;
import com.example.aluno.geronimo_projeto.dao.Contract;
import com.example.aluno.geronimo_projeto.dao.LutadorDAO;
import com.example.aluno.geronimo_projeto.modelo.Lutador;

 public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, ActionBar.TabListener {


     //variáveis globais

     private LutadorDAO lutDAO; //objeto de acesso ao banco de dados
     private Lutador lut; //objeto de modelo de dados
     private EditText etNome, etSobrenome, etPeso, etCategoria; //objeto campo de texto da UI
     private ListView lvListagem; //objeto lista da UI
     ActionBar.Tab tab2;
     //as abas da activity, quando em smartphobe

    /*---------------------------------------
        Métodos do ciclo de vida da Activity
    ----------------------------------------*/


     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         //obtém uma instância da DAO para abrir a conexão com o banco de dados
         lutDAO = LutadorDAO.getInstance(MainActivity.this);
         lutDAO.open(); //abre a conexão

         lut = new Lutador();//inicializa a instância da classe modelo

         //testa o screen para definir o layout

         if (isTablet(MainActivity.this)) {
             setContentView(R.layout.activity_tablet); //associa o layout  do tablet à activity

             //mapeia os componentes de activity_tablet.xml

             etNome = (EditText) findViewById(R.id.editText_nome);
             etSobrenome = (EditText) findViewById(R.id.editText_sobrenome);
             etCategoria = (EditText) findViewById(R.id.editText_categoria);
             etPeso = (EditText) findViewById(R.id.editText_peso);
             lvListagem = (ListView) findViewById(R.id.listView);
             lvListagem.setOnItemClickListener(MainActivity.this);//registra o tratador de eventos para cada item da listView

             carregarListView(lutDAO.getLista());//chama o método que vincula os dados do banco no ListView

             //cria a ActionBar
             ActionBar actionBar = getSupportActionBar();
             actionBar.setTitle("Lutadores");
             actionBar.setIcon(R.drawable.ic_action_good);

             controlarEdicao(false);
             carregarListView(lutDAO.getLista());//chama o método que vincula os dados do banco à ListView
             lvListagem.setOnItemClickListener(MainActivity.this);//registra o tratadpr de eventos para cada item do ListView
         } else {
             //cria a ActionBar
             ActionBar actionBar = getSupportActionBar();
             actionBar.setTitle("Lutadores");
             actionBar.setIcon(R.drawable.ic_action_good);
             actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);//diz que haverá navegação de alto nível por meio de abas(tabs)
            /*
                cria as abas e adiciona à ActionBar
             */
             //tab1
             ActionBar.Tab tab1 = actionBar.newTab().setText("Lista de lutadores");
             tab1.setTabListener(MainActivity.this);
             actionBar.addTab(tab1);



             //tab2
             tab2 = actionBar.newTab().setText("Lutadores");
             tab2.setTabListener(MainActivity.this);
             actionBar.addTab(tab2);
         }
     }

     @Override
     public void onDestroy() {
         super.onDestroy();
         lutDAO.close();
     }

     private void carregarListView(Cursor cursor) {
         final String[] DE = {Contract.Lutador.COLUNA_NOME, Contract.Lutador.COLUNA_SOBRENOME, Contract.Lutador.COLUNA_CATEGORIA,
                 Contract.Lutador.COLUNA_PESO};//DE{colunas do banco de dados}
         final int[] PARA = {R.id.tv_item_nome, R.id.tv_item_sobrenome, R.id.tv_item_categoria, R.id.tv_item_peso};//PARA{Views para onde irão os dados do cursor}

         //cria um objeto da classe ListAdapter , um adaptador Cursor -> ListView
         ListAdapter dadosadapter = new ListAdapter(MainActivity.this,//contexto da aplicação
                 R.layout.adapter_layout,//layout de cada item da lista
                 cursor,
                 DE,
                 PARA,
                 0);
         lvListagem.setAdapter(dadosadapter);//associa o adaptador à ListView
     }

     private void controlarEdicao(boolean enabled) {
         //controla o campo de edição da UI
         etNome.setEnabled(enabled);
         etSobrenome.setEnabled(enabled);
         etCategoria.setEnabled(enabled);
         etPeso.setEnabled(enabled);

     }

     private static boolean isTablet(Context context) {
         return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                 >= Configuration.SCREENLAYOUT_SIZE_LARGE;
     }

    /*------------------------------
    *
    *   Trata eventos das abas da actionBar
    * */

     @Override
     public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
         switch (tab.getPosition()) {
             case 0: {
                 setContentView(R.layout.activity_smartphone_list);

                 //mapeia os componentes da activity_list.xml
                 lvListagem = (ListView) findViewById(R.id.listView);
                 lvListagem.setOnItemClickListener(MainActivity.this);//registra o tratador de eventos para cada item da listView

                 carregarListView(lutDAO.getLista());//chama o método que vincula os dados do banco no ListView
                 break;
             }

             case 1: {
                 setContentView(R.layout.activity_smartphone_inputs);
                 //mapeio os componentes de activity_inputs.xml
                 etNome = (EditText) findViewById(R.id.editText_nome);
                 etSobrenome = (EditText) findViewById(R.id.editText_sobrenome);
                 etPeso = (EditText) findViewById(R.id.editText_peso);
                 etCategoria = (EditText) findViewById(R.id.editText_categoria);

                 controlarEdicao(true);//inicializa a edição da UI

                 break;
             }
         }
     }

     @Override
     public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

     }

     @Override
     public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

     }


     /*-------------------------------------------
     * Criação de tratamentos e eventos da ActionBar
     * */

     @Override
     public boolean onCreateOptionsMenu (Menu menu){
         //Infla os menus; adiciona items à ActionBar
         getMenuInflater().inflate(R.menu.menu, menu);//infla o arquivo menu.xml na ActionBar
        // SearchView mySearchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView();//obtem a sercView
         //mySearchView.setQueryHint("digite o nome");//coloca um hint na searchview
         //mySearchView.setOnQueryTextListener(MainActivity.this);//cadastra o tratador de eventos na lista de tratadores da SearchView
         return true;
     }


     @Override
     public boolean onOptionsItemSelected(MenuItem item){
         switch (item.getItemId()){
             case R.id.menuitem_novo:{
                 novo();
                 return true;
             }
             case R.id.menuitem_salvar:{
                 salvar();
                 return true;
             }
             case R.id.menuitem_excluir:{
                 excluir();
                 return true;
             }
             case R.id.menuitem_cancelar:{
                 cancelar();
                 return true;
             }
         }
         return false;
     }

     /*
        Tratador (Handler) da ListView
     */

     @Override
     public void onItemClick(AdapterView<?> adpListView, View view, int position, long id){
         //obtém o cursor para o registro selecionado na ListView

         Cursor cursor = (Cursor) adpListView.getItemAtPosition(position);

         if(!isTablet(MainActivity.this)){
             tab2.select();//seleciona a aba 2
             onTabSelected(tab2, null); //chama o tratador de eventos para carregar os componentes
         }
         //obtém os dados desse cursor
         lut.setId_lutador(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Lutador.COLUNA_ID)));
         etNome.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Lutador.COLUNA_NOME)));
         etSobrenome.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Lutador.COLUNA_SOBRENOME)));
         etPeso.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Lutador.COLUNA_PESO)));
         etCategoria.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Lutador.COLUNA_CATEGORIA)));
         controlarEdicao(true);
     }

     /*
     /  Método para limpar os campos da UI
     */
     public void limparFormulario(){
         etNome.setText(null);
         etSobrenome.setText(null);
         etPeso.setText(null);
         etCategoria.setText(null);
     }

     public void caixaDeDialogoSimNao(){
     // cria o objeto de caixa de diálogo
         AlertDialog.Builder cxDialogo = new AlertDialog.Builder(this);
         //define o título
         cxDialogo.setTitle("Você tem certeza que quer excluir esse lutador");
         //define a mensagem
         cxDialogo.setMessage("Esse lutador será apagado para sempre do dispositivo");
         //define um botão SIM
         cxDialogo.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 //chame aqui o método excluir da dao. Coloque o Toast em um if, pois pode ocorrer uma exceção ao excluir
                 if(lutDAO.excluir(lut) != 0){
                    carregarListView(lutDAO.getLista()); //cursor com os registros retornados pela ocnsulta (como um ResultSet em um JDBC);
                 }
                 limparFormulario();
                 controlarEdicao(false);
                 Toast.makeText(MainActivity.this, "Contato excluído", Toast.LENGTH_LONG).show();

             }
         });
         //define um botão NÃO
         cxDialogo.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 limparFormulario();
                 controlarEdicao(false);
                 Toast.makeText(MainActivity.this, "Não foi excluído", Toast.LENGTH_LONG).show();
             }
         });
         /*
            Emite a caixa de diálogo em forma de alerta
          */
         AlertDialog alert = cxDialogo.create();
         alert.show();
     }

     /*-----------------------
     *
     * Operações CRUD
     *
     * */

    /*
        Método respon´savel por (re)instanciar um novo objeto da classe modelo

     */
     public void novo(){
         lut = new Lutador();
         controlarEdicao(true);
         etNome.requestFocus();
     }

     /*
     * Método responsável por salvar um onjeto no banco de dados
     * */

     public void salvar(){
         if(etNome.getText().toString().isEmpty()|| etSobrenome.getText().toString().isEmpty()
                 || etPeso.getText().toString().isEmpty() || etCategoria.getText().toString().isEmpty()){
             Toast.makeText(MainActivity.this, "Preencha os todos campos", Toast.LENGTH_LONG).show();
         }
         else{
             lut.setNome(etNome.getText().toString());
             lut.setSobrenome(etSobrenome.getText().toString());
             lut.setPeso(Double.valueOf(etPeso.getText().toString()));
             lut.setCategoria(etCategoria.getText().toString());

             if(lutDAO.salvar(lut)){
                 Toast.makeText(this, "Lutador salvo", Toast.LENGTH_LONG).show();
                 limparFormulario();
                 controlarEdicao(false);
                 carregarListView(lutDAO.getLista());
             }
         }
     }

     /*
     * Método responsável por cancelar todas as operações CRUD
     * */

     public void cancelar(){
         limparFormulario();
         controlarEdicao(false);
     }

     /*
        Método reponsável por controlar as operações de exclusão
      */

     public void excluir(){
         if(etNome.getText().toString().isEmpty() &&
                 etSobrenome.getText().toString().isEmpty() &&
                 etPeso.getText().toString().isEmpty() &&
                 etCategoria.getText().toString().isEmpty()){
             Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
         }
         else{
             caixaDeDialogoSimNao();
         }
     }

     @Override
     public boolean onQueryTextSubmit(String query) {
         return false;
     }

     @Override
     public boolean onQueryTextChange(String nome) {
         if(nome.isEmpty()) { //se a SearchView estiver vazia
            carregarListView(lutDAO.getLista());
         }
         else{
             carregarListView(lutDAO.getListaByNome(nome));
         }

         return false;
     }


 }

