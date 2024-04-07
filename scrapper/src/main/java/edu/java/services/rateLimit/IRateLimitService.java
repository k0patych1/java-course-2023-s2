package edu.java.services.rateLimit;

import io.github.bucket4j.Bucket;

public interface IRateLimitService {
    Bucket resolveBucket(String ip);
}
