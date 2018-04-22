android-learn
=============

Some learning materials that I use and experimental test apps I've created.


#### android-support-design-library-v28
    <android.support.design.button.MaterialButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="MATERIAL BUTTON"
            android:textSize="18sp"
            app:icon="@drawable/ic_android_white_24dp" />

    <android.support.design.chip.Chip
            android:id="@+id/some_chip"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:chipText="This is a chip" />

    <android.support.design.card.MaterialCardView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="16dp">
    ... child views ...
    </android.support.design.card.MaterialCardView>

    <android.support.design.bottomappbar.BottomAppBar
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom"
            app:backgroundTint="@color/colorPrimary"
            app:popupTheme="@style/ThemeOverlay.AppCompat.Light"
            app:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar">

Source: https://medium.com/exploring-android/exploring-the-v28-android-design-support-library-2c96c6031ae8
