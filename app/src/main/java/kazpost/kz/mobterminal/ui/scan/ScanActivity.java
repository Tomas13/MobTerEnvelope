package kazpost.kz.mobterminal.ui.scan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kazpost.kz.mobterminal.R;
import kazpost.kz.mobterminal.ui.base.BaseActivity;
import kazpost.kz.mobterminal.ui.closecell.ChooseCloseActivity;
import kazpost.kz.mobterminal.ui.main.MainActivity;

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
    @BindView(R.id.btn_scan_go_to_main)
    Button btnScanGoToMain;
    @BindView(R.id.btn_scan_go_to_close)
    Button btnScanGoToClose;
    @BindView(R.id.relative_scan)
    RelativeLayout relativeScan;
    @BindView(R.id.progress_scan)
    ProgressBar progressScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(ScanActivity.this);
    }

    @Override
    public void showLoading() {

        progressScan.setVisibility(View.VISIBLE);
        relativeScan.setAlpha(0.5F);

    }

    @Override
    public void hideLoading() {
        if (progressScan.getVisibility() == View.VISIBLE) {
            progressScan.setVisibility(View.GONE);

            relativeScan.setAlpha(1);
        }

    }

    @OnTextChanged(R.id.et_scan_activity)
    public void onScan() {
//        Log.d("ScanAc", " onScan called");
        if (etScanActivity.getText().toString().length() == 13)
            presenter.onScan(etScanActivity.getText().toString());
    }

    @Override
    public void clearEditText() {
        etScanActivity.setText("");
    }

    @Override
    public void clearBagEditText() {
        etScanBagActivity.setText("");
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
            presenter.onBagScan(etScanActivity.getText().toString(),
                    etScanBagActivity.getText().toString());
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showMistakeDialog(String msg) {
        showErrorDialog(msg);
    }

    @OnClick({R.id.btn_scan_go_to_main, R.id.btn_scan_go_to_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_scan_go_to_main:
//                this.finish();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.btn_scan_go_to_close:
                finish();
//                startActivity(this, new CloseCellActivity());
                startActivity(this, new ChooseCloseActivity());
                break;
        }
    }
}
