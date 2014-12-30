package com.chro.beerapp;

import java.util.ArrayList;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.woodchro.bemystore.R;

public class EntryAdapter extends BaseAdapter {

	private ArrayList<Entry> entryList;
	private LayoutInflater mInflater;
	Context context;
	// Resource for layout inflate in getView
	// different layouts for item possible through constructor
	int adapterKind;

	public EntryAdapter(Context context, ArrayList<Entry> results,
			int adapterKind) {
		entryList = results;
		mInflater = LayoutInflater.from(context);
		this.context = context;
		this.adapterKind = adapterKind;
	}

	public int getCount() {
		return entryList.size();
	}

	public Object getItem(int position) {
		return entryList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.txtName = (TextView) convertView
					.findViewById(R.id.entry_content);

			holder.tButton = (TextView) convertView
					.findViewById(R.id.toggleButton1);
			if (adapterKind == 1) {
				holder.tButton.setVisibility(View.INVISIBLE);
			}
			holder.txtCategory = (TextView) convertView
					.findViewById(R.id.entry_category);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		holder.txtName.setText(entryList.get(position).getProductName());
		holder.txtCategory.setText(String.valueOf(entryList.get(position)
				.getCategory()));
		// set on click listener for multiple views within the listitem

		if (adapterKind == 0) {
			holder.tButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "ImageClickClick ",
							Toast.LENGTH_LONG).show();
				}
			});
		}
		return convertView;
	}

	static class ViewHolder {
		TextView txtName;
		TextView tButton;
		TextView txtCategory;
	}

}
