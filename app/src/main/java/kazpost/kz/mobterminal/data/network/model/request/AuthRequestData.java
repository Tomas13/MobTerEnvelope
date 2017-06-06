package kazpost.kz.mobterminal.data.network.model.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by root on 4/17/17.
 */

@Root(name = "sch:AuthorizeRequest", strict = false)
//@Namespace(reference = "http://172.30.223.25:8088/mobiterminal")
public class AuthRequestData {

    @Element(name = "sch:UserBarcode", required = true)
    private String userBarcode;

    @Element(name = "sch:UserPin", required = true)
    private String userPin;

    public String getUserBarcode() {
        return userBarcode;
    }

    public void setUserBarcode(String userBarcode) {
        this.userBarcode = userBarcode;
    }

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }
}
