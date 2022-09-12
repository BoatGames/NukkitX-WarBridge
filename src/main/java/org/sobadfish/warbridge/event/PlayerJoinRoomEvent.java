package org.sobadfish.warbridge.event;

import cn.nukkit.event.Cancellable;
import cn.nukkit.plugin.Plugin;
import org.sobadfish.warbridge.player.PlayerInfo;
import org.sobadfish.warbridge.room.GameRoom;


/**
 * @author SoBadFish
 * 2022/1/15
 */
public class PlayerJoinRoomEvent extends PlayerRoomInfoEvent implements Cancellable {

    private boolean send;

    public PlayerJoinRoomEvent(PlayerInfo playerInfo, GameRoom room, Plugin plugin) {
        super(playerInfo, room, plugin);
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
}
