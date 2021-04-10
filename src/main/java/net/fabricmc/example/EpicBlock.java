package net.fabricmc.example;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class EpicBlock extends Block {

    public EpicBlock() {
        super(FabricBlockSettings.of(Material.WOOL).breakByHand(false).breakByTool(FabricToolTags.HOES).sounds(BlockSoundGroup.WOOL).strength(2,0.2f));
    }

}