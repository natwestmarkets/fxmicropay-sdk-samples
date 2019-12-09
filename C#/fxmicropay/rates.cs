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
using System.Net.Http;
using System.Text;
using Microsoft.Extensions.Configuration;

namespace fxmicropay
{
    class Rates
    {
        static public void getRates(IConfigurationRoot config, HttpClient client)
        {
            string json = "{\"schemeId\":" + config["schemeId"] +"}";
            Console.WriteLine(json);
            var content = new StringContent(json, Encoding.UTF8 , "application/json");
            var url = config["apiUrl"] + config["ratesUri"];
            var responseTask = client.PostAsync(url, content);
            responseTask.Wait();

            var result = responseTask.Result;
            if (result.IsSuccessStatusCode){    
                var val = result.Content.ReadAsStringAsync();
                val.Wait();
                Console.WriteLine(val.Result);
            }
        }

        static public void getRatesBySegment(IConfigurationRoot config, HttpClient client)
        {
            string json = "{\"schemeId\":" + config["schemeId"] + "," + "\"segmentId\":" +  config["segmentId"] + "}";
            Console.WriteLine(json);
            var content = new StringContent(json, Encoding.UTF8 , "application/json");
            var url = config["apiUrl"] + config["ratesUri"];
            var responseTask = client.PostAsync(url, content);
            responseTask.Wait();

            var result = responseTask.Result;
            if (result.IsSuccessStatusCode){    
                var val = result.Content.ReadAsStringAsync();
                val.Wait();
                Console.WriteLine(val.Result);
            }
        }

        static public void getRatesByCurrencyPair(IConfigurationRoot config, HttpClient client)
        {
            string json = "{\"schemeId\":" + config["schemeId"] + "," + "\"segmentId\":" + config["segmentId"] +  "," 
            + "\"schemeBuyCurrency\":\"USD\""
             + "," + "\"schemeSellCurrency\":\"CAD\"" + "}";
            Console.WriteLine(json);
            var content = new StringContent(json, Encoding.UTF8 , "application/json");
            var url = config["apiUrl"] + config["ratesUri"];
            var responseTask = client.PostAsync(url, content);
            responseTask.Wait();

            var result = responseTask.Result;
            if (result.IsSuccessStatusCode){    
                var val = result.Content.ReadAsStringAsync();
                val.Wait();
                Console.WriteLine(val.Result);
            }
        }
    }
}
