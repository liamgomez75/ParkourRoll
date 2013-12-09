package com.gmail.liamgomez75.parkourroll.localisation;

/**
 * Enum to represent a single entry in a Localisation.
 * Holds the config key, the usage, and the default value.
 *
 * @author JamesHealey94 <jameshealey1994.gmail.com>
 */
public enum LocalisationEntry {

    /**
     * Message telling player they do not have permission to use a command.
     */
    ERR_PERMISSION_DENIED (
            "MsgPermissionDenied",
            null,
            "&cYou don't have permission to do that!"),

    /**
     * Message telling user the configuration has been reloaded.
     */
    MSG_CONFIG_RELOADED (
            "MsgConfigReloaded",
            null,
            "&7Configuration reloaded."),

    /**
     * Message telling player they have successfully parkour rolled - no damage
     * was taken during the fall.
     */
    MSG_SUCCESSFUL_ROLL (
            "MsgSuccessfulRoll",
            null,
            "&7You have successfully parkour rolled!"),

    /**
     * Message telling player that although they were injured, their parkour
     * roll reduced enough damage to save their life.
     */
    MSG_INJURED_BUT_SUCCESSFUL_ROLL (
            "MsgInjuredButSuccessfulRoll",
            null,
            "&7You are injured but live to tell the tale");

    /**
     * The name of the entry, as found in the localisation file.
     */
    private String name;

    /**
     * The usage for the entry, used as a comment in the localisation file to
     * help the user.
     * An empty string should be ignored when writing to file.
     */
    private String usage;

    /**
     * The default value of the entry, as found in the localisation file.
     */
    private String defaultValue;

    /**
     * Constructor.
     *
     * @param name          name of the entry, as found in the localisation file
     * @param usage         usage for the entry, used as a comment in the
     *                      localisation file to help the user, can be empty
     * @param defaultValue  default value of the entry, as found in the
     *                      localisation file
     */
    LocalisationEntry(String name, String usage, String defaultValue) {
        this.name = name;
        this.usage = usage;
        this.defaultValue = defaultValue;
    }

    /**
     * Returns the name of the LocalistionEntry.
     *
     * @return  name of the LocalistionEntry
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the usage of the LocalistionEntry.
     *
     * @return  usage of the LocalistionEntry, empty string for no usage.
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Returns the default value of the LocalisationEntry.
     *
     * @return  default value of the LocalisationEntry
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String toString() {
        if (getUsage() == null) {
            return "\n" + getName() + ": '" + getDefaultValue().replace("'", "''") + "'\n";
        } else {
            return "\n# " + getUsage() + "\n" + getName() + ": '" + getDefaultValue().replace("'", "''") + "'\n";
        }
    }
}