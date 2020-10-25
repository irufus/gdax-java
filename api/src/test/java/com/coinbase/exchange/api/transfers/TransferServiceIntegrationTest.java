package com.coinbase.exchange.api.transfers;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.config.IntegrationTestConfiguration;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * See class doc for BaseIntegrationTest
 *
 * Created by robevansuk on 15/02/2017.
 */
@ExtendWith(SpringExtension.class)
@Import({IntegrationTestConfiguration.class})
public class TransferServiceIntegrationTest extends BaseIntegrationTest {

    private TransferService transferService;

    @BeforeEach
    void setUp() {
        this.transferService = new TransferService(exchange);
    }


    @Ignore
    public void canTransferFromCoinbaseAccountToGdax() {
        // TODO
    }
}
