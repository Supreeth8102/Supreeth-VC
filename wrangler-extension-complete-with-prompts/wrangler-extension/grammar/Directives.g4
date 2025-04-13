
// Updated ANTLR grammar rules
value
    : ... // existing alternatives
    | BYTE_SIZE
    | TIME_DURATION
    ;

BYTE_SIZE: DIGITS BYTE_UNIT;
TIME_DURATION: DIGITS TIME_UNIT;

fragment BYTE_UNIT: [kKmMgGtTpPeE]? [bB];
fragment TIME_UNIT: ('ms' | 's' | 'm' | 'h' | 'd');
fragment DIGITS: [0-9]+ ('.' [0-9]+)?;
