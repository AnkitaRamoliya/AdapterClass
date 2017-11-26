package com.oozeetech.bizdesk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.oozeetech.bizdesk.R;
import com.oozeetech.bizdesk.widget.DTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomSpinnerAdapter extends BaseAdapter {

    Context context;
    String[] spinnerItem;
    LayoutInflater mInflater;
    @BindView(R.id.txtSpinnerText)
    DTextView txtSpinnerText;
    @BindView(R.id.llRawItem)
    LinearLayout llRawItem;

    public CustomSpinnerAdapter(Context context, String[] spinnerItem) {
        this.context = context;
        this.spinnerItem = spinnerItem;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return spinnerItem.length;
    }

    @Override
    public String getItem(int i) {
        return spinnerItem[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = mInflater.inflate(R.layout.custom_spinner_item, null);
        txtSpinnerText = view.findViewById(R.id.txtSpinnerText);
        txtSpinnerText.setText(spinnerItem[position]);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.txtSpinnerText)
        DTextView txtSpinnerText;
        @BindView(R.id.llRawItem)
        LinearLayout llRawItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}