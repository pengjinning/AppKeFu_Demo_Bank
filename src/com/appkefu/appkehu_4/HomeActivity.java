package com.appkefu.appkehu_4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.appkefu.lib.interfaces.KFInterfaces;
import com.appkefu.lib.ui.activity.KFChatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	
	int[] image = {
			R.drawable.customer_service, R.drawable.appointment_v2, R.drawable.atm_query, R.drawable.bangzhuzhongxin,
			R.drawable.caipiaotaocan, R.drawable.caipiaotouzhu, R.drawable.card_mgr, R.drawable.branch_query,
			R.drawable.branches 
		};
	
	private MyAdapter adapter = null;
	private ArrayList<Map<String, Object>> array;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_home);
		
		GridViewInterceptor gv = (GridViewInterceptor) findViewById(R.id.gride);
		array = getData();
		adapter = new MyAdapter();
		gv.setDropListener(onDrop);
		gv.setAdapter(adapter);
		gv.setOnItemClickListener(new ItemClickEvent());
	}

	private GridViewInterceptor.DropListener onDrop = new GridViewInterceptor.DropListener() {
		public void drop(int from, int to) {
			Map item = adapter.getItem(from);

			adapter.remove(item);
			adapter.insert(item, to);
		}
	};

	public class ImageList extends BaseAdapter {
		Activity activity;

		// construct
		public ImageList(Activity a) {
			activity = a;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return image.length;
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return image[position];
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView iv = new ImageView(activity);
			iv.setImageResource(image[position]);
			return iv;
		}
	}

	class MyAdapter extends ArrayAdapter<Map<String, Object>> {

		MyAdapter() {
			super(HomeActivity.this, R.layout.gridview_item, array);
		}

		public ArrayList<Map<String, Object>> getList() {
			return array;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.gridview_item, parent, false);
			}
			ImageView imageView = (ImageView) row.findViewById(R.id.img);
			imageView.setImageResource(Integer.valueOf(array.get(position).get("img").toString()));

			TextView textView = (TextView) row.findViewById(R.id.text);
			//在线客服，其他
			if(position == 0)
			{
				textView.setText("在线客服");
			}
			else
			{
				textView.setText("其他");
			}
			
			return (row);
		}
	}

	private ArrayList<Map<String, Object>> getData() {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < image.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("img", image[i]);
			list.add(map);

		}
		return list;
	}

	class ItemClickEvent implements AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			
			view.setPressed(false);
			view.setSelected(false);
			
			TextView textView = (TextView)view.findViewById(R.id.text);
			if(textView.getText().equals("在线客服"))
			{
				chatWithKeFu("admin");
				
			}
			else
				Toast.makeText(HomeActivity.this, textView.getText(), Toast.LENGTH_SHORT).show();
		}
	}

	//启动咨询对话框
	private void chatWithKeFu(String kefuUsername)
	{
		KFInterfaces.startChatWithKeFu(this,
				"admin", //客服用户名
				"您好，我是微客服小秘书，请问有什么可以帮您的?",  //问候语
				"咨询客服");//会话窗口标题
	}
}













