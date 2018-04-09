package com.kshah.springstatemachineexample.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class Request {

    private ProcessingType processingType;

    @JsonCreator
    public Request(@JsonProperty("processingType") String processingType) {
        this.processingType = ProcessingType.fromType(processingType);
    }

    public enum ProcessingType {

        TYPE_A("A"),
        TYPE_B("B"),
        TYPE_C("C");

        private static Map<String, ProcessingType> PROCESSING_TYPE_MAP = Stream
                .of(ProcessingType.values())
                .collect(Collectors.toMap(s -> s.type, Function.identity()));

        private final String type;

        ProcessingType(final String type) {
            this.type = type;
        }

        public static ProcessingType fromType(String type) {
            return Optional
                    .ofNullable(PROCESSING_TYPE_MAP.get(type))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid type '" + type + "'"));
        }


        public String getType() {
            return type;
        }

    }

}
