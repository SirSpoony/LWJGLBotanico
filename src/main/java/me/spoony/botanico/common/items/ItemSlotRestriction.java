package me.spoony.botanico.common.items;

import com.google.common.base.Preconditions;

/**
 * Created by Colten on 12/10/2016.
 */
public class ItemSlotRestriction
{
    public static ItemSlotRestriction DEFAULT = new ItemSlotRestriction();

    public static ItemSlotRestriction TOOLS = new ItemSlotRestriction() {
        @Override
        public boolean isValid(ItemStack stack)
        {
            Preconditions.checkNotNull(stack);
            return (stack.getItem() instanceof ItemTool);
        }
    };

    public static ItemSlotRestriction BLADE = new ItemSlotRestriction() {
        @Override
        public boolean isValid(ItemStack stack)
        {
            Preconditions.checkNotNull(stack);
            return (stack.getItem() instanceof ItemTool) && ((ItemTool)stack.getItem()).toolType == ItemToolType.BLADE;
        }
    };

    public static ItemSlotRestriction HOE = new ItemSlotRestriction() {
        @Override
        public boolean isValid(ItemStack stack)
        {
            Preconditions.checkNotNull(stack);
            return (stack.getItem() instanceof ItemTool) && ((ItemTool)stack.getItem()).toolType == ItemToolType.HOE;
        }
    };

    public static ItemSlotRestriction PICKAXE = new ItemSlotRestriction() {
        @Override
        public boolean isValid(ItemStack stack)
        {
            Preconditions.checkNotNull(stack);
            return (stack.getItem() instanceof ItemTool) && ((ItemTool)stack.getItem()).toolType == ItemToolType.PICKAXE;
        }
    };

    public static ItemSlotRestriction AXE = new ItemSlotRestriction() {
        @Override
        public boolean isValid(ItemStack stack)
        {
            Preconditions.checkNotNull(stack);
            return (stack.getItem() instanceof ItemTool) && ((ItemTool)stack.getItem()).toolType == ItemToolType.AXE;
        }
    };

    public static ItemSlotRestriction RING = new ItemSlotRestriction() {
        @Override
        public boolean isValid(ItemStack stack)
        {
            Preconditions.checkNotNull(stack);
            return false;
        }
    };

    public boolean isValid(ItemStack stack) {
        return true;
    }
}
