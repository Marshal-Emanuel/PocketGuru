# Project: Integrating Native In-App Purchases into PocketGuru PWA

## 1. Current Situation & Problem Statement

**Current Situation:**
*   **PocketGuru PWA:** A Progressive Web App built with WordPress and WooCommerce.
*   **Payments:** Currently handled exclusively through WooCommerce's web-based checkout.
*   **Distribution:** Users can install the PWA to their home screens from a browser. It is not yet published on Google Play Store or Apple App Store.
*   **Native Wrapper (`my-app` directory):** A new Capacitor project has been initiated to wrap the PWA for native platforms, starting with Android.
    *   Capacitor project initialized.
    *   Android platform added.
    *   Google Play Billing library dependency (`com.android.billingclient:billing:7.0.0`) added to the Android project.
    *   A `BillingClientWrapper.java` class has been created, which initializes the `BillingClient`, sets up listeners, and provides a method to start the connection.
    *   `MainActivity.java` instantiates this `BillingClientWrapper` and attempts to start the connection.

**Problem Statement / Goal:**
To enhance the PocketGuru PWA by wrapping it in native Android and iOS applications using Capacitor. This will allow the integration of native In-App Purchase (IAP) systems (Google Play Billing for Android, Apple IAP for iOS). The application must conditionally use these native payment systems when running as an installed mobile app and retain the existing WooCommerce payment gateway when accessed via a web browser.

## 2. Detailed Implementation Plan

**Legend:**
*   ⚪️ - To Do
*   ✅ - Done
*   🚧 - Partially Done / In Progress

### Phase 1: Initial Project Setup & Capacitor Integration

*   ⚪️ **1.1. Prepare PWA for Capacitor:**
    *   ✅ Ensure PWA is functioning correctly and accessible via a stable URL.
    *   ⚪️ Review PWA for any elements that might conflict with a native wrapper environment.
*   ✅ **1.2. Set up Development Environment:**
    *   ✅ Node.js and npm/yarn installed.
    *   ✅ Capacitor CLI installed.
    *   ✅ Android Studio installed.
    *   ✅ JDK installed and configured for Android Studio.
    *   ⚪️ Install Xcode (for iOS development - can be deferred).
*   ✅ **1.3. Create New Capacitor Project (`my-app`):**
    *   ✅ Capacitor project "PocketGuru" (`com.pocketguru.app`) initialized.
*   ✅ **1.4. Add Platforms:**
    *   ✅ Android platform added (`my-app/android`).
    *   ⚪️ Add iOS platform (can be deferred): `npx cap add ios`
*   ✅ **1.5. Configure Capacitor to Load PWA:**
    *   ✅ Open `capacitor.config.json` (or `capacitor.config.ts`).
    *   ✅ Set the `server.url` property to your live PWA's URL.
        ```json
        {
          "appId": "com.pocketguru.app",
          "appName": "PocketGuru",
          "webDir": "www", // Or a placeholder if loading live URL
          "server": {
            "url": "https://your-pwa-domain.com",
            "cleartext": true // If PWA is not HTTPS during local dev
          }
        }
        ```
    *   ⚪️ Or, configure `webDir` to point to a local build of your PWA and run `npx cap sync`.
*   ✅ **1.6. Initial Run & Test:**
    *   ✅ Open Android project: `npx cap open android`
    *   ✅ Build and run the app on an emulator or device from Android Studio.
    *   ✅ Verify that your PWA loads correctly within the Capacitor WebView.
    *   ⚪️ (If iOS added) Open iOS project: `npx cap open ios` and test.

### Phase 2: Android - Google Play Billing Integration

*   🚧 **2.1. Google Play Console Setup:**
    *   ✅ Access to Google Play Developer account granted (`marshcodes@gmail.com`).
    *   ✅ Existing application entry for "PocketGuru" located.
    *   ⚪️ **Create In-App Products** (e.g., subscriptions, one-time purchases). Note Product IDs.
    *   ⚪️ Confirm/Set up license test account(s).
*   ✅ **2.2. Add Billing Library Dependency (Android):**
    *   ✅ `com.android.billingclient:billing:7.0.0` added to `my-app/android/app/build.gradle`.
    *   ✅ Gradle files synced.
*   🚧 **2.3. Create Native Billing Wrapper (`BillingClientWrapper.java`):**
    *   ✅ Class created in `my-app/android/app/src/main/java/com/pocketguru/app/`.
    *   ✅ `BillingClient` initialized with `PurchasesUpdatedListener` and `enablePendingPurchases()`.
    *   ✅ `BillingClientStateListener` implemented for connection setup/disconnection.
    *   ✅ Method: `startConnection()` implemented.
*   ✅ **2.4. Integrate Billing Wrapper into `MainActivity.java`:**
    *   ✅ `BillingClientWrapper` instantiated.
    *   ✅ `startConnection()` called in `onCreate`.
*   ⚪️ **2.5. Implement Core Billing Functions in `BillingClientWrapper.java`:**
    *   ⚪️ Method: `querySkuDetailsAsync(List<String> skuList, SkuDetailsResponseListener listener)` to fetch product details.
    *   ⚪️ Method: `launchBillingFlow(Activity activity, BillingFlowParams params)` to initiate a purchase.
    *   ⚪️ Method: `handlePurchase(Purchase purchase)` (within `PurchasesUpdatedListener`):
        *   ⚪️ Verify purchase (server-side validation highly recommended).
        *   ⚪️ Acknowledge purchases.
        *   ⚪️ Grant entitlement.
    *   ⚪️ Method: `queryPurchasesAsync()` to get active/non-consumed purchases.
*   ⚪️ **2.6. Expose Billing Functions to WebView (Capacitor Plugin):**
    *   ⚪️ Create a custom Capacitor plugin (e.g., `IAPPlugin`).
        *   ⚪️ Define plugin methods in Java (e.g., `initializeBilling`, `getProductDetails`, `makePurchase`, `getOwnedPurchases`) to call `BillingClientWrapper` methods.
        *   ⚪️ Register the plugin.
    *   ⚪️ Create the JavaScript interface for this plugin.

### Phase 3: PWA - Web Layer Integration (for Android IAP)

*   ⚪️ **3.1. Install/Create IAP Plugin JavaScript Interface in PWA.**
*   ⚪️ **3.2. Initialize Billing from PWA (via plugin).**
*   ⚪️ **3.3. Fetch Product Details in PWA (via plugin) & Display.**
*   ⚪️ **3.4. Initiate Purchase Flow from PWA (via plugin).**
*   ⚪️ **3.5. Handle Purchase Results in PWA (from plugin events/callbacks).**
*   ⚪️ **3.6. Restore Purchases / Check Entitlements in PWA (via plugin).**

### Phase 4: Conditional Logic (Native IAP vs. WooCommerce)

*   ⚪️ **4.1. Detect Running Environment in PWA (e.g., `Capacitor.isNativePlatform()`).**
*   ⚪️ **4.2. Implement Conditional UI/UX in PWA.**
*   ⚪️ **4.3. Backend Considerations for Entitlement Sync (Optional but Recommended).**

### Phase 5: iOS - Apple In-App Purchase Integration (Future)
*(Details omitted for brevity, similar structure to Android)*

### Phase 6: Testing & Deployment
*(Details omitted for brevity)*

---