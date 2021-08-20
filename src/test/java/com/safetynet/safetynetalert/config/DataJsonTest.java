package com.safetynet.safetynetalert.config;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynet.safetynetalert.json.DataJson;
import net.minidev.json.writer.JsonReader;

import java.io.IOException;
import java.io.InputStream;

public class DataJsonTest extends DataJson {

    private DataJsonTest() {
    }

        /**
         * Reading Json file "data.json".
         * @return Any deserialized json data
         */
        public static Any inputJson() {
            byte[] tab;
            InputStream inputStream =
                    JsonReader.class.getClassLoader()
                            .getResourceAsStream("dataTest.json");
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




