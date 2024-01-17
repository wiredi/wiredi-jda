package com.wiredi.jda;

import com.wiredi.annotations.properties.Property;
import com.wiredi.annotations.properties.PropertyBinding;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@PropertyBinding(
        prefix = "wiredi.jda"
)
public class JDAProperties {

    private final String token;
    //    private final boolean enableShutdownHook;
    private final int maxBufferSize;
    private final Flags flags;
    private final Activity activity;

    public JDAProperties(
            String token,
            @Property(defaultValue = "2048")
            int maxBufferSize,
            Flags flags,
            Activity activity

    ) {
        this.token = token;
        this.maxBufferSize = maxBufferSize;
        this.flags = flags;
        this.activity = activity;
    }

    public Flags flags() {
        return this.flags;
    }

    public int maxBufferSize() {
        return maxBufferSize;
    }

    public String getToken() {
        return token;
    }

    public Activity activity() {
        return activity;
    }

    public record Flags(List<GatewayIntent> gatewayIntents, List<CacheFlag> cache) {

        public boolean isDisabled(@NotNull CacheFlag cacheFlag) {
            return !cache.contains(cacheFlag);
        }
    }

    public record Activity(
            @Property(name = "type", defaultValue = "custom-status")
            ActivityType type,
            @Property(name = "name", defaultValue = "none")
            String name
    ) {}
}
