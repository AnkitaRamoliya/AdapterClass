package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.listener.ItemListener;
import com.oozeetech.bizdesk.models.mybiz.AddNewBizRequest;
import com.oozeetech.bizdesk.utils.Utils;
import com.oozeetech.bizdesk.widget.DButton;
import com.oozeetech.bizdesk.widget.DTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddItemAdapter extends RecyclerSwipeAdapter<AddItemAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AddNewBizRequest.CBizDetail> items = new ArrayList<>();
    private LayoutInflater inflater;
    private String symbol;
    private ItemListener itemListener;

    public AddItemAdapter(Context context, ItemListener itemListener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.itemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.add_new_bizz_raw_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int i) {
        String text;
        DecimalFormat format = new DecimalFormat("00.00");
        Utils.setDrawableTint(vh.txtShippingPer, context.getResources().getDrawable(R.drawable.ic_shipping_), context.getResources().getColor(R.color.diamondGreen));

        AddNewBizRequest.CBizDetail bizDetail = items.get(i);
        vh.txtItemName.setText(bizDetail.getItemName());
        if (bizDetail.isIsShipping1()) {
            vh.txtShippingPer.setVisibility(View.VISIBLE);
            vh.txtShippingPer.setText(bizDetail.getShipPer() + " %");
        } else
            vh.txtShippingPer.setVisibility(View.GONE);

        vh.txtCrt.setText(bizDetail.getCrt() + " Crt");
        if (bizDetail.getType().equals("Rough")) {
            vh.txtCut.setText(bizDetail.getCut() + " Cut");
            vh.txtRPerCrt.setText(bizDetail.getPricePerCrt() + symbol + "/Crt");

            text = "Premium (%) : <font color = #0488d1>" + format.format(bizDetail.getPremiumPer()) + "</font> ";
            vh.txtPremiumPer.setText(Html.fromHtml(text));
            double finalAmount = getFinalAmount(bizDetail);
            text = "Total : <font color = #0488d1>" + format.format(finalAmount) + "</font> ";
            vh.txtTotalAmt.setText(Html.fromHtml(text) + symbol);
        } else {
            //when bizDetail.getType().equals("Polish")
            //cut will be rate per price and rate per price will be total amount
            vh.txtTotalAmt.setVisibility(View.GONE);
            vh.txtPremiumPer.setVisibility(View.GONE);
            vh.txtCut.setText(bizDetail.getPricePerCrt() + symbol + "/Crt");
            double finalAmount = getFinalAmount(bizDetail);
            text = "Total : <font color = #0488d1>" + format.format(finalAmount) + "</font> ";
            vh.txtRPerCrt.setText(Html.fromHtml(text) + symbol);
        }

        vh.swipeBizDetail.setShowMode(SwipeLayout.ShowMode.LayDown);

        vh.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(vh.swipeBizDetail);
                itemListener.onDeleteClickListener(i);
                notifyDataSetChanged();
                mItemManger.closeAllItems();
            }
        });

        vh.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(vh.swipeBizDetail);
                itemListener.onEditClickListener(i);
                notifyDataSetChanged();
                mItemManger.closeAllItems();
            }
        });

        mItemManger.bindView(vh.itemView, i);
    }

    private double getFinalAmount(AddNewBizRequest.CBizDetail bizDetail) {

        double totalAmt = bizDetail.getCrt() * bizDetail.getPricePerCrt();
        if (bizDetail.getType().equals("Rough"))
            return ((totalAmt / 100) * bizDetail.getPremiumPer()) + totalAmt;
        if (bizDetail.isIsShipping1())
            return ((totalAmt / 100) * bizDetail.getShipPer()) + totalAmt;
        return totalAmt;
    }

    public AddNewBizRequest.CBizDetail get(int position) {
        return items.get(position);
    }

    public void addAll(List<AddNewBizRequest.CBizDetail> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void add(int position, AddNewBizRequest.CBizDetail data) {
        items.add(position, data);
        notifyDataSetChanged();
    }

    public void add(AddNewBizRequest.CBizDetail data, String symbol) {
        this.symbol = symbol;
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

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeBizDetail;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btnEdit)
        DButton btnEdit;
        @BindView(R.id.btnDelete)
        DButton btnDelete;
        @BindView(R.id.swipeLayout)
        LinearLayout swipeLayout;
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
        @BindView(R.id.swipeBizDetail)
        SwipeLayout swipeBizDetail;
        @BindView(R.id.activity_main)
        LinearLayout activityMain;

        ViewHolder(View view) {
            super(view);
//            Utils.setDrawableTint(txtTotalAmt, view.getResources().getDrawable(R.drawable.ic_shipping_), view.getResources().getColor(R.color.diamondGreen));
            ButterKnife.bind(this, view);
        }
    }
}
