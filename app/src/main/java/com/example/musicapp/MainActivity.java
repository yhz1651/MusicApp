package com.example.musicapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * 主界面
 * 底部是navigation菜单
 * 上部是fragment
 */
public class MainActivity extends AppCompatActivity {
    NavController navController;
    private static final String BUNDLE_FRAGMENTS_KEY = "android:support:fragments";
    private boolean i;
    private Button bnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null && this.clearFragmentsTag()) {
            //重建时清除 fragment的状态
            savedInstanceState.remove(BUNDLE_FRAGMENTS_KEY);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            int id = getIntent().getIntExtra("idd", 0);
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);//获得底部导航视图
            navController = Navigation.findNavController(this,R.id.fragment);//hostfragment
            AppBarConfiguration configuration = new AppBarConfiguration.Builder(navController.getGraph()).build();//获取导航图表并装配
//        NavigationUI.setupActionBarWithNavController(this,navController,configuration);
            NavigationUI.setupWithNavController(bottomNavigationView,navController);
        }

        int REQUEST_EXTERNAL_STORAGE = 1; //动态获取
        String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE" };
        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null && this.clearFragmentsTag()) {
            //销毁时不保存fragment的状态
            outState.remove(BUNDLE_FRAGMENTS_KEY);
        }
    }

    protected boolean clearFragmentsTag() {
        return true;
    }

}