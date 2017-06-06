package kazpost.kz.mobterminal.data.network.model.parcel;

/**
 * Created by root on 4/19/17.
 */

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "sch:ParcelToBagRequest", strict = false)
public class ParcelData {


    @Element(name = "sch:SessionId")
    private String ASessionId;

    @Element(name = "sch:ParcelBarcode")
    private String BParcelBarcode;

    @Element(name = "sch:BagBarcode")
    private String CBagBarcode;

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

    public String getCBagBarcode() {
        return CBagBarcode;
    }

    public void setCBagBarcode(String CBagBarcode) {
        this.CBagBarcode = CBagBarcode;
    }
}
