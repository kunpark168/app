package com.example.win81version2.orderdrink.search_user.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.win81version2.orderdrink.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
/**
 * Created by Win 8.1 Version 2 on 5/6/2017.
 */
public class SearchStoreAdapter extends ArrayAdapter<SearchStore> {
    private final String MY_DEBUG_TAG = "SearchStoreAdapter";
    private ArrayList<SearchStore> items;
    private ArrayList<SearchStore> itemsAll;
    private ArrayList<SearchStore> suggestions;
    private int viewResourceId;
    private double loUser, laUser, distance;
    public SearchStoreAdapter(Context context, int viewResourceId, ArrayList<SearchStore> items, double loUser, double laUser) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<SearchStore>) items.clone();
        this.suggestions = new ArrayList<SearchStore>();
        this.viewResourceId = viewResourceId;
        this.loUser = loUser;
        this.laUser = laUser;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        SearchStore search = items.get(position);
        if (search != null) {
            TextView txtStoreName = (TextView) v.findViewById(R.id.txtStoreName_itemsearch);
            TextView txtDistance = (TextView) v.findViewById(R.id.txtDistance_itemsearch);
            ImageView imgPhoto = (ImageView) v.findViewById(R.id.imgPhotoStore_itemsearch);
            if (search.getName() != null) {
                txtStoreName.setText(search.getName());
            }
            //Caculator Distance
            try {
                double loStore = search.getLo();
                double laStore = search.getLa();
                if (loUser != 0 && laUser != 0 && loStore != 0 && laStore != 0){
                    distance = calculationByDistance(laUser, loUser, loStore, laStore);
                    txtDistance.setText(String.valueOf(distance));
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            if (!search.getLinkPhoto().equals("")) {
                Glide.with(getContext())
                        .load(search.getLinkPhoto().toString())
                        .fitCenter()
                        .into(imgPhoto);
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((SearchStore) (resultValue)).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (SearchStore search : itemsAll) {
                    if (search.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(search);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<SearchStore> filteredList = (ArrayList<SearchStore>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (SearchStore c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }

    };

    //Caculator Distance
    public final double calculationByDistance(double lat1, double lon1, double lat2, double lon2) {
        int Radius = 6371;// radius of earth in Km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }
}
