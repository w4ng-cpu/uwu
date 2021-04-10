package net.fabricmc.example;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;

public class ExplosionBlock extends Block {

    public ExplosionBlock() {
        super(FabricBlockSettings.of(Material.STONE).breakByHand(true).luminance(10));
    }
    
}