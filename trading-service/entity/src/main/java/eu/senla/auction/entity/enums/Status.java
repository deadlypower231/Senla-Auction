package eu.senla.auction.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Status {

    INACTIVE((short) 0), ACTIVE((short) 1);

    private static final Map<Short, Status> LOOKUP;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(Status.values())
                .collect(Collectors.toMap(Status::getVal, Function.identity())));
    }

    private final short val;

    public static Status lookup(short val) {
        return Optional.ofNullable(LOOKUP.get(val)).orElseThrow(() -> new IllegalArgumentException("Unknown value " + val));
    }

}
