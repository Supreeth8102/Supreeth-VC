
package io.cdap.wrangler.api.parser;

public class ByteSize extends Token {
  private final long bytes;

  public ByteSize(String value) {
    super(value);
    this.bytes = parseBytes(value);
  }

  private long parseBytes(String input) {
    String unit = input.replaceAll("[0-9.]", "").toLowerCase();
    double num = Double.parseDouble(input.replaceAll("[^0-9.]", ""));

    switch (unit) {
      case "kb": return (long) (num * 1024);
      case "mb": return (long) (num * 1024 * 1024);
      case "gb": return (long) (num * 1024 * 1024 * 1024);
      case "tb": return (long) (num * 1024L * 1024 * 1024 * 1024);
      default: return (long) num;
    }
  }

  public long getBytes() {
    return bytes;
  }
}
