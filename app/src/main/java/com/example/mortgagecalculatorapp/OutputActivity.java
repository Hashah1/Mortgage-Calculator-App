package com.example.mortgagecalculatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class OutputActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        Bundle data = intent.getExtras();

        Fragment fragment = new OutputFragment();
        fragment.setArguments(data);
        return fragment;
    }
}
