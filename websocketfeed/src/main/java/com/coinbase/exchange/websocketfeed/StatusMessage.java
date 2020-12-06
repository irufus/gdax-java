package com.coinbase.exchange.websocketfeed;

import com.coinbase.exchange.model.Currency;
import com.coinbase.exchange.model.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The status channel will send all products and currencies on a preset interval.
 * Example:
 * <pre>
 * {
 *     "type": "status",
 *     "products": [
 *         {
 *             "id": "BTC-USD",
 *             "base_currency": "BTC",
 *             "quote_currency": "USD",
 *             "base_min_size": "0.001",
 *             "base_max_size": "70",
 *             "base_increment": "0.00000001",
 *             "quote_increment": "0.01",
 *             "display_name": "BTC/USD",
 *             "status": "online",
 *             "status_message": null,
 *             "min_market_funds": "10",
 *             "max_market_funds": "1000000",
 *             "post_only": false,
 *             "limit_only": false,
 *             "cancel_only": false
 *         }
 *     ],
 *     "currencies": [
 *         {
 *             "id": "USD",
 *             "name": "United States Dollar",
 *             "min_size": "0.01000000",
 *             "status": "online",
 *             "status_message": null,
 *             "max_precision": "0.01",
 *             "convertible_to": ["USDC"],
 *             "details": {}
 *         },
 *         {
 *             "id": "USDC",
 *             "name": "USD Coin",
 *             "min_size": "0.00000100",
 *             "status": "online",
 *             "status_message": null,
 *             "max_precision": "0.000001",
 *             "convertible_to": ["USD"],
 *             "details": {}
 *         },
 *         {
 *             "id": "BTC",
 *             "name": "Bitcoin",
 *             "min_size":" 0.00000001",
 *             "status": "online",
 *             "status_message": null,
 *             "max_precision": "0.00000001",
 *             "convertible_to": []
 *         }
 *     ]
 * }
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StatusMessage extends FeedMessage {

    private Product[] products;
    private Currency[] currencies;

    public StatusMessage() {
        setType("status");
    }

    public StatusMessage(Product[] products, Currency[] currencies) {
        this();
        this.products = products;
        this.currencies = currencies;
    }
}