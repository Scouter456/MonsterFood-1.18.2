package com.scouter.monsterfood.entity.entities;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.blocks.LavaSnailSpice;
import com.scouter.monsterfood.blocks.MFBlockStateProperties;
import com.scouter.monsterfood.blocks.MFBlocks;
import com.scouter.monsterfood.items.MFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class LavaSnailEntity extends Animal implements IAnimatable, IAnimationTickable {
    private AnimationFactory factory = new AnimationFactory(this);
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final EntityDataAccessor<Boolean> IS_DEAD = SynchedEntityData.defineId(LavaSnailEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer>  DEAD_STATE = SynchedEntityData.defineId(LavaSnailEntity.class, EntityDataSerializers.INT);

    public LavaSnailEntity(EntityType<? extends Animal> props, Level level) {


        super(props, level);
        //this.moveControl = new LavaSnailEntity.LavaSnailMoveControl(this);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);


    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving() && this.getDeadState() != 1) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("lavasnail.idle", true));
            return PlayState.CONTINUE;
        }


        if(this.getHealth() <= 25.0D && this.getDeadState() == 1 && !this.getIsDead()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("lavasnail.hide", false));
            return PlayState.CONTINUE;
        }

        if(this.getIsDead() && (this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("lavasnail.dead", false));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("lavasnail.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<LavaSnailEntity> controller = new AnimationController<>(this, "controller",0, this::predicate);
        data.addAnimationController(controller);
    }

    public static AttributeSupplier setAttributes(){
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_KNOCKBACK, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 10.0D)
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 12f)
                .add(Attributes.ATTACK_SPEED, 1f)
                .add(Attributes.MOVEMENT_SPEED, 0.1f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0D)
                .add(Attributes.ARMOR,5.0f)
                .build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        //this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));

    }

    @Override
    public void die(DamageSource source) {
        if (!this.level.isClientSide) {
            if (source == DamageSource.OUT_OF_WORLD) {
                this.setIsDead(true);
                this.level.broadcastEntityEvent(this, (byte) 3);
                super.die(source);
            }
            if(!this.getIsDead()){
                this.goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
                this.setIsDead(true);
                this.level.broadcastEntityEvent(this, (byte) 3);
                super.die(source);
            }
        }
    }
    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide) {
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
                return;
            }

            //BlockState blockstateNoLava = MFBlocks.SPICE.get().defaultBlockState().setValue(LavaSnailSpice.SPICE_TIER, Integer.valueOf(2)).setValue(LavaSnailSpice.LAVALOGGED, Boolean.valueOf(false));
            //.setValue(LavaSnailSpice.SPICE_TIER, Integer.valueOf(1)).setValue(LavaSnailSpice.LAVALOGGED, Boolean.valueOf(true));
            for(int l = 0; l < 4; ++l) {
                int i = Mth.floor(this.getX() + (double)((float)(l % 2 * 2 - 1) * 0.25F));
                int j = Mth.floor(this.getY());
                int k = Mth.floor(this.getZ() + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockpos1 = new BlockPos(i, j, k);
                FluidState fluidstate = this.getLevel().getFluidState(blockpos1);
                boolean flag = fluidstate.getType() == Fluids.LAVA;
                BlockState blockstate = MFBlocks.SPICE.get().defaultBlockState();
                BlockState blockstateFlag1 = MFBlocks.SPICE.get().defaultBlockState().setValue(LavaSnailSpice.SPICE_TIER, Integer.valueOf(1)).setValue(LavaSnailSpice.LAVALOGGED, Boolean.valueOf(flag));
                if (this.isEmptyOrFluid(blockpos1) && (blockstate.canSurvive(this.level, blockpos1) || blockstate.canSurvive(this.level, blockpos1))) {
                    if(!blockstate.getValue(MFBlockStateProperties.LAVALOGGED)){
                        this.level.setBlockAndUpdate(blockpos1, blockstateFlag1);
                    }else{
                        this.level.setBlockAndUpdate(blockpos1, blockstateFlag1);
                    }

                }
            }
        }

    }

    public boolean isEmptyOrFluid(BlockPos pPos) {
        return this.level.getBlockState(pPos).isAir() || this.level.getBlockState(pPos).getFluidState().is(FluidTags.LAVA);
    }

    @Override
    public void tick(){
        if(this.level == null){
            super.tick();
        }
        int i = Mth.floor(this.getX());
        int j = Mth.floor(this.getY());
        int k = Mth.floor(this.getZ());

        BlockPos blockpos = new BlockPos(i, j, k);
        if(this.level.getBlockState(blockpos.below()).is(Blocks.HOPPER)){
            ItemStack drop = new ItemStack(MFItems.NIGHTMARE.get(), 1);
            this.remove(Entity.RemovalReason.KILLED);
            this.setIsDead(false);
            this.spawnAtLocation(drop, 0);
        }

        if(this.getHealth() <= 25.0D){
            this.getAttribute(Attributes.ARMOR).setBaseValue(60.0f);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0f);
            this.setDeadState(1);
        }

        if(this.getHealth() > 25.0D){
            this.getAttribute(Attributes.ARMOR).setBaseValue(5.0f);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1f);
            this.setDeadState(0);
        }

        super.tick();
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime >= 8000) {
            this.remove(Entity.RemovalReason.KILLED);
            this.setIsDead(false);
        }
        this.goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
    }

    //TODO make it give special items when carved when dead
    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (this.isAlive()) {
            LOGGER.info("Health: " + this.getHealth());
            LOGGER.info("Armor: " + this.getArmorValue());
            LOGGER.info("Movespeed: " + this.getSpeed());
            LOGGER.info("Dead state: " + this.getDeadState());
            if(this.getDeadState() != 1){
                this.setDeadState(3);
                return InteractionResult.CONSUME;
            }

            //if(this.hurt(DamageSource.playerAttack(pPlayer), 0.1f)){
            //    this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6f);
           //     this.doHurtTarget(pPlayer);
            //    this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(12);
           // }



            return InteractionResult.PASS;
        }


        if (this.getIsDead()) {
            if (this.level.isClientSide) {
                return InteractionResult.FAIL;
            }
            if(itemstack.getItem() == Items.STICK) {
                super.remove(Entity.RemovalReason.KILLED);
            }
        }

        return InteractionResult.FAIL;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount){
        boolean prev = super.hurt(pSource, pAmount);
        if(this.getDeadState() != 1){
            return prev;
        }
        if(pSource.getEntity() instanceof Player player){
            player = (Player) pSource.getEntity();
        }

        if(pSource.getEntity() != null && this.getDeadState() == 1 && !this.getIsDead()){
            pSource.getEntity().hurt(DamageSource.mobAttack((LivingEntity)pSource.getEntity()), 10f);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(12f);
            return prev;
        }

        return prev;
    }

    /*
    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader worldIn) {
        //if (worldIn.getBlockState(pos).getFluidState().is(FluidTags.WATER) || worldIn.getBlockState(pos).getFluidState().is(FluidTags.LAVA)) {
        //    return 20.0F;
       // } else {
        //    return this.isInLava() ? 10.0F:10.0F;
        //}
        return 5.0F;
    }*/

    @Override
    public void travel(Vec3 travelVector) {
        boolean liquid = this.shouldSwim();
        if (this.isEffectiveAi() && liquid) {
            this.moveRelative(this.getSpeed()*0.25f, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void kill() {
        this.remove(RemovalReason.KILLED);
        this.setIsDead(false);
    }
    /*
    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && (this.isInWater() || this.isInLava())) {
            this.moveRelative(0.05F, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(pTravelVector);
        }

    }*/

    public boolean shouldSwim() {
        return getMaxFluidHeight() >= 0.1F || this.isInLava() || this.isInWaterOrBubble();
    }

    private double getMaxFluidHeight() {
        return Math.max(this.getFluidHeight(FluidTags.LAVA), this.getFluidHeight(FluidTags.WATER));
    }
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(IS_DEAD, Boolean.valueOf(false));
        this.entityData.define(DEAD_STATE, Integer.valueOf(0));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag){
        super.addAdditionalSaveData(tag);
        tag.putBoolean("isDead", this.getIsDead());
        tag.putInt("getDeadState", this.getDeadState());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setIsDead(tag.getBoolean("isDead"));
        setDeadState(tag.getInt("getDeadState"));
    }

    public void setIsDead(boolean isdead){
        this.entityData.set(IS_DEAD, Boolean.valueOf(isdead));
    }
    public void setDeadState(Integer state){
        this.entityData.set(DEAD_STATE, Integer.valueOf(state));
    }


    public boolean getIsDead(){
        return this.entityData.get(IS_DEAD).booleanValue();
    }
    public Integer getDeadState(){ return this.entityData.get(DEAD_STATE);}

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int tickTimer() {
        return tickCount;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    static class LavaSnailMoveControl extends MoveControl {
        private final LavaSnailEntity lavasnail;

        public LavaSnailMoveControl(LavaSnailEntity p_32433_) {
            super(p_32433_);
            this.lavasnail = p_32433_;
        }

        public void tick() {
            LivingEntity livingentity = this.lavasnail.getTarget();
            if (this.lavasnail.isInWater()) {
                if (livingentity != null && livingentity.getY() > this.lavasnail.getY()) {
                    this.lavasnail.setDeltaMovement(this.lavasnail.getDeltaMovement().add(0.0D, 0.002D, 0.0D));
                }

                if (this.operation != MoveControl.Operation.MOVE_TO || this.lavasnail.getNavigation().isDone()) {
                    this.lavasnail.setSpeed(0.0F);
                    return;
                }

                double d0 = this.wantedX - this.lavasnail.getX();
                double d1 = this.wantedY - this.lavasnail.getY();
                double d2 = this.wantedZ - this.lavasnail.getZ();
                double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 /= d3;
                float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.lavasnail.setYRot(this.rotlerp(this.lavasnail.getYRot(), f, 90.0F));
                this.lavasnail.yBodyRot = this.lavasnail.getYRot();
                float f1 = (float)(this.speedModifier * this.lavasnail.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float f2 = Mth.lerp(0.125F, this.lavasnail.getSpeed(), f1);
                this.lavasnail.setSpeed(f2);
                this.lavasnail.setDeltaMovement(this.lavasnail.getDeltaMovement().add((double)f2 * d0 * 0.005D, (double)f2 * d1 * 0.1D, (double)f2 * d2 * 0.005D));
            } else {
                if (!this.lavasnail.onGround) {
                    this.lavasnail.setDeltaMovement(this.lavasnail.getDeltaMovement().add(0.0D, -0.008D, 0.0D));
                }

                super.tick();
            }

        }
    }
    @Override
    public boolean isPushable() {
        return false;
    }
    //@Override
    //public boolean canBeCollidedWith() {
    //    return true;
    //}
}
