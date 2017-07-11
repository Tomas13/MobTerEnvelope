package kazpost.kz.mobterminal.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kazpost.kz.mobterminal.BuildConfig;
import kazpost.kz.mobterminal.R;
import kazpost.kz.mobterminal.ui.base.BaseActivity;
import kazpost.kz.mobterminal.ui.closecell.CloseCellActivity;
import kazpost.kz.mobterminal.ui.print.ChoosePrinterActivity;
import kazpost.kz.mobterminal.ui.scan.ScanActivity;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;
    @BindView(R.id.btn_sort)
    Button btnSort;
    @BindView(R.id.btn_close_cell)
    Button btnCloseCell;
    @BindView(R.id.btn_config_printer)
    Button btnConfigPrinter;
    @BindView(R.id.btn_exit)
    Button btnExit;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        tvVersionName.append(BuildConfig.VERSION_NAME);

        getActivityComponent().inject(this);
        mPresenter.onAttach(MainActivity.this);

    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }


    @Override
    public void openScanActivity() {
        startActivity(this, new ScanActivity());
    }

    @Override
    public void openConfigPrinter() {
        startActivity(this, new ChoosePrinterActivity());

    }

    @Override
    public void openCloseCellActivity() {
        startActivity(this, new CloseCellActivity());
    }

    @OnClick({R.id.btn_sort, R.id.btn_close_cell, R.id.btn_config_printer, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sort:
                mPresenter.onSortBtnClicked();
                break;
            case R.id.btn_close_cell:
                mPresenter.onCloseCellBtnClicked();
                break;
            case R.id.btn_config_printer:
                mPresenter.onConfigPrinterBtnClicked();
                break;
            case R.id.btn_exit:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

}
