package kazpost.kz.mobterminal.ui.scan;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import kazpost.kz.mobterminal.R;
import kazpost.kz.mobterminal.ui.base.BaseActivity;

public class ScanActivity extends BaseActivity implements ScanMvpView {

    @Inject
    ScanMvpPresenter<ScanMvpView> presenter;

    @BindView(R.id.tv_scan_top)
    TextView tvScanTop;
    @BindView(R.id.tv_tracknumber)
    TextView tvTracknumber;
    @BindView(R.id.ll_found_plan)
    LinearLayout llFoundPlan;
    @BindView(R.id.tv_scan_bag)
    TextView tvScanBag;
    @BindView(R.id.et_scan_activity)
    EditText etScanActivity;

    @BindString(R.string.cell_tracknumber)
    String cellTrack;
    @BindView(R.id.tv_bag_barcode)
    TextView tvBagBarcode;
    @BindView(R.id.tv_bag_number)
    TextView tvBagNumber;
    @BindView(R.id.et_scan_bag_activity)
    EditText etScanBagActivity;


    @BindString(R.string.scan_package)
    String scanPackage;
    String mBagBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(ScanActivity.this);

    }

    @OnTextChanged(R.id.et_scan_activity)
    public void onScan() {
        Log.d("ScanAc", " onScan called");
        if (etScanActivity.getText().toString().length() == 13)
            presenter.onScan(etScanActivity.getText().toString());
    }

    @Override
    public void clearEditText() {
        etScanActivity.setText("");
    }

    @Override
    public void showBagTrackNumber(String bagBarcode, String bagNumber) {

        tvScanTop.setText(cellTrack);

        llFoundPlan.setVisibility(View.VISIBLE);
        tvTracknumber.setVisibility(View.VISIBLE);
        etScanBagActivity.setVisibility(View.VISIBLE);

        etScanActivity.setVisibility(View.GONE);

        mBagBarcode = bagBarcode;
        tvTracknumber.setText(etScanActivity.getText());
        tvBagBarcode.setText(bagBarcode);
        tvBagNumber.setText(bagNumber);
    }

    @Override
    public void readyForNextScan() {
        tvScanTop.setText("");
        tvScanTop.setText(scanPackage);

        etScanActivity.setText("");
        etScanBagActivity.setText("");
        llFoundPlan.setVisibility(View.GONE);
        tvTracknumber.setVisibility(View.GONE);
        etScanBagActivity.setVisibility(View.GONE);
        etScanActivity.setVisibility(View.VISIBLE);


    }

    @Override
    public void startLoginActivity() {
        startLoginActivity(this);
    }


    @OnTextChanged(R.id.et_scan_bag_activity)
    public void onBagScan() {
        if (etScanBagActivity.getText().toString().length() > 0)
            presenter.onBagScan(etScanActivity.getText().toString(), mBagBarcode);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

}
