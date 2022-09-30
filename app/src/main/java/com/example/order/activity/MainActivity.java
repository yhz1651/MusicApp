package com.example.order.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.Button;

import com.example.order.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * 主界面
 * 底部是navigation菜单
 * 上部是fragment
 */
public class MainActivity extends AppCompatActivity {

    private Button bnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);//获得底部导航视图
        NavController navController = Navigation.findNavController(this,R.id.fragment);//hostfragment
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(navController.getGraph()).build();//获取导航图表并装配
//        NavigationUI.setupActionBarWithNavController(this,navController,configuration);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        int REQUEST_EXTERNAL_STORAGE = 1; //动态获取
        String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE" };
        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
    }
}