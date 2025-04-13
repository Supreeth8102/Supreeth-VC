
package io.cdap.wrangler.api.parser;

public class TimeDuration extends Token {
  private final long milliseconds;

  public TimeDuration(String value) {
    super(value);
    this.milliseconds = parseMillis(value);
  }

  private long parseMillis(String input) {
    String unit = input.replaceAll("[0-9.]", "").toLowerCase();
    double num = Double.parseDouble(input.replaceAll("[^0-9.]", ""));

    switch (unit) {
      case "s": return (long) (num * 1000);
      case "m": return (long) (num * 60 * 1000);
      case "h": return (long) (num * 3600 * 1000);
      case "d": return (long) (num * 24 * 3600 * 1000);
      default: return (long) num;
    }
  }

  public long getMilliseconds() {
    return milliseconds;
  }
}
