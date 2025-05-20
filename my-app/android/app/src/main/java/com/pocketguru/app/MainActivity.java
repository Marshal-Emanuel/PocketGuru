package com.pocketguru.app;

import android.os.Bundle;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {

    private BillingClientWrapper billingClientWrapper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the BillingClientWrapper
        billingClientWrapper = new BillingClientWrapper(this);
        billingClientWrapper.startConnection();
    }
}
