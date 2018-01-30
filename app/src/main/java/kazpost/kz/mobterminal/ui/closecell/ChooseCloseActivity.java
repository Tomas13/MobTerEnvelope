package kazpost.kz.mobterminal.ui.closecell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kazpost.kz.mobterminal.R;
import kazpost.kz.mobterminal.ui.openbagscan.OpenBagScanActivity;
import kazpost.kz.mobterminal.utils.AppConstants;

public class ChooseCloseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_close);

        ButterKnife.bind(this);

    }


    //в поле vBagtype передаешь 20 если это сактандыру
//                в поле vBagtype передаешь 2 если это мешок  с писс корреспонденцией
    @OnClick({R.id.btn_insurance, R.id.btn_open_bag, R.id.btn_letter_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_insurance:
                Intent intent = new Intent(this, CloseCellActivity.class);
                intent.putExtra(AppConstants.BAG_TYPE, AppConstants.INSURANCE_BAG_TYPE_INT);
                startActivity(intent);
                break;
            case R.id.btn_open_bag:
                startActivity(new Intent(this, OpenBagScanActivity.class));
                break;
            case R.id.btn_letter_post:
                Intent intent1 = new Intent(this, CloseCellActivity.class);
                intent1.putExtra(AppConstants.BAG_TYPE, AppConstants.LETTER_POST_BAG_TYPE_INT);
                startActivity(intent1);
                break;

        }
    }

}
