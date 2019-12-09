/*
Copyright [2019] [NatWest Markets Plc]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package fxmicropay.request;

import java.io.Serializable;

public class RateRequest implements Serializable {

    private String schemeBuyCurrency;
    private String schemeSellCurrency;
    private Long schemeId;
    private Long segmentId;

    public Long getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Long segmentId) {
        this.segmentId = segmentId;
    }

    public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    public String getSchemeBuyCurrency() {
        return schemeBuyCurrency;
    }

    public void setSchemeBuyCurrency(String schemeBuyCurrency) {
        this.schemeBuyCurrency = schemeBuyCurrency;
    }

    public String getSchemeSellCurrency() {
        return schemeSellCurrency;
    }

    public void setSchemeSellCurrency(String schemeSellCurrency) {
        this.schemeSellCurrency = schemeSellCurrency;
    }

}
