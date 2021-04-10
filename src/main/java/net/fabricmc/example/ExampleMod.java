package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.items.CustomGui;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item.Settings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExampleMod implements ModInitializer {
	public static final Block EXPLOSION = new ExplosionBlock();
	public static final Block EPIC_BLOCK = new EpicBlock();

	public static final StatusEffect EXP = new ConstantStatusEffects();
	public static final StatusEffect AQUATIC = new AquaticStatus();

	public static StatusEffectInstance SPEEDO = new StatusEffectInstance(StatusEffects.SPEED, 20*60, 2);
	public static StatusEffectInstance JUMPO = new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20*60, 2);	
	public static final Item SOSAGE= new SosageItem(new Item.Settings().group(ItemGroup.MISC).food(new FoodComponent.Builder().hunger(5).saturationModifier(6f).snack().alwaysEdible().statusEffect(SPEEDO, 1f).statusEffect(JUMPO, 1f).build()));

	public static Item CUSTOM_GUI = new CustomGui(new Settings().group(ItemGroup.MISC));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		
		Registry.register(Registry.BLOCK, new Identifier("stepid", "epic_block"), EPIC_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier("stepid", "explosion"), EXPLOSION);

		Registry.register(Registry.STATUS_EFFECT, new Identifier("stepid", "exp"), EXP);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("stepid", "aquatic"), AQUATIC);

		Registry.register(Registry.ITEM, new Identifier("stepid", "sosage"), SOSAGE);
		
		Registry.register(Registry.ITEM, new Identifier("stepid", "epic_block"), new BlockItem(EPIC_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
		Registry.register(Registry.ITEM, new Identifier("stepid", "explosion"), new BlockItem(EXPLOSION, new FabricItemSettings().group(ItemGroup.MISC)));

		FuelRegistry.INSTANCE.add(SOSAGE, 10000);

		Registry.register(Registry.ITEM, new Identifier("stepid", "gui_item"), CUSTOM_GUI);


	}
}
