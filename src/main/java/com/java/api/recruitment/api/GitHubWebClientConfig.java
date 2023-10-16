package com.java.api.recruitment.api;

import com.java.api.recruitment.utils.exception.exceptions.GitHubServerException;
import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * Configuration - Creates a WebClient.Builder for GitHub API
 *
 * @author Alicja Gratkowska
 */
@Configuration
public class GitHubWebClientConfig {

    private final static String GITHUB_BASE_URL = "https://api.github.com";

    @Bean
    public static WebClient.Builder getInstance() {
        final HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .responseTimeout(Duration.ofSeconds(2));

        return WebClient.builder()
                .filter(errorHandler())
                .baseUrl(GITHUB_BASE_URL)
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }

    public static ExchangeFilterFunction errorHandler() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            final HttpStatusCode statusCode = clientResponse.statusCode();
            if (clientResponse.statusCode().is5xxServerError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new GitHubServerException(statusCode, errorBody)));
            } else if (clientResponse.statusCode().is4xxClientError() && clientResponse.statusCode().value() != 404) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new GitHubServerException(statusCode, errorBody)));
            } else {
                return Mono.just(clientResponse);
            }
        });
    }
}
