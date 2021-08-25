package com.safetynet.safetynetalert.constant;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import net.minidev.json.writer.JsonReader;

import java.io.IOException;
import java.io.InputStream;

public final class DataJson {
    private DataJson() {
    }

    /** Json file path. */
    private static final String INPUT_FILE = "data.json";
    /**
     * Reading Json file "data.json".
     * @return Any deserialized json data
     */
    public static Any inputJson() {
        byte[] tab;
        InputStream inputStream =
                JsonReader.class.getClassLoader()
                        .getResourceAsStream(INPUT_FILE);
        try {
            if (inputStream != null) {
                tab = inputStream.readAllBytes();
                return JsonIterator.deserialize(tab);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


