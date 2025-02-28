package cloud.terium.cloudsystem.common.utils.version;


import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Getter
public enum ServerVersions {
    VELOCITY_3_2_0_SNAPSHOT("velocity-3.2.0-SNAPSHOT", "https://api.papermc.io/v2/projects/velocity/versions/3.2.0-SNAPSHOT/"),
    PAPER_1_20_1("paper-1.20.1", "https://api.papermc.io/v2/projects/paper/versions/1.20.1/"),
    PAPER_1_19_4("paper-1.19.4", "https://api.papermc.io/v2/projects/paper/versions/1.19.4/"),
    PAPER_1_19_3("paper-1.19.3", "https://api.papermc.io/v2/projects/paper/versions/1.19.3/"),
    PAPER_1_19_2("paper-1.19.2", "https://api.papermc.io/v2/projects/paper/versions/1.19.2/"),
    PAPER_1_18_2("paper-1.18.2", "https://api.papermc.io/v2/projects/paper/versions/1.18.2/"),
    PAPER_1_17_1("paper-1.17.1", "https://api.papermc.io/v2/projects/paper/versions/1.17.1/"),
    PAPER_1_16_5("paper-1.16.5", "https://api.papermc.io/v2/projects/paper/versions/1.16.5/"),
    PAPER_1_15_2("paper-1.15.2", "https://api.papermc.io/v2/projects/paper/versions/1.15.2/"),
    PAPER_1_14_4("paper-1.14.4", "https://api.papermc.io/v2/projects/paper/versions/1.14.4/"),
    PAPER_1_13_2("paper-1.13.2", "https://api.papermc.io/v2/projects/paper/versions/1.13.2/"),
    PAPER_1_12_2("paper-1.12.2", "https://api.papermc.io/v2/projects/paper/versions/1.12.2/"),
    PURPUR_1_16_5("purpur-1.16.6", "https://api.purpurmc.org/v2/purpur/1.16.5/"),
    PURPUR_1_17_1("purpur-1.17.1", "https://api.purpurmc.org/v2/purpur/1.17.1/"),
    PURPUR_1_18_2("purpur-1.18.2", "https://api.purpurmc.org/v2/purpur/1.18.2/"),
    PURPUR_1_19_2("purpur-1.19.2", "https://api.purpurmc.org/v2/purpur/1.19.2/"),
    PURPUR_1_19_3("purpur-1.19.3", "https://api.purpurmc.org/v2/purpur/1.19.3/"),
    PURPUR_1_19_4("purpur-1.19.4", "https://api.purpurmc.org/v2/purpur/1.19.4/"),
    PURPUR_1_20_1("purpur-1.20.1", "https://api.purpurmc.org/v2/purpur/1.20.1/");

    private final String name;
    private final String url;

    ServerVersions(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @SneakyThrows
    public static String getLatestVersion(ServerVersions serverVersion) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(serverVersion.getUrl()).openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();

        String line = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuilder response = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            response.append(line);
        }
        bufferedReader.close();

        if (serverVersion.getName().startsWith("purpur-")) {
            JsonArray jsonArray = new JsonParser().parse(response.toString()).getAsJsonObject().get("builds").getAsJsonObject().get("all").getAsJsonArray();
            int latestBuild = jsonArray.get(jsonArray.size() - 1).getAsInt();
            return serverVersion.getUrl() + latestBuild + "/download";
        } else {
            JsonArray jsonArray = new JsonParser().parse(response.toString()).getAsJsonObject().get("builds").getAsJsonArray();
            int latestBuild = jsonArray.get(jsonArray.size() - 1).getAsInt();
            return serverVersion.getUrl() + "builds/" + latestBuild + "/downloads/" + serverVersion.getName() + "-" + latestBuild + ".jar";
        }
    }
}