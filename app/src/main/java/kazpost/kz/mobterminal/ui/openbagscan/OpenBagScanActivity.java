package kazpost.kz.mobterminal.ui.openbagscan;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kazpost.kz.mobterminal.R;
import kazpost.kz.mobterminal.ui.base.BaseActivity;
import kazpost.kz.mobterminal.ui.main.MainActivity;

public class OpenBagScanActivity extends BaseActivity implements OpenBagScanView {

    @Inject
    OpenBagPresenter<OpenBagScanView> presenter;

    public static final String TAG = "OpenBagScanActivity";
    @BindView(R.id.et_openbag_scan_activity)
    EditText etOpenbagScanActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_scan);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(this);

    }

    @OnTextChanged(R.id.et_openbag_scan_activity)
    public void onOpenBagScan() {
        Log.d("ScanAc", " onScan called");
        if (etOpenbagScanActivity.getText().toString().length() == 13)
            presenter.onOpenBagScan(etOpenbagScanActivity.getText().toString());
    }

    @OnClick(R.id.btn_scan_go_to_main)
    public void onViewClicked() {
        startActivity(this, new MainActivity());
    }


    @Override
    public void clearEditText() {
        etOpenbagScanActivity.setText("");
    }

    @Override
    public void showMistakeDialog(String msg) {
        showErrorDialog(msg);
    }


    @Override
    public void startLoginActivity() {
        startLoginActivity(this);
    }


    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}
