package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BehindYouBookEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Book Behind You";
    }

    @Override
    public int getWeight() {
        return 15;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        CompoundTag tag = book.getOrCreateTag();
        tag.putString("title", "...");
        tag.putString("author", "???");
        ListTag pages = new ListTag();
        pages.add(StringTag.valueOf("{\"text\":\"§4§lBehind you...\"}"));
        tag.put("pages", pages);
        if (InventoryHelper.tryAddItem(player, book, "Mysterious Book")) {
            player.sendSystemMessage(Component.literal("§4\uD83D\uDCD6 Chaos just donated you a mysterious book"));
        }
    }
}
