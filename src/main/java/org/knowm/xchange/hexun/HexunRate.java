package org.knowm.xchange.hexun;

public class HexunRate {

  private String code;

  private String name;

  private double price;

  private double updown;

  private double updownrate;

  private double open;

  private double high;

  private double low;

  private double bidPrice;

  private double askPrice;

  private long datetime;

  private int priceWeight;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public double getUpdown() {
    return updown;
  }

  public void setUpdown(double updown) {
    this.updown = updown;
  }

  public double getUpdownrate() {
    return updownrate;
  }

  public void setUpdownrate(double updownrate) {
    this.updownrate = updownrate;
  }

  public double getOpen() {
    return open;
  }

  public void setOpen(double open) {
    this.open = open;
  }

  public double getHigh() {
    return high;
  }

  public void setHigh(double high) {
    this.high = high;
  }

  public double getLow() {
    return low;
  }

  public void setLow(double low) {
    this.low = low;
  }

  public double getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(double bidPrice) {
    this.bidPrice = bidPrice;
  }

  public double getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(double askPrice) {
    this.askPrice = askPrice;
  }

  public long getDatetime() {
    return datetime;
  }

  public void setDatetime(long datetime) {
    this.datetime = datetime;
  }

  public int getPriceWeight() {
    return priceWeight;
  }

  public void setPriceWeight(int priceWeight) {
    this.priceWeight = priceWeight;
  }

  @Override
  public String toString() {
    return "HexunRate [code="
        + code
        + ", price="
        + price
        + ", bidPrice="
        + bidPrice
        + ", askPrice="
        + askPrice
        + ", datetime="
        + datetime
        + "]";
  }
}
