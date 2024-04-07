package edu.java.bot.services.rateLimit;

import io.github.bucket4j.Bucket;

public interface IRateLimitService {
    Bucket resolveBucket(String ip);
}
