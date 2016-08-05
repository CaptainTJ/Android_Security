package com.study.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.androidsecurity.R;

/**
 * Created by ZhaoYi on 2016-08-05.
 */
public class HomeAdapter extends BaseAdapter{
    int[] imageId = {
        R.drawable.atools,R.drawable.app,R.drawable.safe,
            R.drawable.callmsgsafe,R.drawable.trojan, R.drawable.sysoptimize,
            R.drawable.taskmanager,R.drawable.netmanager,R.drawable.settings};
    String[] names = {"高级工具","软件管家","手机防盗","通讯卫士","手机杀毒","缓存清理","进程管理","流量管理","设置中心"};
    private Context context;
    public HomeAdapter(Context context){
        this.context = context;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
/**逐条设置每个选项的界面*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context,R.layout.item_home,null);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        iv_icon.setImageResource(imageId[position]);
        tv_name.setText(names[position]);
        return view;
    }
}
