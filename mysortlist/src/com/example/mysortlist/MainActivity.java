package com.example.mysortlist;

import java.util.ArrayList;
import java.util.List;

import com.mobeta.android.dslv.DragSortListView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	//关注列表相关
	private ArrayList<Column> followList = new ArrayList<Column>();
	private DragSortListView followListView;
	private FollowAdapter mFollowAdapter;
	//未关注列表相关
	private ArrayList<Column> notFollowList = new ArrayList<Column>();
	private ListView notFollowListView;
	private NotFollowAdapter mNotFollowAdapter;
	
	//增加一个临时变量 保存所有的值 因为在后面列表为空时存在问题
	private ArrayList<Column> allList = new ArrayList<Column>();;

	// 监听器在手机拖动停下的时候触发
	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {// from to 分别表示 被拖动控件原位置 和目标位置
			if (from != to) {
				Column column = (Column) mFollowAdapter.getItem(from);// 得到listview的适配器
				mFollowAdapter.remove(from);// 在适配器中”原位置“的数据。
				mFollowAdapter.insert(column, to);// 在目标位置中插入被拖动的控件。
			}
		}
	};
	
	// 删除监听器，点击左边差号就触发。删除item操作。
	private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener() {
		@Override
		public void remove(int which) {
			
			//未关注列表增加一行
			if (followList.size() != 0) {
				Column column = (Column) mFollowAdapter.getItem(which);
				editor.putBoolean(column.name, false);
				notFollowList.add(column);
				mNotFollowAdapter.notifyDataSetChanged();
			}else {
				notFollowList.clear();
				notFollowList.addAll(allList);
				mNotFollowAdapter.notifyDataSetChanged();
			}
			//再删除
			mFollowAdapter.remove(which);
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题
		setContentView(R.layout.activity_main);
		initView();
		initData();
		initSavaData();
		
		followListView.setDropListener(onDrop);
		followListView.setRemoveListener(onRemove);

		mFollowAdapter = new FollowAdapter(MainActivity.this, followList);
		followListView.setAdapter(mFollowAdapter);
		followListView.setDragEnabled(true); // 设置是否可拖动。

		mNotFollowAdapter = new NotFollowAdapter(MainActivity.this, notFollowList);
		notFollowListView.setAdapter(mNotFollowAdapter);
	}

	private void initView() {
		followListView = (DragSortListView) findViewById(R.id.follow_list);
		notFollowListView = (ListView) findViewById(R.id.not_follow_list);
	}

	private void initData() {
		// followList = NewsCategoryDao.getInstance(context).queryAll();
		Column column1 = new Column();
		column1.id = 1;
		column1.name = "国际足球";
		followList.add(column1);
		allList.add(column1);

		Column column2 = new Column();
		column2.id = 2;
		column2.name = "NBA";
		followList.add(column2);
		allList.add(column2);

		Column column3 = new Column();
		column3.id = 3;
		column3.name = "中国足球";
		followList.add(column3);
		allList.add(column3);

		Column column4 = new Column();
		column4.id = 4;
		column4.name = "中国蓝球";
		followList.add(column4);
		allList.add(column4);

		Column column5 = new Column();
		column5.id = 5;
		column5.name = "视频";
		followList.add(column5);
		allList.add(column5);
	}

	private void initSavaData() {
		preferences = getSharedPreferences("column",
				MODE_WORLD_READABLE);
		editor = preferences.edit();
		for (int i = 0; i < followList.size(); i++) {
			editor.putBoolean(followList.get(i).name, true);
		}
		editor.commit();
	}

	public FollowAdapter getFollowAdapter() {
		return mFollowAdapter;
	}

	public ArrayList<Column> getFollowList() {
		return followList;
	}

	public NotFollowAdapter getNotFollowAdapter() {
		return mNotFollowAdapter;
	}

	public ArrayList<Column> getNotFollowList() {
		return notFollowList;
	}

}
