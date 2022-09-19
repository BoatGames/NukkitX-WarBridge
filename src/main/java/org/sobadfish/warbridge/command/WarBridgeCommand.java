package org.sobadfish.warbridge.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.utils.TextFormat;
import org.sobadfish.warbridge.WarBridgeMain;
import org.sobadfish.warbridge.manager.RandomJoinManager;
import org.sobadfish.warbridge.panel.DisPlayWindowsFrom;
import org.sobadfish.warbridge.panel.from.GameFrom;
import org.sobadfish.warbridge.panel.from.button.BaseIButtom;
import org.sobadfish.warbridge.player.PlayerInfo;
import org.sobadfish.warbridge.room.GameRoom;
import org.sobadfish.warbridge.room.WorldRoom;
import org.sobadfish.warbridge.room.config.GameRoomConfig;

/**
 * @author SoBadFish
 * 2022/1/12
 */
public class WarBridgeCommand extends Command {

    public WarBridgeCommand(String name) {
        super(name,"战桥游戏房间");
    }


    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof Player) {
            if(strings.length == 0) {
                PlayerInfo info = new PlayerInfo((Player)commandSender);
                PlayerInfo i = WarBridgeMain.getRoomManager().getPlayerInfo((Player) commandSender);
                if(i != null){
                    info = i;
                }
                GameFrom simple = new GameFrom("§c战§9桥",
                        "《战桥》是一款决斗的小游戏玩法。\n" +
                        "为了防止敌人的入侵我们要握好手中的剑,\n" +
                        "与敌人展开殊死搏斗,跟队友配合,一起来防御敌人。\n" +
                        "踏着队友铺好的嶙峋小路,迈向敌人的基地,来获取胜利。\n" +
                        "趁着敌人疏忽防守,凭借高超的跑搭和侧搭技巧,\n" +
                        "向敌人发起最后一击,哪怕是付出生命代价。", DisPlayWindowsFrom.getId(51530, 99810));
                PlayerInfo finalInfo = info;
                /*simple.add(new BaseIButtom(new ElementButton("随机匹配",new ElementButtonImageData("path","textures/ui/dressing_room_skins"))) {*/
                simple.add(new BaseIButtom(new ElementButton("随机匹配")) {
                    @Override
                    public void onClick(Player player) {
                        RandomJoinManager.joinManager.join(finalInfo,null);
                    }
                });
                for (String wname : WarBridgeMain.getMenuRoomManager().getNames()) {
                    WorldRoom worldRoom = WarBridgeMain.getMenuRoomManager().getRoom(wname);
                    int size = 0;
                    for (GameRoomConfig roomConfig : worldRoom.getRoomConfigs()) {
                        GameRoom room = WarBridgeMain.getRoomManager().getRoom(roomConfig.name);
                        if (room != null) {
                            size += room.getPlayerInfos().size();
                        }
                    }
                    /*simple.add(new BaseIButtom(new ElementButton(TextFormat.colorize('&', wname + " &2" + size + " &r位玩家正在游玩\n&r房间数量: &a" + worldRoom.getRoomConfigs().size()), worldRoom.getImageData())) {*/
                    simple.add(new BaseIButtom(new ElementButton(TextFormat.colorize('&', "&c战&9桥 &r- &l&5"+wname+" \n&r\uE105 "+size))) {
                        @Override
                        public void onClick(Player player) {
                            disPlayRoomsFrom(player, wname);
                        }
                    });
                }
                simple.disPlay((Player) commandSender);
                DisPlayWindowsFrom.FROM.put(commandSender.getName(), simple);
            }else{
                PlayerInfo playerInfo = new PlayerInfo((Player) commandSender);
                PlayerInfo info = WarBridgeMain.getRoomManager().getPlayerInfo((Player) commandSender);
                if(info != null){
                    playerInfo = info;
                }
                switch (strings[0]){
                    case "quit":
                        PlayerInfo player = WarBridgeMain.getRoomManager().getPlayerInfo((Player) commandSender);
                        if (player != null) {
                            GameRoom room = player.getGameRoom();
                            if (room.quitPlayerInfo(player,true)) {
                                playerInfo.sendForceMessage("&a你成功离开房间: &r" + room.getRoomConfig().getName());

                                room.getRoomConfig().quitRoomCommand.forEach(cmd-> Server.getInstance().dispatchCommand(commandSender,cmd));
                            }
                        }
                        break;
                    case "join":
                        if (strings.length > 1) {
                            String name = strings[1];
                            if (WarBridgeMain.getRoomManager().joinRoom(playerInfo, name)) {
                                playerInfo.sendForceMessage("&a成功加入房间: &r"+name);
                            }
                        } else {
                            playerInfo.sendForceMessage("&c请输入房间名");
                        }
                        break;
                    case "rjoin":
                    String name = null;
                        if(commandSender.isPlayer()){
                            if(strings.length > 1){
                                name = strings[1];
                            }
                            if(name != null){
                                if("".equals(name.trim())){
                                    name = null;
                                }
                            }

                            info = new PlayerInfo((Player)commandSender);
                            PlayerInfo i = WarBridgeMain.getRoomManager().getPlayerInfo((Player) commandSender);
                            if(i != null){
                                info = i;
                            }
                            String finalName = name;
                            RandomJoinManager.joinManager.join(info,finalName);

                        }else{
                            commandSender.sendMessage("请在控制台执行");
                        }

                        break;
                        default:break;
                }
            }
        }else{
            commandSender.sendMessage("请不要在控制台执行");
            return false;
        }
        return true;
    }
    private void disPlayRoomsFrom(Player player, String name){
        DisPlayWindowsFrom.FROM.remove(player.getName());
        //GameFrom simple = new GameFrom(WarBridgeMain.getTitle(), "请选择房间",DisPlayWindowsFrom.getId(51530,99810));
        GameFrom simple = new GameFrom("§c战§9桥§r 的房间列表", "请选择房间点击进入：\n§e注意：由于房间人数及状态变动极快,部分房间可能已开始游戏,你将以观战者模式进入",DisPlayWindowsFrom.getId(51530,99810));
        WorldRoom worldRoom = WarBridgeMain.getMenuRoomManager().getRoom(name);
        PlayerInfo info = new PlayerInfo(player);
        //simple.add(new BaseIButtom(new ElementButton("随机匹配",new ElementButtonImageData("path","textures/ui/dressing_room_skins"))) {
        simple.add(new BaseIButtom(new ElementButton("随机匹配")) {
            @Override
            public void onClick(Player player) {
                RandomJoinManager.joinManager.join(info,null);

            }
        });
        for (GameRoomConfig roomConfig: worldRoom.getRoomConfigs()) {
            int size = 0;
            String type = "&l&5等待中 &r&e可加入";
            GameRoom room = WarBridgeMain.getRoomManager().getRoom(roomConfig.name);
            if(room != null){
                size = room.getPlayerInfos().size();
                switch (room.getType()){
                    case START:
                        type = "&l&2游戏中 &r&b可观战";
                        break;
                    case END:
                        type = "&c等待房间结束";
                        break;
                        default:break;
                }
            }

            //simple.add(new BaseIButtom(new ElementButton(TextFormat.colorize('&',roomConfig.name+" &r状态:"+type + "&r\n人数: "+size+" / " + roomConfig.getMaxPlayerSize()), worldRoom.getImageData())) {
            simple.add(new BaseIButtom(new ElementButton(TextFormat.colorize('&',type + "\n&r&4玩家数: "+size+"/" + roomConfig.getMaxPlayerSize()+"  &r&1地图： "+ roomConfig.name))) {
                @Override
                public void onClick(Player player) {
                    PlayerInfo playerInfo = new PlayerInfo(player);
                    if (!WarBridgeMain.getRoomManager().joinRoom(info,roomConfig.name)) {
                        playerInfo.sendForceMessage("&c无法加入房间");
                    }else{
                        playerInfo.sendForceMessage("&a你已加入 "+roomConfig.getName()+" 房间");
                    }
//                    if (BedWarMain.getRoomManager().hasRoom(roomConfig.name)) {
                    DisPlayWindowsFrom.FROM.remove(player.getName());

                }
            });
        }
        simple.disPlay(player);
        DisPlayWindowsFrom.FROM.put(player.getName(),simple);
    }



}
