package com.growonline.gomobishop;

import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.growonline.gomobishop.adapter.ChatFragmentsAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetPrivateMessageIndex;
import com.growonline.gomobishop.control.BackStackFragment;
import com.growonline.gomobishop.control.HostFragment;
import com.growonline.gomobishop.fragment.ContactsFragment;
import com.growonline.gomobishop.fragment.ConversationFragment;
import com.growonline.gomobishop.fragment.InboxFragment;
import com.growonline.gomobishop.model.Chat;
import com.growonline.gomobishop.model.InboxModel;
import com.growonline.gomobishop.util.AppConstant;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    HubConnection hubConnection;
    private ViewPager2 mainPager;
    private ChatFragmentsAdapter mainPagerAdapter;
    private GetPrivateMessageIndex getPrivateMessageIndex;
    private Integer fromCustomerId;
    private Integer toCustomerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        hubConnection = HubConnectionBuilder.create(AppConstant.BASE_DOMAIN + "signalr/hubs").build();
        hubConnection.start();
        hubConnection.on("addNewMessageToPage", (toCustomerId, FromCustomerId) -> {
            if (this.fromCustomerId.equals(toCustomerId)) {
                if (FromCustomerId.equals(this.toCustomerId)) {
                    reloadConversationFragment();
                } else {
                    openConversationFragment(this.toCustomerId, FromCustomerId);
                }
            }
        }, Integer.class, Integer.class);

        mainPager = (ViewPager2) findViewById(R.id.fragment_container);
        mainPagerAdapter = new ChatFragmentsAdapter(getSupportFragmentManager(), getLifecycle(), getBaseContext());
        mainPager.setAdapter(mainPagerAdapter);
        mainPager.setOffscreenPageLimit(1);
        setGetPrivateMessageIndex();
    }

    public void setGetPrivateMessageIndex() {
        getPrivateMessageIndex = new GetPrivateMessageIndex();
        getPrivateMessageIndex.addOnResultListener(new AsyncTaskResultListener<Chat>() {
            @Override
            public void response(AsyncTaskResult<Chat> response) {
                if (!response.hasException()) {
                    if (response.getResult().getInboxModel() != null)
                        addInboxToInboxFragment(response.getResult().getInboxModel());
                    fromCustomerId = response.getResult().getFromCustomerId();
                    toCustomerId = response.getResult().getToCustomerId();
                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        GoMobileApp.Toast(response.getException().getMessage());
                    else
                        GoMobileApp.Toast(response.getException().getMessage());
                }
            }
        });
        getPrivateMessageIndex.execute();
    }

    public void addInboxToInboxFragment(List<InboxModel> inboxModel) {
        HostFragment hostFragment = (HostFragment) mainPagerAdapter.createFragment(mainPager.getCurrentItem());
        InboxFragment f1 = (InboxFragment) hostFragment.GetOriginalFragment();
        f1.addItemToInbox(inboxModel);
    }

    public void reloadConversationFragment() {
        for (int i = 0; i < mainPagerAdapter.getItemCount(); i++) {
            HostFragment hostFragment = (HostFragment) mainPagerAdapter.createFragment(i);
            if (hostFragment.GetOriginalFragment() instanceof ConversationFragment)
                ((ConversationFragment) hostFragment.GetOriginalFragment()).GetConversation();
        }
    }


    public void openContactFragment() {
        HostFragment hostFragment = (HostFragment) mainPagerAdapter.createFragment(mainPager.getCurrentItem());
        hostFragment.replaceFragment(ContactsFragment.newInstance(), true);
    }

    public void openInboxFragment() {
        HostFragment hostFragment = (HostFragment) mainPagerAdapter.createFragment(mainPager.getCurrentItem());
        hostFragment.replaceFragment(InboxFragment.newInstance(), true);
    }

    public void openConversationFragment(Integer toCustomerId, Integer fromCustomerId) {
        HostFragment hostFragment = (HostFragment) mainPagerAdapter.createFragment(mainPager.getCurrentItem());
        if (toCustomerId.equals(this.fromCustomerId)) {
            toCustomerId = fromCustomerId;
        }
        hostFragment.replaceFragment(ConversationFragment.newInstance(toCustomerId, this.fromCustomerId), true);
    }

    @Override
    public void onBackPressed() {
        if (!BackStackFragment.handleBackPressed(getSupportFragmentManager())) {
            if (mainPager.getCurrentItem() != 0) {
                mainPager.setCurrentItem(0, true);
            } else {
                if (doubleBackToExitPressedOnce) {
                    finish();
                }
                this.doubleBackToExitPressedOnce = true;
                GoMobileApp.Toast(R.string.exit_chat);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
    }

    public void updateInbox(List<InboxModel> updateinboxsectionhtml) {
        addInboxToInboxFragment(updateinboxsectionhtml);
    }

    @Override
    protected void onDestroy() {
        hubConnection.close();
        super.onDestroy();
    }

    public void SendChat(final Integer toCustomerId, final Integer fromCustomerId) {
        this.toCustomerId = toCustomerId;
        this.fromCustomerId = fromCustomerId;

        if (hubConnection.getConnectionState() != HubConnectionState.CONNECTED) {
            hubConnection.start();
        }
        if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED)
            hubConnection.send("send", toCustomerId, fromCustomerId);
    }
}