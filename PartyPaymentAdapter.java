package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.models.payment.GetPaymentOutstandingResponse;
import com.oozeetech.bizdesk.widget.DTextView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartyPaymentAdapter extends RecyclerView.Adapter<PartyPaymentAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GetPaymentOutstandingResponse.Data.List> items = new ArrayList<>();
    private LayoutInflater inflater;

    public PartyPaymentAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.dashboard_payment_raw_item_temp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int i) {
        GetPaymentOutstandingResponse.Data.List data = items.get(i);
        vh.txtPartyName.setText(data.getPartyName());
        vh.txtPaymentDate.setText(data.getPaymentDate());
        vh.txtDate.setText(data.getSaleDate());
        vh.txtDueDays.setText(data.getDueDay());
        vh.txtCurrencySymbol.setText(MessageFormat.format("{0} ", data.getCurrancySymbol()));
        vh.txtPaymentOutstanding.setText(data.getOutstandingAmount());
    }

    public GetPaymentOutstandingResponse.Data.List get(int position) {
        return items.get(position);
    }

    public void addAll(List<GetPaymentOutstandingResponse.Data.List> list) {

        items.addAll(list);
        notifyDataSetChanged();
    }

    public void add(GetPaymentOutstandingResponse.Data.List data) {
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
        @BindView(R.id.txtPartyName)
        DTextView txtPartyName;
        @BindView(R.id.txtDate)
        DTextView txtDate;
        @BindView(R.id.txtPaymentDate)
        DTextView txtPaymentDate;
        @BindView(R.id.txtPaymentOutstanding)
        DTextView txtPaymentOutstanding;
        @BindView(R.id.txtDueDays)
        DTextView txtDueDays;
        @BindView(R.id.activity_main)
        LinearLayout activityMain;
        @BindView(R.id.txtCurrencySymbol)
        DTextView txtCurrencySymbol;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
