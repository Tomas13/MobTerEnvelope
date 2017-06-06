package kazpost.kz.mobterminal.data.network.model.closebag;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by root on 4/17/17.
 */

@Root(name = "soapenv:Body", strict = false)
public class CloseBagBody {

    @Element(name = "sch:CloseBagRequest", required = true)
    private CloseBagData closeBagData;

    public CloseBagData getCloseBagData() {
        return closeBagData;
    }

    public void setCloseBagData(CloseBagData closeBagData) {
        this.closeBagData = closeBagData;
    }

}
