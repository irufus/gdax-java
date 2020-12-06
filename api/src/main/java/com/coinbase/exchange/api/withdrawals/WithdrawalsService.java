package com.coinbase.exchange.api.withdrawals;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.model.*;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class WithdrawalsService {

	private static final String WITHDRAWALS_ENDPOINT = "/withdrawals";
	private static final String PAYMENT_METHOD = "/payment-method";
	private static final String COINBASE = "/coinbase-account";
	private static final String CRYPTO = "/crypto";

	private final CoinbaseExchange coinbaseExchange;

	public PaymentResponse makeWithdrawalToPaymentMethod(BigDecimal amount, String currency, String paymentMethodId) {
		PaymentRequest request = new PaymentRequest(amount, currency, paymentMethodId);
		return makeWithdrawal(request, PAYMENT_METHOD);
	}

	// TODO untested - needs coinbase account ID to work.
	public PaymentResponse makeWithdrawalToCoinbase(BigDecimal amount, String currency, String coinbaseAccountId) {
		CoinbasePaymentRequest request = new CoinbasePaymentRequest(amount.setScale(8, RoundingMode.HALF_DOWN), currency, coinbaseAccountId);
		return makeWithdrawal(request, COINBASE);
	}

	// TODO untested - needs a crypto currency account address
	public PaymentResponse makeWithdrawalToCryptoAccount(BigDecimal amount, String currency, String cryptoAccountAddress) {
		CryptoPaymentRequest request = new CryptoPaymentRequest(amount.setScale(8, RoundingMode.HALF_DOWN), currency, cryptoAccountAddress);
		return makeWithdrawal(request, CRYPTO);
	}


	private PaymentResponse makeWithdrawal(MonetaryRequest request, String withdrawalType) {
		return coinbaseExchange.post(WITHDRAWALS_ENDPOINT + withdrawalType,
				new ParameterizedTypeReference<>() {
				},
				request);
	}
}
