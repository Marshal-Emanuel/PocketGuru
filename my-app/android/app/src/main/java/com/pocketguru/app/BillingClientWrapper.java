package com.pocketguru.app;

import android.content.Context;
import androidx.annotation.NonNull;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.Purchase;
import java.util.List;

public class BillingClientWrapper {

    private BillingClient billingClient;
    private Context context;

    private final PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(@NonNull BillingResult billingResult, List<Purchase> purchases) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (Purchase purchase : purchases) {
                    // Process purchase
                }
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                // Handle user cancellation
            } else {
                // Handle other errors
            }
        }
    };

    public BillingClientWrapper(Context context) {
        this.context = context;
        this.billingClient = BillingClient.newBuilder(context)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
    }

    public void startConnection() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    System.out.println("BillingClient: Setup successful.");
                } else {
                    System.err.println("BillingClient: Error during setup. Response code: " + billingResult.getResponseCode() + " Debug message: " + billingResult.getDebugMessage());
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                System.err.println("BillingClient: Service disconnected.");
            }
        });
    }
}
