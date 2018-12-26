package org.knowm.xchange.hexun;

import java.util.Map;
import org.knowm.xchange.currency.CurrencyPair;

public final class HexunTickers {

  private final Map<CurrencyPair, HexunRate> rates;

  public HexunTickers(Map<CurrencyPair, HexunRate> rates) {
    super();
    this.rates = rates;
  }

  public Map<CurrencyPair, HexunRate> getRates() {
    return rates;
  }

  @Override
  public String toString() {
    return "HexunTickers [rates=" + rates + "]";
  }
}
