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

package rates

import (
	"bytes"
	"encoding/json"
	"fmt"
	"fxmicropay/common"
	"io/ioutil"
	"net/http"
)

// GetRates gets rates for all the currency pair configured for an account
func GetRates(config *common.Configuration, client *http.Client) {

	fmt.Println("Getting all rates...")

	jsonData := map[string]string{"schemeId": config.SchemeId}
	jsonValue, _ := json.Marshal(jsonData)
	url := config.ApiUrl + config.RatesUri

	request, _ := http.NewRequest("POST", url, bytes.NewBuffer(jsonValue))
	request.Header.Set("Content-Type", "application/json")

	response, err := client.Do(request)
	if err != nil {
		fmt.Printf("The HTTP request failed with error %s\n", err)
	} else {
		data, _ := ioutil.ReadAll(response.Body)
		fmt.Println(string(data))
	}
}

// GetRateByCurrencyPair get rates for a specific cuureny pair
func GetRateByCurrencyPair(config *common.Configuration, client *http.Client) {

	fmt.Println("Getting rates for USDCAD...")

	jsonData := map[string]string{"schemeId": config.SchemeId, "segmentId": config.SegmentId, "schemeBuyCurrency": "USD", "schemeSellCurrency": "CAD"}
	jsonValue, _ := json.Marshal(jsonData)
	url := config.ApiUrl + config.RatesUri

	request, _ := http.NewRequest("POST", url, bytes.NewBuffer(jsonValue))
	request.Header.Set("Content-Type", "application/json")

	response, err := client.Do(request)
	if err != nil {
		fmt.Printf("The HTTP request failed with error %s\n", err)
	} else {
		data, _ := ioutil.ReadAll(response.Body)
		fmt.Println(string(data))
	}
}

// GetRateBySegment gets rates for all the currency pairs configured for an account under a specific segment
func GetRateBySegment(config *common.Configuration, client *http.Client) {

	fmt.Println("By Segment...")

	jsonData := map[string]string{"schemeId": config.SchemeId, "segmentId": config.SegmentId}
	jsonValue, _ := json.Marshal(jsonData)
	url := config.ApiUrl + config.RatesUri

	request, _ := http.NewRequest("POST", url, bytes.NewBuffer(jsonValue))
	request.Header.Set("Content-Type", "application/json")

	response, err := client.Do(request)
	if err != nil {
		fmt.Printf("The HTTP request failed with error %s\n", err)
	} else {
		data, _ := ioutil.ReadAll(response.Body)
		fmt.Println(string(data))
	}
}
