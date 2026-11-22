package com.graduation.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/** Loads testdata.json once and gives easy access to its values using JSON pointers (e.g. "/validUser/username"). */
public final class TestData {

    private static final JsonNode ROOT = load();

    private TestData() { }

    private static JsonNode load() {
        try (InputStream in = TestData.class.getClassLoader().getResourceAsStream("testdata.json")) {
            if (in == null) {
                throw new IllegalStateException("testdata.json not found in src/test/resources");
            }
            return new ObjectMapper().readTree(in);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read testdata.json", e);
        }
    }

    public static JsonNode node(String pointer) {
        return ROOT.at(pointer);
    }

    public static String str(String pointer) {
        return ROOT.at(pointer).asText();
    }

    public static List<String> list(String pointer) {
        List<String> values = new ArrayList<>();
        node(pointer).forEach(n -> values.add(n.asText()));
        return values;
    }
}
