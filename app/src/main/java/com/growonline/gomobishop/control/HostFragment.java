package com.growonline.gomobishop.control;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.growonline.gomobishop.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HostFragment extends BackStackFragment {

    private Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_host, container, false);
        if (fragment != null) {
            replaceFragment(fragment, false);
        }
        return view;
    }

    public void replaceFragment(Fragment fragment, boolean addToBackstack) {
        if (addToBackstack) {
            getChildFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit,R.anim.left_to_right, R.anim.right_to_left).replace(R.id.hosted_fragment, fragment, fragment.getClass().getName()).addToBackStack(fragment.getClass().getName()).commit();
        } else {
            getChildFragmentManager().beginTransaction().replace(R.id.hosted_fragment, fragment, fragment.getClass().getName()).commit();
        }

    }

    public static HostFragment newInstance(Fragment fragment) {
        HostFragment hostFragment = new HostFragment();
        hostFragment.fragment = fragment;
        return hostFragment;
    }

    public Fragment GetOriginalFragment() {
        return fragment;
    }

}
