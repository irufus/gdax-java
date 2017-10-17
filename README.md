# gdax-java

[![Join the chat at https://gitter.im/irufus/gdax-java](https://badges.gitter.im/irufus/gdax-java.svg)](https://gitter.im/irufus/gdax-java?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
Java based wrapper for the [GDAX API](https://docs.gdax.com/#introduction) that follows the development style similar to [coinbase-java](https://github.com/coinbase/coinbase-java)

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
- added pagination to all the relevant calls (some not supported since it seems pointless due to the limited offering from gdax - e.g. products)

#Features wanted:
- Web Socket support
