package com.growonline.gomobishop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.growonline.gomobishop.ChatActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.adapter.ChatAdapter;
import com.growonline.gomobishop.base.Fab;
import com.growonline.gomobishop.model.InboxModel;

import java.util.ArrayList;
import java.util.List;

public class InboxFragment extends Fragment implements ChatAdapter.ViewHolder.ClickListener {
    List<InboxModel> data = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private TextView tv_selection;
    private Fab add_button;
    private SwipeRefreshLayout swipeLayout;

    public InboxFragment() {
        setHasOptionsMenu(true);
    }

    public static InboxFragment newInstance() {
        InboxFragment fragment = new InboxFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle a) {
        super.onCreate(a);
        if (getArguments() != null) {

        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, null, false);
//Getting SwipeContainerLayout
        swipeLayout = view.findViewById(R.id.swipe_container);
        add_button = (Fab) view.findViewById(R.id.add_chat);
        tv_selection = (TextView) view.findViewById(R.id.tv_selection);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ChatAdapter(getContext(), setData(), this);
        mRecyclerView.setAdapter(mAdapter);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ChatActivity) getActivity()).openContactFragment();
            }
        });

        //swip listener
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((ChatActivity) getActivity()).setGetPrivateMessageIndex();
                swipeLayout.setRefreshing(false);
            }
        });
        return view;
    }

    public List<InboxModel> setData() {
        return data;
    }

    public void addItemToInbox(List<InboxModel> inboxModel) {
        data.clear();
        data.addAll(inboxModel);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int position) {
        ((ChatActivity) getActivity()).openConversationFragment(data.get(position).getToCustomerId(), data.get(position).getFromCustomerId());
    }

    @Override
    public boolean onItemLongClicked(int position) {
        toggleSelection(position);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        if (mAdapter.getSelectedItemCount() > 0) {
            tv_selection.setVisibility(View.VISIBLE);
        } else
            tv_selection.setVisibility(View.GONE);


        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                tv_selection.setText("Delete (" + mAdapter.getSelectedItemCount() + ")");
            }
        });

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_edit, menu);
    }
}