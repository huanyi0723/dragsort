package com.example.mysortlist1;

import java.util.ArrayList;


import com.mobeta.android.dslv.DragSortListView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	
	private ArrayList<String> mylist = new ArrayList<String>();

	private ArrayList<String> mylist1 = new ArrayList<String>();
	private MyBaseAdapter myBaseAdapter;
	private MyBaseAdapter1 myBaseAdapter1;
	
	
	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		
		//Ωªªª ˝æ›
		@Override
		public void drop(final int from, final int to) {
			
			
			
			
			
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					String tmpString1 = mylist.get(from);
					String tmpString2 = mylist.get(from);
					mylist.remove(from);
					mylist.add(from, tmpString2);
					
					mylist.remove(to);
					mylist.add(to, tmpString1);
					myBaseAdapter.notifyDataSetChanged();
				}
			}, 1000);// —”≥Ÿ1√Î÷¥––
			
			
			
			
		}
	};

	private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener() {
		@Override
		public void remove(final int which) {
			
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					mylist.remove(which);
					myBaseAdapter.notifyDataSetChanged();
				}
			}, 1000);
		}
	};
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DragSortListView lv = (DragSortListView) findViewById(R.id.mylistview);
		String[] myStrings = { "≤‚ ‘1", "≤‚ ‘2", "≤‚ ‘3", "≤‚ ‘4", "≤‚ ‘5" };
		for (int i = 0; i < myStrings.length; i++) {
			mylist.add(myStrings[i]);
		}
		
		setListViewHeightBasedOnChildren(lv);
		myBaseAdapter = new MyBaseAdapter(MainActivity.this,
				mylist);
		lv.setAdapter(myBaseAdapter);
		
		

		ListView lv1 = (ListView) findViewById(R.id.lv1);
		myBaseAdapter1 = new MyBaseAdapter1(MainActivity.this,
				mylist);
		lv1.setAdapter(myBaseAdapter1);

	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		listView.setLayoutParams(params);
	}

}
