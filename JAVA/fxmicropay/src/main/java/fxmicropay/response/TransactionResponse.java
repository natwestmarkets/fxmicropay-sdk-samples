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
import java.math.BigDecimal;

public class TransactionResponse implements Serializable {

    private Integer transactionId;
    private String schemeTransactionId;
    private String receivedDateTime;
    private String tradeDate;
    private BigDecimal effectiveRate;
    private String schemeBuyCurrency;
    private Double schemeBuyAmount;
    private String schemeSellCurrency;
    private Double schemeSellAmount;
    private Integer statusCode;
    private String statusMessage;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getSchemeTransactionId() {
        return schemeTransactionId;
    }

    public void setSchemeTransactionId(String schemeTransactionId) {
        this.schemeTransactionId = schemeTransactionId;
    }

    public String getReceivedDateTime() {
        return receivedDateTime;
    }

    public void setReceivedDateTime(String receivedDateTime) {
        this.receivedDateTime = receivedDateTime;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public BigDecimal getEffectiveRate() {
        return effectiveRate;
    }

    public void setEffectiveRate(BigDecimal effectiveRate) {
        this.effectiveRate = effectiveRate;
    }

    public String getSchemeBuyCurrency() {
        return schemeBuyCurrency;
    }

    public void setSchemeBuyCurrency(String schemeBuyCurrency) {
        this.schemeBuyCurrency = schemeBuyCurrency;
    }

    public Double getSchemeBuyAmount() {
        return schemeBuyAmount;
    }

    public void setSchemeBuyAmount(Double schemeBuyAmount) {
        this.schemeBuyAmount = schemeBuyAmount;
    }

    public String getSchemeSellCurrency() {
        return schemeSellCurrency;
    }

    public void setSchemeSellCurrency(String schemeSellCurrency) {
        this.schemeSellCurrency = schemeSellCurrency;
    }

    public Double getSchemeSellAmount() {
        return schemeSellAmount;
    }

    public void setSchemeSellAmount(Double schemeSellAmount) {
        this.schemeSellAmount = schemeSellAmount;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "transactionId=" + transactionId +
                ", schemeTransactionId='" + schemeTransactionId + '\'' +
                ", receivedDateTime='" + receivedDateTime + '\'' +
                ", tradeDate='" + tradeDate + '\'' +
                ", effectiveRate=" + effectiveRate +
                ", schemeBuyCurrency='" + schemeBuyCurrency + '\'' +
                ", schemeBuyAmount=" + schemeBuyAmount +
                ", schemeSellCurrency='" + schemeSellCurrency + '\'' +
                ", schemeSellAmount=" + schemeSellAmount +
                ", statusCode=" + statusCode +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }
}
