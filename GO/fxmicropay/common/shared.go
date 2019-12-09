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

package common

import (
	"crypto/tls"
	"encoding/json"
	"encoding/pem"
	"fmt"
	"io/ioutil"
	"net/http"

	"golang.org/x/crypto/pkcs12"
)

// RateQuote struct
type RateQuote struct {
	RateQuoteId        int     `json:"rateQuoteId"`
	SegmentId          int     `json:"segmentId"`
	SchemeBuyCurrency  string  `json:"schemeBuyCurrency"`
	SchemeSellCurrency string  `json:"schemeSellCurrency"`
	TenorDays          int     `json:"tenorDays"`
	UnitCurrency       string  `json:"unitCurrency"`
	Rate               float64 `json:"rate"`
	IsExecutable       bool    `json:"isexecutable"`
	ValueDate          string  `json:"valueDate"`
	ExpiryDateTime     string  `json:"expiryDateTime"`
}

// Configuration struct
type Configuration struct {
	SchemeId       string `json:"schemeId"`
	SegmentId      string `json:"segmentId"`
	ApiUrl         string `json:"apiUrl"`
	RatesUri       string `json:"ratesUri"`
	TransactionUri string `json:"transactionUri"`
	CertLocation   string `json:"certLocation"`
	CertPassword   string `json:"certPassword"`
	Counterparty   string `json:"counterparty"`
}

// ReadConfig reads the configuration file
func ReadConfig() *Configuration {

	data, err := ioutil.ReadFile("config.json")
	if err != nil {
		fmt.Print(err)
	}

	var result Configuration
	err = json.Unmarshal(data, &result)
	if err != nil {
		fmt.Println("error:", err)
	}

	return &result
}

// GetHttpClient returns http.client ready for Mutual TLS connection
func GetHttpClient(config *Configuration) *http.Client {

	// Load P12 cert
	caCertPK, err := ioutil.ReadFile(config.CertLocation)
	if err != nil {
		fmt.Println(err)
	}

	caCert, err := pkcs12.ToPEM(caCertPK, config.CertPassword)
	if err != nil {
		fmt.Println(err)
	}

	var pemData []byte
	for _, b := range caCert {
		pemData = append(pemData, pem.EncodeToMemory(b)...)
	}

	cert, err := tls.X509KeyPair(pemData, pemData)
	if err != nil {
		fmt.Println(err)
	}

	tlsconfig := &tls.Config{
		Certificates: []tls.Certificate{cert},
	}

	client := &http.Client{
		Transport: &http.Transport{
			TLSClientConfig: tlsconfig,
		},
	}
	return client
}
