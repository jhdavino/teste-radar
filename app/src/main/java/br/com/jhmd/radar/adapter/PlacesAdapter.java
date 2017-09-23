package br.com.jhmd.radar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.jhmd.radar.R;
import br.com.jhmd.radar.model.PlaceMap;

/**
 * Created by josehenrique on 22/09/17.
 */


public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private List<PlaceMap> palces = null;


    public PlacesAdapter( List<PlaceMap> placesList) {

        this.palces = placesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setDados(palces.get(position));
    }

    @Override
    public int getItemCount() {
        return palces.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvAddress;
        private final TextView tvPhoneNumber;
        private final Context context;

        private ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.tv_phone_number);
            context = itemView.getContext();

        }

        private void setDados(PlaceMap place) {
            tvName.setText(place.getName());
            tvAddress.setText(place.getAdrress());
            tvPhoneNumber.setText((place.getPhoneAddress().trim().length() < 1 ?
                    context.getResources().getText(R.string.label_notfound) :
                    place.getPhoneAddress().trim()));
        }

    }
}