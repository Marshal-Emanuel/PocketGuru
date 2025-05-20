package com.pocketguru.app;

import android.os.Bundle;
import android.webkit.WebView; // Added for WebView debugging
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {

    private BillingClientWrapper billingClientWrapper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView.setWebContentsDebuggingEnabled(true); // Enable WebView debugging

        // Initialize the BillingClientWrapper
        billingClientWrapper = new BillingClientWrapper(this);
        billingClientWrapper.startConnection();
    }
}
