package code.lumiseven.test.discord1;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.neovisionaries.ws.client.ProxySettings;
import com.neovisionaries.ws.client.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import okhttp3.OkHttpClient;

public class MessageListener extends ListenerAdapter {
    public static void main(String[] args) throws InterruptedException {
        // 创建 Proxy 对象
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 11081));
        // 创建 OkHttpClient.Builder 对象并配置代理
        OkHttpClient client = new OkHttpClient.Builder()
                .proxy(proxy)
                .build();
        WebSocketFactory webSocketFactory = new WebSocketFactory();
        ProxySettings proxySettings = webSocketFactory.getProxySettings();
        proxySettings.setHost("localhost");
        proxySettings.setPort(11081);
        // MTA4MjY0NDc1OTcwNjg3Mzg5OA.GmAwNs.7OVZh8r8rjFW6jlm4vR-bhBhB0g7isv-HsrOkA
        JDA jda = JDABuilder
//                .createDefault("")
                .createDefault("")
                .enableIntents(GatewayIntent.MESSAGE_CONTENT) // enables explicit access to message.getContentDisplay()
                .setHttpClient(client)
                .setWebsocketFactory(webSocketFactory)
                .build();
        jda.addEventListener(new MessageListener());
        // optionally block until JDA is ready
        jda.awaitReady();

        System.out.println("do send message");
        TextChannel textChannelById = jda.getTextChannelById(1078864151436341321L);
        textChannelById.sendMessage(MessageCreateData.fromContent("/imagine Cute trendy little girl")).queue();
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE))
        {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                    event.getMessage().getContentDisplay());
        }
        else
        {
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
                    event.getChannel().getName(), event.getMember().getEffectiveName(),
                    event.getMessage().getContentDisplay());
            try {
                System.out.println("messageId:" + event.getMessage().getId());
                System.out.println("getEmbeds:" + new ObjectMapper().writeValueAsString(event.getMessage().getEmbeds()));
                System.out.println("getAttachments:" + new ObjectMapper().writeValueAsString(event.getMessage().getAttachments().stream().map(x -> x.getUrl()).collect(Collectors.toList())));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
