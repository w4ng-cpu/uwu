package net.fabricmc.example.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    private String specie = "Homosapien";

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract EntityDimensions getDimensions(EntityPose pose);

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow protected boolean isSubmergedInWater;

    @Shadow @Final public PlayerInventory inventory;

    @Shadow public abstract ItemEntity dropItem(ItemStack stack, boolean retainOwnership);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    // SWIMMING
    /*
    @Inject(method = "updateSwimming", at = @At("TAIL"))
    private void updateSwimmingPower(CallbackInfo ci) {
        this.setSwimming(this.isSprinting() && !this.hasVehicle());
        if (this.isSwimming() && this.isTouchingWater()) {
            Vec3d look = this.getRotationVector();
            move(MovementType.SELF, new Vec3d(look.x/4, look.y/4, look.z/4));
        }
    }*/
    
    @Inject(at = @At("RETURN"), method = "afterSpawn")
    private void afterSpawn(CallbackInfo info) {
        System.out.println("My Specie is " + this.specie);
    }

    @Inject(method = "writeCustomDataToTag", at = @At("RETURN"))
    public void writeCustomDataToTag(CompoundTag tag, CallbackInfo ci) {
        tag.putString("specie", this.specie);
    }
    
    @Inject(method = "readCustomDataFromTag", at = @At("RETURN"))
    public void readCustomDataFromTag(CompoundTag tag, CallbackInfo ci) {
        this.specie = tag.getString("specie");
    }

    // WATER_BREATHING
    @Inject(at = @At("TAIL"), method = "tick")
    private void tick(CallbackInfo info) {
        if(true) {
            if(!this.isSubmergedIn(FluidTags.WATER) && !this.hasStatusEffect(StatusEffects.WATER_BREATHING) && !isTouchingWater()) { //if not subermeged in water and not have water breathing run this
                if(!this.isRainingAtPlayerPosition()) { //if not raining at player position
                    //int landGain = this.getNextAirOnLand(0);    // landGain = 4
                    this.setAir(this.getNextAirUnderwater(this.getAir()) - 4); //set air = air - 4
                    
                    if (this.getAir() == -20) { //runs ever 5 ticks
                        this.setAir(0); //reset air so to run after 5 more ticks

                        for(int i = 0; i < 8; ++i) {
                            double f = this.random.nextDouble() - this.random.nextDouble();
                            double g = this.random.nextDouble() - this.random.nextDouble();
                            double h = this.random.nextDouble() - this.random.nextDouble();
                            this.world.addParticle(ParticleTypes.BUBBLE, this.getParticleX(0.5), this.getEyeY() + this.random.nextGaussian() * 0.08D, this.getParticleZ(0.5), f * 0.5F, g * 0.5F + 0.25F, h * 0.5F);
                        }

                        this.damage(DamageSource.DROWN, 1.0F); //take damage if air == -20
                    }
                }
                else { // raining at player position
                    int landGain = this.getNextAirOnLand(0);    //landGain = 4
                    this.setAir(this.getAir() - landGain);   // take away 4 from air
                }
            }
            // if subermeged in water or have water breathing
            else if (this.getAir() <= this.getMaxAir()){    //max air = 300 int, if air is < 300
                this.setAir(this.getNextAirOnLand(getAir())); //add 4 + to air, returns 300 if air is 304
            }
        }
    }

    private boolean isRainingAtPlayerPosition() {
        BlockPos blockPos = this.getBlockPos();
        return this.world.hasRain(blockPos) || this.world.hasRain(blockPos.add(0.0D, this.getDimensions(this.getPose()).height, 0.0D));
    }

    // AQUA_AFFINITY
    @ModifyConstant(method = "getBlockBreakingSpeed", constant = @Constant(ordinal = 0, floatValue = 5.0F))
    private float modifyWaterBlockBreakingSpeed(float in) {
        if(this.isInsideWaterOrBubbleColumn() && true) {
            return 1F;
        }
        return in;
    }

    // AQUA_AFFINITY
    @ModifyConstant(method = "getBlockBreakingSpeed", constant = @Constant(ordinal = 1, floatValue = 5.0F))
    private float modifyUngroundedBlockBreakingSpeed(float in) {
        if(this.isInsideWaterOrBubbleColumn() && true) {
            return 1F;
        }
        return in;
    }

/*
    // WATER_BREATHING
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSubmergedIn(Lnet/minecraft/tag/Tag;)Z"), method = "updateTurtleHelmet")
    public boolean isSubmergedInProxy(PlayerEntity player, Tag<Fluid> fluidTag) {
        boolean submerged = this.isSubmergedIn(fluidTag);
        if(true) {
            return !submerged;
        }
        return submerged;
    }
    */
}