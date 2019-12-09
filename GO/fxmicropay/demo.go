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

package main

import (
	"fmt"
	"fxmicropay/common"
	"fxmicropay/rates"
	"fxmicropay/transactions"
)

func main() {
	fmt.Println("Starting FXmicropay APIs demo...")

	config := common.ReadConfig()
	fmt.Println(config)
	client := common.GetHttpClient(config)

	// Rates API
	rates.GetRates(config, client)
	rates.GetRateBySegment(config, client)
	rates.GetRateByCurrencyPair(config, client)

	// Transaction API
	transactions.SubmitTransaction(config, client)
	transactions.CancelTransaction(config, client)

	fmt.Println("Done...")
}
