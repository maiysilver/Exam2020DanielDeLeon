package com.example.examdanieldeleonmairena;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainRecycler extends AppCompatActivity {

    ArrayList<TodoListt> listaToDo;
    RecyclerView recycler;
    ConexionSQL conex;
    EditText id, nom, tel;
    public static String TABLA ="todolist";
    public static String ID ="id";
    public static String TEXT="texto";
    public static String FECHA_INICIO ="fecha_ini";
    public static String FECHA_FINAL ="fecha_fin";
    public static String COMPLETE = "checkbox";
    public static final String CREAR_TABLA = "CREATE TABLE "+ TABLA +" ("+ ID +" INTEGER, "+TEXT+" TEXT, "+ FECHA_INICIO +" DATE, "+ FECHA_FINAL +" DATE, "+COMPLETE+" INTEGER )";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_recycler);
        try{
            conex =new ConexionSQL(getApplicationContext(),"bd_usuarios",null,1);
            SQLiteDatabase db= conex.getWritableDatabase();
            db.execSQL(MainRecycler.CREAR_TABLA);
        }catch(Exception e){
            conex =new ConexionSQL(getApplicationContext(),"bd_usuarios",null,1);
        }


        /*id= (EditText) findViewById(R.id.input_id);
        nom= (EditText) findViewById(R.id.input_nom);
        tel= (EditText) findViewById(R.id.input_tel);*/


        listaToDo =new ArrayList<>();
        recycler =(RecyclerView)findViewById(R.id.recycle);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        listener_recicler();
        boton_fab();
        //consultaNoCompletos();
        consultar();
        Adapter adapter=new Adapter(listaToDo);
        recycler.setAdapter(adapter);
    }

    public void listener_recicler(){
        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recycler,new RecyclerItemClickListener.OnItemClickListener() {
                    String id="";
                    String textt ="";
                    String fech_inn ="";
                    String fech_fii ="";
                    String checkk ="";
                    @Override public void onItemClick(View view, int position) {
                        try{
                        SQLiteDatabase db= conex.getReadableDatabase();
                        TodoListt todoListt =null;
                        Cursor cursor =db.rawQuery("SELECT * FROM "+ TABLA,null);
                        int index=0;
                        while (cursor.moveToNext()){
                            todoListt =new TodoListt();
                            todoListt.setId(cursor.getInt(0));
                            todoListt.setTexto(cursor.getString(1));
                            todoListt.setFecha_ini(cursor.getString(2));
                            todoListt.setFecha_fin(cursor.getString(3));
                            todoListt.setCheckbox(cursor.getInt(4));
                            listaToDo.add(todoListt);
                            id= String.valueOf(listaToDo.get(position).getId());
                            textt = listaToDo.get(position).getTexto();
                            fech_inn = listaToDo.get(position).getFecha_ini();
                            fech_fii = listaToDo.get(position).getFecha_ini();
                            checkk = String.valueOf(listaToDo.get(position).getCheckbox());
                            index++;
                        }
                        }catch(Exception e){
                            toastea(("Error al cargar"));
                        }
                        cargar_ventana_detalle(id, textt, fech_inn, fech_fii, checkk );
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        //eliminar(String.valueOf(position));
                    }
                })
        );
    }

    /*public void eliminar(View view) {
        if(!id.getText().toString().equals("")){
            SQLiteDatabase db= conex.getWritableDatabase();
            String[] parametros = {id.getText().toString()};
            db.delete(TABLA,ID+"=?",parametros);
            toastea(("Contacto eliminado!"));
            db.close();
            consultar();
            Adapter adapter=new Adapter(listaToDo);
            recycler.setAdapter(adapter);
            id.setText("");
        }else{
            toastea(("Introduce una ID para eliminar contacto!"));
        }
    }*/

    public void actualizar(View view) {
        if(!id.getText().toString().equals("") && !nom.getText().toString().equals("") && !tel.getText().toString().equals("")){
            SQLiteDatabase db= conex.getWritableDatabase();
            String[] parametros = {id.getText().toString()};
            ContentValues values = new ContentValues();
            values.put(FECHA_INICIO,nom.getText().toString());
            values.put(FECHA_FINAL,tel.getText().toString());
            db.update(TABLA,values,ID+"=?",parametros);
            toastea(("Contacto "+nom.getText().toString()+" actualizado!"));
            db.close();
            consultar();
            Adapter adapter=new Adapter(listaToDo);
            recycler.setAdapter(adapter);
            id.setText("");
            nom.setText("");
            tel.setText("");
        }else{
            toastea(("Introduce ID, Nombre y telefono para actualizar!"));
        }
    }
