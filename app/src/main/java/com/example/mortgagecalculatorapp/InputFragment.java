package com.example.mortgagecalculatorapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class InputFragment extends Fragment {
    private EditText mHomeValueView, mDownPaymentView, mInterestRateView, mPropertyTaxRateView;
    private Spinner mTermsView;
    private Button mResetButton, mCalculateButton;
    private InputFragmentListener mListener;

    public interface InputFragmentListener {
        void onCalculatedResult(double homeValue,
                                double downPayment,
                                double interestRate,
                                int termsView,
                                double propertyTaxRate);
    }

    /**
     * An exception to catch potential activity which wont implement fragment listener
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (InputFragmentListener) getActivity();
        } catch (Exception e) {
            Log.e("InputFragment", "Activity must implement InputFragmentListener interface");
        }
    }

    /**
     *  The onCreateView method is called when Fragment should create its View object hierarchy,
     *      either dynamically or via XML layout inflation.
     */     //TODO: Append $ sign and % sign to appropriate views
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_input, parent, false);

        mHomeValueView = view.findViewById(R.id.input_home_value);
        mDownPaymentView = view.findViewById(R.id.input_down_payment);
        mInterestRateView = view.findViewById(R.id.input_interest_rate);
        mTermsView = view.findViewById(R.id.input_terms);
        mPropertyTaxRateView = view.findViewById(R.id.input_property_tax_rate);
        mResetButton = view.findViewById(R.id.reset_button);
        mCalculateButton = view.findViewById(R.id.calculate_button);

        // Set up spinner
        setUpSpinner();

        // Go to next fragment and display results accordingly
        mCalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean areFieldsPopulated = areFieldsPopulated();
                boolean isValidInput = false;
                if (areFieldsPopulated) {
                    isValidInput = isValidInput();
                }
                if (areFieldsPopulated && isValidInput) {
                    calculateResults();
                }
                else
                    displayError("Please enter valid input");
            }
        });

        // Clear all fields on the form
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
                System.out.println("Cleared all results");
            }
        });
        return view;
    }

    /**
     * Pass in all input values to the calculate result listener
     */
    public void calculateResults() {
        double homeVal = Double.valueOf(mHomeValueView.getText().toString());
        double downPayment = Double.valueOf(mDownPaymentView.getText().toString());
        double interestRate = Double.valueOf(mInterestRateView.getText().toString());
        int terms = Integer.valueOf(mTermsView.getSelectedItem().toString());
        double propertyTaxRate = Double.valueOf(mPropertyTaxRateView.getText().toString());
        mListener.onCalculatedResult(homeVal, downPayment, interestRate,terms, propertyTaxRate);
    }

    // Clear all fields upon Rest button click
    public void clearFields(){
        mHomeValueView.setText("");
        mDownPaymentView.setText("");
        mInterestRateView.setText("");
        mPropertyTaxRateView.setText("");
    }

    // Display error message modal
    private void displayError(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    // Helper function to enable spinner functionality
    private void setUpSpinner(){
        // Set up Spinner for terms view
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.terms, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mTermsView.setAdapter(adapter);
    }

    // Helper function to check if  allfields are populated
    private boolean areFieldsPopulated() {
        String homeVal = mHomeValueView.getText().toString();
        String downPayment = mDownPaymentView.getText().toString();
        String interestRate = mInterestRateView.getText().toString();
        String propertyTaxRate = mPropertyTaxRateView.getText().toString();

        if (homeVal.length() == 0|| downPayment.length() == 0 || interestRate.length() == 0 || propertyTaxRate.length() == 0) {
            return false;
        }
        return true;
    }

    // Helper function to check if valid input is entered
    private boolean isValidInput() {
        double home = Double.valueOf(mHomeValueView.getText().toString());
        double downpayment = Double.valueOf(mDownPaymentView.getText().toString());
        double interestrate = Double.valueOf(mInterestRateView.getText().toString());

        if (downpayment > home) return false;
        if (interestrate > 100) return false;
        return true;
    }
}