package com.example.examdanieldeleonmairena;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class pantallaDetalles  extends AppCompatActivity {

    private static final String TAG_ACTIVITY = pantallaDetalles.class.getSimpleName();
    private TextView texto1;
    private TextView fecha;
    private TextView fecha2;
    private String info_id;
    private String info_text;
    private String info_fechini;
    private String info_check;
    EditText  nom, tel;
    LinearLayout layout;
    private Boolean bo=true;
    ArrayList<TodoListt> listaToDo;
    RecyclerView recycler;
    ConexionSQL conex;
    public static String TABLA ="todolist";
    public static String ID ="id";
    public static String TEXT="texto";
    public static String FECHA_INICIO ="fecha_ini";
    public static String FECHA_FINAL ="fecha_fin";
    public static String COMPLETE = "checkbox";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_detalle);
        conex =new ConexionSQL(getApplicationContext(),"bd_usuarios",null,1);
        layout= findViewById(R.id.finishDateLayout);
        texto1 = (TextView)findViewById(R.id.task);
        fecha = (TextView)findViewById(R.id.fechaa);
        fecha2 = (TextView)findViewById(R.id.fechaa2);
       /* nom= (EditText) findViewById(R.id.input_nom);
        tel= (EditText) findViewById(R.id.input_tel);*/
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null) {
            info_id = (String) b.get("id");
            info_text =(String) b.get("fecha_ini");
            texto1.setText(info_text);
            info_fechini =(String) b.get("texto");
            fecha.setText(info_fechini);
            info_check= (String) b.get("check");
        }
    }

    public void checkear(View view){
        if(bo){
            layout.setVisibility(View.VISIBLE);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            String fecha = dateFormat.format(date);
            fecha2.setText(fecha);
            info_check="1";
            bo=false;
        }else{
            layout.setVisibility(View.INVISIBLE);
            info_check="0";
            bo=true;
        }
    }

    public void actualizar(View view) {
            //ConexionSQL conn = new ConexionSQL(this, "bd_usuarios", null, 1);
            SQLiteDatabase db= conex.getWritableDatabase();
            //String[] parametros = {info_text};
            String[] parametros = {info_id};
            ContentValues values = new ContentValues();
            values.put(ID, info_id);
            values.put(TEXT, texto1.getText().toString());
            values.put(FECHA_INICIO, info_fechini);
            values.put(FECHA_FINAL, fecha2.getText().toString());
            values.put(COMPLETE, info_check);
            db.update(TABLA,values,ID+"=?",parametros);
            //MainRecycler.toastea(("Contacto "+nom.getText().toString()+" actualizado!"));
            db.close();
            //consultar();
            Adapter adapter=new Adapter(listaToDo);
            recycler.setAdapter(adapter);


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
        //toastea(("contacto añadido!"));
        db.close();
        //consultar();
        Adapter adapter=new Adapter(listaToDo);
        recycler.setAdapter(adapter);
    }

}

