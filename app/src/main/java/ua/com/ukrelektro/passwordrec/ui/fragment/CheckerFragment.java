package ua.com.ukrelektro.passwordrec.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.com.ukrelektro.passwordrec.R;
import ua.com.ukrelektro.passwordrec.model.State;

/**
 * Fragment with CodeChecker
 */
public class CheckerFragment extends Fragment {
    public static final int LAYOUT = R.layout.checker_fragment;
    private View view;

    @Bind(R.id.progressBarPercent) ProgressBar pbPercent;
    @Bind(R.id.progressBarTestedOut) ProgressBar pbPassedCodes;
    @Bind(R.id.tvCurrentCode) TextView tvCurrentCode;
    @Bind(R.id.tvPercentCovered) TextView tvPercentCovered;
    @Bind(R.id.tvTestedOut) TextView tvTestedOut;



    /**
     * Get instance of CheckerFragment
     *
     * @return instance of CheckerFragment
     */
    public static CheckerFragment getInstance() {
        Bundle args = new Bundle();
        CheckerFragment fragment = new CheckerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);

        updateTextViev();
        updateProgressBars();
        return view;
    }

    private void updateProgressBars() {

        Handler progressBarHandler = new Handler();
        progressBarHandler.post(new Runnable() {

            public void run() {
                pbPassedCodes.setProgress(0);
                pbPercent.setProgress((int) 0);
            }
        });

    }

    /**
     * Initialization of TextView
     */
    private void updateTextViev() {
        String currentCode = String.format("%04d", 0);
        String percentPassCodes = "0";
        String testedOut = String.valueOf(0);
        tvCurrentCode.setText(currentCode);
        tvPercentCovered.setText(percentPassCodes + " Percent covered");
        tvTestedOut.setText(testedOut + " Tested out of 9,999");
    }




    /**
     * onClick handler for button "Worked and Next"
     *
     * @param view current view
     */
    @OnClick({R.id.btnNext, R.id.btnWorked})
    public void nextNumber(View view) {
        switch (view.getId()) {
            case R.id.btnNext:

                break;
            case R.id.btnWorked:

                break;
            default:
                break;
        }

        updateTextViev();
        updateProgressBars();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
