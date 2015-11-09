package ua.com.ukrelektro.passwordrec.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.com.ukrelektro.passwordrec.R;
import ua.com.ukrelektro.passwordrec.model.CodeChecker;

/**
 * Fragment with CodeChecker
 */
public class CheckerFragment extends Fragment {
    public static final int LAYOUT = R.layout.checker_fragment;
    private View view;

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
        initTvCurrentCode();
        initTvPercentCovered();
        initTvTestedOut();
        return view;
    }

    /**
     * Initialization of TextView with current code
     */
    private void initTvCurrentCode() {
        TextView tvCurrentCode = (TextView) view.findViewById(R.id.tvCurrentCode);
        String currentCode = String.format("%04d", CodeChecker.getCurrentCode().getCode());
        tvCurrentCode.setText(currentCode);
    }

    /**
     * Initialization of TextView with code percent covered
     */
    private void initTvPercentCovered() {
        TextView tvPercentCovered = (TextView) view.findViewById(R.id.tvPercentCovered);
        String result = String.format("%.1f", CodeChecker.getPercentPassCodes());
        tvPercentCovered.setText(result + " Percent covered");
    }

    /**
     * Initialization of TextView with number of passed codes
     */
    private void initTvTestedOut() {
        TextView tvTestedOut = (TextView) view.findViewById(R.id.tvTestedOut);
        String testedOut = String.valueOf(CodeChecker.getCurrentCountNumber());
        tvTestedOut.setText(testedOut + " Tested out of 9,999");
    }


}
