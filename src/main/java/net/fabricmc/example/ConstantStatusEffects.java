package net.fabricmc.example;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;

public class ConstantStatusEffects extends StatusEffect {
    
    public ConstantStatusEffects() {
        super(StatusEffectType.BENEFICIAL, 0x98D982);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            ((PlayerEntity) entity).addExperience(1 << amplifier); // Higher amplifier gives you EXP faster
            if (!entity.isSubmergedIn(FluidTags.WATER)) {
                entity.damage(DamageSource.DROWN, 1.0F);
            }
        }
    }
}
