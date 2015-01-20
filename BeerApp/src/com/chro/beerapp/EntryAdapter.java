package com.chro.beerapp;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
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

	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.txtName = (TextView) convertView
					.findViewById(R.id.entry_content);

			holder.tButton = (Switch) convertView
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
			holder.tButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			    	ProgressDialog dialog = ProgressDialog.show(context, "Sending Activation", "Please wait ...",true);
		        	ConnectionActivate req = new ConnectionActivate(context,dialog);
			        if (isChecked) {
			            // The toggle is enabled
			    		
			        	
			        	req.execute(String.valueOf(entryList.get(position).getID()),"true");
			        	Toast.makeText(context, String.valueOf(entryList.get(position).getID()) ,
								Toast.LENGTH_SHORT).show();
			        } else {
			            // The toggle is disabled
			        	req.execute(String.valueOf(entryList.get(position).getID()),"false");
			        	Toast.makeText(context, "switch disabled ",
								Toast.LENGTH_SHORT).show();
			        }
			    }
			});			
		}
		return convertView;
	}

	static class ViewHolder {
		TextView txtName;
		Switch tButton;
		TextView txtCategory;
	}

}
