# gdax-java

[![Join the chat at https://gitter.im/irufus/gdax-java](https://badges.gitter.im/irufus/gdax-java.svg)](https://gitter.im/irufus/gdax-java?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Java based wrapper for the [GDAX API](https://docs.gdax.com/#introduction) that follows the development style similar to [coinbase-java](https://github.com/coinbase/coinbase-java)

# In Development

Documenting usages, documenting examples, writing guidelines on how to contribute, and writing unit tests.

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

#Notes:

> GDAX primary data sources and servers run in the Amazon US East data center. To minimize latency for API access, we recommend making requests from servers located near the US East data center.

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

# API
--------

The Api for this application/library is as follows:
(Note: this section is likely to change but is provided on the basis it will work well for example usage)

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
- `ReportService.createReport(String product, String startDate, String endDate)` - not certain about this one as I've not tried it but presumably generates a report of a given product's trade history for the dates supplied


# WebsocketFeed API
---------------------

At present the WebsocketFeed is implemented and does work (as in it will receive the messages from the exchange and route them according to the message type) but it requires you to pass a `new Subscribe(productIds)` message to it and to implement an OrderBook class as I've partly done under the `gui` package (as I was attempting to build a desktop gui since the web one has consistently had problems).

Presently this is not an interface as this is a work in progress, however feel free to fork this repo, make the change and send me a pull request back and I'll gladly merge in the change.
