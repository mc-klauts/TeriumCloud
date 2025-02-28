package cloud.terium.cloudsystem.cluster.config;

import com.google.gson.JsonObject;

public record CloudConfig(String name, String key, String ip, int port, int memory, String serviceAddress, String promt,
                          String splitter, boolean checkUpdate, boolean debugMode, JsonObject nodes) {
}
