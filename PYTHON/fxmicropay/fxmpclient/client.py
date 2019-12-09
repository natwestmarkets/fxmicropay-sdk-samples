"""
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
"""

import requests
import json
from OpenSSL import crypto
import time
import configparser
import os
import errno


class Connection:

    def read_configs(self, path_to_file):
        config = configparser.ConfigParser()
        config.read(path_to_file)
        section = config[config.sections()[0]]
        return section

    def get_pem_cert(self, configs, cert_dir_path):
        path_to_cert, passphrase = configs['certPath'], configs['certPwd']
        p12 = crypto.load_pkcs12(open(path_to_cert, 'rb').read(), passphrase)

        if not os.path.exists(cert_dir_path):
            try:
                os.makedirs(cert_dir_path)
            except OSError as exc:  # Guard against race condition
                if exc.errno != errno.EEXIST:
                    raise

        cert_path = os.path.join(cert_dir_path, 'cert.pem')
        key_path = os.path.join(cert_dir_path, 'key.pem')

        # PEM formatted certificate
        file = open(cert_path, 'wb')
        file.write(crypto.dump_certificate(crypto.FILETYPE_PEM, p12.get_certificate()))
        file.close()

        # PEM formatted private key
        file = open(key_path, 'wb')
        file.write(crypto.dump_privatekey(crypto.FILETYPE_PEM, p12.get_privatekey()))
        file.close()

        return cert_path, key_path


class Rates:

    def __init__(self, configs):
        __base_url = configs['apiUrl']
        __request = configs['rateUri']
        self.__api_url = __base_url + __request

    def get_rates_by_scheme(self, configs, cert):
        __data = {}
        __data['schemeId'] = configs['schemeId']

        __payload = json.dumps(__data)

        __headers = {'Content-Type': 'application/json'}

        r = requests.post(self.__api_url, data=__payload, cert=cert, headers=__headers)
        print("RATE RESPONSE:")
        print(r.json())
        return r.json()

    def get_rates_by_segment(self, configs, cert):
        __data = {}
        __data['schemeId'] = configs['schemeId']
        __data['segmentId'] = configs['segmentId']

        __payload = json.dumps(__data)

        __headers = {'Content-Type': 'application/json'}

        r = requests.post(self.__api_url, data=__payload, cert=cert, headers=__headers)
        print("RATE RESPONSE:")
        print(r.json())
        return r.json()

    def get_rates_by_currency(self, configs, cert):
        __data = {}
        __data['schemeId'] = configs['schemeId']
        __data['segmentId'] = configs['segmentId']
        __data['schemeBuyCurrency'] = configs['buyCurr']
        __data['schemeSellCurrency'] = configs['sellCurr']

        __payload = json.dumps(__data)

        __headers = {'Content-Type': 'application/json'}

        r = requests.post(self.__api_url, data=__payload, cert=cert, headers=__headers)
        print("RATE RESPONSE:")
        print(r.json())
        return r.json()


class Transactions:

    def __init__(self, configs):
        __base_url = configs['apiUrl']
        __request = configs['transactionUri']
        self.__api_url = __base_url + __request

    @staticmethod
    def __get_current_time_in_millis():
        return int(round(time.time() * 1000))

    def __generate_txn_request(self, configs, cert):
        __rates = Rates(configs)
        __rateResponse = __rates.get_rates_by_currency(configs, cert)[0]

        __obj = {}
        __obj['segmentId'] = __rateResponse['segmentId']
        __obj['counterparty1'] = configs['cpty']
        __obj['dealtAmount'] = 100
        __obj['dealtCurrency'] = __rateResponse['schemeBuyCurrency']
        __obj['rateQuoteId'] = __rateResponse['rateQuoteId']
        __obj['schemeBatchId'] = 1
        __obj['schemeTransactionId'] = "Sample|Transaction|" + str(self.__get_current_time_in_millis())

        return __obj

    def submit_transaction(self, configs, cert):
        __data = {}
        __data['schemeId'] = configs['schemeId']
        __data['transactionType'] = 'QUOTED'
        __data['transactionRequests'] = []

        __request = self.__generate_txn_request(configs, cert)
        __data['transactionRequests'].append(__request)

        __payload = json.dumps(__data)

        __headers = {'Content-Type': 'application/json'}

        r = requests.post(self.__api_url, data=__payload, cert=cert, headers=__headers)
        print("TRANSACTION RESPONSE:")
        print(r.json())
        return r.json()

    def __generate_cancel_txn_request(self, configs, cert):
        __txn_response = self.submit_transaction(configs, cert)[0]

        __obj = {}
        __obj['amount'] = __txn_response['schemeBuyAmount'] / 10
        __obj['currency'] = __txn_response['schemeBuyCurrency']
        __obj['schemeTransactionId'] = "Sample|Cancel|" + str(self.__get_current_time_in_millis())
        __obj['originalSchemeTransactionId'] = __txn_response['schemeTransactionId']

        return __obj

    def cancel_transaction(self, configs, cert):
        __data = {}
        __data['schemeId'] = configs['schemeId']
        __data['transactionType'] = 'CANCEL'
        __data['transactionRequests'] = []

        __request = self.__generate_cancel_txn_request(configs, cert)
        __data['transactionRequests'].append(__request)

        __payload = json.dumps(__data)

        __headers = {'Content-Type': 'application/json'}

        r = requests.post(self.__api_url, data=__payload, cert=cert, headers=__headers)
        print("CANCEL TRANSACTION RESPONSE:")
        print(r.json())
        return r.json()
