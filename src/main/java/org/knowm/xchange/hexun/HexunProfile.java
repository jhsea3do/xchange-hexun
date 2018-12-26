package org.knowm.xchange.hexun;

import java.util.Date;

public interface HexunProfile {

  String getColumn();

  Integer getBlock();

  Integer getNumber();

  String getTitle();

  Integer getCommodityid();

  Integer getDirection();

  Integer getStart();

  String[] getColumns();

  String getCallback();

  String getTime(Date date);
}
