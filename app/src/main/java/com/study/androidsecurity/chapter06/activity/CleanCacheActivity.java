package com.study.androidsecurity.chapter06.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.study.androidsecurity.R;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * Created by 46358_000 on 2016/8/15 0015.
 */
public class CleanCacheActivity extends Activity implements View.OnClickListener {
    private static final int CLEANNING=100;
    private AnimationDrawable animation;
    private long cacheMemory;
    private TextView mMemoryTV;
    private TextView mMemoryUnitTV;
    private PackageManager pm;
    private FrameLayout mCleanCacheFL;
    private FrameLayout mFinishCleanFL;
    private TextView mSizeTV;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CLEANNING:
                    long memory = (long) msg.obj;
                    formatMemory(memory);
                    if (memory == cacheMemory) {
                        animation.stop();
                        mCleanCacheFL.setVisibility(View.GONE);
                        mFinishCleanFL.setVisibility(View.VISIBLE);
                        mSizeTV.setText("成功清理：" + Formatter.formatFileSize(CleanCacheActivity.this, cacheMemory));

                    }
                    break;
            }
        }


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cleancache);
        initView();
        pm = getPackageManager();
        Intent intent = getIntent();
        cacheMemory = intent.getLongExtra("cacheMemory", 0);
        initData();
    }

    private void initView() {
        findViewById(R.id.rl_titlebar).setBackgroundColor(Color.parseColor("#FF3030"));
        ((TextView)findViewById(R.id.tv_title)).setText("缓存清理");
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        animation = (AnimationDrawable) findViewById(R.id.imgv_trashbin_cacheclean).getBackground();
        animation.setOneShot(false);
        animation.start();
        mMemoryTV = (TextView) findViewById(R.id.tv_cleancache_memory);
        mMemoryUnitTV = (TextView) findViewById(R.id.tv_cleancache_memoryunit);
        mCleanCacheFL = (FrameLayout) findViewById(R.id.fl_cleancache);
        mFinishCleanFL = (FrameLayout) findViewById(R.id.fl_finishclean);
        mSizeTV = (TextView) findViewById(R.id.tv_cleanmemorysize);
        findViewById(R.id.btn_finish_cleancache).setOnClickListener(this);

    }

    private void initData() {
        cleanAll();
        new Thread(){
            public void run() {
                long memory = 0;
                while (memory < cacheMemory) {
                    try {
                        Thread.sleep(300);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Random rand = new Random();
                    int i=rand.nextInt();
                    i=rand.nextInt(1024);
                    memory += 1024 * i;
                    if (memory > cacheMemory) {
                        memory = cacheMemory;
                    }
                    Message message = Message.obtain();
                    message.what = CLEANNING;
                    message.obj = memory;
                    mHandler.sendMessageDelayed(message, 200);
                }
            }
        }.start();
    }

    private void formatMemory(long memory) {
        String cacheMemoryStr = Formatter.formatFileSize(this, memory);
        String memoryStr;
        String memoryUnit;
        if (memory > 900) {
            memoryStr = cacheMemoryStr.substring(0, cacheMemoryStr.length() - 2);
            memoryUnit = cacheMemoryStr.substring(cacheMemoryStr.length() - 2, cacheMemoryStr.length());

        }else {
            memoryStr = cacheMemoryStr.substring(0, cacheMemoryStr.length() - 1);
            memoryUnit = cacheMemoryStr.substring(cacheMemoryStr.length() - 1, cacheMemoryStr.length());

        }
        mMemoryTV.setText(memoryStr);
        mMemoryUnitTV.setText(memoryUnit);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.btn_finish_cleancache:
                finish();
                break;
        }

    }

    class ClearCacheObserver extends android.content.pm.IPackageDataObserver.Stub {
        public void onRemoveCompleted(final String packageName, final boolean succeeded) {

        }
    }

    private void cleanAll() {
        Method[] methods = PackageManager.class.getMethods();
        for (Method method : methods) {
            if ("freeStorageAndNotify".equals(method.getName())) {
                try {
                    method.invoke(pm, Long.MAX_VALUE, new ClearCacheObserver());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        Toast.makeText(this,"清理完毕",Toast.LENGTH_SHORT).show();
    }


}