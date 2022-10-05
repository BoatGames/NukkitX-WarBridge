package org.sobadfish.warbridge.item.button;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import org.sobadfish.warbridge.WarBridgeMain;

import java.util.ArrayList;

/**
 * @author SoBadFish
 * 2022/1/3
 */
public class RoomQuitItem {


    public static ArrayList<Player> clickAgain = new ArrayList<>();


    private final ArrayList<Player> clickAgainTeam = new ArrayList<>();

    public static int getIndex(){
        return 8;
    }

    public static Item get(){
        Item item = Item.get(437);
        item.addEnchantment(Enchantment.get(9));
        CompoundTag tag = item.getNamedTag();
        tag.putString(WarBridgeMain.GAME_NAME,"quitItem");
        item.setNamedTag(tag);
        item.setCustomName(TextFormat.colorize('&',"&r&l&e返回大厅 &r&7[再次手持]"));
        return item;

    }
}
