package org.knowm.xchange.hexun;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import si.mazi.rescu.annotation.JSONP;
import si.mazi.rescu.annotation.UrlEncoding;

@Path("forex")
@Produces({MediaType.APPLICATION_JSON, "application/javascript"})
public interface HexunApi {

  @GET
  @Path("sortlist")
  @JSONP(queryParam = "callback", callback = "quoteforex")
  @UrlEncoding(false)
  ObjectNode getTickers(
      @QueryParam("block") String block,
      @QueryParam("number") String number,
      @QueryParam("title") String title,
      @QueryParam("commodityid") String commodityid,
      @QueryParam("direction") String direction,
      @QueryParam("start") String start,
      @QueryParam("column") String column,
      @QueryParam("time") String time)
      throws IOException;
}
