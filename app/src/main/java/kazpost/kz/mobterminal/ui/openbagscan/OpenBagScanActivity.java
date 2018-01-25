package kazpost.kz.mobterminal.ui.openbagscan;

import android.os.Bundle;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kazpost.kz.mobterminal.R;
import kazpost.kz.mobterminal.ui.base.BaseActivity;
import kazpost.kz.mobterminal.ui.main.MainActivity;

public class OpenBagScanActivity extends BaseActivity implements OpenBagScanView {

    @Inject
    public OpenBagPresenter<OpenBagScanView> presenter;

    public static final String TAG = "OpenBagScanActivity";
    @BindView(R.id.et_openbag_scan_activity)
    EditText etOpenbagScanActivity;

    @BindString(R.string.success_close_open_bag)
    String successCloseBagMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_scan);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(OpenBagScanActivity.this);

    }

    @OnClick(R.id.btn_scan_go_to_main)
    public void onViewClicked() {
        startActivity(this, new MainActivity());
    }

    @OnClick(R.id.btn_close_openbag)
    public void onBtnCloseClicked() {
        presenter.onOpenBagScan(etOpenbagScanActivity.getText().toString());
    }

    @Override
    public void showMistakeDialog(boolean success) {
        if (success) {
            showErrorDialog(successCloseBagMsg);
        }
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
