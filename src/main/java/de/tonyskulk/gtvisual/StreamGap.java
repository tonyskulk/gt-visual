package de.tonyskulk.gtvisual;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.NavigableMap;

public class StreamGap {

  private NavigableMap<BigDecimal, GapDetails> gapLevels;
  private String lowerPriceExchange;
  private String higherPriceExchange;
  private Instant timestamp;
  private String baseCurrency;
  private String counterCurrency;
  private boolean exploited;
  private Duration timegap;

  public StreamGap(NavigableMap<BigDecimal, GapDetails> gapLevels, String lowerPriceExchange,
      String higherPriceExchange, Instant timestamp, Duration timegap, String baseCurrency,
      String counterCurrency, boolean exploited) {
    this.gapLevels = gapLevels;
    this.lowerPriceExchange = lowerPriceExchange;
    this.higherPriceExchange = higherPriceExchange;
    this.timestamp = timestamp;
    this.timegap = timegap;
    this.baseCurrency = baseCurrency;
    this.counterCurrency = counterCurrency;
    this.exploited = exploited;
  }

  public NavigableMap<BigDecimal, GapDetails> getGapLevels() {
    return gapLevels;
  }

  public void setGapLevels(NavigableMap<BigDecimal, GapDetails> gapLevels) {
    this.gapLevels = gapLevels;
  }

  public String getLowerPriceExchange() {
    return lowerPriceExchange;
  }

  public void setLowerPriceExchange(String lowerPriceExchange) {
    this.lowerPriceExchange = lowerPriceExchange;
  }

  public String getHigherPriceExchange() {
    return higherPriceExchange;
  }

  public void setHigherPriceExchange(String higherPriceExchange) {
    this.higherPriceExchange = higherPriceExchange;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public Duration getTimegap() {
    return timegap;
  }

  public void setTimegap(Duration timegap) {
    this.timegap = timegap;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public void setBaseCurrency(String baseCurrency) {
    this.baseCurrency = baseCurrency;
  }

  public String getCounterCurrency() {
    return counterCurrency;
  }

  public void setCounterCurrency(String counterCurrency) {
    this.counterCurrency = counterCurrency;
  }

  public boolean isExploited() {
    return exploited;
  }

  public void setExploited(boolean exploited) {
    this.exploited = exploited;
  }

  @Override
  public String toString() {
    return "StreamGap [gapLevels=" + gapLevels + ", lowerPriceExchange=" + lowerPriceExchange
        + ", higherPriceExchange=" + higherPriceExchange + ", timestamp=" + timestamp
        + ", baseCurrency=" + baseCurrency + ", counterCurrency=" + counterCurrency
        + ", exploited=" + exploited + ", timegap=" + timegap + "]";
  }
}
