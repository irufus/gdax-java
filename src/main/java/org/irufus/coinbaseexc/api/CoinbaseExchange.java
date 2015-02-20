/**
 * Created by irufus on 2/19/15.
 */
package org.irufus.coinbaseexc.api;

import org.irufus.coinbaseexc.api.entity.Account;

import java.util.List;

public class CoinbaseExchange {
    
    private Authentication auth;
    
    public CoinbaseExchange(String public_key, String secret_key, String passphrase){
        auth = new Authentication(public_key, secret_key, passphrase);
    }
    
    public List<Account> getAccounts(){
        
    }
}
