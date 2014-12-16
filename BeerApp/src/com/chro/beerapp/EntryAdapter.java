package com.chro.beerapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.woodchro.bemystore.R;

public class EntryAdapter extends BaseAdapter{
	
	private ArrayList<Entry> entryList;
	private LayoutInflater mInflater;
	
	public EntryAdapter(Context context, ArrayList<Entry> results){
		entryList = results;
		mInflater = LayoutInflater.from(context);
	}
	
	public int getCount(){
		return entryList.size();
	}
	
	public Object getItem(int position){
		return entryList.get(position);
	}
	
	public long getItemId(int position){
		return position;
	}
	
	public View getView(int postion, View convertView, ViewGroup parent){
		ViewHolder holder;
		if (convertView ==null){
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.txtName = (TextView) convertView.findViewById(R.id.entry_content);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtName.setText(entryList.get(postion).getProductName());
		return convertView;
		}
	static class ViewHolder{
		TextView txtName;
	}

}
