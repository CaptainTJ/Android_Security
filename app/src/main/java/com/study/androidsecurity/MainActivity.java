package com.study.androidsecurity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import com.study.adapter.HomeAdapter;

public class MainActivity extends Activity {
    private GridView gv_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        gv_home = (GridView) findViewById(R.id.gv_main);
        gv_home.setAdapter(new HomeAdapter(MainActivity.this));
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://
                        break;
                    case 1://
                        break;
                    case 2://
                        break;
                    case 3://
                        break;
                    case 4://
                        break;
                    case 5://
                        break;
                    case 6://
                        break;
                    case 7://
                        break;
                    case 8://
                        break;
                }
            }
        });
    }
}
