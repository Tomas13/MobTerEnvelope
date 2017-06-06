package kazpost.kz.mobterminal.data.network.model.findplan;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

import kazpost.kz.mobterminal.data.network.model.request.AuthRequestBody;

/**
 * Created by root on 4/17/17.
 */

@Root(name = "soapenv:Envelope", strict = false)
@NamespaceList({
        @Namespace( prefix = "soapenv", reference = "http://schemas.xmlsoap.org/soap/envelope/"),
        @Namespace( prefix = "sch", reference = "http://webservices.kazpost.kz/mobiterminal/schema"),
})
public class FindPlanEnvelope {

    @Element(name = "soapenv:Header", required = false)
    private String header = "";


    @Element(name = "soapenv:Body", required = false)
    private FindPlanBody findPlanBody;

    public FindPlanBody getFindPlanBody() {
        return findPlanBody;
    }

    public void setFindPlanBody(FindPlanBody findPlanBody) {
        this.findPlanBody = findPlanBody;
    }
}
