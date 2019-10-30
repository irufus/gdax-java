package com.coinbase.exchange.api.constants;

import javax.crypto.Mac;
import java.security.NoSuchAlgorithmException;

/**
 * Created by robevansuk on 25/01/2017.
 */
public class GdaxConstants {

    public static Mac SHARED_MAC;

    static {
        try {
            SHARED_MAC = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException nsaEx) {
            nsaEx.printStackTrace();
        }
    }
}
