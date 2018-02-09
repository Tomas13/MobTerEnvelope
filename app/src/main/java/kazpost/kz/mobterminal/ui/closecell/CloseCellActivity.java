package kazpost.kz.mobterminal.ui.closecell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kazpost.kz.mobterminal.R;
import kazpost.kz.mobterminal.ui.base.BaseActivity;
import kazpost.kz.mobterminal.ui.print.PrintActivity;
import kazpost.kz.mobterminal.utils.AppConstants;

import static kazpost.kz.mobterminal.utils.AppConstants.PRINT_ACTIVITY;

public class CloseCellActivity extends BaseActivity implements CloseCellMvpView {

    @Inject
    CloseCellMvpPresenter<CloseCellMvpView> presenter;

    @BindView(R.id.et_code_bag)
    EditText etCodeBag;
    @BindView(R.id.et_number_seal)
    EditText etNumberSeal;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.btn_back_closecell)
    Button btnBackClosecell;
    @BindView(R.id.btn_next_closecell)
    Button btnNextClosecell;
    @BindView(R.id.progress_close)
    ProgressBar progressClose;

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_cell);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(CloseCellActivity.this);


        if (getIntent().getExtras().get(AppConstants.BAG_TYPE) != null) {
            type = getIntent().getExtras().getInt(AppConstants.BAG_TYPE);
        }

        Log.d("CloseVMAc", "onCreate: " + type);

    }

    @OnClick({R.id.btn_back_closecell, R.id.btn_next_closecell})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_closecell:
                showDialog();
//                super.onBackPressed();
                break;
            case R.id.btn_next_closecell:


                String bagBarcode = etCodeBag.getText().toString();
                String sealNumber = etNumberSeal.getText().toString();
                String wei = etWeight.getText().toString();

                if (checkEmptyFields(bagBarcode, sealNumber, wei) && checkWeight(wei)) {
                    presenter.closeBagRequest(bagBarcode, sealNumber, wei, type);
                } /*else {
//                    onErrorToast("Заполните все поля");
                }
*/
                break;
        }
    }

    private boolean checkWeight(String wei) {
        if (Double.parseDouble(wei) > 14.5) {
            showMistakeDialog("Вес не может превышать 14.5 кг");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkEmptyFields(String bagBarcode, String sealNumber, String wei) {

        if (bagBarcode.length() > 0 && sealNumber.length() > 0 && wei.length() > 0) {
            Pattern mPatternBar = Pattern.compile("^\\d+(\\.\\d{0,2})?$");
            Matcher matcher = mPatternBar.matcher(etWeight.getText());
            if (matcher.find()) {
                return true;
            } else {
                onErrorToast("Неверный формат веса");
            }

        } else {
            onErrorToast("Заполните все поля");
        }

        return false;
    }

    @Override
    public void openPrintActivity(Bundle bundle) {

        Intent intent = new Intent(this, PrintActivity.class);
        intent.putExtra(PRINT_ACTIVITY, bundle);

        startActivity(intent);
    }

    @Override
    public void startLoginActivity() {
        startLoginActivity(this);
    }

    @Override
    public void showMistakeDialog(String msg) {
        showErrorDialog(msg);
    }

    @Override
    public void showLoading() {
        progressClose.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (progressClose.getVisibility() == View.VISIBLE) {
            progressClose.setVisibility(View.GONE);

//            relativeScan.setAlpha(1);
        }
    }


    @Override
    public void onBackPressed() {
        showDialog();
//        super.onBackPressed();
    }

    private void showDialog() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_exit_message);
        //                .setTitle(R.string.dialog_title);

        builder.setCancelable(false);
        builder.setPositiveButton("Да", (dialog, which) -> super.onBackPressed());
        builder.setNegativeButton("Нет", ((dialog, which) -> dialog.dismiss()));

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

}
