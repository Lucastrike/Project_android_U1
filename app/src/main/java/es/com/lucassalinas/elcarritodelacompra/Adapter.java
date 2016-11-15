package es.com.lucassalinas.elcarritodelacompra;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alumno_solvam on 9/11/16.
 */

public class Adapter extends BaseAdapter{

    private Activity myActivity;
    private ArrayList<Articulo> articulos;

    public Adapter(Activity myActivity, ArrayList<Articulo> articulos){
        this.myActivity = myActivity;
        this.articulos = articulos;
    }

    @Override
    public int getCount() {
        return articulos.size();
    }

    @Override
    public Object getItem(int position) {
        return articulos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View item = convertView;
        ViewHolder vistaTag;

        if (item==null){
            LayoutInflater inflater = myActivity.getLayoutInflater();
            item = inflater.inflate(R.layout.list_design, null);

            vistaTag = new ViewHolder();
            vistaTag.textView = (TextView) item.findViewById(R.id.itemId);

            item.setTag(vistaTag);
        }
        else {
            vistaTag = (ViewHolder)item.getTag();
        }

        vistaTag.textView.setText(articulos.get(position).getNombre());

        return (item);
    }
}
