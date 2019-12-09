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

package fxmicropay;

import fxmicropay.request.RateRequest;
import fxmicropay.request.SchemeTransactionRequest;
import fxmicropay.request.TransactionRequest;
import fxmicropay.response.RateResponse;
import fxmicropay.response.TransactionResponse;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

public class ApiClient {

    private static final String ratesPath = "/rates";
    private static final String txnPath = "/transactions";
    private String rateUri;
    private String txnUri;
    private Long schemeId;
    private Long segmentId;
    private String schemeBuyCurrency;
    private String schemeSellCurrency;
    private ApiCaller apiCaller;
    private String counterparty;

    public ApiClient(Properties config) {
        this.schemeId = Long.valueOf(config.getProperty("schemeId"));
        this.rateUri = config.getProperty("hostUri") + ratesPath;
        this.txnUri = config.getProperty("hostUri") + txnPath;
        this.segmentId = Long.valueOf(config.getProperty("segmentId"));
        this.schemeBuyCurrency = config.getProperty("schemeBuyCurrency");
        this.schemeSellCurrency = config.getProperty("schemeSellCurrency");
        this.apiCaller = new ApiCaller(config.getProperty("certLocation"), config.getProperty("certPassword"));
        this.counterparty = config.getProperty("cpty");
    }

	//Retrieve rate for a specific currency pair
    public List<RateResponse> getRates() {
        RateRequest rateRequest = new RateRequest();
        rateRequest.setSchemeId(schemeId);
        Response response = apiCaller.call(rateUri, rateRequest);
        List<RateResponse> rateQuotes = response.readEntity(new GenericType<List<RateResponse>>() {
        });
        System.out.println("Rate Quotes received count " + rateQuotes.size());
        return rateQuotes;
    }

	//Retrieve rate for a specific currency pair
    public List<RateResponse> getRatesBySegment() {
        RateRequest rateRequest = new RateRequest();
        rateRequest.setSchemeId(schemeId);
        rateRequest.setSegmentId(segmentId);
        Response response = apiCaller.call(rateUri, rateRequest);
        List<RateResponse> rateQuotes = response.readEntity(new GenericType<List<RateResponse>>() {
        });
        System.out.println("Rate Quotes received count " + rateQuotes.size());
        return rateQuotes;
    }

	//Retrieve rate for a specific currency pair
    public List<RateResponse> getRatesByCurrencyPair() {
        RateRequest rateRequest = new RateRequest();
        rateRequest.setSchemeId(schemeId);
        rateRequest.setSegmentId(segmentId);
        rateRequest.setSchemeBuyCurrency(schemeBuyCurrency);
        rateRequest.setSchemeSellCurrency(schemeSellCurrency);
        Response response = apiCaller.call(rateUri, rateRequest);
        List<RateResponse> rateQuotes = response.readEntity(new GenericType<List<RateResponse>>() {
        });
        System.out.println("Rate Quotes received count " + rateQuotes.size());
        return rateQuotes;
    }

	//Trade against a guaranteed rate quote
    public List<TransactionResponse> submitTransaction() {
        List<RateResponse> rateResponses = getRatesByCurrencyPair();
        if (rateResponses.isEmpty()) {
            throw new RuntimeException("No rate quote received");
        }

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setRateQuoteId(rateResponses.get(0).getRateQuoteId());
        transactionRequest.setSegmentId(segmentId);
        transactionRequest.setCounterparty1(counterparty);
        transactionRequest.setDealtAmount(new BigDecimal(100));
        transactionRequest.setDealtCurrency(schemeBuyCurrency);
        transactionRequest.setSchemeTransactionId("SAMPLE" + System.currentTimeMillis());

        SchemeTransactionRequest schemeTransactionRequest = new SchemeTransactionRequest();
        schemeTransactionRequest.setSchemeId(schemeId);
        schemeTransactionRequest.setTransactionType("QUOTED");
        schemeTransactionRequest.setTransactionRequests(new TransactionRequest[]{transactionRequest});

        Response response = apiCaller.call(txnUri, schemeTransactionRequest);
        return response.readEntity(new GenericType<List<TransactionResponse>>() {
        });
    }

	//Cancel a trade
    public List<TransactionResponse> submitCancelTransaction() {
        List<TransactionResponse> txnResponses = submitTransaction();
        TransactionResponse originalTransaction = txnResponses.get(0);

        if (originalTransaction.getStatusCode() != 101 && originalTransaction.getStatusCode() != 111 && originalTransaction.getStatusCode() != 121) {
            System.out.println("Original transaction: "+originalTransaction);
            throw new RuntimeException("Original Txn not processed successfully");
        }

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(new BigDecimal(100));
        transactionRequest.setCurrency(schemeBuyCurrency);
        transactionRequest.setSchemeTransactionId("SAMPLE-CANCEL" + System.currentTimeMillis());
        transactionRequest.setOriginalSchemeTransactionId(originalTransaction.getSchemeTransactionId());

        SchemeTransactionRequest schemeTransactionRequest = new SchemeTransactionRequest();
        schemeTransactionRequest.setSchemeId(schemeId);
        schemeTransactionRequest.setTransactionType("CANCEL");
        schemeTransactionRequest.setTransactionRequests(new TransactionRequest[]{transactionRequest});

        Response response = apiCaller.call(txnUri, schemeTransactionRequest);
        return response.readEntity(new GenericType<List<TransactionResponse>>() {
        });
    }

}
