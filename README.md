# Coinbase Pro

[![Join the chat at https://gitter.im/irufus/gdax-java](https://badges.gitter.im/irufus/gdax-java.svg)](https://gitter.im/irufus/gdax-java?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Java based wrapper for the [Coinbase Pro API](https://docs.pro.coinbase.com/)

## Notes:

> Coinbase Pro primary data sources and servers run in the Amazon US East data center. To minimize latency for API access, we recommend making requests from servers located near the US East data center.
> Some of the methods do not yet have tests and so may not work as expected until someone tries them and fixes them at a later date. Please raise an issue in github if you want something in particular as a priority.
> This codebase is maintained independently of Coinbase. We are not in any way affiliated with coinbase or coinbase pro.

## Functions supported:
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
    
### In Development

Check the issues on the repo for open items to work on.
Please join the gitter channel if you have any questions. Support always welcome. 
Note the channel uses the legacy name of 'gdax-java' rather than 'coinbase-pro-java'

### Contributing

Please see CONTRIBUTE.md if your interested in getting involved.

## Usage
--------

**If you commit your secure keys, passphrase or secrete, disable/delete them from Coinbase Pro immediately**.

1. tests can be run with `./gradlew test`, and `./gradlew integrationTest`.
    1. unit tests have file/class names ending `Test` and run locally
    1. integration tests have file/class names ending `IntegrationTest` and should run against the sandbox api 

## Examples

To make use of this library you only need a reference to the Service that you want. 
 - For Accounts, get an instance of the `AccountService` See CONTRIBUTING.md for an example of how to do this 
 - For MarketData, use the `MarketDataService`, and so on.

## Examples
--------

At present the Services and Data objects returned should match the interface specified in the Coinbase Pro api here: [https://docs.pro.coinbase.com/#api](https://docs.pro.coinbase.com/#api)

Each `Service` class requires the `CoinbaseExchange` object (see CONTRIBUTING.md for examples of how to create this) so that methods calling the REST endpoints can be made, using a `RestTemplate` that has
the correct headers and signatures is used.

## API
--------

This library is as set up as follows:
(Note: this section is likely to change over time)

- `AccountService.getAccounts()` - returns a List Accounts
- `AccountService.getAccountHistory(String accountId)` - returns the history for a given account as a List
- `AccountService.getHolds(String accountId)` - returns a List of all held funds for a given account.
- `DepositService.depositViaPaymentMethod(BigDecimal amount, String currency, String paymentMethodId)` - makes a deposit from a stored payment method into your GDAX account
- `DepositService.coinbaseDeposit(BigDecimal amount, String currency, String coinbaseAccountId)` - makes a deposit from a coinbase account into your GDAX account
- `MarketDataService.getMarketDataOrderBook(String productId, String level)` - a call to ProductService.getProducts() will return the order book for a given product. You can then use the WebsocketFeed api to keep your orderbook up to date. This is implemented in this codebase. Level can be 1 (top bid/ask only), 2 (top 50 bids/asks only), 3 (entire order book - takes a while to pull the data.)
- `OrderService.getOpenOrders(String accountId)` - returns a List of Orders for any outstanding orders
- `OrderService.cancelOrder(String orderId)` - cancels a given order
- `OrderService.createOrder(NewOrderSingle aSingleOrder)` - construct an order and send it to this method to place an order for a given product on the exchange.
- `PaymentService.getCoinbaseAccounts()` - gets the coinbase accounts for the logged in user
- `PaymentService.getPaymentTypes()` - gets the payment types available for the logged in user
- `ProductService.getProducts()` - returns a List of Products available from the exchange - BTC-USD, BTC-EUR, BTC-GBP, etc.
- `ReportService.createReport(String product, String startDate, String endDate)` - not certain about this one as its untested but presumably it generates a report of a given product's trade history for the dates supplied - dates are assumed to be ISO 8601 compliant
- `TransferService.transfer(String type, BigDecimal amount, String coinbaseAccountId)` - initiates a transfer to your (standard) Coinbase account. 
- `UserAccountService.getTrailingVolume()` - Returns the 30 day trailing volume information from all accounts
- `WithdrawalsService` - methods that enable Withdrawals from a Coinbase-Pro account to a Coinbase Account/Payment method


## WebsocketFeed API
---------------------

The WebsocketFeed is implemented and works. However, there are techniques to using it successfully for production use - e.g. monitoring for 'heartbeats'. 

To use the WSF check out the API documentation and look at usages of `websocketFeed.subscribe(String)` as an example that already works.

## Updates - v 0.11.0
-------------------
- decoupling the `api` code from the spring boot desktop client application so the api can be used and eventually published as a library.
- decoupling `model` code so that it can become shared/common for multiple projects and make building out a FIX client potentially easier
- decoupling the `websocketfeed` code from the api implementation
- new `security` module so websocketfeed and api can share the `Signature` code
- removal of the `gui` desktop app - this needs rebuilding properly (with tests)
- new modularised multi-project gradle build
- centralised dependency versioning for libraries, in the root `build.gradle` as its easier to manage them in a single location
- segregating the unit tests from the integration tests
- updated api/sandbox-api endpoints for use with tests
- renaming project to `Coinbase-Pro-java` in the `settings.gradle` file
- remove joda time lib in favour of the standard library Instant implementation `JavaTimeModule` for the `ObjectMapper`
- updated libraries to newer versions: spring boot, jackson, gson, etc.
- removal of Gson in favour of Jackson libs
- updated classes to use constructor injection rather than field based 


## Updates - v 0.9.1
-------------------
- building an order book that works ready for a desktop client.

## Updates
---------
- converted to using Gradle as a build tool
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
- added pagination to all the relevant calls (some not supported since it seems pointless due to the limited offering from gdax - e.g. products)
- GDAX is updating its API without updating documentation - I've fixed an issue with market data because of this.
- WebsocketFeed added
- OrderBook GUI component added - enable in the `application.yml` by setting enabled to `true`
- LiveOrderBook (full channel) Implemented and viewable via the GUI when enabled

## TODO
-------
- add pagination versions of all endpoints, or offer a way to append to the endpoint urls.

From the GDAX API documentation the Websocket implementation follows the following implementation:
  Send a subscribe message for the product(s) of interest and the full channel.
  Queue any messages received over the websocket stream.
  Make a REST request for the order book snapshot from the REST feed.
  Playback queued messages, discarding sequence numbers before or equal to the snapshot sequence number.
  Apply playback messages to the snapshot as needed (see below).
  After playback is complete, apply real-time stream messages in sequential order, queuing any that arrive out of order for later processing.
  Discard messages once they've been processed.

