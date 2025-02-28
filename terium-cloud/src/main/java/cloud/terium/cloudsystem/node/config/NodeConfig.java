package cloud.terium.cloudsystem.node.config;

import com.google.gson.JsonObject;

public record NodeConfig(String name, String ip, int port, int memory, String serviceAddress, String promt,
                         String splitter, boolean checkUpdate, boolean debugMode, JsonObject master) {
}