package ru.swat1x.itemsapi.api;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem {

    private final ItemStack itemStack;
    private final String customName;

    public CustomItem(ItemStack itemStack){
        this.itemStack = itemStack;
        customName = getCustomName();
    }

    public String getCustomName(){
        if(customName != null){
            return customName;
        }
        else{
            if(isValid()){
                NBTItem nbtItem = new NBTItem(getClearItem());
                return nbtItem.getString("customName");
            }
        }
        return null;
    }

    public CustomItem setCustomName(String name){
        if(isValid()){
            NBTItem nbtItem = new NBTItem(getClearItem());
            nbtItem.setString("customName", name);
            ItemStack item = nbtItem.getItem();
            return new CustomItem(item);
        }
        return null;
    }

    public ItemStack getClearItem(){
        return itemStack;
    }

    public boolean isValid(){
        return isCustomItem(getClearItem());
    }


    public static CustomItem createItem(Material material, String customName){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("Кастомный предмет - "+customName);
        itemStack.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("customName", customName);
        itemStack = nbtItem.getItem();

        return new CustomItem(itemStack);
    }

    public static boolean isCustomItem(ItemStack itemStack){
        if(itemStack == null){
            return false;
        }
        NBTItem nbtItem = new NBTItem(itemStack);
        return !nbtItem.getString("customName").equalsIgnoreCase("");
    }

}
