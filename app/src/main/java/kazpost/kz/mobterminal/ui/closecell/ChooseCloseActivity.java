package kazpost.kz.mobterminal.ui.closecell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kazpost.kz.mobterminal.R;
import kazpost.kz.mobterminal.ui.openbagscan.OpenBagScanActivity;
import kazpost.kz.mobterminal.ui.scan.ScanActivity;

public class ChooseCloseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_close);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_insurance, R.id.btn_open_bag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_insurance:
                startActivity(new Intent(this, CloseCellActivity.class));
                break;
            case R.id.btn_open_bag:
                startActivity(new Intent(this, OpenBagScanActivity.class));
                break;
        }
    }
}
