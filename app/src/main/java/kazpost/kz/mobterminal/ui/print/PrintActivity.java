package kazpost.kz.mobterminal.ui.print;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
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
import static kazpost.kz.mobterminal.utils.AppConstants.BAG_TYPE;
import static kazpost.kz.mobterminal.utils.AppConstants.CLOSE_BAG_TIME;
import static kazpost.kz.mobterminal.utils.AppConstants.FROM_DEP;
import static kazpost.kz.mobterminal.utils.AppConstants.G_NUMBER;
import static kazpost.kz.mobterminal.utils.AppConstants.OPERATOR_NAME;
import static kazpost.kz.mobterminal.utils.AppConstants.PRINT_ACTIVITY;
import static kazpost.kz.mobterminal.utils.AppConstants.SEAL_NUMBER;
import static kazpost.kz.mobterminal.utils.AppConstants.SEND_METHOD;
import static kazpost.kz.mobterminal.utils.AppConstants.TO_DEP;
import static kazpost.kz.mobterminal.utils.AppConstants.WEIGHT_RESPONSE;

public class PrintActivity extends BaseActivity {

    @Inject
    DataManager dataManager;

    @BindString(R.string.bag_is_closed)
    String bagIsClosed;
    @BindString(R.string.wrong_g)
    String wrongG;

    @BindView(R.id.tv_g_number)
    TextView tvGNumber;
    @BindView(R.id.tv_seal_number)
    TextView tvSealNumber;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_operator_name)
    TextView tvOperatorName;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.tv_close_bag_time)
    TextView tvCloseBagTime;
    @BindView(R.id.tv_to_dep_index)
    TextView tvToDepIndex;
    @BindView(R.id.tv_to_dep_name)
    TextView tvToDepName;
    @BindView(R.id.ll_main_print)
    LinearLayout llMainPrint;
    @BindView(R.id.et_code_g)
    EditText etCodeG;
    @BindView(R.id.ll_print_buttons)
    LinearLayout llPrintButtons;

    @BindString(R.string.g_number)
    String gNumberTitle;
    @BindString(R.string.seal_number)
    String sealNumberTitle;
    @BindString(R.string.bag_weight)
    String weightTitle;
    @BindString(R.string.operator_name)
    String operatorTitle;
    @BindString(R.string.to_dep_index)
    String toDepIndexTitle;
    @BindString(R.string.to_dep_name)
    String toDepNameTitle;
    @BindString(R.string.bag_close_time)
    String closeTimeTitle;
    //    @BindView(R.id.btn_choose_printer)
//    Button btnChoosePrinter;
    @BindView(R.id.btn_repeat_print)
    Button btnRepeatPrint;

    String gNumber, sealNumber, weightResponse, fromDep,
            toDep, sendMethod, bagType, operatorName, date2,
            closeTime, ipAddress, printerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);

        Bundle bundle = getIntent().getBundleExtra(PRINT_ACTIVITY);

        if (bundle != null) {
            gNumber = bundle.getString(G_NUMBER, "G000000000");
            sealNumber = bundle.getString(SEAL_NUMBER, "00000");
            weightResponse = bundle.getString(WEIGHT_RESPONSE, "0");
            fromDep = bundle.getString(FROM_DEP, "fromDep");
            toDep = bundle.getString(TO_DEP, "toDep");
            sendMethod = bundle.getString(SEND_METHOD, "sendMethod");
            bagType = bundle.getString(BAG_TYPE, "bagType");
            operatorName = bundle.getString(OPERATOR_NAME, "operatorName");
            closeTime = bundle.getString(CLOSE_BAG_TIME, "00:00");
            date2 = "date";

            SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
            try {
                Date date = formatter3.parse(closeTime);
                SimpleDateFormat formatter2 = new SimpleDateFormat("dd.MM.yyyy 'в' HH:mm:ss", Locale.US);
                date2 = formatter2.format(date);
                Log.d("PrintActivity: ", date2);
            } catch (ParseException e) {
                Log.d("PrintActivity: ", e.toString());
            }

            tvGNumber.setText(gNumberTitle + " " + gNumber);
            tvSealNumber.setText(sealNumberTitle + " " + sealNumber);
            tvWeight.setText(weightTitle + " " + weightResponse);
//            tvToDepIndex.setText(toDepIndexTitle + " " + toDep);
            tvToDepName.setText(toDepNameTitle + " " + toDep);
            tvCloseBagTime.setText(closeTimeTitle + " " + date2);
            tvOperatorName.setText(operatorTitle + " " + operatorName);
        }
    }

    @OnTextChanged(R.id.et_code_g)
    public void etCode() {
        String enteredG = etCodeG.getText().toString();
        if (enteredG.equals(gNumber)) {
            onErrorToast(bagIsClosed);
        } else {
            onErrorToast(wrongG);
        }
    }


    private void sendToPrint() {

        String weightKg = weightResponse;
        String weightGr = "";

        if (weightResponse != null && weightResponse.contains(".")) {
            String[] weightStrArray = weightResponse.split("\\.");

            weightKg = weightStrArray[0];
            weightGr = weightStrArray[1] + "0";
        }

        String url = "http://" + dataManager.getServerIp() + ":8585";

        Retrofit retrofitRoutes = new Retrofit.Builder()
//                .baseUrl("http://192.168.204.85:8585")
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
//
//                .client(getUserClient(gNumber,
//                        operatorName,
//                        sealNumber,
//                        bagType,
//                        date2,
//                        weightKg,
//                        weightGr,
//                        fromDep,
//                        toDep,
//                        "Без акта",
//                        ipAddress,
//                        printerName
//                ))
                .build();


        NetworkService gitHubServ = retrofitRoutes.create(NetworkService.class);

        Observable<ResponseBody> sendPrint = gitHubServ.sendToPrint();

        sendPrint.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                            try {
                                showPrintSuccessOrFailureDialog(responseBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("PrintA", responseBody.toString());
                        },
                        throwable -> {
                            showPrintSuccessOrFailureDialog(throwable.getMessage());
                            Log.d("PrintAT", throwable.getMessage());
                        });
    }

    @OnClick({R.id.btn_go_main, R.id.btn_repeat_print})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_go_main:
                startActivity(this, new MainActivity());
                finish();
                break;
            case R.id.btn_repeat_print:

                ipAddress = dataManager.getPrinterIp();
                printerName = dataManager.getPrinterName();

                if (ipAddress != null & printerName != null) {
                    sendToPrint();
                } else {
                    onErrorToast("Пропишите адрес и название принтера");
                    startActivity(this, new ChoosePrinterActivity());
                }

                break;
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

        builder.setPositiveButton("Да", (dialog, which) -> super.onBackPressed());
        builder.setNegativeButton("Нет", ((dialog, which) -> dialog.dismiss()));

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void showPrintSuccessOrFailureDialog(String msg) {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(msg);

        builder.setNegativeButton("Ok", ((dialog, which) -> dialog.dismiss()));

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
