package me.dodeedoo.magicpit.events.magicdamage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class MagicDamageHandle implements Listener {

    @EventHandler
    public void magicdamage(MagicDamage event) {
        event.victim.damage(event.value);
        if (event.victim.isDead()) return;
        switch (event.type) {
            case ICE: {
                int amp;
                int dur;
                if (event.value > 300) {
                    amp = 3;
                    dur = 30;
                }else{
                    amp = event.value / 100;
                    dur = event.value / 10;
                }
                event.victim.addPotionEffect(PotionEffectType.SLOW.createEffect(dur, amp));
                return;
            }
            case FIRE: {

            }
        }
    }
}
