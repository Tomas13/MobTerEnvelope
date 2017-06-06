package kazpost.kz.mobterminal.data.network.model.findplan;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.net.URL;
import java.util.List;

@Root(name="Envelope")
public class Envelope {

    @Element(name="Header", required=false)
    String header;

    @Element(name="Body", required=false)
    Body body;

    public String getHeader() {return this.header;}
    public void setHeader(String value) {this.header = value;}

    public Body getBody() {return this.body;}
    public void setBody(Body value) {this.body = value;}

    public static class ResponseInfo {

        @Element(name="ResponseCode", required=false)
        String responseCode;

        @Element(name="ResponseText", required=false)
        String responseText;

        @Element(name="ResponseGenTime", required=false)
        String responseGenTime;

        public String getResponseCode() {return this.responseCode;}
        public void setResponseCode(String value) {this.responseCode = value;}

        public String getResponseText() {return this.responseText;}
        public void setResponseText(String value) {this.responseText = value;}

        public String getResponseGenTime() {return this.responseGenTime;}
        public void setResponseGenTime(String value) {this.responseGenTime = value;}

    }

    public static class FindPlanResponse {

        @Element(name="ResponseInfo", required=false)
        ResponseInfo responseInfo;

        @Element(name="BagBarcode", required=false)
        String bagBarcode;

        @Element(name="BagNumber", required=false)
        String bagNumber;

        public ResponseInfo getResponseInfo() {return this.responseInfo;}
        public void setResponseInfo(ResponseInfo value) {this.responseInfo = value;}

        public String getBagBarcode() {return this.bagBarcode;}
        public void setBagBarcode(String value) {this.bagBarcode = value;}

        public String getBagNumber() {return this.bagNumber;}
        public void setBagNumber(String value) {this.bagNumber = value;}

    }

    public static class Body {

        @Element(name="FindPlanResponse", required=false)
        FindPlanResponse findPlanResponse;

        public FindPlanResponse getFindPlanResponse() {return this.findPlanResponse;}
        public void setFindPlanResponse(FindPlanResponse value) {this.findPlanResponse = value;}

    }

}