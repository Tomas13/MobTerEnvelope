package kazpost.kz.mobterminal.data.network.model.openbag;

/**
 * Created by root on 4/19/17.
 */

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "sch:regWAYBILLOpRequest", strict = false)
public class ScanOpenBagData {


    @Element(name = "sch:p_sessionid")
    private String ASessionId;

    @Element(name = "sch:p_bag_barcode")
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
