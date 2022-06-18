package com.scouter.monsterfood.entity;

import com.scouter.monsterfood.entity.entities.WalkingMushroomEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MushroomEntity extends PathfinderMob implements NeutralMob {

    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(WalkingMushroomEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer>  ANGER_TIME = SynchedEntityData.defineId(WalkingMushroomEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer>  DISTANCE = SynchedEntityData.defineId(WalkingMushroomEntity.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(WalkingMushroomEntity.class, EntityDataSerializers.INT);
    private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(15, 50);
    private UUID targetUuid;

    protected MushroomEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.noCulling = true;
        this.xpReward = (int) (this.getMaxHealth());
    }

    @Override
    public int getRemainingPersistentAngerTime() {return this.entityData.get(ANGER_TIME);}

    @Override
    public void setRemainingPersistentAngerTime(int time) {this.entityData.set(ANGER_TIME, time);}

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {return this.targetUuid;}

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) { this.targetUuid = target;}

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void startPersistentAngerTimer() {this.setRemainingPersistentAngerTime(ANGER_TIME_RANGE.sample(this.random));}

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }


    @Override
    public void playAmbientSound() {
        SoundEvent soundevent = this.getAmbientSound();
        if (soundevent != null) {
            this.playSound(soundevent, 0.25F, this.getVoicePitch());
        }

    }
    @Override
    public void readAdditionalSaveData(CompoundTag tag){
        super.readAdditionalSaveData(tag);
        tag.putBoolean("isAttacking", this.isAttacking());
        tag.putInt("getAngerTime", this.getAngerTime());
        tag.putInt("getDistance", this.getDistance());
        tag.putInt("getAttackingState", this.getAttackingState());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag){
        super.addAdditionalSaveData(tag);
        tag.putBoolean("isAttacking", this.isAttacking());
        tag.putInt("getAngerTime", this.getAngerTime());
        tag.putInt("getDistance", this.getDistance());
        tag.putInt("getAttackingState", this.getAttackingState());
    }

    @Override
    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
        this.entityData.define(ANGER_TIME, 0);
        this.entityData.define(DISTANCE, 0);
        this.entityData.define(STATE, 0);
    }

    public void setAttacking(boolean attacking){
        this.entityData.set(ATTACKING, attacking);
    }
    public void setAngerTime(Integer timer){
        this.entityData.set(ANGER_TIME, timer);
    }
    public void setDistance(Integer distance){
        this.entityData.set(DISTANCE, distance);
    }
    public void setAttackingState(int time){ this.entityData.set(STATE, time);}

    public boolean isAttacking(){
        return this.entityData.get(ATTACKING);
    }
    public Integer getAngerTime(){ return this.entityData.get(ANGER_TIME);}
    public Integer getDistance(){ return this.entityData.get(DISTANCE);}

    public int getAttackingState(){return this.entityData.get(STATE);}
}
