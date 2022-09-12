package org.sobadfish.warbridge.thread;

import cn.nukkit.Server;
import org.sobadfish.warbridge.WarBridgeMain;
import org.sobadfish.warbridge.manager.RoomManager;
import org.sobadfish.warbridge.manager.ThreadManager;
import org.sobadfish.warbridge.player.PlayerInfo;
import org.sobadfish.warbridge.room.GameRoom;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoomLoadRunnable extends ThreadManager.AbstractBedWarRunnable {

    public LinkedHashMap<String,Long> time = new LinkedHashMap<>();

    @Override
    public GameRoom getRoom() {
        return null;
    }

    @Override
    public String getThreadName() {
        String color = "&a";
        if(isClose){
            color = "&7";
        }
        StringBuilder s = new StringBuilder(color+"房间进程 &7(" + WarBridgeMain.getRoomManager().getRooms().size() + ")\n");
        for(Map.Entry<String,Long> room: time.entrySet()){
            s.append("     &r").append(room.getKey()).append("  &a").append(room.getValue()).append(" ms\n");

        }
        return s.toString();
    }

    @Override
    public void run() {
        try {
            if (isClose) {
                ThreadManager.cancel(this);
            }

            if (WarBridgeMain.getWarBridgeMain().isDisabled()) {
                isClose = true;
                return;
            }
            List<GameRoom> gameRooms = new CopyOnWriteArrayList<>(WarBridgeMain.getRoomManager().getRooms().values());
            for (GameRoom room : gameRooms) {
                long t1 = System.currentTimeMillis();
                if (WarBridgeMain.getRoomManager().getRoom(room.getRoomConfig().name) == null) {
                    RoomManager.LOCK_GAME.remove(room.getRoomConfig());
                    WarBridgeMain.getRoomManager().getRooms().remove(room.getRoomConfig().name);
                    continue;
                }

                if (room.close || room.getWorldInfo().getConfig().getGameWorld() == null) {
                    continue;
                }
                room.onUpdate();
                for (PlayerInfo playerInfo : room.getPlayerInfos()) {
                    if (playerInfo.cancel || playerInfo.isLeave) {
                        playerInfo.removeScoreBoard();

                    } else {
                        playerInfo.onUpdate();
                    }

                }

                if (room.loadTime > 0) {
                    room.loadTime--;

                }
                try {
                    if (room.worldInfo != null) {
                        if (!room.worldInfo.isClose()) {
                            //把掉落物啥的扔回主线程
                            Server.getInstance().getScheduler().scheduleTask(WarBridgeMain.getWarBridgeMain(),new WorldInfoMasterThread(room,room.worldInfo,WarBridgeMain.getWarBridgeMain()));
                        }
                    }
                } catch (Exception ignore) {
                }

                time.put(room.getRoomConfig().name, System.currentTimeMillis() - t1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
