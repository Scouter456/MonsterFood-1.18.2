package com.scouter.monsterfood.entity.entities;

import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
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

public class JellyFishEntity extends Animal implements IAnimatable, IAnimationTickable {

    private AnimationFactory factory = new AnimationFactory(this);
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final EntityDataAccessor<Boolean> IS_DEAD = SynchedEntityData.defineId(JellyFishEntity.class, EntityDataSerializers.BOOLEAN);

    public JellyFishEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("jellyfish.move", true));
            return PlayState.CONTINUE;
        }



        if(this.getIsDead() && (this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("jellyfish.dead", false));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("jellyfish.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<JellyFishEntity> controller = new AnimationController<>(this, "controller",0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public static AttributeSupplier setAttributes(){
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_KNOCKBACK, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 10.0D)
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 12f)
                .add(Attributes.ATTACK_SPEED, 1f)
                .add(Attributes.MOVEMENT_SPEED, 3f)
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
    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(IS_DEAD, Boolean.valueOf(false));

    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag){
        super.addAdditionalSaveData(tag);
        tag.putBoolean("isDead", this.getIsDead());

    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setIsDead(tag.getBoolean("isDead"));
    }

    public void setIsDead(boolean isdead){
        this.entityData.set(IS_DEAD, Boolean.valueOf(isdead));
    }


    public boolean getIsDead(){
        return this.entityData.get(IS_DEAD).booleanValue();
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public int tickTimer() {
        return tickCount;
    }
}
