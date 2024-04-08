package com.sermaluc.testbci.dto;

import java.util.Map;

public record ResponseError(String message, Map<String, String> detailedMessages) {
}
