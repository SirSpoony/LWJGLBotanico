package me.spoony.botanico;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.crafting.Recipes;
import me.spoony.botanico.common.items.Item;
import me.spoony.botanico.common.net.Packets;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.server.net.BotanicoServer;

import java.util.Scanner;

/**
 * Created by Colten on 12/27/2016.
 */
public class StartServer {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Initialize Registries");
        Packets.init();
        Tile.initRegistry();
        Building.initRegistry();
        Item.initRegistry();
        Recipes.init();

        BotanicoServer server = new BotanicoServer();
        server.run();

        System.out.println("Listening for commands");

        Scanner scanner = new Scanner(System.in);
        do {
            String command = scanner.nextLine();
            if (!BotanicoServer.RUNNING) {
                break;
            }
        }
        while (scanner.hasNext());

        System.out.println("Stopped listening for commands");
    }
}
