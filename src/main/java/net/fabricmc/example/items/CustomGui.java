package net.fabricmc.example.items;

import net.fabricmc.example.gui.ExampleScreen;
import net.fabricmc.example.gui.ExampleGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CustomGui extends Item {

    public CustomGui(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        MinecraftClient.getInstance().openScreen(new ExampleScreen(new ExampleGui()));
        return super.use(world,user,hand);
    }
}
