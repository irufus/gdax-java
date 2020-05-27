package com.coinbase.exchange.gui.exceptions;

public class CoinbaseDesktopAppException extends Throwable {

    public CoinbaseDesktopAppException(String websocket_connection_failed, Exception e) {
        super(websocket_connection_failed, e);
    }
}
