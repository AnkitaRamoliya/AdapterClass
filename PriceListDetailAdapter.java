package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.models.pricelist.GetPriceListResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class PriceListDetailAdapter extends RecyclerView.Adapter<PriceListDetailAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GetPriceListResponse.Data> items = new ArrayList<>();
    private LayoutInflater inflater;

    public PriceListDetailAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.price_detail_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int i) {

    }

    public GetPriceListResponse.Data get(int position) {
        return items.get(position);
    }

    public void addAll(List<GetPriceListResponse.Data> getPartyResponses) {

        items.addAll(getPartyResponses);
        notifyDataSetChanged();
    }

    public void add(GetPriceListResponse.Data data) {
        items.add(data);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
