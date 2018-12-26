package org.knowm.xchange.hexun;

import com.google.common.base.Joiner;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("api")
@Produces({ MediaType.APPLICATION_JSON, "application/javascript" })
public class HexunDefaultProfile implements HexunProfile {

	/*
	 * // http://quote.hexun.com/default.htm#forex
	 * 
	 * // http://forex.wiapi.hexun.com/forex/sortlist
	 * // ?block=302&number=1000&title=15&commodityid=0&direction=0&start=0
	 * // &column=code,name,price,updown,updownrate,open,high,low,buyPrice,sellPrice,datetime,PriceWeight
	 * // &callback=quoteforex&time=143530
	*/
	protected static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HHmmss");

	private Integer block = 302;

	private Integer number = 1000;

	private String title = String.valueOf(Integer.valueOf(15));

	private Integer commodityid = 0;

	private Integer direction = 0;

	private Integer start = 0;

	private String[] columns = "code,name,price,updown,updownrate,open,high,low,buyPrice,sellPrice,datetime,PriceWeight"
			.split(",");

	private String callback = "quoteforex";

	private String time = "154820";

	public String getColumn() {
		return Joiner.on(",").join(this.getColumns());
	}

	public Integer getBlock() {
		return block;
	}

	public Integer getNumber() {
		return number;
	}

	public String getTitle() {
		return title;
	}

	public Integer getCommodityid() {
		return commodityid;
	}

	public Integer getDirection() {
		return direction;
	}

	public Integer getStart() {
		return start;
	}

	public String[] getColumns() {
		return columns;
	}

	public String getCallback() {
		return callback;
	}

	public String getTime() {
		return time;
	}

	@Override
	public String getTime(Date date) {
		// TODO
		// if(null == date) {
		// return getTime();
		// }
		// return TIME_FORMAT.format(date);
		return getTime();
	}
}
