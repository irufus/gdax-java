# gdax-java (coinbase-exchange-java)
Java based wrapper for the [GDAX API](https://docs.gdax.com/#introduction) that follows the development style similar to [coinbase-java](https://github.com/coinbase/coinbase-java)

#Functions needed:
    - [x]Authentication (GET, POST, DELETE supported)
    - [x]Get Account
    - [x]Get Accounts
    - [x]Get Account History
    - [x]Get Holds
    - [x]Place a new Order (limit order)
    - [x]Get an Order
    - [x]Cancel an Order
    - [x]List all open Orders
    - [x]Get Market Data
    - [x]List fills
    - [x]List Products
    - [x]HTTP Error code support
    - [x]List of Currencies - comes from Accounts
    - [ ]Pagination support for all calls that support it
    - [ ]Ethereal support
    - [x]Sandbox support
    
#Features wanted:
    - [ ]Transfer Funds
    - [ ]Web Socket support
    - [x]Market Data support
    - [x]Product Order book support

#Notes:
>GDAX primary data sources and servers run in the Amazon US East data center. To minimize latency for API access, we recommend making requests from servers located near the US East data center.

>Some of the methods do not yet have tests and so may not work as expected until a later date. Please raise an issue in github if you want something in particular as a priority. I'll be looking to fully flesh this out if possible over the coming months.

#Examples
--------
To make use of this library you only need a reference to the service that you want.

At present the classes match the interface specified in the coinbase/gdax api here: https://docs.gdax.com/#api

e.g. 
`public OrderService orderService(){
    new OrderService();
}`

This works better if you declare the above method as a google guice/spring bean and then wire it in using dependency injection.

Then in your method you can carry out any of the public api operations such as `orderService().createOrder(NewSingleOrder order);` - this creates a limit order. Currently this is only the basic order.

#Updates
--------
- converted to using Gradle
- converted to using SpringBoot for DI and request building
- updated all libraries used - removed some unnecessary libraries
- refactored the code to remove error handling from every method (rightly/wrongly) - its easier to maintain and extend now as a result
- more modular code that matches the service api - favour composition over inheritance
- removed a lot of boilerplate code
- logging added - Logging will output an equivalent curl command now for each get/post/delete request so that when debugging you can copy the curl request and execute it on the command line.
- service tests added for sanity - no unit tests against the data objects
- better configuration options using `application.yml` for your live environment and `application-test.yml` for your sandbox environment.
- banner displayed (specific to each environment) :)
- generally more structure.