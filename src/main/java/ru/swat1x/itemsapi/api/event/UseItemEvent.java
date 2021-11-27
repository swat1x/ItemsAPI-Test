package ru.swat1x.itemsapi.api.event;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import ru.swat1x.itemsapi.api.CustomItem;
import ru.swat1x.itemsapi.api.value.UseType;

@Value
@EqualsAndHashCode(callSuper = true)
public class UseItemEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Player player;
    UseType useType;
    CustomItem item;

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
