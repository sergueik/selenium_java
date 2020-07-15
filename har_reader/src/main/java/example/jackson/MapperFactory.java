package example.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.HarReaderMode;

public interface MapperFactory {

    ObjectMapper instance(HarReaderMode mode);

}
