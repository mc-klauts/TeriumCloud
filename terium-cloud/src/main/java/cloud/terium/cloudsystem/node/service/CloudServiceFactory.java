package cloud.terium.cloudsystem.node.service;

import cloud.terium.networking.packet.service.PacketPlayOutCreateService;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.teriumapi.service.ICloudServiceFactory;
import cloud.terium.teriumapi.service.group.ICloudServiceGroup;
import cloud.terium.teriumapi.template.ITemplate;

import java.util.HashMap;
import java.util.List;

public class CloudServiceFactory implements ICloudServiceFactory {

    @Override
    public void createService(ICloudServiceGroup serviceGroup) {
        TeriumAPI.getTeriumAPI().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateService(serviceGroup.getGroupName(), -1, serviceGroup.hasPort() ? serviceGroup.getPort() : -1, serviceGroup.getMaxPlayers(), serviceGroup.getMemory(), serviceGroup.getGroupNode().getName(), serviceGroup.getGroupName(), serviceGroup.getTemplates().stream().map(ITemplate::getName).toList(), new HashMap<>()));
    }

    @Override
    public void createService(ICloudServiceGroup serviceGroup, List<ITemplate> templates) {
        TeriumAPI.getTeriumAPI().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateService(serviceGroup.getGroupName(), -1, serviceGroup.hasPort() ? serviceGroup.getPort() : -1, serviceGroup.getMaxPlayers(), serviceGroup.getMemory(), serviceGroup.getGroupNode().getName(), serviceGroup.getGroupName(), templates.stream().map(ITemplate::getName).toList(), new HashMap<>()));
    }

    @Override
    public void createService(String serviceName, ICloudServiceGroup serviceGroup, List<ITemplate> templates, int serviceId, int maxPlayers, int memory) {
        TeriumAPI.getTeriumAPI().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateService(serviceName, serviceId, serviceGroup.hasPort() ? serviceGroup.getPort() : -1, maxPlayers, memory, serviceGroup.getGroupNode().getName(), serviceGroup.getGroupName(), templates.stream().map(ITemplate::getName).toList(), new HashMap<>()));
    }

    @Override
    public void createService(String serviceName, ICloudServiceGroup serviceGroup) {
        TeriumAPI.getTeriumAPI().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateService(serviceName, -1, serviceGroup.hasPort() ? serviceGroup.getPort() : -1, serviceGroup.getMaxPlayers(), serviceGroup.getMemory(), serviceGroup.getGroupNode().getName(), serviceGroup.getGroupName(), serviceGroup.getTemplates().stream().map(ITemplate::getName).toList(), new HashMap<>()));
    }

    @Override
    public void createService(String serviceName, ICloudServiceGroup serviceGroup, List<ITemplate> templates) {
        TeriumAPI.getTeriumAPI().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateService(serviceName, -1, serviceGroup.hasPort() ? serviceGroup.getPort() : -1, serviceGroup.getMaxPlayers(), serviceGroup.getMemory(), serviceGroup.getGroupNode().getName(), serviceGroup.getGroupName(), templates.stream().map(ITemplate::getName).toList(), new HashMap<>()));
    }
}
