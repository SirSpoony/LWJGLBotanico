package me.spoony.botanico.client;

import me.spoony.botanico.server.net.ServerNetworkManager;

/**
 * Created by Colten on 12/28/2016.
 */
public class ClientConnectionConfig {
    public String playerName;
    public String serverAddress;
    public int port;

    public ClientConnectionConfig(String playerName, String serverAddress, int port) {
        this.playerName = playerName;
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public ClientConnectionConfig(String playerName, String serverAddress) {
        this.playerName = playerName;
        this.serverAddress = serverAddress;
        this.port = ServerNetworkManager.DEFAULT_PORT;
    }
}