/*
    public void buscar(View view){
        if(!id.getText().toString().equals("") || !nom.getText().toString().equals("")){
            listaToDo =new ArrayList<>();
            SQLiteDatabase db= conex.getReadableDatabase();
            TodoListt todoListt =null;
            Cursor cursor =db.rawQuery("SELECT * FROM "+ TABLA,null);
            while (cursor.moveToNext()){
                if(String.valueOf(cursor.getInt(0)).equals(id.getText().toString())){
                    todoListt =new TodoListt();
                    todoListt.setId(cursor.getInt(0));
                    todoListt.setNombre(cursor.getString(1));
                    todoListt.setTelefono(cursor.getString(2));
                    listaToDo.add(todoListt);
                }else if(cursor.getString(1).equals(nom.getText().toString())){
                    todoListt =new TodoListt();
                    todoListt.setId(cursor.getInt(0));
                    todoListt.setNombre(cursor.getString(1));
                    todoListt.setTelefono(cursor.getString(2));
                    listaToDo.add(todoListt);
                }
            }
            Adapter adapter=new Adapter(listaToDo);
            recycler.setAdapter(adapter);
            id.setText("");
            nom.setText("");
        }else{
            toastea(("Introduce ID o Nombre para buscar un contacto!"));
            consultar();
            Adapter adapter=new Adapter(listaToDo);
            recycler.setAdapter(adapter);
        }
    }*/

    public void boton_fab(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventana_emergente();
            }
        });
    }

    public void ventana_emergente(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Añadir tarea:");
        //final EditText input_id = new EditText(this);
        final EditText input_texto = new EditText(this);
        //input_id.setInputType(InputType.TYPE_CLASS_PHONE | InputType.TYPE_CLASS_PHONE);
        input_texto.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
       // input_id.setHint("Id");
       // layout.addView(input_id);
        input_texto.setHint("Texto");
        layout.addView(input_texto);
        builder.setView(layout);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String  m_id = String.valueOf(listaToDo.size()+1);
                String  m_texto = input_texto.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();
                String fecha = dateFormat.format(date);
                String  m_fechaini= fecha;
                Insertar(m_id, m_fechaini,m_texto);
                toastea(("Item "+m_texto+" - "+m_fechaini+" añadido!"));
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                toastea(("item no añadido!"));
            }
        });
        builder.show();
    }

    private void Insertar(String campoId, String campoNombre, String campofechain) {
        ConexionSQL conn = new ConexionSQL(this, "bd_usuarios", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, campoId);
        values.put(TEXT, campoNombre);
        values.put(FECHA_INICIO, campofechain);
        values.put(FECHA_FINAL, "null");
        values.put(COMPLETE, 0);
        Long idResultante = db.insert(TABLA, ID, values);
        toastea(("contacto añadido!"));
        db.close();
        consultar();
        Adapter adapter=new Adapter(listaToDo);
        recycler.setAdapter(adapter);
    }

    public void toastea(String tosta){
        String tostada= tosta;
        Toast toast1 =
                Toast.makeText(this, tostada, Toast.LENGTH_SHORT);
        toast1.show();
    }

    public void consultaNoCompletos(View view){
        listaToDo =new ArrayList<>();
        SQLiteDatabase db= conex.getReadableDatabase();
        TodoListt todoListt =null;
        Cursor cursor =db.rawQuery("SELECT * FROM "+ TABLA+" WHERE "+COMPLETE+" = 0 ORDER BY ID ASC",null);
        while (cursor.moveToNext()){
            todoListt =new TodoListt();
            todoListt.setId(cursor.getInt(0));
            todoListt.setTexto(cursor.getString(1));
            todoListt.setFecha_ini(cursor.getString(2));
            todoListt.setFecha_fin(cursor.getString(3));
            todoListt.setCheckbox(cursor.getInt(4));
            listaToDo.add(todoListt);
        }
        Adapter adapter=new Adapter(listaToDo);
        recycler.setAdapter(adapter);
    }

    public void consultaCompletos(View view){
        listaToDo =new ArrayList<>();
        SQLiteDatabase db= conex.getReadableDatabase();
        TodoListt todoListt =null;
        Cursor cursor =db.rawQuery("SELECT * FROM "+ TABLA+" WHERE "+COMPLETE+" = 1 ORDER BY ID ASC",null);
        while (cursor.moveToNext()){
            todoListt =new TodoListt();
            todoListt.setId(cursor.getInt(0));
            todoListt.setTexto(cursor.getString(1));
            todoListt.setFecha_ini(cursor.getString(2));
            todoListt.setFecha_fin(cursor.getString(3));
            todoListt.setCheckbox(cursor.getInt(4));
            listaToDo.add(todoListt);
        }
        Adapter adapter=new Adapter(listaToDo);
        recycler.setAdapter(adapter);
    }

    private void consultar() {
        listaToDo =new ArrayList<>();
        SQLiteDatabase db= conex.getReadableDatabase();
        TodoListt todoListt =null;
        Cursor cursor =db.rawQuery("SELECT * FROM "+ TABLA+" WHERE "+COMPLETE+" = 0 ORDER BY ID ASC",null);
        //Cursor cursor =db.rawQuery("SELECT * FROM "+ TABLA,null);
        while (cursor.moveToNext()){
            todoListt =new TodoListt();
            todoListt.setId(cursor.getInt(0));
            todoListt.setTexto(cursor.getString(1));
            todoListt.setFecha_ini(cursor.getString(2));
            todoListt.setFecha_fin(cursor.getString(3));
            todoListt.setCheckbox(cursor.getInt(4));
            listaToDo.add(todoListt);
        }
    }

    public void cargar_ventana_detalle(String id, String tex, String fechain, String fechafi, String check){
        Intent intent = new Intent(this, pantallaDetalles.class);
        String dato=null;
        intent.putExtra("id", id);
        intent.putExtra("texto", tex);
        intent.putExtra("fecha_ini", fechain);
        intent.putExtra("fecha_fin", fechafi);
        intent.putExtra("check", check);
        this.startActivity(intent);
    }


}
