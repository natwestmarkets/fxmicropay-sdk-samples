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

package fxmicropay.response;

import java.io.Serializable;

public class RateResponse implements Serializable {

    private Long rateQuoteId;
    private Integer segmentId;
    private String schemeBuyCurrency;
    private String schemeSellCurrency;
    private Integer tenorDays;
    private String valueDate;
    private String expiryDateTime;
    private String unitCurrency;
    private Double rate;
    private Boolean isExecutable;

    public Long getRateQuoteId() {
        return rateQuoteId;
    }

    public void setRateQuoteId(Long rateQuoteId) {
        this.rateQuoteId = rateQuoteId;
    }

    public Integer getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Integer segmentId) {
        this.segmentId = segmentId;
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

    public Integer getTenorDays() {
        return tenorDays;
    }

    public void setTenorDays(Integer tenorDays) {
        this.tenorDays = tenorDays;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(String expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }

    public String getUnitCurrency() {
        return unitCurrency;
    }

    public void setUnitCurrency(String unitCurrency) {
        this.unitCurrency = unitCurrency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Boolean getIsExecutable() {
        return isExecutable;
    }

    public void setIsExecutable(Boolean executable) {
        isExecutable = executable;
    }

    @Override
    public String toString() {
        return "RateResponse{" +
                "rateQuoteId=" + rateQuoteId +
                ", segmentId=" + segmentId +
                ", schemeBuyCurrency='" + schemeBuyCurrency + '\'' +
                ", schemeSellCurrency='" + schemeSellCurrency + '\'' +
                ", tenorDays=" + tenorDays +
                ", valueDate='" + valueDate + '\'' +
                ", expiryDateTime='" + expiryDateTime + '\'' +
                ", unitCurrency='" + unitCurrency + '\'' +
                ", rate=" + rate +
                ", isExecutable=" + isExecutable +
                '}';
    }
}
