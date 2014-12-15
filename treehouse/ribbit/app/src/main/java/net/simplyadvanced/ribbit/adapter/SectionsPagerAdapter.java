/**
 * Created by Danial on 12/8/2014.
 */
package net.simplyadvanced.ribbit.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import net.simplyadvanced.ribbit.R;
import net.simplyadvanced.ribbit.ui.FriendsFragment;
import net.simplyadvanced.ribbit.ui.InboxFragment;

import java.util.Locale;

/**
 * A {@link android.support.v13.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new InboxFragment();
            case 1: return new FriendsFragment();
        }
        return null; // Should never reach here.
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0: return mContext.getString(R.string.title_section1).toUpperCase(l);
            case 1: return mContext.getString(R.string.title_section2).toUpperCase(l);
        }
        return null;
    }

    public int getPageIcon(int position) {
        switch (position) {
            case 0: return R.drawable.ic_tab_inbox;
            case 1: return R.drawable.ic_tab_friends;
        }
        return R.drawable.ic_tab_inbox;
    }

}