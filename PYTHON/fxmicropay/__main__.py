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

import os
from fxmpclient import client

# Initial Setup
config_path = ".\\config\\configs.properties"
cert_dir_name = 'var'
cert_dir_path = os.path.join(os.getcwd(), cert_dir_name)

conn = client.Connection()
props = conn.read_configs(config_path)
cert_details = conn.get_pem_cert(props, cert_dir_path)

# Rates API
rates = client.Rates(props)

# fetch by scheme
rates.get_rates_by_scheme(props, cert_details)    

# fetch by scheme and segment
rates.get_rates_by_segment(props, cert_details)

# fetch by scheme, segment and currency pair
rates.get_rates_by_currency(props, cert_details)  

# Transaction API
txn = client.Transactions(props)

# Function call to submit transaction
txn.submit_transaction(props, cert_details)

# Function call to cancel a submitted transaction
txn.cancel_transaction(props, cert_details)
