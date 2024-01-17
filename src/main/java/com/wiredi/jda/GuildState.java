package com.wiredi.jda;

import com.wiredi.annotations.Wire;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildTimeoutEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.*;

@Wire(proxy = false)
public class GuildState extends ListenerAdapter {

    private final Map<Long, Guild> guilds = new HashMap<>();

    public void register(Guild guild) {
        this.guilds.put(guild.getIdLong(), guild);
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        register(event.getGuild());
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        register(event.getGuild());
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        event.getJDA().getGuilds().forEach(this::register);
    }

    public void unregister(Guild guild) {
        this.guilds.remove(guild.getIdLong());
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        unregister(event.getGuild());
    }

    @Override
    public void onGuildTimeout(@Nonnull GuildTimeoutEvent event) {
        guilds.remove(event.getGuildIdLong());
    }

    public List<Guild> allAvailableGuilds() {
        return new ArrayList<>(this.guilds.values());
    }
}
