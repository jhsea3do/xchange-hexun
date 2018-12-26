package org.knowm.xchange.hexun.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

@SuppressWarnings("rawtypes")
public class HexunBaseService extends BaseExchangeService implements BaseService {

  @SuppressWarnings("unchecked")
  public HexunBaseService(Exchange exchange) {
    super(exchange);
  }
}
