package org.knowm.xchange.hexun.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.hexun.HexunApi;
import org.knowm.xchange.hexun.HexunDefaultProfile;
import org.knowm.xchange.hexun.HexunProfile;
import org.knowm.xchange.hexun.HexunRate;
import org.knowm.xchange.hexun.HexunTickers;
import si.mazi.rescu.RestProxyFactory;

public class HexunMarketDataServiceRaw extends HexunBaseService {

  private final HexunApi hexunApi;

  private final HexunProfile hexunProfile;

  /**
   * Constructor
   *
   * @param exchange
   */
  public HexunMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.hexunApi =
        RestProxyFactory.createProxy(
            HexunApi.class,
            exchange.getExchangeSpecification().getPlainTextUri(),
            getClientConfig());
    this.hexunProfile = new HexunDefaultProfile();
  }

  public HexunRate getHexunTicker(CurrencyPair pair) throws IOException {

    // Request data
    ObjectNode hexunTickersWrapper =
        hexunApi.getTickers(
            hexunProfile.getBlock().toString(),
            hexunProfile.getNumber().toString(),
            hexunProfile.getTitle(),
            hexunProfile.getCommodityid().toString(),
            hexunProfile.getDirection().toString(),
            hexunProfile.getStart().toString(),
            hexunProfile.getColumn(),
            hexunProfile.getTime(new Date()));
    if (hexunTickersWrapper == null) {
      throw new ExchangeException("Null response returned from Hexun Rates!");
    }
    return parseHexunTicker(hexunTickersWrapper).getRates().get(pair);
  }

  public Double parseDoubleObject(JsonNode jsonNode) {
    if (jsonNode.isArray()) {
      double x = jsonNode.get(0).doubleValue();
      StringBuffer sb = new StringBuffer("0.");
      for (int a = 1; a < jsonNode.size(); a++) {
        sb.append(jsonNode.get(a));
      }
      double y = Double.parseDouble(sb.toString());
      return x + y;
    } else if (jsonNode.isNumber()) {
      return jsonNode.doubleValue();
    } else {
      return Double.NaN;
    }
  }

  public HexunTickers parseHexunTicker(ObjectNode hexunTickersWrapper) {
    Map<CurrencyPair, HexunRate> map = Maps.newHashMap();
    if (null != hexunTickersWrapper && hexunTickersWrapper.has("Data")) {
      ArrayNode tickers = (ArrayNode) (hexunTickersWrapper.get("Data").get(0));
      for (int i = 0; i < tickers.size(); i++) {
        ArrayNode ticker = (ArrayNode) tickers.get(i);
        String code = ticker.get(0).textValue();
        if (!code.endsWith("CNY")) {
          continue;
        }
        String name = ticker.get(1).textValue();
        String from = code.substring(0, 3);
        String to = code.substring(3);

        Double price = parseDoubleObject(ticker.get(2));
        Double bidPrice = parseDoubleObject(ticker.get(8));
        Double askPrice = parseDoubleObject(ticker.get(9));
        HexunRate rate = new HexunRate();
        rate.setCode(code);
        rate.setName(name);
        rate.setPrice(price);
        rate.setBidPrice(bidPrice);
        rate.setAskPrice(askPrice);
        map.put(new CurrencyPair(from, to), rate);
      }
    }

    return new HexunTickers(map);
  }
}
