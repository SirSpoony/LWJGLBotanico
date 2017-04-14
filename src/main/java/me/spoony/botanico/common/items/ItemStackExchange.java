package me.spoony.botanico.common.items;

/**
 * Created by Colten on 11/29/2016.
 */
public class ItemStackExchange
{
    public ItemStack to;
    public ItemStack from;

    public ItemStackExchange(ItemStack to, ItemStack from)
    {
        this.to = ItemStack.clone(to);
        this.from = ItemStack.clone(from);
    }

    public void exchangeHalf() {
        if (from == null) {
            from = ItemStack.split(to);
        } else {
            if (ItemStack.match(to, from) && to.getCount()+1 <= to.getItem().maxStackSize) {
                to.increaseCount(1);
                from.increaseCount(-1);
            } else if (to == null) {
                to = ItemStack.clone(from);
                to.setCount(1);
                from.increaseCount(-1);
            } else {
                exchange();
            }
        }
    }

    public void exchange() {
        if (!merge()) {
            ItemStack temp = ItemStack.clone(to);
            to = ItemStack.clone(from);
            from = ItemStack.clone(temp);
        }
    }

    public boolean merge() {
        if (from == null && to == null) {
            return false; // no merge can take place if there is nothing to take from or merge to
        }

        if (from == null) {
            return false; // no merge can take place if there is nothing to take from
        }

        if (to == null) {
            to = from;
            from = null;
            return true; // add the from to to is easy because both are null
        }

        if (!ItemStack.match(to, from)) {
            return false; // if they don't match there is no way they can be merged
        }

        int maxcount = to.getItem().maxStackSize;
        int tocount = to.getCount();
        int fromcount = from.getCount();

        if (tocount + fromcount > maxcount) {
            to.setCount(maxcount);
            from.setCount(fromcount-(maxcount-tocount));
            return true; // add the count of the from stack to the to stack with respect for maxstacksize
        }

        if (tocount + fromcount <= maxcount) {
            to.setCount(tocount + fromcount);
            from = null;
            return true;// take all of from and add it to to
        }

        return false; // should never reach this code
    }

    public boolean mergeIntoOneStack() {
        ItemStack tempTo = ItemStack.clone(to);
        ItemStack tempFrom = ItemStack.clone(from);

        if (merge() && from == null) {
            return true;
        }

        to = tempTo;
        from = tempFrom;
        return false;
    }
}
