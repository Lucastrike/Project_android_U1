package es.com.lucassalinas.elcarritodelacompra;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    static ArrayList<Articulo> lista;
    static Adapter listaCompra;
    static EditText nuevoArticulo;
    static EditText edit;
    TextView comprado;
    LinearLayout layout;
    RelativeLayout layoutNuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listview);
        lista = new ArrayList<Articulo>();

        listaCompra = new Adapter(this, lista);

        listView.setAdapter(listaCompra);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                comprado = (TextView) view.findViewById(R.id.itemId);
                if (lista.get(position).isComprado()==false)	{
                    comprado.setPaintFlags(comprado.getPaintFlags()	|
                            Paint.STRIKE_THRU_TEXT_FLAG);
                    comprado.setTextColor(Color.parseColor("#FF4081"));
                    lista.get(position).setComprado(true);
                    Toast.makeText(getApplicationContext(), "Has comprado "+lista.get(position).getNombre(), Toast.LENGTH_SHORT).show();
                }	else {
                    comprado.setPaintFlags(comprado.getPaintFlags()
                            & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    comprado.setTextColor(Color.parseColor("#00a5ff"));
                    lista.get(position).setComprado(false);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View viewDialog = inflater.inflate(R.layout.dialog,null);

                builder.setView(viewDialog)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                nuevoArticulo = (EditText) viewDialog.findViewById(R.id.nuevoArticulo);
                                String nuevoArt = nuevoArticulo.getText().toString();
                                nuevoArt = nuevoArt.trim();

                                if (nuevoArt.length()<=0){
                                    Toast.makeText(getApplicationContext(), "Debes añadir un artículo", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    lista.add(new Articulo(nuevoArt));
                                    listaCompra.notifyDataSetChanged();
                                }

                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
                            }
                        });

                builder.create().show();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        layout = (LinearLayout) findViewById(R.id.content_main);


        //layoutNuevo = new RelativeLayout(getApplicationContext(), R.layout.dialog);
        //noinspection SimplifiableIfStatement
        if (id == R.id.modo_noche) {
            layout.setBackgroundColor(Color.BLACK);
            //layoutNuevo.setBackgroundColor(Color.BLACK);
        }
        else {
            layout.setBackgroundColor(Color.WHITE);
            //layoutNuevo.setBackgroundColor(Color.WHITE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(lista.get(info.position).getNombre());
        inflater.inflate(R.menu.item_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {

            case R.id.EditTextOp:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.edit_dialog, null);

                edit = (EditText) view.findViewById(R.id.editText);
                edit.setText(lista.get(info.position).getNombre());
                edit.setSelection(edit.getText().length());

                builder.setView(view)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String editarArt = edit.getText().toString();
                                editarArt = editarArt.trim();
                                if (editarArt.length()<=0) {
                                    Toast.makeText(getApplicationContext(), "El artículo debe tener un nombre", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    lista.get(info.position).setNombre(edit.getText().toString());
                                    listaCompra.notifyDataSetChanged();
                                }

                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
                            }
                        });

                builder.create().show();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                return true;
            case R.id.BorraTextOp:
                lista.remove(info.position);
                listaCompra.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
