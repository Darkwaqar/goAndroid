package com.growonline.gomobishop.fragment;

import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.growonline.gomobishop.ChatActivity;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetConversation;
import com.growonline.gomobishop.asynctask.SendChatMessage;
import com.growonline.gomobishop.control.ConversationRecyclerView;
import com.growonline.gomobishop.model.ConversationsModel;
import com.growonline.gomobishop.model.InboxModel;
import com.growonline.gomobishop.model.UpdateChat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConversationFragment extends Fragment {

    private static final String ARG_PARAM1 = "toCustomer";
    private static final String ARG_PARAM2 = "fromCustomer";
    List<ConversationsModel> data = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ConversationRecyclerView mAdapter;
    private EditText text;
    private Button send;
    private Integer toCustomerId;
    private Integer fromCustomerId;
    private GetConversation getConversation;
    private SendChatMessage sendChatMessage;
    private SwipeRefreshLayout swipeLayout;

    public ConversationFragment() {
        // Required empty public constructor
    }

    public static ConversationFragment newInstance(Integer toCustomerId, Integer fromCustomerId) {
        ConversationFragment fragment = new ConversationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, toCustomerId);
        args.putInt(ARG_PARAM2, fromCustomerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            toCustomerId = getArguments().getInt(ARG_PARAM1);
            fromCustomerId = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        //Getting SwipeContainerLayout
        swipeLayout = view.findViewById(R.id.swipe_container);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ConversationRecyclerView(getContext(), setData());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ScrollToLastChat();
            }
        }, 1000);

        text = (EditText) view.findViewById(R.id.et_message);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScrollToLastChat();
                    }
                }, 500);
            }
        });
        //swip listener
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetConversation();

            }
        });
        send = (Button) view.findViewById(R.id.bt_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!text.getText().equals("")) {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("FromCustomerId", fromCustomerId);
                        params.put("ToCustomerId", toCustomerId);
                        params.put("Message", text.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    sendChatMessage = new SendChatMessage(params.toString());
                    sendChatMessage.addOnResultListener(new AsyncTaskResultListener<UpdateChat>() {
                        @Override
                        public void response(AsyncTaskResult<UpdateChat> response) {
                            if (!response.hasException()) {
                                if (response.getResult().getUpdateconversationsectionhtml() != null)
                                    SetConversation(response.getResult().getUpdateconversationsectionhtml());
                                if (response.getResult().getUpdateinboxsectionhtml() != null)
                                    updateInbox(response.getResult().getUpdateinboxsectionhtml());
                                ScrollToLastChat();
                                text.setText("");
                            } else {
                                if (response.getException() instanceof NetworkErrorException)
                                    GoMobileApp.Toast(response.getException().getMessage());
                                else
                                    GoMobileApp.Toast(response.getException().getMessage());
                            }
                        }
                    });
                    sendChatMessage.execute();
                    ((ChatActivity) getActivity()).SendChat(toCustomerId, fromCustomerId);

                }
            }
        });

        GetConversation();
        return view;
    }

    public void GetConversation() {
        getConversation = new GetConversation(toCustomerId);
        getConversation.addOnResultListener(new AsyncTaskResultListener<List<ConversationsModel>>() {
            @Override
            public void response(AsyncTaskResult<List<ConversationsModel>> response) {
                swipeLayout.setRefreshing(false);
                if (!response.hasException()) {
                    if (response.getResult() != null)
                        SetConversation(response.getResult());
                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        GoMobileApp.Toast(response.getException().getMessage());
                    else
                        GoMobileApp.Toast(response.getException().getMessage());
                }
            }
        });
        getConversation.execute();
    }

    private void updateInbox(List<InboxModel> updateinboxsectionhtml) {
        ((ChatActivity) getActivity()).updateInbox(updateinboxsectionhtml);
    }

    public void ScrollToLastChat() {
        if (mRecyclerView.getAdapter() != null && mRecyclerView.getAdapter().getItemCount() > 0)
            mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
    }


    public List<ConversationsModel> setData() {
        return data;
    }

    public void SetConversation(List<ConversationsModel> result) {
        data.clear();
        data.addAll(result);
        mAdapter.notifyDataSetChanged();
    }
}