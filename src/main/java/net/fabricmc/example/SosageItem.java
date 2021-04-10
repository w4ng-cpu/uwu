package net.fabricmc.example;

import net.minecraft.item.Item;

public class SosageItem extends Item {

    public SosageItem(Settings item$Settings_1) {
        super(item$Settings_1);
    }
/*
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        playerEntity.playSound(SoundEvents.ENTITY_PIG_DEATH, 1.0F, 1.0F);
        return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
    }
*/
}