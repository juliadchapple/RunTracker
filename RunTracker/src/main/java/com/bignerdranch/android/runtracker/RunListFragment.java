package com.bignerdranch.android.runtracker;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;

/**
 * Created by jchapple on 5/16/14.
 */
public class RunListFragment extends Fragment {


    private static class RunListCursorLoader extends SQLiteCursorLoader {

        public RunListCursorLoader(Context context) {
            super(context);
        }

        @Override
        protected Cursor loadCursor() {
            return null;//pg 565 //RunManager.get(getContext()).;
        }
    }
}
