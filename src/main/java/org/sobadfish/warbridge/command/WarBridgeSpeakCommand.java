package org.sobadfish.warbridge.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import org.sobadfish.warbridge.WarBridgeMain;
import org.sobadfish.warbridge.player.PlayerInfo;


/**
 * @author SoBadFish
 * 2022/1/15
 */
public class WarBridgeSpeakCommand extends Command {

    public WarBridgeSpeakCommand(String name) {
        super(name);
    }


    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof Player){
            PlayerInfo info = WarBridgeMain.getRoomManager().getPlayerInfo((Player) commandSender);
            if(info == null){
                new PlayerInfo((Player)commandSender).sendForceMessage("&c你不在游戏房间内!");
                return false;
            }else{
                if(strings.length > 0){
                    info.getGameRoom().sendFaceMessage("&l&7(全体消息)&r "+info+"&r >> "+strings[0]);

                }else{
                    info.sendForceMessage("&c指令:/bws <你要说的内容> 全体消息");
                }
            }

        }
        return true;
    }
}
