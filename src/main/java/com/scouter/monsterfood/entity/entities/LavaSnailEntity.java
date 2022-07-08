package com.scouter.monsterfood.entity.entities;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.entity.MushroomEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.core.jmx.Server;
import org.slf4j.Logger;
import org.w3c.dom.Attr;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class LavaSnailEntity extends Mob implements IAnimatable, IAnimationTickable {
    private AnimationFactory factory = new AnimationFactory(this);
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final EntityDataAccessor<Boolean> IS_DEAD = SynchedEntityData.defineId(LavaSnailEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer>  DEAD_STATE = SynchedEntityData.defineId(MushroomEntity.class, EntityDataSerializers.INT);

    public LavaSnailEntity(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
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
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0D)
                .add(Attributes.ARMOR,5.0f)
                .build();
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
    public void tick(){

        if(this.getHealth() <= 25.0D){
            this.getAttribute(Attributes.ARMOR).setBaseValue(60.0f);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0f);
            this.setDeadState(1);
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

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (this.isAlive()) {
            LOGGER.info("Health: " + this.getHealth());
            LOGGER.info("Armor: " + this.getArmorValue());

            if(this.getDeadState() != 1){
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
        Player player = (Player) pSource.getEntity();
        if(this.getDeadState() != 1){
            return prev;
        }
        if(pSource.getEntity() != null && this.getDeadState() == 1 && !this.getIsDead()){
            pSource.getEntity().hurt(DamageSource.mobAttack((LivingEntity)pSource.getEntity()), 10f);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(12f);
            return prev;
        }

        return prev;
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

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
}
