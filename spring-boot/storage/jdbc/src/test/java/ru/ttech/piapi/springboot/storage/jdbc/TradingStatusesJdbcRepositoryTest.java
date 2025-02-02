package ru.ttech.piapi.springboot.storage.jdbc;

import com.google.protobuf.Timestamp;
import lombok.SneakyThrows;
import ru.tinkoff.piapi.contract.v1.SecurityTradingStatus;
import ru.tinkoff.piapi.contract.v1.TradingStatus;
import ru.ttech.piapi.springboot.storage.jdbc.config.JdbcConfiguration;
import ru.ttech.piapi.springboot.storage.jdbc.repository.TradingStatusesJdbcRepository;

import java.util.UUID;

public class TradingStatusesJdbcRepositoryTest extends BaseJdbcRepositoryTest<TradingStatus, TradingStatusesJdbcRepository> {

  @SneakyThrows
  @Override
  protected TradingStatusesJdbcRepository createRepository(JdbcConfiguration configuration) {
    return new TradingStatusesJdbcRepository(configuration);
  }

  @Override
  protected TradingStatus createEntity() {
    return TradingStatus.newBuilder()
      .setTime(getTimestampNow())
      .setInstrumentUid(UUID.randomUUID().toString())
      .setTradingStatus(SecurityTradingStatus.SECURITY_TRADING_STATUS_NORMAL_TRADING)
      .setLimitOrderAvailableFlag(true)
      .setMarketOrderAvailableFlag(true)
      .build();
  }

  @Override
  protected Timestamp getEntityTime(TradingStatus entity) {
    return entity.getTime();
  }

  @Override
  protected String getEntityInstrumentUid(TradingStatus entity) {
    return entity.getInstrumentUid();
  }
}
