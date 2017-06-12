package kazpost.kz.mobterminal.ui.print;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
//    @BindView(R.id.et_server_address)
//    EditText etServerAddress;

    @OnClick(R.id.btn_save_printer)
    public void savePrinter() {

//        String serverIp = ;
        String printerName = etPrinterName.getText().toString();
        String printerIp = etIpAddress.getText().toString();

        if (printerName.length() > 0 && printerIp.length() > 0) {
            dataManager.savePrinter(printerIp, printerName);
            startActivity(this, new MainActivity());
            this.finish();
        }else{
            onErrorToast("Сканируйте ip-адрес и название");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_printer);

        ButterKnife.bind(this);
        getActivityComponent().inject(this);

        getPrinter();

    }

    private void getPrinter() {
        if (dataManager.getPrinterIp().length() > 0 && dataManager.getPrinterName().length()>0){
            etIpAddress.setText(dataManager.getPrinterIp());
            etPrinterName.setText(dataManager.getPrinterName());
        }
    }


}
