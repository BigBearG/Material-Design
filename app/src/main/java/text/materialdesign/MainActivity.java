package text.materialdesign;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDLlayout;
    private Fruit[] fruits={
            new Fruit("Apple",R.mipmap.apple_pic),
            new Fruit("Banana",R.mipmap.banana_pic),
            new Fruit("Orange",R.mipmap.orange_pic),
            new Fruit("Pear",R.mipmap.pear_pic),
            new Fruit("Grade",R.mipmap.grape_pic),
            new Fruit("Pineapple",R.mipmap.pineapple_pic),
    };
    private List<Fruit> fruitList=new ArrayList<>();
    private FruitAdapter adapter;
    private SwipeRefreshLayout mswipRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDLlayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);//是否显示导航按钮
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);//设置导航按钮图标
        }
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDLlayout.closeDrawers();
                return true;
            }
        });
        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"悬浮式按钮",Toast.LENGTH_SHORT).show();
                Snackbar.make(view,"数据删除",Snackbar.LENGTH_SHORT).setAction(
                        "否", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,"数据恢复",Toast.LENGTH_SHORT).show();
                            }
                        }
                ).show();
            }
        });
        initFruits();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutmanager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutmanager);
        adapter=new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
        mswipRefresh= (SwipeRefreshLayout) findViewById(R.id.swip_refresh);
        mswipRefresh.setColorSchemeResources(R.color.colorPrimary);
        mswipRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreFruits();
            }
        });
    }

    private void refreFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               try{
                   Thread.sleep(2000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       initFruits();
                       adapter.notifyDataSetChanged();
                       mswipRefresh.setRefreshing(false);
                   }
               });
            }
        }).start();
    }

    private void initFruits() {
        fruitList.clear();
        for (int i=0;i<50;i++){
            Random random=new Random();
            int index=random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home://导航按钮即HomeAsUp按钮id保持不变android.R.id.home
                mDLlayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this,"你选择了回收站",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"你选择了删除",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this,"你选择了设置",Toast.LENGTH_SHORT).show();
                break;
            case R.id.startactivty:
                Intent intent=new Intent(MainActivity.this,FruitActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
