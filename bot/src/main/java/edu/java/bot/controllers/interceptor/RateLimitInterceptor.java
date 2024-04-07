package edu.java.bot.controllers.interceptor;

import edu.java.bot.exception.RateLimitExceededException;
import edu.java.bot.services.rateLimit.IRateLimitService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {
    private static final String IP_ADDRESS_HEADER = "X-FORWARDED-FOR";
    private final IRateLimitService rateLimitService;

    @Override
    public boolean preHandle(
        @NotNull HttpServletRequest request,
        @NotNull HttpServletResponse response,
        @NotNull Object handler
    ) {
        String ipAddress = request.getHeader(IP_ADDRESS_HEADER);
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        Bucket bucket = rateLimitService.resolveBucket(ipAddress);

        if (!bucket.tryConsume(1)) {
            throw new RateLimitExceededException();
        }

        return true;
    }
}
