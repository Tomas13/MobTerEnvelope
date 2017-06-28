package kazpost.kz.mobterminal.ui.print;

import android.os.Bundle;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kazpost.kz.mobterminal.R;
import kazpost.kz.mobterminal.data.DataManager;
import kazpost.kz.mobterminal.ui.base.BaseActivity;
import kazpost.kz.mobterminal.ui.main.MainActivity;

public class ChoosePrinterActivity extends BaseActivity {

    @Inject
    DataManager dataManager;

    @BindView(R.id.et_ip_address)
    EditText etIpAddress;
    @BindView(R.id.et_printer_name)
    EditText etPrinterName;
    @BindView(R.id.et_printer_barcode)
    EditText etPrinterBarcode;
//    @BindView(R.id.et_server_address)
//    EditText etServerAddress;

    private String printerName;
    private String printerIp;

    @OnClick(R.id.btn_save_printer)
    public void savePrinter() {

//        String serverIp = ;
//        printerName = etPrinterName.getText().toString();
//        printerIp = etIpAddress.getText().toString();

        if (printerName.length() > 0 && printerIp.length() > 0) {
            dataManager.savePrinter(printerIp, printerName);
            startActivity(this, new MainActivity());
            this.finish();
        } else {
            onErrorToast("Сканируйте ip-адрес и название");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_printer);

        ButterKnife.bind(this);
        getActivityComponent().inject(this);

//        getPrinter();

    }

    @OnTextChanged(R.id.et_printer_barcode)
    public void onPrinterScan() {
        String printerBarcode = etPrinterBarcode.getText().toString();
        if (printerBarcode.length() > 0) {
            switch (printerBarcode) {
                case "1":
                    printerIp = "192.168.204.47";
                    printerName = "EasyCode PD42 - Esim";
                    break;
                case "2":
                    printerIp = "192.168.204.22";
                    printerName = "EasyCode PD42 - Esim";
                    break;
            }
        }
    }

    private void getPrinter() {
        if (dataManager.getPrinterIp() != null && dataManager.getPrinterName() != null) {
//            etIpAddress.setText(dataManager.getPrinterIp());
//            etPrinterName.setText(dataManager.getPrinterName());
        }
    }


}
