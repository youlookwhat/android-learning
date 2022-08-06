package me.jingbin.byrv.viewbinding;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import me.jingbin.library.ByRecyclerView;

/**
 * 实践 ListView 和 RecyclerView 的 adapter 集成 ViewBinding
 * https://github.com/youlookwhat/ByRecyclerView
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        ByRecyclerView recyclerView = findViewById(R.id.recyclerView);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("content1");
        strings.add("content2");
        strings.add("content3");
        strings.add("content4");

        ContentLVAdapter adapter = new ContentLVAdapter();
        adapter.setData(strings);
        listView.setAdapter(adapter);

        ContentRVAdapter rvAdapter = new ContentRVAdapter();
        rvAdapter.setNewData(strings);
        recyclerView.setAdapter(rvAdapter);
    }
}