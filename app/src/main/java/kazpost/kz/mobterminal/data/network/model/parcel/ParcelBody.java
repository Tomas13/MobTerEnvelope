package kazpost.kz.mobterminal.data.network.model.parcel;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by root on 4/17/17.
 */

@Root(name = "soapenv:Body", strict = false)
public class ParcelBody {

    @Element(name = "sch:ParcelToBagRequest", required = true)
    private ParcelData parcelData;

    public ParcelData getParcelData() {
        return parcelData;
    }

    public void setParcelData(ParcelData parcelData) {
        this.parcelData = parcelData;
    }

}
