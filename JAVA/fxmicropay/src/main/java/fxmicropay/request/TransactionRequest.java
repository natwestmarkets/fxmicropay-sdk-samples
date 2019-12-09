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
import java.math.BigDecimal;
import java.util.Date;

public class TransactionRequest implements Serializable {

    private Long segmentId;
    private String counterparty1;

    private Date schemeDateTime;
    private Long rateQuoteId;
    private String dealtCurrency;
    private BigDecimal dealtAmount;
    private String schemeBatchId;
    private String schemeTransactionId;
    private BigDecimal amount;
    private String currency;

    private Integer originalTransactionId;
    private String originalSchemeTransactionId;

    public Long getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Long segmentId) {
        this.segmentId = segmentId;
    }

    public String getCounterparty1() {
        return counterparty1;
    }

    public void setCounterparty1(String counterparty1) {
        this.counterparty1 = counterparty1;
    }

    public Date getSchemeDateTime() {
        return schemeDateTime;
    }

    public void setSchemeDateTime(Date schemeDateTime) {
        this.schemeDateTime = schemeDateTime;
    }

    public Long getRateQuoteId() {
        return rateQuoteId;
    }

    public void setRateQuoteId(Long rateQuoteId) {
        this.rateQuoteId = rateQuoteId;
    }

    public String getDealtCurrency() {
        return dealtCurrency;
    }

    public void setDealtCurrency(String dealtCurrency) {
        this.dealtCurrency = dealtCurrency;
    }

    public BigDecimal getDealtAmount() {
        return dealtAmount;
    }

    public void setDealtAmount(BigDecimal dealtAmount) {
        this.dealtAmount = dealtAmount;
    }

    public String getSchemeBatchId() {
        return schemeBatchId;
    }

    public void setSchemeBatchId(String schemeBatchId) {
        this.schemeBatchId = schemeBatchId;
    }

    public String getSchemeTransactionId() {
        return schemeTransactionId;
    }

    public void setSchemeTransactionId(String schemeTransactionId) {
        this.schemeTransactionId = schemeTransactionId;
    }

    public Integer getOriginalTransactionId() {
        return originalTransactionId;
    }

    public void setOriginalTransactionId(Integer originalTransactionId) {
        this.originalTransactionId = originalTransactionId;
    }

    public String getOriginalSchemeTransactionId() {
        return originalSchemeTransactionId;
    }

    public void setOriginalSchemeTransactionId(String originalSchemeTransactionId) {
        this.originalSchemeTransactionId = originalSchemeTransactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


}
