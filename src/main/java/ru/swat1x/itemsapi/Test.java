package ru.swat1x.itemsapi;

import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_16_R3.ParticleParam;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftParticle;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import ru.swat1x.itemsapi.api.CustomItem;
import ru.swat1x.itemsapi.api.event.UseItemEvent;
import ru.swat1x.itemsapi.api.value.UseType;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static class Command implements CommandExecutor {
        @Override
        public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
            Player player = (Player) sender;

            CustomItem item1 = CustomItem.createItem(Material.BLAZE_ROD, "BlindingStaff");
            CustomItem item2 = CustomItem.createItem(Material.ENCHANTED_BOOK, "GrimoireOfFire");

            player.getInventory().addItem(item1.getClearItem());
            player.getInventory().addItem(item2.getClearItem());
            return false;
        }
    }

    public static class Handler implements Listener {
        @EventHandler
        public void itemUse(UseItemEvent e) {

            if (e.getUseType() == UseType.HB_SELECT) {
                if(e.getItem().getCustomName().equalsIgnoreCase("GrimoireOfFire")){
                    e.getPlayer().sendMessage("Вы взяли в руки книгу");
                    BookAnimation.addPlayer(e.getPlayer());
                }
            }
            if (e.getUseType() == UseType.HB_UNSELECT) {
                if(e.getItem().getCustomName().equalsIgnoreCase("GrimoireOfFire")){
                    e.getPlayer().sendMessage("Вы убрали книгу из рук");
                    BookAnimation.removePlayer(e.getPlayer());
                }
            }

            if (e.getUseType() == UseType.RIGHT_MAIN_CLICK) {
                if (e.getItem().getCustomName().equalsIgnoreCase("BlindingStaff")) {
                    e.getPlayer().sendMessage("Вы использовали ослепляющий посох");

                    for (Entity ent : e.getPlayer().getNearbyEntities(3, 3, 3)) {

                        if (ent instanceof Player) {
                            if (e.getPlayer().hasLineOfSight(ent)) {
                                Player target = (Player) ent;
                                e.getPlayer().sendMessage("Вы ослепили " + target.getName());
                                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 1));
                                return;
                            }
                        }

                    }

                    e.getPlayer().sendMessage("Упс... Для начала наведитесь на кого-нибудь");

                }
            }
        }

    }

    public static class BookAnimation extends BukkitRunnable {
        private static final List<Player> players = new ArrayList<>();

        public static void addPlayer(Player player) {
            players.add(player);
        }

        public static void removePlayer(Player player) {
            players.remove(player);
        }

        @Override
        public void run() {
            conus();
        }

        private double i = 0;
//        private double y = 0;
//        private boolean up = true;

        private final double radius = 1.1;

        private void conus() {
            if (i < 4 * Math.PI) {
                i += 0.2;
            } else {
                i = 0;
            }

//            if(y > 1.5){
//                if(up){
//                    up = false;
//                }
//            }
//            else{
//                if(y <= 0){
//                    if(!up){
//                        up=true;
//                    }
//                }
//            }

//            if(up){
//                y = y+0.1;
//            }
//            else{
//                y = y-0.1;
//            }

            for (Player player : players) {
                Location loc = player.getLocation();
                loc = loc.add(new Vector(0, 0.5, 0));
//                for(double i = 0; i < 2 * Math.PI; i+=0.2){
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                float newX = (float) Math.cos(i * radius);
                float newY = (float) Math.sin(radius);
                float newZ = (float) Math.sin(i * radius);

                Location newLocation = loc.clone();

                newLocation.setX(newLocation.getX() + newX);
                newLocation.setY(newLocation.getY() + newY);
                newLocation.setZ(newLocation.getZ() + newZ);

//                p.spawnParticle(Particle.FLAME, newLocation, 1);

                ParticleParam particleParam = CraftParticle.toNMS(Particle.FLAME);

                PacketPlayOutWorldParticles packet1 = new PacketPlayOutWorldParticles(particleParam, true,
                        newLocation.getX(), newLocation.getY(), newLocation.getZ(), 25, 13, 8, 0, 0);

                PacketPlayOutWorldParticles packet2 = new PacketPlayOutWorldParticles(particleParam, true,
                        newLocation.getX(), newLocation.getY()-0.5, newLocation.getZ(), 25, 13, 8, 0, 0);

                PacketPlayOutWorldParticles packet3 = new PacketPlayOutWorldParticles(particleParam, true,
                        newLocation.getX(), newLocation.getY()+0.5, newLocation.getZ(), 25, 13, 8, 0, 0);

                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet1);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet2);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet3);

                for(Entity e : player.getNearbyEntities(2, 2, 2)){
                    e.setFireTicks(20*2);
                }

            }
//            }

        }
    }


}
