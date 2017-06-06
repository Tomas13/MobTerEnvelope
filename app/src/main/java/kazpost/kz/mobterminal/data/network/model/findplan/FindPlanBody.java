package kazpost.kz.mobterminal.data.network.model.findplan;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by root on 4/17/17.
 */

@Root(name = "soapenv:Body", strict = false)
public class FindPlanBody {

    @Element(name = "sch:FindPlanRequest", required = true)
    private FindPlanData findPlanData;

    public FindPlanData getFindPlanData() {
        return findPlanData;
    }

    public void setFindPlanData(FindPlanData findPlanData) {
        this.findPlanData = findPlanData;
    }

}
