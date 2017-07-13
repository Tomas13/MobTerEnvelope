package kazpost.kz.mobterminal.ui.print;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kazpost.kz.mobterminal.R;
import kazpost.kz.mobterminal.data.DataManager;
import kazpost.kz.mobterminal.data.network.NetworkService;
import kazpost.kz.mobterminal.ui.base.BaseActivity;
import kazpost.kz.mobterminal.ui.main.MainActivity;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static kazpost.kz.mobterminal.ui.print.Singleton.getUserClient;

public class ChoosePrinterActivity extends BaseActivity {

    @Inject
    DataManager dataManager;

    @BindView(R.id.et_ip_address)
    EditText etIpAddress;
    @BindView(R.id.et_printer_name)
    EditText etPrinterName;
    @BindView(R.id.et_server_address)
    EditText etServerAddress;
    @BindView(R.id.btn_save_printer)
    Button btnSavePrinter;
    @BindView(R.id.btn_back_choose_printer)
    Button btnBackChoosePrinter;
    @BindView(R.id.btn_test_print)
    Button btnTestPrint;
    String ipAddress, printerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_printer);

        ButterKnife.bind(this);
        getActivityComponent().inject(this);

        getPrinter();

    }

    private void getPrinter() {
        if (dataManager.getPrinterIp() != null && dataManager.getPrinterName() != null && dataManager.getServerIp() != null) {
            if (dataManager.getPrinterIp().length() > 0 && dataManager.getPrinterName().length() > 0
                    && dataManager.getServerIp().length() > 0) {
                etServerAddress.setText(dataManager.getServerIp());
                etIpAddress.setText(dataManager.getPrinterIp());
                etPrinterName.setText(dataManager.getPrinterName());
            }
        }
    }

    @OnClick({R.id.btn_save_printer, R.id.btn_back_choose_printer, R.id.btn_test_print})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save_printer:
                onSavePrinterBtn();
                onErrorToast("Сохранено");
                hideKeyboard();
                break;
            case R.id.btn_back_choose_printer:

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

//                this.finish();
//                startActivity(this, new MainActivity());
                break;
            case R.id.btn_test_print:

                ipAddress = dataManager.getPrinterIp();
                printerName = dataManager.getPrinterName();

                if (ipAddress != null & printerName != null) {
                    sendToPrint();
                } else {
                    onErrorToast("Пропишите адрес и название принтера");
                }

                break;
        }
    }


    private void onSavePrinterBtn() {
        String serverIp = etServerAddress.getText().toString();
        String printerName = etPrinterName.getText().toString();
        String printerIp = etIpAddress.getText().toString();

        if (printerName.length() > 0 && printerIp.length() > 0) {
            dataManager.savePrinter(serverIp, printerIp, printerName);

        } else {
            onErrorToast("Сканируйте ip-адрес и название");
        }
    }

    private void sendToPrint() {

        String url = "http://" + dataManager.getServerIp() + ":8585";

        Retrofit retrofitRoutes = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getUserClient("G1234567878923",
                        "Коктеубаева Айжан",
                        "239023",
                        "мешок \"Cактандыру\"",
                        "12:32:12",
                        "3",
                        "340",
                        "Алматы",
                        "Астана",
                        "Без акта",
                        dataManager.getPrinterIp(),
                        dataManager.getPrinterName()
                ))
                .build();


        NetworkService gitHubServ = retrofitRoutes.create(NetworkService.class);

        Observable<ResponseBody> sendPrint = gitHubServ.sendToPrint();

        showLoading();
        sendPrint.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                            try {
                                hideLoading();
                                showPrintSuccessOrFailureDialog(responseBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("PrintA", responseBody.toString());
                        },
                        throwable -> {
                            hideLoading();
                            showPrintSuccessOrFailureDialog(throwable.getMessage());
                            Log.d("PrintAT", throwable.getMessage());
                        });
    }

    private void showPrintSuccessOrFailureDialog(String msg) {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(msg);

        builder.setNegativeButton("Ok", ((dialog, which) -> dialog.dismiss()));

        builder.setCancelable(false);
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
