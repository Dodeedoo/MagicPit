package me.dodeedoo.magicpit.skills;

import org.bukkit.boss.BarColor;

public class SkillIndicator {

    public enum indicatorType {
        BOSSBAR,
        MESSAGE,
        SCOREBOARD,
    }

    public String indicatorString;
    public Integer[] progress;
    public Integer time;
    public indicatorType type;
    public BarColor barColor;

    public SkillIndicator() {

    }

    public void setIndicatorString(String indicatorString) {
        this.indicatorString = indicatorString;
    }

    public void setProgress(Integer[] progress) {
        this.progress = progress;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void setType(indicatorType type) {
        this.type = type;
    }

    public void setBarColor(BarColor color) {
        this.barColor = color;
    }

}
