package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.listener.MyBizListener;
import com.oozeetech.bizdesk.models.mybiz.GetMyBizResponse;
import com.oozeetech.bizdesk.ui.BizDetailActivity;
import com.oozeetech.bizdesk.utils.Constants;
import com.oozeetech.bizdesk.widget.DButton;
import com.oozeetech.bizdesk.widget.DTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetMyBizSwipeAdapter extends RecyclerSwipeAdapter<GetMyBizSwipeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GetMyBizResponse.Data> items = new ArrayList<>();
    private LayoutInflater inflater;
    private MyBizListener myBizListener;

    public GetMyBizSwipeAdapter(Context context, MyBizListener myBizListener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.myBizListener = myBizListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.my_biz_raw_item_temp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int i) {
        final GetMyBizResponse.Data data = items.get(i);

        vh.txtPartyName.setText(data.getPartyName());
        vh.txtCustomerName.setText(data.getCustomerName());
        vh.txtBizType.setText(data.getBizType());
        vh.txtBizType.setTextColor(ContextCompat.getColor(context, data.getBizType().equals("Rough") ? R.color.diamondOrange : R.color.diamondGreen));
        vh.txtBrokerage.setText(data.getBrokerageAmt());
        Log.e("getBrokerageAmt", data.getBrokerageAmt());
        vh.txtDueDays.setText("Due Days : " + data.getDueDay());
//        vh.txtDueDays.setText(data.getDueDay());
        vh.txtTotalAmount.setText(data.getFinalAmt());
        vh.txtSaleDate.setText(data.getSaleDate());
        vh.txtPaymentDate.setText(data.getPaymentDate());

        vh.llRowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BizDetailActivity.class);
                intent.putExtra(Constants.ID, data.getBizMasterID());
                context.startActivity(intent);
            }
        });

        vh.swipeBizDetail.setShowMode(SwipeLayout.ShowMode.LayDown);

        vh.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(vh.swipeBizDetail);
                myBizListener.onDeleteClickListener(data.getBizMasterID());
//                items.remove(i);
                notifyDataSetChanged();
                mItemManger.closeAllItems();
            }
        });
        vh.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(vh.swipeBizDetail);
                myBizListener.onConfirmClickListener(data.getBizMasterID());
                notifyDataSetChanged();
                mItemManger.closeAllItems();
            }
        });
        vh.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(vh.swipeBizDetail);
                myBizListener.onEditClickListener(data.getBizMasterID());
                notifyDataSetChanged();
                mItemManger.closeAllItems();
            }
        });
        mItemManger.bindView(vh.itemView, i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addAll(List<GetMyBizResponse.Data> data) {
        items.addAll(data);
        notifyDataSetChanged();
    }

    public void add(GetMyBizResponse.Data data) {
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
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeBizDetail;
    }

    public GetMyBizResponse.Data get(int position) {
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        @BindView(R.id.btnEdit)
        DButton btnEdit;
        @BindView(R.id.btnConfirm)
        DButton btnConfirm;
        @BindView(R.id.btnDelete)
        DButton btnDelete;
        @BindView(R.id.swipeLayout)
        LinearLayout swipeLayout;
        @BindView(R.id.txtSaleDate)
        DTextView txtSaleDate;
        @BindView(R.id.txtDueDays)
        DTextView txtDueDays;
        @BindView(R.id.txtPaymentDate)
        DTextView txtPaymentDate;
        @BindView(R.id.txtPartyName)
        DTextView txtPartyName;
        @BindView(R.id.txtBizType)
        DTextView txtBizType;
        @BindView(R.id.txtCustomerName)
        DTextView txtCustomerName;
        @BindView(R.id.txtTotalAmount)
        DTextView txtTotalAmount;
        @BindView(R.id.txtBrokerage)
        DTextView txtBrokerage;
        @BindView(R.id.llRowData)
        LinearLayout llRowData;
        @BindView(R.id.swipeBizDetail)
        SwipeLayout swipeBizDetail;
        @BindView(R.id.activity_main)
        LinearLayout activityMain;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
