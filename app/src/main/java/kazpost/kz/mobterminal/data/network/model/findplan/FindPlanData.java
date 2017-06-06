package kazpost.kz.mobterminal.data.network.model.findplan;

/**
 * Created by root on 4/19/17.
 */

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "sch:FindPlanRequest", strict = false)
public class FindPlanData {


    @Element(name = "sch:SessionId")
    private String ASessionId;

    @Element(name = "sch:ParcelBarcode")
    private String BParcelBarcode;

    public String getASessionId() {
        return ASessionId;
    }

    public void setASessionId(String ASessionId) {
        this.ASessionId = ASessionId;
    }

    public String getBParcelBarcode() {
        return BParcelBarcode;
    }

    public void setBParcelBarcode(String BParcelBarcode) {
        this.BParcelBarcode = BParcelBarcode;
    }
}
