package at.study.automation.api.client;

import java.util.Map;

public interface RestResponse {

    int getStatusCode();

    Map<String, String> getHeaders();

    String getPayload();

    <T> T getPayload(Class<T> clazz);
}
