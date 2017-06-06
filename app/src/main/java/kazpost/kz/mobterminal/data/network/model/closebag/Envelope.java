package kazpost.kz.mobterminal.data.network.model.closebag;

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

    public static class Body {

        @Element(name="CloseBagResponse", required=false)
        CloseBagResponse closeBagResponse;

        public CloseBagResponse getCloseBagResponse() {return this.closeBagResponse;}
        public void setCloseBagResponse(CloseBagResponse value) {this.closeBagResponse = value;}

    }

    public static class CloseBagResponse {

        @Element(name="ResponseInfo", required=false)
        ResponseInfo responseInfo;

        @Element(name="OperatorName", required=false)
        String operatorName;

        @Element(name="SendMethod", required=false)
        String sendMethod;

        @Element(name="SealNumber", required=false)
        String sealNumber;

        @Element(name="BagType", required=false)
        String bagType;

        @Element(name="ToDep", required=false)
        String toDep;

        @Element(name="GNumber", required=false)
        String gNumber;

        @Element(name="Weight", required=false)
        String weight;

        @Element(name="FromDep", required=false)
        String fromDep;

        public ResponseInfo getResponseInfo() {return this.responseInfo;}
        public void setResponseInfo(ResponseInfo value) {this.responseInfo = value;}

        public String getOperatorName() {return this.operatorName;}
        public void setOperatorName(String value) {this.operatorName = value;}

        public String getSendMethod() {return this.sendMethod;}
        public void setSendMethod(String value) {this.sendMethod = value;}

        public String getSealNumber() {return this.sealNumber;}
        public void setSealNumber(String value) {this.sealNumber = value;}

        public String getBagType() {return this.bagType;}
        public void setBagType(String value) {this.bagType = value;}

        public String getToDep() {return this.toDep;}
        public void setToDep(String value) {this.toDep = value;}

        public String getGNumber() {return this.gNumber;}
        public void setGNumber(String value) {this.gNumber = value;}

        public String getWeight() {return this.weight;}
        public void setWeight(String value) {this.weight = value;}

        public String getFromDep() {return this.fromDep;}
        public void setFromDep(String value) {this.fromDep = value;}

    }

}