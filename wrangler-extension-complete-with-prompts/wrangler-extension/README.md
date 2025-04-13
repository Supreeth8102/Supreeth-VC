
# Wrangler Extension

## Byte Size & Time Duration Parsers

Wrangler now supports parsing and aggregating byte sizes and time durations using the `aggregate-stats` directive.

### Example Recipe

```
aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec
```

### Supported Units

- Byte Size: B, KB, MB, GB, TB
- Time Duration: ms, s, m, h, d

### Output

A single row with aggregate values in megabytes and seconds.
- `total_size_mb`: total bytes converted to MB
- `total_time_sec`: total time in seconds
