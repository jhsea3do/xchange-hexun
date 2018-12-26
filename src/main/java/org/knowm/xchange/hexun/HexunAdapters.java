package org.knowm.xchange.hexun;

import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

public final class HexunAdapters {

  /** private Constructor */
  private HexunAdapters() {}

  public static Ticker adaptTicker(
      CurrencyPair currencyPair,
      Double exchangeRate,
      Double bidRate,
      Double askRate,
      Date timestamp) {

    BigDecimal last = BigDecimal.valueOf(exchangeRate);
    BigDecimal bid = BigDecimal.valueOf(bidRate);
    BigDecimal ask = BigDecimal.valueOf(askRate);
    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .timestamp(timestamp)
        .build();
  }
}
