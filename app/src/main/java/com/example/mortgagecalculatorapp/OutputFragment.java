package com.example.mortgagecalculatorapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OutputFragment extends Fragment {

    private TextView totalMonthlyView, totalInterestView;
    private TextView totalPropertyTaxView, payOffDateView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_output, parent, false);
        Bundle data = getArguments();

        // Link text views
        totalMonthlyView = view.findViewById(R.id.total_monthly_payment);
        totalInterestView = view.findViewById(R.id.total_interest_paid);
        totalPropertyTaxView = view.findViewById(R.id.propertyTaxRate);
        payOffDateView = view.findViewById(R.id.pay_off_date);

        if (data != null) {
            double homeValue = data.getDouble("homeValue");
            double downPayment = data.getDouble("downPayment");
            double interestRate = data.getDouble("interestRate");
            int terms = data.getInt("termsView");
            double propertyTaxRate = data.getDouble("propertyTaxRate");

            calcResults(homeValue,
                    downPayment,
                    interestRate,
                    terms,
                    propertyTaxRate);

        }

        return view;
    }

    /**
     * Do all calculation to display in detail fragment
     * @param homeValue
     * @param downPayment
     * @param interestRate
     * @param terms
     * @param propertyTaxRate
     */
    public void calcResults(double homeValue,
                            double downPayment,
                            double interestRate,
                            int terms,
                            double propertyTaxRate) {
        double principleVal = homeValue - downPayment; // calculate principle
        double monthlyInterestVal = (interestRate)/ 1200;
        double numMonthlyPayments= terms * 12;

        // total monthly payment result
        double monthlyPaymentResult = ((principleVal*monthlyInterestVal * Math.pow(1 + monthlyInterestVal, numMonthlyPayments))) / (Math.pow(1 + monthlyInterestVal, numMonthlyPayments) - 1);

        // total interest paid
        double totalInterestPaidResult = monthlyPaymentResult * numMonthlyPayments - principleVal;


        double propertyTax = propertyTaxRate/1200 * homeValue;

        // total property tax paid
        double totalPropertyTaxRateResult = propertyTax * numMonthlyPayments;

        // Pay off date
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM");
        String month = dateFormat.format(cal.getTime());
        SimpleDateFormat anotherDateFormat = new SimpleDateFormat("yyy");

        cal.add(Calendar.YEAR, terms);
        String year = anotherDateFormat.format(cal.getTime());
        String date = month + " " + year;

        totalMonthlyView.setText(String.format("%.2f", monthlyPaymentResult));
        totalInterestView.setText(String.format("%.2f", totalInterestPaidResult));
        totalPropertyTaxView.setText(String.format("%.2f", totalPropertyTaxRateResult));
        payOffDateView.setText(date);
    }
}
