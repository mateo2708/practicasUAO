package com.proyecto.mtg.practicas_uao;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lab.informatica on 17/04/2018.
 */

public class PracticasAdapter extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Practica> items;
    private double localLatitude, localLongitud;

    public PracticasAdapter(Activity activity, ArrayList<Practica> items, double lat, double lng) {
        this.activity = activity;
        this.items = items;
        this.localLatitude = lat;
        this.localLongitud = lng;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }
    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public void addAll(ArrayList<Practica> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }
    public Practica getIt(int position){
        return items.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.list_item, parent, false);
        }

        Practica dir = items.get(position);

        try {
            ImageView image = (ImageView) v.findViewById(R.id.Image);
            new LoadImage(image, dir.getFotografia()).execute();
        }
        catch(Exception e){
            System.out.println("Error al cargar la imágen "+ e);
        }

        TextView empresa = (TextView) v.findViewById(R.id.empresa);
        empresa.setText(dir.getnom_empres());

        TextView carrera = (TextView) v.findViewById(R.id.carrera);
        carrera.setText(dir.getProgramas());

        TextView distancia = (TextView) v.findViewById(R.id.distancia);
        distancia.setText(processDistance(dir.getSede()) + "m");

        return v;
    }


    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }


    public double[] processLocation(String loc) {
        int sep = loc.indexOf(',');
        int start = loc.indexOf('[');
        int fin = loc.indexOf(']');

        String latString = "";
        String lonString = "";

        latString = loc.substring(start + 1, sep);
        lonString = loc.substring(sep + 1, fin);

        double coordenadas[] = {0,0};

        coordenadas[0] = Double.parseDouble(latString);
        coordenadas[1] = Double.parseDouble(lonString);
        //Log.d("lectura", "processLocation: " + latString + "-----------" + lonString);

        return coordenadas;
    }

    public String processDistance(String loc) {
        double[] coordenadas = processLocation(loc);

        double distance = distance(localLatitude, coordenadas[0], localLongitud, coordenadas[1],0,0);

        String dist = "" + ((int) distance);
        Log.d("lectura", "processDistance: ");
        return dist;
    }

    public void inits(){}
}