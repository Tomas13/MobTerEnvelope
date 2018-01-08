package kazpost.kz.mobterminal.data.network.model.openbag;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by root on 4/17/17.
 */

@Root(name = "soapenv:Body", strict = false)
public class ScanOpenBagRequestBody {

    @Element(name = "sch:regWAYBILLOpRequest", required = true)
    private ScanOpenBagData scanOpenBagData;

    public ScanOpenBagData getScanOpenBagData() {
        return scanOpenBagData;
    }

    public void setScanOpenBagData(ScanOpenBagData scanOpenBagData) {
        this.scanOpenBagData = scanOpenBagData;
    }

}
