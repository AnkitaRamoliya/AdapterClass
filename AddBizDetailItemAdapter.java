package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.models.mybiz.GetBizDetailResponse;
import com.oozeetech.bizdesk.utils.Utils;
import com.oozeetech.bizdesk.widget.DTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBizDetailItemAdapter extends RecyclerView.Adapter<AddBizDetailItemAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GetBizDetailResponse.Data.CBizDetail> items = new ArrayList<>();
    private LayoutInflater inflater;
    private String type;
    private String currencySymbol;

    public AddBizDetailItemAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bizz_detail_raw_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int i) {
        String text;
        DecimalFormat format = new DecimalFormat("00.00");
        GetBizDetailResponse.Data.CBizDetail data = items.get(i);
        Utils.setDrawableTint(vh.txtShippingPer, context.getResources().getDrawable(R.drawable.ic_shipping_), context.getResources().getColor(R.color.diamondGreen));

        vh.txtItemName.setText(data.getItemName());
        if (data.isIsShipping())
            vh.txtShippingPer.setText(data.getShipPer() + " %");
        else
            vh.txtShippingPer.setVisibility(View.GONE);
        vh.txtCrt.setText(data.getCrt() + " Crt");
//        vh.txtCut.setText(data.getCut() + " Cut");
//        vh.txtRPerCrt.setText(data.getPricePerCrt() + " $/Crt");
//        text = "Premium (%) : <font color = #0488d1>" + Math.round(data.getPremiumPer() * 100.00) / 100.00 + "</font> ";
//        vh.txtPremiumPer.setText(Html.fromHtml(text));
//        text = "Total : <font color = #0488d1>" + data.getTotalAmt() + "</font> ";
//        vh.txtTotalAmt.setText(Html.fromHtml(text));
        if (type.equals("Rough")) {
            vh.txtCut.setText(data.getCut() + " Cut");
            vh.txtRPerCrt.setText(data.getPricePerCrt() + " " + currencySymbol + "/Crt");
            text = "Premium (%) : <font color = #0488d1>" + format.format(data.getPremiumPer()) + "</font> ";
            vh.txtPremiumPer.setText(Html.fromHtml(text));
            double total = Double.parseDouble(data.getTotalAmt());
            text = "Total : <font color = #0488d1>" + format.format(total) + "</font> ";
            vh.txtTotalAmt.setText(Html.fromHtml(text) + currencySymbol);
        } else {
            //when bizDetail.getType().equals("Polish")
            //cut will be rate per price and rate per price will be total amount
            vh.txtCut.setVisibility(View.GONE);
            vh.txtTotalAmt.setVisibility(View.GONE);
            vh.txtRPerCrt.setText(data.getPricePerCrt() + " " + currencySymbol + "/Crt");
            double total = Double.parseDouble(data.getTotalAmt());
            text = "Total : <font color = #0488d1>" + format.format(total) + "</font> ";
            vh.txtPremiumPer.setText(Html.fromHtml(text) + currencySymbol);

//            vh.txtCut.setText(data.getPricePerCrt() + "$/Crt");
//            vh.txtRPerCrt.setText(data.getPricePerCrt() + "$");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public GetBizDetailResponse.Data.CBizDetail get(int position) {
        return items.get(position);
    }

    public void addAll(List<GetBizDetailResponse.Data.CBizDetail> list, String type, String currencySymbol) {
        this.type = type;
        this.currencySymbol = currencySymbol;

        items.addAll(list);
        notifyDataSetChanged();
    }

    public void add(GetBizDetailResponse.Data.CBizDetail data) {
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

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtItemName)
        DTextView txtItemName;
        @BindView(R.id.txtShippingPer)
        DTextView txtShippingPer;
        @BindView(R.id.txtCrt)
        DTextView txtCrt;
        @BindView(R.id.txtCut)
        DTextView txtCut;
        @BindView(R.id.txtRPerCrt)
        DTextView txtRPerCrt;
        @BindView(R.id.txtPremiumPer)
        DTextView txtPremiumPer;
        @BindView(R.id.txtTotalAmt)
        DTextView txtTotalAmt;
        @BindView(R.id.ll4RoughBiz)
        LinearLayout ll4RoughBiz;
        @BindView(R.id.SingleRowItem)
        LinearLayout SingleRowItem;
        @BindView(R.id.activity_main)
        LinearLayout activityMain;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
