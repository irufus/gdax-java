# gdax-java
Java based wrapper for the [GDAX API](https://docs.gdax.com/#introduction) that follows the development style similar to [coinbase-java](https://github.com/coinbase/coinbase-java)

# Functions implemented:
- [x] Authentication (GET, POST, DELETE supported)
- [x] Get Account
- [x] Get Accounts
- [x] Get Account History
- [x] Get Holds
- [x] Place a new Order (limit order)
- [x] Get an Order
- [x] Cancel an Order
- [x] List all open Orders
- [x] Get Market Data
- [x] List fills
- [x] List Products
- [x] HTTP Error code support
- [x] List of Currencies - from Accounts
- [x] Withdrawals - from coinbase accounts / payment methods / crypto account address
- [x] Deposits - from coinbase accounts / payment methods
- [x] Transfers - from coinbase accounts
- [x] Payment methods - coinbase / payment methods
- [x] Reports
- [x] Pagination support for all calls that support it.
- [x] Pagination support for all calls that support it.
- [x] Sandbox support - *sandbox support was dropped by gdax so this is now redundant*
    
# Features wanted:
- [x] Transfer Funds
- [x] Web Socket support
- [x] Market Data support
- [x] Product Order book support

# Notes:
>GDAX primary data sources and servers run in the Amazon US East data center. To minimize latency for API access, we recommend making requests from servers located near the US East data center.

>Some of the methods do not yet have tests and so may not work as expected until a later date. Please raise an issue in github if you want something in particular as a priority. I'll be looking to fully flesh this out if possible over the coming months.

# Usage
--------

To build and run the application you can use the gradle script - this requires no installation as the "gradle wrapper" is included as part of the source code. All you need to do is:

1. supply your api keys in `gdax-java/src/main/resources/application.yml`
1. open a command line terminal
1. navigate to the root directory of this project (where `build.gradle` sits) 
1. execute `./gradlew bootRun` (Mac/unix). For equivalent Windows commands just remove the `./` from the commands, there's a gradlew.bat included as well.

This won't actually do much on its own but I did implement a slightly experimental (buggy) GUI which you can switch on and try by changing the setting gui.enabled in the application.yml above to `true`. You should then see a streamed list of updates displayed in a rather rubbish GUI I was attempting to build out. This is a work in progress so please don't assume its a finished product - its just for demoing that this works.

1. tests can also be run with  `./gradlew test` - simple

For a lib:

1. If you'd rather work purely in java then you can build an executable jar file `./gradlew jar` and you should be able to find the jar in the build directory.

# Examples
--------
To make use of this library you only need a reference to the service that you want.

At present the classes match the interface specified in the coinbase/gdax api here: https://docs.gdax.com/#api

e.g. 
`public OrderService orderService(){
    new OrderService();
}`

This works better if you declare the above method as a google guice/spring bean and then wire it in using dependency injection.

Then in your method you can carry out any of the public api operations such as `orderService().createOrder(NewSingleOrder order);` - this creates a limit order. Currently this is only the basic order.

# Updates
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
- GDAX is updating its API without updating documentation - I've fixed an issue with market data because of this.

# TODO
-------
- add pagination versions of all endpoints, or offer a way to append to the endpoint urls.
