package org.knowm.xchange.hexun;

import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Joiner;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.hexun.service.HexunMarketDataService;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HexunExchangeTest {

  HexunExchange exchange;

  HexunApi hexunApi;

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
    exchange = ExchangeFactory.INSTANCE.createExchange(HexunExchange.class);
    hexunApi = new MockHexunApi(MockHexunApi.FAKE_FILE_DEFAULT);
  }

  @After
  public void tearDown() {
    exchange = null;
  }

  @Test
  public void testMarketDataService() {
    MarketDataService marketDataService = exchange.getMarketDataService();
    assertNotNull(marketDataService);
  }

  @Test
  public void testGetTicker() {
    CurrencyPair currencyPair = new CurrencyPair(Currency.USD, Currency.CNY);
    MarketDataService marketDataService = exchange.getMarketDataService();
    try {
      HexunMarketDataService hexunMarketDataService = (HexunMarketDataService) marketDataService;
      hexunMarketDataService.setHexunApi(hexunApi);
      Ticker ticker = marketDataService.getTicker(currencyPair);
      assertNotNull(ticker);
      assertEquals(currencyPair.base, ticker.getCurrencyPair().base);
      assertEquals(currencyPair.counter, ticker.getCurrencyPair().counter);
      assertTrue(ticker.getLast().doubleValue() > 0);
      assertTrue(ticker.getLast().doubleValue() == 68885);
      System.out.println(ticker);
    } catch (IOException e) {
      assertNull(e);
    }
  }
}
