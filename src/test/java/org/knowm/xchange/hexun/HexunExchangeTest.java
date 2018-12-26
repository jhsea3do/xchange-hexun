package org.knowm.xchange.hexun;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HexunExchangeTest {

	HexunExchange exchange;

	HexunApi hexunApi;

	@Before
	public void setUp() {
		exchange = ExchangeFactory.INSTANCE.createExchange(HexunExchange.class);
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
}
