package com.service_mikke.mikke.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.service_mikke.mikke.R;
import com.service_mikke.mikke.adapters.NoticeRecyclerAdapter;
import com.service_mikke.mikke.models.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takuya on 3/17/17.
 */

public class NoticeFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.notice_fragment,container,false);

        List<Message> mDataset = initMessage();

        mRecyclerView =(RecyclerView)v.findViewById(R.id.notice_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new NoticeRecyclerAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }


    private List<Message> initMessage(){
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("aaa","test"));
        messages.add(new Message("bbb",
                "This lesson teaches you to\n" +
                "Create an IntentService\n" +
                "Define the IntentService in the Manifest\n" +
                "You should also read\n" +
                "Extending the IntentService Class\n" +
                "Intents and Intent Filters\n" +
                "Try it out\n" +
                "DOWNLOAD THE SAMPLE\n" +
                "ThreadSample.zip\n" +
                "\n" +
                "The IntentService class provides a straightforward structure for running an operation on a single background thread. This allows it to handle long-running operations without affecting your user interface's responsiveness. Also, an IntentService isn't affected by most user interface lifecycle events, so it continues to run in circumstances that would shut down an AsyncTask"));
        messages.add(new Message("ccc","test test"));
        messages.add(new Message("ddd","test test"));
        messages.add(new Message("eee","test test"));
        messages.add(new Message("fff","test test"));
        messages.add(new Message("ggg","test test"));

        return messages;

    }
}
