package code.lumiseven.test.discord1;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class D1 {
    public static void main(String[] args) {
        JDA jda = JDABuilder.createDefault("token")
                .build();
        TextChannel textChannel = jda.getTextChannelById(1078864151436341318L);
        GuildChannel guildChannel = jda.getGuildChannelById(1078864151436341318L);
        textChannel.sendMessage(MessageCreateData.fromContent("test content1"));
    }
}
