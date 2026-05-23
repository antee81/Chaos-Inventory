package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RunBookEvent implements ChaosEvent {
    @Override public String getName() { return "Book RUN"; }
    @Override public int getWeight() { return 15; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        CompoundTag tag = book.getOrCreateTag();
        tag.putString("title", "RUN");
        tag.putString("author", "Chaos");
        ListTag pages = new ListTag();
        pages.add(StringTag.valueOf("{\"text\":\"§4§lCORRI.\"}"));
        tag.put("pages", pages);
        if (!player.getInventory().add(book)) player.drop(book, false);
        player.sendSystemMessage(Component.literal("§4\uD83D\uDCD6 Chaos just donated you a book.. §lopen it"));
    }
}
