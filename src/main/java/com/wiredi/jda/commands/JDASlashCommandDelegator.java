package com.wiredi.jda.commands;

import com.wiredi.annotations.Wire;
import com.wiredi.annotations.aspects.Pure;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Wire(proxy = false)
public class JDASlashCommandDelegator extends ListenerAdapter {

    private final Map<String, SlashCommand> commands;

    public JDASlashCommandDelegator(List<SlashCommand> slashCommands) {
        this.commands = slashCommands.stream()
                        .collect(
                                Collectors.toMap(
                                        slashCommand -> slashCommand.data().getName(),
                                        slashCommand -> slashCommand
                                )
                        );
    }

    @Override
    public void onSlashCommandInteraction(
            @NotNull SlashCommandInteractionEvent event
    ) {
        SlashCommand slashCommand = commands.get(event.getName());
        slashCommand.process(event);
    }
}
