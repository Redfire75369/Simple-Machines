package mods.redfire.simplemachinery.util.inventory;

public interface IMachineInventoryCallback {
    default void onInventoryChange(int slot) {}

    default void onTankChange(int tank) {}

    default boolean clearSlot(int slot) {
        return false;
    }

    default boolean clearTank(int tank) {
        return false;
    }

    default boolean clearEnergy(int coil) {
        return false;
    }
}