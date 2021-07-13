package com.fikrabd.mehna_w_amal.callbacks;

import android.view.View;

/**
 * Created by Saif M Jaradat on 2/3/2021.
 */
public interface AddressListener {

    void deliverToThisAddress(View view, int position);

    void editAddress(View view, int position);

    void removeAddress(View view, int position);
}