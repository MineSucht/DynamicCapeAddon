package eu.dynamics;

import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;

import java.util.List;

public class Core extends LabyModAddon {
    public boolean isActivated;
    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void loadConfig() {
        this.isActivated = getConfig().has("isActivated") ? getConfig().get("isActivated").getAsBoolean() : false;
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        //Enabled?
        list.add(new BooleanElement("Enable Dynamic Capes", this,new ControlElement.IconData(Material.LEVER), "isActivated",this.isActivated));
    }
}