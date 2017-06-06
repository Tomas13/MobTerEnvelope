package kazpost.kz.mobterminal.data.network.model.closebag;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by root on 4/17/17.
 */

@Root(name = "soapenv:Envelope", strict = false)
@NamespaceList({
        @Namespace( prefix = "soapenv", reference = "http://schemas.xmlsoap.org/soap/envelope/"),
        @Namespace( prefix = "sch", reference = "http://webservices.kazpost.kz/mobiterminal/schema"),
})
public class CloseBagEnvelope {

    @Element(name = "soapenv:Header", required = false)
    private String header = "";


    @Element(name = "soapenv:Body", required = false)
    private CloseBagBody closeBagBody;

    public CloseBagBody getCloseBagBody() {
        return closeBagBody;
    }

    public void setCloseBagBody(CloseBagBody closeBagBody) {
        this.closeBagBody = closeBagBody;
    }
}
