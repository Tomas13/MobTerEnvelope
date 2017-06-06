package kazpost.kz.mobterminal.data.network.model.parcel;

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

    public static class ParcelToBagResponse {

        @Element(name="ResponseInfo", required=false)
        ResponseInfo responseInfo;

        @Element(name="CreateTime", required=false)
        String createTime;

        public ResponseInfo getResponseInfo() {return this.responseInfo;}
        public void setResponseInfo(ResponseInfo value) {this.responseInfo = value;}

        public String getCreateTime() {return this.createTime;}
        public void setCreateTime(String value) {this.createTime = value;}

    }

    public static class Body {

        @Element(name="ParcelToBagResponse", required=false)
        ParcelToBagResponse parcelToBagResponse;

        public ParcelToBagResponse getParcelToBagResponse() {return this.parcelToBagResponse;}
        public void setParcelToBagResponse(ParcelToBagResponse value) {this.parcelToBagResponse = value;}

    }

}