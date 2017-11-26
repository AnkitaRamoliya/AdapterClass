package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.models.pricelist.GetPriceMasterDetailResponse;
import com.oozeetech.bizdesk.widget.DTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetPriceListDetailAdapter extends RecyclerView.Adapter<GetPriceListDetailAdapter.ViewHolder> {

    DTextView txtSizeName;
    DTextView txtCrt;
    DTextView txtPricePerCrt;
    private Context context;
    private ArrayList<GetPriceMasterDetailResponse.Data> items = new ArrayList<>();
    private LayoutInflater inflater;

    public GetPriceListDetailAdapter(Context context) {
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
        GetPriceMasterDetailResponse.Data data = items.get(i);

        vh.txtRoughType.setText(data.getRoughType());

        for (int j = 0; j < data.getSizeDetail().size(); j++) {
            View v = LayoutInflater.from(context).inflate(R.layout.price_detail_row_data, null);
            findViews(v);
            txtSizeName.setText(data.getSizeDetail().get(j).getSizeName());
            txtCrt.setText(data.getSizeDetail().get(j).getCrt());
            txtPricePerCrt.setText(String.format("$%s", data.getSizeDetail().get(j).getPricePerCrt()));
            vh.llType.addView(v);
        }

    }

    private void findViews(View v) {
        txtSizeName = v.findViewById(R.id.txtSizeName);
        txtCrt = v.findViewById(R.id.txtCrt);
        txtPricePerCrt = v.findViewById(R.id.txtPricePerCrt);
    }

    public void addAll(List<GetPriceMasterDetailResponse.Data> getPartyResponses) {
        items.clear();
        items.addAll(getPartyResponses);
        notifyDataSetChanged();
    }

    public void add(GetPriceMasterDetailResponse.Data data) {
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

        View view;
        @BindView(R.id.txtRoughType)
        DTextView txtRoughType;
        @BindView(R.id.llType)
        LinearLayout llType;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }
}
