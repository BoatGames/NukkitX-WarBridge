package org.sobadfish.warbridge.player.team.config;


import cn.nukkit.item.Item;
import cn.nukkit.utils.BlockColor;

import java.util.Map;

/**
 * @author SoBadFish
 * 2022/1/2
 */

public class TeamConfig {

    private String name;

    private String nameColor;

    /**团队的图标*/
    private String nameUnicode;

    private Item blockWoolColor;

    private BlockColor rgb;

    private TeamConfig(String name, String nameColor,String nameUnicode, Item blockWoolColor, BlockColor rgb){
        this.name = name;
        this.nameColor = nameColor;
        this.nameUnicode = nameUnicode;
        this.blockWoolColor = blockWoolColor;
        this.rgb = rgb;
    }

    public String getName() {
        return name;
    }

    public Item getBlockWoolColor() {
        return blockWoolColor;
    }

    public BlockColor getRgb() {
        return rgb;
    }

    public String getNameColor() {
        return nameColor;
    }

    public String getNameUnicode(){
        return nameUnicode;
    }

    public static TeamConfig getInstance(Map<?,?> map){
        String name = map.get("name").toString();
        String nameColor = map.get("nameColor").toString();
        String nameUnicode = map.get("nameUnicode").toString();
        Map<?,?> m = (Map<?,?>) map.get("rgb");
        int r = Integer.parseInt(m.get("r").toString());
        int g = Integer.parseInt(m.get("g").toString());
        int b = Integer.parseInt(m.get("b").toString());
        return new TeamConfig(name,nameColor,nameUnicode, Item.fromString(map.get("blockWoolColor")
                .toString()),new BlockColor(r,g,b));
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof TeamConfig){
            return ((TeamConfig) obj).getName().equalsIgnoreCase(getName());
        }
        return false;
    }

}
