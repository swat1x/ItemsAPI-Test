package ru.swat1x.itemsapi.bukkit.handler;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;

import org.bukkit.inventory.ItemStack;
import ru.swat1x.itemsapi.api.CustomItem;
import ru.swat1x.itemsapi.api.value.UseType;
import ru.swat1x.itemsapi.api.event.UseItemEvent;

public class UseListener implements Listener {

    @EventHandler
    public void clickCaller(PlayerInteractEvent e){
        if(e.getItem() == null) return;

        UseType useType = null;

        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
            useType = UseType.LEFT_CLICK;
        }

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){

            if(e.getHand() == EquipmentSlot.HAND) useType = UseType.RIGHT_MAIN_CLICK;

            if(e.getHand() == EquipmentSlot.OFF_HAND) useType = UseType.RIGHT_OFF_CLICK;

        }

        if(useType != null) Bukkit.getPluginManager().callEvent(new UseItemEvent(e.getPlayer(), useType, new CustomItem(e.getItem())));

    }

    @EventHandler
    public void clickCaller(PlayerItemHeldEvent e){
        ItemStack oldItemStack = e.getPlayer().getInventory().getItem(e.getPreviousSlot());
        ItemStack newItemStack = e.getPlayer().getInventory().getItem(e.getNewSlot());

        CustomItem oldItem = new CustomItem(oldItemStack);
        CustomItem newItem = new CustomItem(newItemStack);

        if(oldItem.isValid()){
            Bukkit.getPluginManager().callEvent(new UseItemEvent(e.getPlayer(), UseType.HB_UNSELECT, oldItem));
        }
        if(newItem.isValid()){
            Bukkit.getPluginManager().callEvent(new UseItemEvent(e.getPlayer(), UseType.HB_SELECT, newItem));
        }

    }




}
