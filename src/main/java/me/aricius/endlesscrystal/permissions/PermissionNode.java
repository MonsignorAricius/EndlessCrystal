package me.aricius.endlesscrystal.permissions;

public enum PermissionNode {

    GIVE(".give"),
    GIVEALL(".giveall"),
    TAKE(".take"),
    LOOK(".look"),
    PAY(".pay"),
    SET(".set"),
    RESET(".reset"),
    ME(".me");
    private static final String prefix = "Krystaly";
    private String node;
    private PermissionNode(String node) {
        this.node = prefix + node;
    }
    public String getNode() {
        return node;
    }
}
