package cloud.terium.cloudsystem.utils.logger;

import cloud.terium.cloudsystem.TeriumCloud;
import cloud.terium.teriumapi.console.LogType;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Logger {

    private static final List<String> savedLogs = new ArrayList<>();

    public static void log(String message, LogType logType) {
        if (TeriumCloud.getTerium().getCloudUtils().isInScreen() && !logType.equals(LogType.SCREEN)) {
            savedLogs.add(LoggerColors.replaceColorCodes(("§f[" + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()) + "§f] " + logType.getPrefix() + message)));
            return;
        }

        if (TeriumCloud.getTerium().getConsoleManager() != null)
            TeriumCloud.getTerium().getConsoleManager().getLineReader().printAbove(LoggerColors.replaceColorCodes(("§f[" + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()) + "§f] " + logType.getPrefix() + message)));
        else
            System.out.println(LoggerColors.replaceColorCodes(("§f[" + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()) + "§f] " + logType.getPrefix() + message)));
    }

    public static void log(String message) {
        if (TeriumCloud.getTerium().getConsoleManager() != null)
            TeriumCloud.getTerium().getConsoleManager().getLineReader().printAbove(LoggerColors.replaceColorCodes(message));
        else
            System.out.println(LoggerColors.replaceColorCodes(message));
    }

    @SneakyThrows
    public static void licenseLog(String message, LogType logType) {
        System.out.println(LoggerColors.replaceColorCodes(("§f[" + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()) + "§f] " + logType.getPrefix() + message)));
    }

    public static void logAllCachedLogs() {
        savedLogs.forEach(log -> {
            if (TeriumCloud.getTerium().getConsoleManager() != null)
                TeriumCloud.getTerium().getConsoleManager().getLineReader().printAbove(log);
            else System.out.println(log);
        });
        savedLogs.clear();
    }
}