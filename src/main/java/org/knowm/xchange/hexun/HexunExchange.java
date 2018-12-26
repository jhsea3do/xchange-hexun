package org.knowm.xchange.hexun;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.hexun.service.HexunMarketDataService;
import si.mazi.rescu.SynchronizedValueFactory;

public class HexunExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new HexunMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setPlainTextUri("http://forex.wiapi.hexun.com");
    exchangeSpecification.setHost("forex.wiapi.hexun.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Hexun Exchange Rates"); // Hexun Exchange Rates
    exchangeSpecification.setExchangeDescription(
        "Open Exchange Rates is an exchange rate provider for a wide range of currencies.");
    // exchangeSpecification.setMetaDataJsonFileOverride("classpath:hexun.json");
    exchangeSpecification.setExchangeName("hexun");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // TODO Auto-generated method stub
    return null;
  }
}
