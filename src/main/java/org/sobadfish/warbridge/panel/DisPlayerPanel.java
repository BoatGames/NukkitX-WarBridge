package org.sobadfish.warbridge.panel;

import cn.nukkit.entity.Entity;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryHolder;
import org.sobadfish.warbridge.panel.items.BasePlayPanelItemInstance;
import org.sobadfish.warbridge.panel.lib.AbstractFakeInventory;
import org.sobadfish.warbridge.player.PlayerInfo;

import java.util.Map;

/**
 * @author SoBadFish
 * 2022/1/2
 */
public class DisPlayerPanel implements InventoryHolder {

    private AbstractFakeInventory inventory;





    public void displayPlayer(PlayerInfo player, Map<Integer, BasePlayPanelItemInstance> itemMap, String name){
        ChestInventoryPanel panel = new ChestInventoryPanel(player,this,name);
        panel.setPanel(itemMap);
        panel.id = ++Entity.entityCount;
        inventory = panel;
        panel.getPlayer().addWindow(panel);

    }



    @Override
    public Inventory getInventory() {
        return inventory;
    }


}
