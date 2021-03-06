package net.quickwrite.miniminigames.map;

import com.google.common.collect.ImmutableMap;
import net.quickwrite.miniminigames.builder.items.ItemBuilder;
import net.quickwrite.miniminigames.display.HorizontalDisplay;
import net.quickwrite.miniminigames.display.VerticalDisplay;
import net.quickwrite.miniminigames.ships.Ship;
import net.quickwrite.miniminigames.ships.ShipManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map implements ConfigurationSerializable {

    private final ItemStack displayItem;
    private final MapSide attacker, defender;
    private Player player1, player2;
    private final String name;
    private final java.util.Map<Ship, Integer> ships;


    public Map(VerticalDisplay attackerVerticalDisplay, VerticalDisplay defenderVerticalDisplay, HorizontalDisplay attackerHorizontalDisplay,
               HorizontalDisplay defenderHorizontalDisplay, Material displayItem, String name, Location attackerSpawnLocation, Location defenderSpawnLocation, java.util.Map<Ship, Integer> ships){
        this.attacker = new MapSide(attackerVerticalDisplay, attackerHorizontalDisplay, attackerSpawnLocation);
        this.defender = new MapSide(defenderVerticalDisplay, defenderHorizontalDisplay, defenderSpawnLocation);
        this.displayItem = new ItemBuilder(displayItem).setDisplayName(name).build();
        this.name = name;
        this.ships = ships;

        applyLore(this.displayItem);
    }

    public Map(java.util.Map<String, Object> data){
        attacker = (MapSide) data.get("attacker");
        defender = (MapSide) data.get("defender");
        name = (String) data.get("name");
        displayItem = (ItemStack) data.get("displayItem");

        HashMap<String, Integer> savedShips = (HashMap<String, Integer>) data.get("ships");
        this.ships = new HashMap<>();
        for(java.util.Map.Entry<String, Integer> entry : savedShips.entrySet()){
            ships.put(ShipManager.getShipWithName(entry.getKey()), entry.getValue());
        }


        applyLore(displayItem);
    }

    public void addPlayer(Player player){
        if(player1 == null) {
            player1 = player;
            attacker.addPlayingPlayer(player);
        }else if(player2 == null){
            player2 = player;
            defender.addPlayingPlayer(player);
        }
    }

    public void teleportToSpawn(){
        attacker.teleportToSpawn();
        defender.teleportToSpawn();
    }

    private void applyLore(ItemStack stack){
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.stripColor(meta.getDisplayName()));
        List<String> lore = new ArrayList<>();
        lore.add("??r??7Map: ??6" + name);
        lore.add("??r??7Size: ??6" + attacker.getThisPlayerDisplay().getWidth() + "x" + attacker.getThisPlayerDisplay().getHeight());
        lore.add("??r??7Ships:");
        for(java.util.Map.Entry<Ship, Integer> entry : ships.entrySet()){
            lore.add("??r??6 - " + entry.getKey().getName() + "??6(" + entry.getKey().getSize() + ") x " + entry.getValue());
        }

        meta.setLore(lore);
        stack.setItemMeta(meta);
    }

    public static boolean isValid(VerticalDisplay attackerVerticalDisplay, VerticalDisplay defenderVerticalDisplay, HorizontalDisplay attackerHorizontalDisplay,
                                  HorizontalDisplay defenderHorizontalDisplay){
        return (attackerHorizontalDisplay.getWidth() == defenderHorizontalDisplay.getWidth())
                && (attackerVerticalDisplay.getWidth() == defenderVerticalDisplay.getWidth())
                && (defenderHorizontalDisplay.getWidth() == defenderVerticalDisplay.getWidth())
                &&
                (attackerHorizontalDisplay.getHeight() == defenderHorizontalDisplay.getHeight())
                && (attackerVerticalDisplay.getHeight() == defenderVerticalDisplay.getHeight())
                && (defenderHorizontalDisplay.getHeight() == defenderVerticalDisplay.getHeight());
    }

    @Override
    public java.util.Map<String, Object> serialize() {

        HashMap<String, Integer> saveShips = new HashMap<>();
        for(java.util.Map.Entry<Ship, Integer> entry : ships.entrySet()){
            saveShips.put(entry.getKey().getName(), entry.getValue());
        }

        return new ImmutableMap.Builder<String, Object>()
                .put("attacker", attacker)
                .put("defender", defender)
                .put("name", name)
                .put("displayItem", displayItem)
                .put("ships", saveShips)
                .build();
    }

    public Player getPlayer1() {
        return player1;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public Player getPlayer2() {
        return player2;
    }

    public String getName() {
        return name;
    }

    public void displayAll() {
        attacker.display();
        defender.display();
    }

    public java.util.Map<Ship, Integer> getShips() {
        return ships;
    }

    public MapSide getAttacker() {
        return attacker;
    }

    public MapSide getDefender() {
        return defender;
    }
}
