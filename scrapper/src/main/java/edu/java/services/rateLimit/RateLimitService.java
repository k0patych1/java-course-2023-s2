package edu.java.services.rateLimit;

import edu.java.configuration.ApplicationConfig;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RateLimitService implements IRateLimitService {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private final ApplicationConfig applicationConfig;

    @Override
    public Bucket resolveBucket(String ip) {
        return cache.computeIfAbsent(ip, this::newBucket);
    }

    private Bucket newBucket(String ip) {
        Long tokens = applicationConfig.rateLimiting().tokensCapacity();
        Long tokensPerMinute = applicationConfig.rateLimiting().tokensPerSecond();

        Bandwidth bandwidth = Bandwidth.builder()
            .capacity(tokens)
            .refillGreedy(tokensPerMinute, Duration.ofSeconds(1))
            .build();

        return Bucket.builder()
            .addLimit(bandwidth)
            .build();
    }
}
