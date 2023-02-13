package com.example;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import io.opentelemetry.instrumentation.annotations.WithSpan;

import java.util.Optional;

public class Function {

    private static final TelemetryClient telemetryClient = new TelemetryClient();

    @FunctionName("HttpExample")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET, HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request) {

        telemetryClient.trackMetric("test", 123);

        telemetryClient.trackTrace("test");

        test();

        return request.createResponseBuilder(HttpStatus.OK).body("Hello world!").build();
    }

    @WithSpan
    private void test() {
        System.out.println("here");
    }
}
