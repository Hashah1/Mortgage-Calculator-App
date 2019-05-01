package com.example.mortgagecalculatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

public class InputActivity extends SingleFragmentActivity implements InputFragment.InputFragmentListener {

    private boolean isTwoPane;

    @Override
    protected Fragment createFragment() {
        return new InputFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = findViewById(R.id.fragment_details_container);
        if (frameLayout != null) {
            isTwoPane = true;
        }
    }

    @Override
    public void onCalculatedResult(double homeValue,
                                   double downPayment,
                                   double interestRate,
                                   int termsView,
                                   double propertyTaxRate) {
        // Put all data into the double
        Bundle data = new Bundle();
        data.putDouble("homeValue", homeValue);
        data.putDouble("downPayment", downPayment);
        data.putDouble("interestRate", interestRate);
        data.putInt("termsView", termsView);
        data.putDouble("propertyTaxRate", propertyTaxRate);

        // If device is large enough, then it should have a two pane window
        // Then display screen for large device
        if (isTwoPane) {
            // show the output fragment in fragment_details_container
            OutputFragment fragment = new OutputFragment();
            fragment.setArguments(data);

            FragmentManager fragmentManager = getSupportFragmentManager();

            // Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // Replace the contents of the container with the new fragment
            ft.replace(R.id.fragment_details_container, fragment);
            // Complete the changes added above
            ft.commit();
        }
        // Otherwise, display screen on normal phone as a "second" page
        else {

            Intent intent = new Intent(this, OutputActivity.class);
            intent.putExtras(data);
            startActivity(intent);
        }
    }
}
