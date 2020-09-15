package com.miro.assignment.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class provides utility functions
 */
public final class Utils {
    private Utils() {
        // Do not instantiate
    }
    
    /**
     * Serialize obejct to JSON
     * @param obj The object to serialize
     * @return The serialized object in JSON format
     */
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }  
}
