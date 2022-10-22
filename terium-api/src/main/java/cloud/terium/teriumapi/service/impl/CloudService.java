package cloud.terium.teriumapi.service.impl;

import cloud.terium.networking.packets.PacketPlayOutServiceMemoryUpdatePacket;
import cloud.terium.networking.packets.PacketPlayOutUpdateService;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.teriumapi.service.CloudServiceState;
import cloud.terium.teriumapi.service.ICloudService;
import cloud.terium.teriumapi.service.group.ICloudServiceGroup;

public class CloudService implements ICloudService {

    private final String serviceName;
    private final int serviceId;
    private final int port;
    private final ICloudServiceGroup iCloudServiceGroup;
    private int onlinePlayers;
    private long usedMemory;
    private boolean locked;
    private CloudServiceState serviceState;

    public CloudService(String serviceName, int serviceId, int port, ICloudServiceGroup iCloudServiceGroup) {
        this(serviceName, serviceId, port, iCloudServiceGroup, false);
    }

    public CloudService(String serviceName, int serviceId, int port, ICloudServiceGroup iCloudServiceGroup, boolean online) {
        this.serviceName = serviceName;
        this.serviceId = serviceId;
        this.port = port;
        this.iCloudServiceGroup = iCloudServiceGroup;
        this.onlinePlayers = 0;
        this.usedMemory = 0;
        this.serviceState = online ? CloudServiceState.ONLINE : CloudServiceState.PREPARING;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public int getServiceId() {
        return serviceId;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    @Override
    public long getUsedMemory() {
        return usedMemory;
    }

    @Override
    public void update() {
        TeriumAPI.getTeriumAPI().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutUpdateService(serviceName, locked, serviceState, onlinePlayers, usedMemory));
    }

    @Override
    public ICloudServiceGroup getServiceGroup() {
        return iCloudServiceGroup;
    }

    @Override
    public CloudServiceState getServiceState() {
        return serviceState;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public void setOnlinePlayers(int onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    @Override
    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }

    @Override
    public void setServiceState(CloudServiceState serviceState) {
        this.serviceState = serviceState;
    }
}
