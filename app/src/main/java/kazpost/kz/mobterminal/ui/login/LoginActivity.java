package kazpost.kz.mobterminal.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kazpost.kz.mobterminal.R;
import kazpost.kz.mobterminal.ui.base.BaseActivity;
import kazpost.kz.mobterminal.ui.main.MainActivity;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_pin)
    EditText etPin;
    @BindString(R.string.enter_your_pin)
    String enterPin;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.et_code)
    EditText etCode;

    String userBarcode, userPin;
    @BindView(R.id.btn_barcode)
    Button btnBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);

        mPresenter.onAttach(LoginActivity.this);

        if (mPresenter.isLoggedIn()){
            openMainActivity();
            finish();
        }
    }


    @OnClick(R.id.btn_barcode)
    public void onViewClicked() {
        userBarcode = etCode.getText().toString();
        mPresenter.onLoginCodeScan();

    }


    @OnClick(R.id.btn_login_exit)
    public void onExitClicked() {
        finish();
    }

    @OnClick(R.id.btn_login)
    public void onBtnLoginClicked() {

        userPin = etPin.getText().toString();

        if (userPin.length() == 4) {
            mPresenter.onLoginBtnClicked(userBarcode, userPin);
        }else{
            onErrorToast(getString(R.string.pin_length));
        }


/*
        if (userBarcode.length() > 0) {
            mPresenter.onLoginBtnClicked(userBarcode, userPin);
        } else {
            onErrorToast(getString(R.string.no_user_barcode));
        }
*/

    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void showPinEditText() {
        showKeyboard();
        etCode.setVisibility(View.INVISIBLE);
        btnBarcode.setVisibility(View.INVISIBLE);
        btnLogin.setVisibility(View.VISIBLE);
        etPin.setVisibility(View.VISIBLE);

        etPin.requestFocus();
        tvLogin.setText(enterPin);
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }


}
