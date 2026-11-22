package com.decodinator.liroth.core.blocks.basis;

import com.decodinator.liroth.core.complexmaterials.MetalMaterial;
import org.betterx.bclib.behaviours.interfaces.BehaviourMetal;
import org.betterx.bclib.blocks.LeveledAnvilBlock;

import net.minecraft.world.level.material.MapColor;

public class LirothAnvilBlock extends LeveledAnvilBlock implements BehaviourMetal {
    protected MetalMaterial metalMaterial;

    public LirothAnvilBlock(MapColor color, int level) {
        super(color, level);
    }

    public LirothAnvilBlock(MetalMaterial metalMaterial, MapColor color, int level) {
        super(color, level);
        this.metalMaterial = metalMaterial;
    }
}