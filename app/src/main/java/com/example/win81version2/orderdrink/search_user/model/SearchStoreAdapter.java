package com.example.win81version2.orderdrink.search_user.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.win81version2.orderdrink.R;

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
    public SearchStoreAdapter(Context context, int viewResourceId, ArrayList<SearchStore> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<SearchStore>) items.clone();
        this.suggestions = new ArrayList<SearchStore>();
        this.viewResourceId = viewResourceId;
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
            TextView txtSumProduct = (TextView) v.findViewById(R.id.txtSumProduct_itemsearch);
            ImageView imgPhoto = (ImageView) v.findViewById(R.id.imgPhotoStore_itemsearch);
            if (txtStoreName != null) {
                txtStoreName.setText(search.getName());
            }
            if (txtSumProduct != null) {
                txtSumProduct.setText(String.valueOf(search.getSumProduct()));
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
}
