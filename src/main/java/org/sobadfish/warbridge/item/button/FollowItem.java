package org.sobadfish.warbridge.item.button;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import org.sobadfish.warbridge.WarBridgeMain;

/**
 * 跟随玩家
 *
 * @author SoBadFish
 * 2022/8/10
 */
public class FollowItem {



    public static int getIndex(){
        return 0;
    }

    public static Item get(){
        Item item = Item.get(345);
        item.addEnchantment(Enchantment.get(9));
        CompoundTag tag = item.getNamedTag();
        tag.putString(WarBridgeMain.GAME_NAME,"follow");
        item.setNamedTag(tag);
        item.setCustomName(TextFormat.colorize('&',"&r&l&b传送玩家 &r&7[再次手持]"));
        return item;

    }
}
