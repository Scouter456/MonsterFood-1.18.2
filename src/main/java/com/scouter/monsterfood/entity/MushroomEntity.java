package com.scouter.monsterfood.entity;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.entity.entities.WalkingMushroomEntity;
import com.scouter.monsterfood.items.MFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.Tags;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Random;
import java.util.UUID;

public abstract class MushroomEntity extends PathfinderMob implements NeutralMob {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final EntityDataAccessor<Boolean> IS_DEAD = SynchedEntityData.defineId(MushroomEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer>  DEAD_STATE = SynchedEntityData.defineId(MushroomEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(MushroomEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer>  ANGER_TIME = SynchedEntityData.defineId(MushroomEntity.class, EntityDataSerializers.INT);
    private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(50, 100);
    private UUID targetUuid;

    protected MushroomEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.noCulling = true;
        this.xpReward = (int) (this.getMaxHealth());
    }


    public static boolean passPeacefulAndYCheck(EntityType<? extends MushroomEntity> config, LevelAccessor world,
                                                MobSpawnType reason, BlockPos pos, Random random) {
        if (world.getDifficulty() == Difficulty.PEACEFUL)
            return false;
        return  world.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && isBrightEnoughToSpawn(world, pos);
    }

    protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter p_186210_, BlockPos p_186211_) {
        return p_186210_.getRawBrightness(p_186211_, 0) > 8;
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

    public abstract ResourceLocation getDeadLootTable();

    protected ItemStack getRandomDrop(){
        ItemStack lootItem = getItemFromLootTable();
        if(lootItem.getItem() == MFItems.NIGHTMARE.get()){
            this.playSound(SoundEvents.BONE_BLOCK_HIT, 1,1);
        } else {
            this.playSound(SoundEvents.MAGMA_CUBE_SQUISH,1,1);
        }
        return lootItem;
    }


    public ItemStack getItemFromLootTable() {
        if(this.level.getServer() == null){
            return ItemStack.EMPTY;
        }
        LootTable loottable = this.level.getServer().getLootTables().get(this.getDeadLootTable());
        LootContext.Builder lootcontext$builder = this.createLootContext(false, DamageSource.GENERIC);
        for (ItemStack itemstack : loottable.getRandomItems(lootcontext$builder.create(LootContextParamSets.ENTITY))) {
            return itemstack;
        }
        return ItemStack.EMPTY;
    }


    @Override
    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(IS_DEAD, Boolean.valueOf(false));
        this.entityData.define(ANGER_TIME, Integer.valueOf(0));
        this.entityData.define(DEAD_STATE, Integer.valueOf(0));
        this.entityData.define(ATTACK_STATE, Integer.valueOf(0));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag){
        super.addAdditionalSaveData(tag);
        tag.putBoolean("isDead", this.getIsDead());
        tag.putInt("getAngerTime", this.getAngerTime());
        tag.putInt("getDeadState", this.getDeadState());
        tag.putInt("getAttackingState", this.getAttackingState());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag){
        super.readAdditionalSaveData(tag);
        setIsDead(tag.getBoolean("isDead"));
        setAngerTime(tag.getInt("getAngerTime"));
        setDeadState(tag.getInt("getDeadState"));
        setAttackingState(tag.getInt("getAttackingState"));
    }

    public void setIsDead(boolean isdead){
        this.entityData.set(IS_DEAD, Boolean.valueOf(isdead));
    }
    public void setAngerTime(Integer timer){
        this.entityData.set(ANGER_TIME, Integer.valueOf(timer));
    }
    public void setDeadState(Integer state){
        this.entityData.set(DEAD_STATE, Integer.valueOf(state));
    }
    public void setAttackingState(int state){ this.entityData.set(ATTACK_STATE, Integer.valueOf(state));}

    public boolean getIsDead(){
        return this.entityData.get(IS_DEAD).booleanValue();
    }
    public Integer getAngerTime(){ return this.entityData.get(ANGER_TIME).intValue();}
    public Integer getDeadState(){ return this.entityData.get(DEAD_STATE);}
    public int getAttackingState(){return this.entityData.get(ATTACK_STATE);}
}
