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

package transactions

import (
	"bytes"
	"encoding/json"
	"fmt"
	"fxmicropay/common"
	"io/ioutil"
	"net/http"
	"strconv"
	"time"
)

// SubmitTransaction - pulls a ratw quote and submits transaction based on the quote.
func SubmitTransaction(config *common.Configuration, client *http.Client) {

	// preparing JSON data  for rates request
	jsonData := map[string]string{"schemeId": config.SchemeId, "segmentId": config.SegmentId, "schemeBuyCurrency": "USD", "schemeSellCurrency": "CAD"}
	jsonValue, _ := json.Marshal(jsonData)
	url := config.ApiUrl + config.RatesUri

	request, _ := http.NewRequest("POST", url, bytes.NewBuffer(jsonValue))
	request.Header.Set("Content-Type", "application/json")

	response, err := client.Do(request)
	if err != nil {
		fmt.Println("The HTTP request failed with error  \n", err)
	}

	data, err := ioutil.ReadAll(response.Body)
	if err != nil {
		fmt.Println("response body read error ", err)
	}

	var quote []common.RateQuote
	err = json.Unmarshal(data, &quote)
	if err != nil {
		fmt.Println("json unmarshall error", err)
	}

	//prepare transaction data
	transactionRequest := make(map[string]string)
	transactionRequest["segmentId"] = strconv.Itoa(quote[0].SegmentId)
	transactionRequest["rateQuoteId"] = strconv.Itoa(quote[0].RateQuoteId)
	transactionRequest["counterparty1"] = config.Counterparty
	transactionRequest["dealtAmount"] = "100"
	transactionRequest["dealtCurrency"] = "CAD"
	transactionRequest["schemeBatchId"] = "myBatchId"
	transactionRequest["schemeTransactionId"] = "myIdentifier" + time.Now().Format("150405")
	transactionRequest["schemeDateTime"] = time.Now().Format("2006-01-02 15:04:05")

	transaction := make(map[string]interface{})
	transaction["schemeId"] = config.SchemeId
	transaction["transactionType"] = "QUOTED"
	transactionRequests := make([]map[string]string, 1)
	transactionRequests[0] = transactionRequest
	transaction["transactionRequests"] = transactionRequests

	fmt.Println(transaction)

	jsonValue, _ = json.Marshal(transaction)
	url = config.ApiUrl + config.TransactionUri

	request, _ = http.NewRequest("POST", url, bytes.NewBuffer(jsonValue))
	request.Header.Set("Content-Type", "application/json")

	response, err = client.Do(request)
	if err != nil {
		fmt.Println("The HTTP request failed with error  \n", err)
	}

	data, err = ioutil.ReadAll(response.Body)
	if err != nil {
		fmt.Println("response body read error ", err)
	}

	fmt.Println(string(data))
}

// CancelTransaction - pulls a ratw quote and submits transaction based on the quote.
// Then again calls the transaction api to cancel the original transaction
func CancelTransaction(config *common.Configuration, client *http.Client) {

	// start preparing JSON data  for rates request
	jsonData := map[string]string{"schemeId": config.SchemeId, "segmentId": config.SegmentId, "schemeBuyCurrency": "USD", "schemeSellCurrency": "CAD"}
	jsonValue, _ := json.Marshal(jsonData)
	url := config.ApiUrl + config.RatesUri

	request, _ := http.NewRequest("POST", url, bytes.NewBuffer(jsonValue))
	request.Header.Set("Content-Type", "application/json")

	response, err := client.Do(request)
	if err != nil {
		fmt.Println("The HTTP request failed with error  \n", err)
	}

	data, err := ioutil.ReadAll(response.Body)
	if err != nil {
		fmt.Println("response body read error ", err)
	}

	var quote []common.RateQuote
	err = json.Unmarshal(data, &quote)
	if err != nil {
		fmt.Println("json unmarshall error", err)
	}

	//prepare transaction data
	transactionRequest := make(map[string]string)
	transactionRequest["segmentId"] = strconv.Itoa(quote[0].SegmentId)
	transactionRequest["rateQuoteId"] = strconv.Itoa(quote[0].RateQuoteId)
	transactionRequest["counterparty1"] = config.Counterparty
	transactionRequest["dealtAmount"] = "100"
	transactionRequest["dealtCurrency"] = "CAD"
	transactionRequest["schemeBatchId"] = "myBatchId"
	transactionRequest["schemeTransactionId"] = "myIdentifier" + time.Now().Format("150405")
	transactionRequest["schemeDateTime"] = time.Now().Format("2006-01-02 15:04:05")

	transaction := make(map[string]interface{})
	transaction["schemeId"] = config.SchemeId
	transaction["transactionType"] = "QUOTED"
	transactionRequests := make([]map[string]string, 1)
	transactionRequests[0] = transactionRequest
	transaction["transactionRequests"] = transactionRequests

	fmt.Println(transaction)

	jsonValue, _ = json.Marshal(transaction)
	url = config.ApiUrl + config.TransactionUri

	request, _ = http.NewRequest("POST", url, bytes.NewBuffer(jsonValue))
	request.Header.Set("Content-Type", "application/json")

	response, err = client.Do(request)
	if err != nil {
		fmt.Println("The HTTP request failed with error  \n", err)
	}

	data, err = ioutil.ReadAll(response.Body)
	if err != nil {
		fmt.Println("response body read error ", err)
	}

	var transactionResposne []map[string]interface{}
	err = json.Unmarshal(data, &transactionResposne)
	if err != nil {
		fmt.Println("json unmarshall error", err)
	}

	//Prepare data for Cancel transaction
	transactionRequest = make(map[string]string)
	transactionRequest["amount"] = "100"
	transactionRequest["currency"] = "CAD"
	transactionRequest["schemeTransactionId"] = "myCancelIdentifier" + time.Now().Format("150405")
	transactionRequest["schemeDateTime"] = time.Now().Format("2006-01-02 15:04:05")
	transactionRequest["originalSchemeTransactionId"] = transactionResposne[0]["schemeTransactionId"].(string)
	transactionRequest["originalTransactionId"] = strconv.Itoa(int((transactionResposne[0]["transactionId"].(float64))))

	transaction = make(map[string]interface{})
	transaction["schemeId"] = config.SchemeId
	transaction["transactionType"] = "CANCEL"
	transactionRequests = make([]map[string]string, 1)
	transactionRequests[0] = transactionRequest
	transaction["transactionRequests"] = transactionRequests

	fmt.Println(transaction)

	jsonValue, _ = json.Marshal(transaction)

	request, _ = http.NewRequest("POST", url, bytes.NewBuffer(jsonValue))
	request.Header.Set("Content-Type", "application/json")

	response, err = client.Do(request)
	if err != nil {
		fmt.Println("The HTTP request failed with error  \n", err)
	}

	data, err = ioutil.ReadAll(response.Body)
	if err != nil {
		fmt.Println("response body read error ", err)
	}

	fmt.Print(string(data))
}
