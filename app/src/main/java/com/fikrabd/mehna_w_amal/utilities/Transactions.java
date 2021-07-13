package com.fikrabd.mehna_w_amal.utilities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fikrabd.mehna_w_amal.R;


/**
 * Created by Saif M Jaradat on 2/3/2021.
 */
public class Transactions {

    public static void replaceFragmentInActivity(Fragment fragment, int frameId, boolean addToBackStack, FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, fragment.getTag());
        if (addToBackStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void replaceFragmentWithAnimation(Fragment fragment, int frameId, boolean addToBackStack, FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(frameId, fragment, fragment.getTag());
        if (addToBackStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void replaceFragment(Fragment fragment, int frameId, boolean addToBackStack, FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, fragment.getTag());
        if (addToBackStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }
}
