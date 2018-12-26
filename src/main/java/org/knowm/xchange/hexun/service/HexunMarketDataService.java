package org.knowm.xchange.hexun.service;

import java.io.IOException;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.hexun.HexunAdapters;
import org.knowm.xchange.hexun.HexunRate;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HexunMarketDataService extends HexunMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public HexunMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    HexunRate rates = getHexunTicker(currencyPair);

    // Adapt to XChange DTOs
    return HexunAdapters.adaptTicker(
        currencyPair,
        rates.getPrice(),
        rates.getBidPrice(),
        rates.getAskPrice(),
        new Date(rates.getDatetime()));
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
