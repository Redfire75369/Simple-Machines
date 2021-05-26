package mods.redfire.simplemachinery.util.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class MachineItemSlot implements IItemHandlerModifiable {
    protected Predicate<ItemStack> validator;

    @Nonnull
    protected ItemStack item = ItemStack.EMPTY;
    protected int capacity;

    public MachineItemSlot() {
        this(e -> true);
    }

    public MachineItemSlot(int capacity) {
        this(capacity, e -> true);
    }

    public MachineItemSlot(Predicate<ItemStack> validator) {
        this(0, validator);
    }

    public MachineItemSlot(int capacity, Predicate<ItemStack> validator) {
        this.capacity = capacity;
        this.validator = validator;
    }

    public MachineItemSlot setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }
    public MachineItemSlot setValidator(Predicate<ItemStack> validator) {
        if (validator != null) {
            this.validator = validator;
        }
        return this;
    }

    public boolean isItemValid(@Nonnull ItemStack stack) {
        return validator.test(stack);
    }

    public void consume(int amount) {
        item.setCount(item.getCount() - amount);
    }

    public void setItemStack(@Nonnull ItemStack item) {
        this.item = item;
    }

    public MachineItemSlot read(CompoundNBT tag) {
        item.setTag(tag);
        return this;
    }

    public CompoundNBT write(CompoundNBT tag) {
        tag.merge(item.getOrCreateTag());
        return tag;
    }

    public ItemStack getItemStack() {
        return item;
    }

    public int getCount() {
        return item.getCount();
    }

    public boolean isEmpty() {
        return item.isEmpty();
    }


    public int getSlots() {
        return 1;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return item;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        setItemStack(stack);
    }


    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        if (!isItemValid(stack)) {
            return stack;
        }

        if (item.isEmpty()) {
            if (!simulate) {
                setItemStack(stack);
            }
            return ItemStack.EMPTY;
        } else if (ItemStack.matches(item, stack) && ItemStack.tagMatches(item, stack)) {
            int totalCount = item.getCount() + stack.getCount();
            int limit = getSlotLimit(0);
            if (totalCount <= limit) {
                if (!simulate) {
                    item.setCount(totalCount);
                }
                return ItemStack.EMPTY;
            }
            if (!simulate) {
                item.setCount(limit);
            }
            return new ItemStack(stack.getItem(), totalCount - limit);
        }
        return stack;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {

        if (amount <= 0 || item.isEmpty()) {
            return ItemStack.EMPTY;
        }
        int retCount = Math.min(item.getCount(), amount);
        ItemStack ret = new ItemStack(item.getItem(), retCount);
        if (!simulate) {
            item.shrink(retCount);
            if (item.isEmpty()) {
                setItemStack(ItemStack.EMPTY);
            }
        }
        return ret;
    }

    @Override
    public int getSlotLimit(int slot) {
        return capacity <= 0 ? item.getMaxStackSize() : capacity;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return isItemValid(stack);
    }

    public boolean clear() {
        if (isEmpty()) {
            return false;
        }
        this.item = ItemStack.EMPTY;
        return true;
    }

    public void modify(int quantity) {
        this.item.setCount(Math.min(item.getCount() + quantity, getCapacity()));
        if (this.item.isEmpty()) {
            this.item = ItemStack.EMPTY;
        }
    }

    public int getCapacity() {
        return getSlotLimit(0);
    }

    public int getStored() {
        return item.getCount();
    }
}