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

import fxmicropay.response.RateResponse;
import fxmicropay.response.TransactionResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class Program {

    public static void main(String[] args) throws IOException {

        Properties config = new Properties();
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("config.properties");
        config.load(inputStream);

        ApiClient apiClient = new ApiClient(config);

        // Rates API
        List<RateResponse> rateResponse = apiClient.getRates();
        System.out.println(rateResponse);

        rateResponse = apiClient.getRatesBySegment();
        System.out.println(rateResponse);

        rateResponse = apiClient.getRatesByCurrencyPair();
        System.out.println(rateResponse);

        // Transaction API
        List<TransactionResponse> transactionResponse = apiClient.submitTransaction();
        System.out.println(transactionResponse);

        transactionResponse = apiClient.submitCancelTransaction();
        System.out.println(transactionResponse);

    }
}
