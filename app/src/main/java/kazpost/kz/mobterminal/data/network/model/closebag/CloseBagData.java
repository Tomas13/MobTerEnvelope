package kazpost.kz.mobterminal.data.network.model.closebag;

/**
 * Created by root on 4/19/17.
 */

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "sch:CloseBagRequest", strict = false)
public class CloseBagData {


    @Element(name = "sch:SessionId")
    private String ASessionId;

    @Element(name = "sch:BagBarcode")
    private String BBagBarcode;

    @Element(name = "sch:SealNumber")
    private String CSealNumber;

    @Element(name = "sch:Weight")
    private String DWeight;


    public String getCSealNumber() {
        return CSealNumber;
    }

    public void setCSealNumber(String CSealNumber) {
        this.CSealNumber = CSealNumber;
    }

    public String getDWeight() {
        return DWeight;
    }

    public void setDWeight(String DWeight) {
        this.DWeight = DWeight;
    }

    public String getASessionId() {
        return ASessionId;
    }

    public void setASessionId(String ASessionId) {
        this.ASessionId = ASessionId;
    }

    public String getBBagBarcode() {
        return BBagBarcode;
    }

    public void setBBagBarcode(String BBagBarcode) {
        this.BBagBarcode = BBagBarcode;
    }
}
