package com.example.mysortlist;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NotFollowAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
	private Context mContext;
	private List<Column> notFollowList;
	private MainActivity mActivity;
	
	public NotFollowAdapter(Context context, List<Column> notFollowList) {
		this.mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.notFollowList = notFollowList;
		this.mActivity = (MainActivity)context;
	}

	@Override
	public int getCount() {
		return notFollowList.size();
	}

	@Override
	public Object getItem(int position) {
		return notFollowList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Column column = (Column) getItem(position);
		ViewHolder holder = null;
		
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.notfollow_listview_item, null);
			holder.columnName = (TextView) convertView.findViewById(R.id.columnName);
			holder.columnAdd = (ImageView) convertView.findViewById(R.id.columnAdd);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.columnName.setText(column.name);
		
		//添加按钮点击后 移除该行 上面列表增加一行 修改本地数据
		final int tmp = position;
		holder.columnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				notFollowList.remove(tmp);
				mActivity.getNotFollowAdapter().notifyDataSetChanged();
				mActivity.getFollowList().add(column);
				mActivity.getFollowAdapter().notifyDataSetChanged();
				
				//保存数据相关
				SharedPreferences preferences = mContext.getSharedPreferences("column",
						Context.MODE_WORLD_READABLE);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean(column.name, false);
			}
		});
		return convertView;
	}

	// ViewHolder静态类
	static class ViewHolder {
		TextView columnName; //栏目名
		ImageView columnAdd; //添加按钮
	}
	




}
