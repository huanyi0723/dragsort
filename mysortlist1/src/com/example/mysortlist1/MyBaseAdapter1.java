package com.example.mysortlist1;

import java.util.ArrayList;

import com.example.mysortlist1.MyBaseAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyBaseAdapter1 extends BaseAdapter{

	private ArrayList<String> mylist;  
	private Context mcontext;
	private LayoutInflater mInflater;
	
	public MyBaseAdapter1(Context context, ArrayList<String> mylist){ 
		
		this.mcontext = context; 
        this.mylist = mylist; 
        this.mInflater = LayoutInflater.from(context);
        
    }  

	@Override
	public int getCount() {
		
		return mylist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mylist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
        ViewHolder viewHolder = null;  
        if(convertView==null){  
        	convertView = mInflater.inflate(  
                    R.layout.jazz_artist_list_item, null);  
              
            viewHolder = new ViewHolder();  
            viewHolder.textViewItem = (TextView)convertView.findViewById(  
                    R.id.artist_name_textview);  
              
            convertView.setTag(viewHolder);  
        }else{  
            viewHolder = (ViewHolder)convertView.getTag();  
        }  
          
        viewHolder.textViewItem.setText(mylist.get(arg0));  
        
        return convertView;  
	}

	
	public class ViewHolder{  
        TextView textViewItem;  
    }  
	
	

}
