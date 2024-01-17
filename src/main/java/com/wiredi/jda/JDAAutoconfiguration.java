package com.wiredi.jda;

import com.wiredi.annotations.Provider;
import com.wiredi.annotations.stereotypes.AutoConfiguration;
import com.wiredi.domain.conditional.builtin.ConditionalOnMissingBean;
import com.wiredi.jda.commands.SlashCommand;
import com.wiredi.time.Timed;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@AutoConfiguration
public class JDAAutoconfiguration {

    private static final Logger logger = LoggerFactory.getLogger(JDAAutoconfiguration.class);
    private final JDAProperties properties;

    public JDAAutoconfiguration(JDAProperties properties) {
        this.properties = properties;
    }

    @Provider
    public JDA configureJDA(
            List<EventListener> eventListeners,
            List<ListenerAdapter> adapters, // TODO: These classes should be found in the eventListeners parameters
            List<SlashCommand> commandList,
            Activity activity,
            JDAProperties jdaProperties
    ) {
        JDAProperties.Flags flags = jdaProperties.flags();
        logger.info("Building JDA");
        JDA jda = Timed.of(() -> JDABuilder.create(properties.getToken(), Collections.emptyList())
                        .addEventListeners(eventListeners.toArray())
                        .addEventListeners(adapters.toArray())
                        .setActivity(activity)
                        .setMaxBufferSize(jdaProperties.maxBufferSize())
                        .setEnabledIntents(flags.gatewayIntents())
                        .enableCache(flags.cache())
                        .disableCache(Stream.of(CacheFlag.values()).filter(flags::isDisabled).toList())
                        .build()
                )
                .then(timed -> logger.info("JDA started up in {}", timed.time()))
                .value();

        if (logger.isInfoEnabled()) {
            logger.info("Registering commands: {}", commandList.stream().map(it -> it.data().getName()).toList());
        }
        jda.updateCommands()
                .addCommands(commandList.stream().map(SlashCommand::data).toList())
                .queue();

        return jda;
    }

    @Provider
    @ConditionalOnMissingBean(type = Activity.class)
    public Activity activity(JDAProperties properties) {
        return Activity.of(properties.activity().type(), properties.activity().name());
    }
}
