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

using System;
using System.Collections.Generic;
using System.IO;
using System.Net.Http;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using Microsoft.Extensions.Configuration;
using Newtonsoft.Json;

namespace myApp
{
    class Transactions
    {
        
        static public void submitTransaction(IConfigurationRoot config, HttpClient client)
        {
            string json = "{\"schemeId\":" + config["schemeId"] + "," + "\"segmentId\":" + config["segmentId"] +  "," 
            + "\"schemeBuyCurrency\":\"USD\""
             + "," + "\"schemeSellCurrency\":\"CAD\"" + "}";
            Console.WriteLine(json);
            var content = new StringContent(json, Encoding.UTF8 , "application/json");
            var url = config["apiUrl"] + config["ratesUri"];
            var responseTask = client.PostAsync(url, content);
            responseTask.Wait();

            string jsonRateQuote = "";
            var result = responseTask.Result;
            if (result.IsSuccessStatusCode){    
                var val = result.Content.ReadAsStringAsync();
                val.Wait();
                jsonRateQuote = val.Result;

                
            }
            var rateQuote = JsonConvert.DeserializeObject<List<Dictionary<string, string>>>(jsonRateQuote);

            Console.WriteLine(rateQuote[0]["segmentId"]);

            var transactionRequest = new Dictionary<string,string>();
            transactionRequest.Add("segmentId",rateQuote[0]["segmentId"]);
            transactionRequest.Add("rateQuoteId",rateQuote[0]["rateQuoteId"]);
            transactionRequest.Add("counterparty1",config["counterparty"]);
            transactionRequest.Add("dealtAmount","100");
            transactionRequest.Add("dealtCurrency","CAD");
            transactionRequest.Add("schemeBatchId","myBatchId");
            transactionRequest.Add("schemeTransactionId","myIdentifier" + DateTime.Now.ToString("HHmmss"));
            transactionRequest.Add("schemeDateTime",DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));

            var transaction = new Dictionary<object,object>();
            transaction.Add("schemeId", config["schemeId"]);
            transaction.Add("transactionType", "QUOTED");
            transaction.Add("transactionRequests", new List<Dictionary<string,string>>(){transactionRequest});

            Console.WriteLine(JsonConvert.SerializeObject(transaction));

            content = new StringContent(JsonConvert.SerializeObject(transaction), Encoding.UTF8 , "application/json");
            url = config["apiUrl"] + config["transactionUri"];
            responseTask = client.PostAsync(url, content);
            responseTask.Wait();

            result = responseTask.Result;
            if (result.IsSuccessStatusCode){    
                var val = result.Content.ReadAsStringAsync();
                val.Wait();
                Console.WriteLine(val.Result);
            }
        }

         static public void cancelTransaction(IConfigurationRoot config, HttpClient client)
        {
            string json = "{\"schemeId\":" + config["schemeId"] + "," + "\"segmentId\":" + config["segmentId"] +  "," 
            + "\"schemeBuyCurrency\":\"USD\""
             + "," + "\"schemeSellCurrency\":\"CAD\"" + "}";
            Console.WriteLine(json);
            var content = new StringContent(json, Encoding.UTF8 , "application/json");
            var url = config["apiUrl"] + config["ratesUri"];
            var responseTask = client.PostAsync(url, content);
            responseTask.Wait();

            string jsonRateQuote = "";
            var result = responseTask.Result;
            if (result.IsSuccessStatusCode){    
                var val = result.Content.ReadAsStringAsync();
                val.Wait();
                jsonRateQuote = val.Result;

                
            }
            var rateQuote = JsonConvert.DeserializeObject<List<Dictionary<string, string>>>(jsonRateQuote);

            Console.WriteLine(rateQuote[0]["segmentId"]);

            var transactionRequest = new Dictionary<string,string>();
            transactionRequest.Add("segmentId",rateQuote[0]["segmentId"]);
            transactionRequest.Add("rateQuoteId",rateQuote[0]["rateQuoteId"]);
            transactionRequest.Add("counterparty1",config["counterparty"]);
            transactionRequest.Add("dealtAmount","100");
            transactionRequest.Add("dealtCurrency","CAD");
            transactionRequest.Add("schemeBatchId","myBatchId");
            transactionRequest.Add("schemeTransactionId","myIdentifier" + DateTime.Now.ToString("HHmmss"));
            transactionRequest.Add("schemeDateTime",DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));

            var transaction = new Dictionary<object,object>();
            transaction.Add("schemeId", config["schemeId"]);
            transaction.Add("transactionType", "QUOTED");
            transaction.Add("transactionRequests", new List<Dictionary<string,string>>(){transactionRequest});

            Console.WriteLine(JsonConvert.SerializeObject(transaction));

            content = new StringContent(JsonConvert.SerializeObject(transaction), Encoding.UTF8 , "application/json");
            url = config["apiUrl"] + config["transactionUri"];
            responseTask = client.PostAsync(url, content);
            responseTask.Wait();

            result = responseTask.Result;
            string jsonTransactionResponse = "";
            if (result.IsSuccessStatusCode){    
                var val = result.Content.ReadAsStringAsync();
                val.Wait();
                jsonTransactionResponse = val.Result;
            }

            var transactionResponse = JsonConvert.DeserializeObject<List<Dictionary<string, string>>>(jsonTransactionResponse);

            transactionRequest = new Dictionary<string,string>();
            transactionRequest.Add("amount","100");
            transactionRequest.Add("currency","CAD");
            transactionRequest.Add("schemeTransactionId","myCancelIdentifier" + DateTime.Now.ToString("HHmmss"));
            transactionRequest.Add("schemeDateTime",DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
            transactionRequest.Add("originalSchemeTransactionId", transactionResponse[0]["schemeTransactionId"]);
            transactionRequest.Add("originalTransactionId",transactionResponse[0]["transactionId"]);            

            transaction = new Dictionary<object,object>();
            transaction.Add("schemeId", config["schemeId"]);
            transaction.Add("transactionType", "CANCEL");
            transaction.Add("transactionRequests", new List<Dictionary<string,string>>(){transactionRequest});

            content = new StringContent(JsonConvert.SerializeObject(transaction), Encoding.UTF8 , "application/json");
            responseTask = client.PostAsync(url, content);
            responseTask.Wait();

            result = responseTask.Result;
            if (result.IsSuccessStatusCode){    
                var val = result.Content.ReadAsStringAsync();
                val.Wait();
                Console.WriteLine(val.Result);
            }
        }
    }
}
