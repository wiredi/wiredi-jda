package com.wiredi.jda.commands;

import com.wiredi.annotations.Wire;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

@Wire
public class TestSlashCommand implements SlashCommand {
    @Override
    public void process(SlashCommandInteractionEvent event) {
        event.reply("Hihi. That worked!").queue();
    }

    @Override
    public CommandData data() {
        return Commands.slash("test", "Activate the apprve bot in this channel")
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.USE_APPLICATION_COMMANDS));
    }
}
