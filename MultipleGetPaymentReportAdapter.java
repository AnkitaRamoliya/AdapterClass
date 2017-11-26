package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.listener.MonthWiseListener;
import com.oozeetech.bizdesk.models.payment.PaymentReportResponse;
import com.oozeetech.bizdesk.widget.DTextView;

import java.util.ArrayList;
import java.util.List;

public class MultipleGetPaymentReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //    private static final int DATE_WISE = 0;
//    private static final int MONTH_WISE = 1;
    View view;
    private Context context;
    private ArrayList<PaymentReportResponse.Data.Payment> items = new ArrayList<>();
    private LayoutInflater inflater;
    private int type;
    private MonthWiseListener monthWiseListener;

    public MultipleGetPaymentReportAdapter(Context context, int type, MonthWiseListener monthWiseListener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.type = type;
        this.monthWiseListener = monthWiseListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        view = inflater.inflate(R.layout.payment_report_raw_item, parent, false);
//        return new GetPaymentReportAdapter.ViewHolder(view);
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == 0) {
            final View v = inflater.inflate(R.layout.payment_report_raw_item, parent, false);
            viewHolder = new DateWiseViewHolder(v);
        } else if (viewType == 1) {
            final View v = inflater.inflate(R.layout.payment_report_raw_item_month_wise, parent, false);
            viewHolder = new MonthWiseViewHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PaymentReportResponse.Data.Payment data = items.get(position);

        if (type == 0) {
            ((DateWiseViewHolder) holder).txtPartyName.setText(data.getPartyName());
            ((DateWiseViewHolder) holder).txtDate.setText(data.getPaymentDate());
            ((DateWiseViewHolder) holder).txtAmount.setText(data.getCurrancySymbol() + " " + data.getAmount());
        } else {
            ((MonthWiseViewHolder) holder).txtMonthName.setText(data.getPaymentDate());
            ((MonthWiseViewHolder) holder).txtINRAmount.setText("₹ " + data.getRsAmount());
            ((MonthWiseViewHolder) holder).txtUSDAmount.setText("$ " + data.getDollerAmount());
            ((MonthWiseViewHolder) holder).activityMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long month = data.getMonthInInteger();
                    long year = data.getYearInInteger();

                    monthWiseListener.monthWiseListener(month, year);
                }
            });
        }
    }

    public PaymentReportResponse.Data.Payment get(int position) {
        return items.get(position);
    }

    public void addAll(List<PaymentReportResponse.Data.Payment> getPartyResponses) {
        items.addAll(getPartyResponses);
        notifyDataSetChanged();
    }

    public void add(PaymentReportResponse.Data.Payment data) {
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
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    class DateWiseViewHolder extends RecyclerView.ViewHolder {
        DTextView txtPartyName;
        DTextView txtDate;
        DTextView txtAmount;
        LinearLayout activityMain;

        DateWiseViewHolder(View view) {
            super(view);
            txtPartyName = view.findViewById(R.id.txtPartyName);
            txtDate = view.findViewById(R.id.txtDate);
            txtAmount = view.findViewById(R.id.txtAmount);
            activityMain = view.findViewById(R.id.activity_main);
        }
    }

    class MonthWiseViewHolder extends RecyclerView.ViewHolder {
        DTextView txtMonthName;
        DTextView txtINRAmount;
        DTextView txtUSDAmount;
        LinearLayout activityMain;

        MonthWiseViewHolder(View view) {
            super(view);
            txtMonthName = view.findViewById(R.id.txtMonthName);
            txtINRAmount = view.findViewById(R.id.txtINRAmount);
            txtUSDAmount = view.findViewById(R.id.txtUSDAmount);
            activityMain = view.findViewById(R.id.activity_main);
        }
    }
}
