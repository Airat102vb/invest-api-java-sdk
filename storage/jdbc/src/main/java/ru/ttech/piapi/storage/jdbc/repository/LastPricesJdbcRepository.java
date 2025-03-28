package ru.ttech.piapi.storage.jdbc.repository;

import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.LastPriceType;
import ru.ttech.piapi.core.helpers.NumberMapper;
import ru.ttech.piapi.core.helpers.TimeMapper;
import ru.ttech.piapi.storage.jdbc.config.JdbcConfiguration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LastPricesJdbcRepository extends JdbcRepository<LastPrice> {

  public LastPricesJdbcRepository(JdbcConfiguration configuration) {
    super(configuration);
  }

  @Override
  protected String getTableQuery() {
    return "CREATE TABLE IF NOT EXISTS " + getTableName() + " (" +
      "c_time TIMESTAMP(6), " +
      "c_instrument_uid VARCHAR(255), " +
      "c_price DECIMAL(19,4), " +
      "c_last_price_type TEXT, " +
      "PRIMARY KEY (c_time, c_instrument_uid))";
  }

  @Override
  protected String getInsertQuery() {
    return "INSERT INTO " + getTableName() + " (c_time, c_instrument_uid, c_price, c_last_price_type) VALUES (?, ?, ?, ?)";
  }

  @Override
  protected LastPrice parseEntityFromResultSet(ResultSet rs) throws SQLException {
    return LastPrice.newBuilder()
      .setTime(TimeMapper.localDateTimeToTimestamp(rs.getTimestamp(1).toLocalDateTime()))
      .setInstrumentUid(rs.getString(2))
      .setPrice(NumberMapper.bigDecimalToQuotation(rs.getBigDecimal(3)))
      .setLastPriceType(LastPriceType.valueOf(rs.getString(4)))
      .build();
  }

  @Override
  protected void setStatementParameters(PreparedStatement stmt, LastPrice entity) throws SQLException {
    stmt.setTimestamp(1, Timestamp.valueOf(TimeMapper.timestampToLocalDateTime(entity.getTime())));
    stmt.setString(2, entity.getInstrumentUid());
    stmt.setBigDecimal(3, NumberMapper.quotationToBigDecimal(entity.getPrice()));
    stmt.setString(4, entity.getLastPriceType().name());
  }
}
