package cloud.terium.cloudsystem.cluster.console.commands;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.cloudsystem.cluster.utils.Logger;
import cloud.terium.teriumapi.console.LogType;
import cloud.terium.teriumapi.console.command.Command;

public class ListCommand extends Command {

    public ListCommand() {
        super("list", "List of all services");
    }

    @Override
    public void execute(String[] args) {
        Logger.log("All active cloud services:", LogType.INFO);
        Logger.log("", LogType.INFO);
        ClusterStartup.getCluster().getServiceGroupProvider().getAllServiceGroups().forEach(group -> {
            Logger.log("Services from group '§b" + group.getGroupName() + "§f':", LogType.INFO);
            ClusterStartup.getCluster().getServiceProvider().getServicesByGroupName(group.getGroupName()).forEach(service ->
                    Logger.log(" §7● §fName: " + service.getServiceName() + " | State: " + service.getServiceState() + " | Players: " + service.getOnlinePlayers() + "/" + service.getMaxPlayers(), LogType.INFO));

            Logger.log("", LogType.INFO);
        });
    }
}
