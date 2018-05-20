package com.example.administrator.wclass.findFuture;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.wclass.R;
import com.example.administrator.wclass.base.BaseFragment;
import com.example.administrator.wclass.base.OnClickerListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment {


    private static final String TAG = "FindFragment";
    private String url;
    private static final String NUC = "http://www.nuc.edu.cn";
    private static final String NUCPage = "http://www.nuc.edu.cn/index/xxxw/";
    @BindView(R.id.find_recycler_view)
    RecyclerView findRecyclerView;
    Unbinder unbinder;
    private FindAdapter findAdapter;
    private List<String>keyList = new ArrayList<>();


    public static FindFragment getInstance() {
        // Required empty public constructor
        return new FindFragment();
    }


    @Override
    protected void logic() {
        ExecutorService executorService = new ThreadPoolExecutor(5,
                5,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>());
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                for (int i = 106;i>=105;i--){
                    if (i == 106){
                        url ="http://www.nuc.edu.cn/index/xxxw.htm";
                    }else {
                        url =NUCPage+i+".htm";
                    }
                    Document doc = null;
                    try {
                        doc = Jsoup.connect(url).get();
                    } catch (IOException mE) {
                        mE.printStackTrace();
                    }
                    Elements topnews = doc != null ? doc.getElementsByClass("list_con_rightlist") : null;
                    Elements links = topnews.select("a[href]");
                    for (Element link : links) {
                        if (!link.text().equals("")) {
                            String key = link.html() + link.attr("href");
                            if (key.startsWith("首页")||key.startsWith("上页")||key.startsWith("下页")||key.startsWith("尾页")||key.startsWith("《中北大学报》")||key.startsWith("中北大学电视台")||key.startsWith("中北大学第"))continue;
                            keyList.add(key);
                            //Log.d(TAG, "onCreate: " + link.html() + link.attr("href"));
                        }

                    }
                }
                return 0;
            }
        });
        try {
            if (future.get() == 0){
                Log.i(TAG, "onCreate: "+keyList.size()+keyList.get(0));
                findAdapter = new FindAdapter(getContext(), new OnClickerListener() {
                    @Override
                    public void click(int position, View view) {
                        Intent i = new Intent(getContext(),WebActivity.class);
                        Bundle b = new Bundle();
                        b.putString("title",keyList.get(position).split("\\..")[0]);
                        b.putString("url",NUC+keyList.get(position).split("\\..")[1]+".htm#tips");
                        Log.i(TAG, "click: "+NUC+keyList.get(position).split("\\..")[1]);
                        i.putExtras(b);
                        startActivity(i);
                    }
                },keyList);
                findRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                findRecyclerView.setAdapter(findAdapter);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void init(View mView, Bundle mSavedInstanceState) {

    }

    @Override
    protected int getResourcesLayout() {
        return R.layout.fragment_find;
    }

}
