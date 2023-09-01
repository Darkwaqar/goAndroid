package com.growonline.gomobishop.fragment;

import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.ChatActivity;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.adapter.ContactAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetAllVendorForChat;
import com.growonline.gomobishop.model.InboxModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment implements ContactAdapter.ViewHolder.ClickListener {
    List<InboxModel> data = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ContactAdapter mAdapter;
    private GetAllVendorForChat getAllVendorForChat;

    public ContactsFragment() {

    }

    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle a) {
        super.onCreate(a);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, null, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ContactAdapter(getContext(), setData(), this);
        mRecyclerView.setAdapter(mAdapter);

        getAllVendorForChat = new GetAllVendorForChat();
        getAllVendorForChat.addOnResultListener(new AsyncTaskResultListener<List<InboxModel>>() {
            @Override
            public void response(AsyncTaskResult<List<InboxModel>> response) {
                if (!response.hasException()) {
                    if (response.getResult() != null)
                        SetAllVendorContacts(response.getResult());
                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        GoMobileApp.Toast(response.getException().getMessage());
                    else
                        GoMobileApp.Toast(response.getException().getMessage());
                }
            }
        });
        getAllVendorForChat.execute();
        return view;
    }

    public List<InboxModel> setData() {

//        String name[] = {"Laura Owens", "Angela Price", "Donald Turner", "Kelly", "Julia Harris", "Laura Owens", "Angela Price", "Donald Turner", "Kelly", "Julia Harris"};
//        @DrawableRes int img[] = {R.drawable.user1, R.drawable.user2, R.drawable.user1, R.drawable.user2, R.drawable.user1, R.drawable.user2, R.drawable.user1, R.drawable.user2, R.drawable.user2, R.drawable.user1};
//
//        for (int i = 0; i < 10; i++) {
//            InboxModel contact = new InboxModel();
//            contact.setCustomerFromName(name[i]);
//            contact.setCount(img[i]);
//            data.add(contact);
//        }
        return data;
    }

    public void SetAllVendorContacts(List<InboxModel> result) {
        data.clear();
        data.addAll(result);
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
    }

}