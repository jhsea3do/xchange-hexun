package org.knowm.xchange.hexun;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Joiner;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.hexun.service.HexunMarketDataService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class HexunMarketDataServiceTest {

  HexunExchange exchange;

  @Mock HexunMarketDataService mockHexunMarketDataService;

  HexunMarketDataService realHexunMarketDataService;

  HexunProfile hexunProfile;

  CurrencyPair currencyPair;

  protected class MockHexunApi implements HexunApi {

    protected static final String FAKE_FILE_DEFAULT = "sample.jsonp";

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected ClassLoader classloader = Thread.currentThread().getContextClassLoader();

    protected String fakeFile = FAKE_FILE_DEFAULT;

    public MockHexunApi(String fakeFile) {
      this.fakeFile = fakeFile;
    }

    @Override
    public ObjectNode getTickers(
        String block,
        String number,
        String title,
        String commodityid,
        String direction,
        String start,
        String column,
        String time)
        throws IOException {
      ObjectNode hexunTickersWrapper;
      try {
        String content =
            Joiner.on("")
                .join(
                    Files.readAllLines(
                        Paths.get(classloader.getResource(fakeFile).toURI()),
                        StandardCharsets.UTF_8));
        assertNotNull(content);
        hexunTickersWrapper = (ObjectNode) (objectMapper.readTree(parseJsonpData(content)));
      } catch (URISyntaxException e) {
        hexunTickersWrapper = null;
        assertNull(e);
      }
      return hexunTickersWrapper;
    }

    protected String parseJsonpData(String jsonpData) {
      String parsedJsonpData =
          jsonpData.substring(jsonpData.indexOf("(") + 1, jsonpData.lastIndexOf(")"));
      return parsedJsonpData;
    }
  }

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    exchange = ExchangeFactory.INSTANCE.createExchange(HexunExchange.class);
    hexunProfile = new HexunDefaultProfile();
    currencyPair = new CurrencyPair(Currency.USD, Currency.CNY);
    realHexunMarketDataService = new HexunMarketDataService(exchange);

    try {
      HexunApi hexunApi = new MockHexunApi(MockHexunApi.FAKE_FILE_DEFAULT);
      HexunTickers tickers =
          realHexunMarketDataService.parseHexunTicker(
              hexunApi.getTickers(
                  hexunProfile.getBlock().toString(),
                  hexunProfile.getNumber().toString(),
                  hexunProfile.getTitle(),
                  hexunProfile.getCommodityid().toString(),
                  hexunProfile.getDirection().toString(),
                  hexunProfile.getStart().toString(),
                  hexunProfile.getColumn(),
                  hexunProfile.getTime(new Date())));
      assertNotNull(tickers);
      HexunRate rate = tickers.getRates().get(currencyPair);
      when(mockHexunMarketDataService.getHexunTicker(any(CurrencyPair.class))).thenReturn(rate);
    } catch (IOException e) {
      assertNull(e);
    }
  }

  @Test
  public void testGetTicker() {

    assertNotNull(mockHexunMarketDataService);

    Ticker ticker;
    try {
      HexunRate rate = mockHexunMarketDataService.getHexunTicker(currencyPair);
      ticker =
          HexunAdapters.adaptTicker(
              currencyPair,
              rate.getPrice(),
              rate.getBidPrice(),
              rate.getAskPrice(),
              new Date(rate.getDatetime()));
    } catch (IOException e) {
      ticker = null;
      assertNull(e);
    }

    assertNotNull(ticker);
    assertEquals(currencyPair.base, ticker.getCurrencyPair().base);
    assertEquals(currencyPair.counter, ticker.getCurrencyPair().counter);
    assertTrue(ticker.getLast().doubleValue() > 0);
    assertTrue(ticker.getLast().doubleValue() == 68885);
    System.out.println(ticker);
  }
}
