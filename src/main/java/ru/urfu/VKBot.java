
package ru.urfu;

import ru.codeoff.bots.keyboard.Button;
import ru.codeoff.bots.keyboard.ButtonColor;
import ru.codeoff.bots.keyboard.Keyboard;
import ru.codeoff.bots.sdk.clients.Group;
import ru.codeoff.bots.sdk.objects.Message;

public class VKBot {

    public static final String BOT_TOKEN = "23c089432a9cfc87a7617d20cded74a7c58c9024495ee339ab44dacfcdac809b9b7af6238b0d540fe9753";
    private static ChatBot chatBot = new ChatBot();

    public static void main(String[] args) {

        Group group = new Group(200955058, BOT_TOKEN);

        Keyboard keys = Keyboard.of(new Button("sample", ButtonColor.DEFAULT), new Button("text", ButtonColor.NEGATIVE));
// для кнопок с ButtonColor.DEFAULT вы можете использовать String в качестве аргумента
// addButtons всегда добавляет одну новую строку и автоматически группирует кнопки по 4 в каждой строке.
// они не будут добавлять кнопки к существующим строкам
        keys.addButtons("A","B","C","D","A1");
// «А1» будет автоматически перемещен на новую строку
// затем добавляем его в ответ
        group.onSimpleTextMessage(message -> {
            new Message()
                    .from(group)
                    .to(message.authorId())
                    .text(message.getText())
                    .keyboard(keys)
                    .send();
        });

        /*group.onSimpleTextMessage(message ->
                new Message()
                        .from(group)
                        .to(message.authorId())
                        .text("Что-то скучновато буковки читать. Картинку кинь лучше.")
                        .send()
        );*/
    }
}

/*import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;

import java.io.IOException;

public class VKBot {

    public static final String BOT_TOKEN = "23c089432a9cfc87a7617d20cded74a7c58c9024495ee339ab44dacfcdac809b9b7af6238b0d540fe9753";
    private static ChatBot chatBot = new ChatBot();

    public static void main(String[] args) {
        Group group = new Group(200955058, BOT_TOKEN);

        group.onSimpleTextMessage(message ->
                {
                    try {
                        new Message()
                                .from(group)
                                .to(message.authorId())
                                .text(chatBot.analyzeCommand(message.getText(), Integer.toString(message.authorId())))
                                .send();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}*/

/*
import com.petersamokhin.vksdk.core.api.VkApi;
import com.petersamokhin.vksdk.core.callback.Callback;
import com.petersamokhin.vksdk.core.client.VkApiClient;
import com.petersamokhin.vksdk.core.http.HttpClient;
import com.petersamokhin.vksdk.core.http.Parameters;
import com.petersamokhin.vksdk.core.model.VkSettings;
import com.petersamokhin.vksdk.core.model.objects.Message;
import com.petersamokhin.vksdk.http.VkOkHttpClient;
import kotlinx.serialization.json.JsonElement;
import org.jetbrains.annotations.NotNull;

public class JavaBot {
    public void start(final int clientId, @NotNull final String accessToken) {
        if (accessToken.equals("abcdef123456...")) throw new RuntimeException("Please, replace dummy access_token with yours in Launcher.kt");

        // OkHttp client is available only for JVM
        final HttpClient httpClient = new VkOkHttpClient();

        // Woo-hoo! We can use different constructors. Thanks @JvmOverloads
        final VkSettings vkSettings = new VkSettings(httpClient,
                VkApi.DEFAULT_VERSION,
                // Woo-hoo! @JvmStatic
                Parameters.of("lang", "en"),
                3
        );

        final VkApiClient vkApiClient = new VkApiClient(clientId, accessToken, VkApiClient.Type.Community, vkSettings);

        // Using of the kotlin coroutines flow will be not so convenient, but still possible;
        // otherwise, you can use sync and async calls with callbacks.
        vkApiClient.call("users.get", Parameters.of("user_id", "1"), false, new Callback<JsonElement>() {
            @Override
            public void onResult(@NotNull final JsonElement jsonElement) {
                // Parsing of POJOs is not so easy using kotlinx.serialization
                // Of course, you can use GSON, Moshi, etc.
                System.out.println(jsonElement);
            }

            @Override
            public void onError(@NotNull Exception e) {
                e.printStackTrace();
            }
        });

        // Woo-hoo! SAM! As you can see, callbacks are more pretty in Java.
        vkApiClient.onMessage(event -> {
            new Message()
                    .peerId(event.getMessage().getPeerId())
                    .text("Hello, world!")
                    .sendFrom(vkApiClient)
                    .execute();
        });

        // And, of course, to chat bot be working,
        // we must start the long polling loop.
        // Do it at the end of your method,
        // or call in the background thread or coroutine.
        vkApiClient.startLongPolling();
    }

    public static void main(final String[] args) {
        final int groupId = 151083290;
        final String groupAccessToken = "abcdef123456...";

        final JavaBot bot = new JavaBot();
        bot.start(groupId, groupAccessToken);
    }
}
*/
