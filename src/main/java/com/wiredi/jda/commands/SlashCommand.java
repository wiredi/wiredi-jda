package com.wiredi.jda.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface SlashCommand {

    void process(SlashCommandInteractionEvent event);

    CommandData data();

}
