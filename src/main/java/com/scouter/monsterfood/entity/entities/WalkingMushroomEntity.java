package com.scouter.monsterfood.entity.entities;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.entity.MushroomEntity;
import com.scouter.monsterfood.entity.ai.MushroomAttackGoal;
import com.scouter.monsterfood.items.MFItems;
import com.scouter.monsterfood.misc.MFSounds;
import com.scouter.monsterfood.utils.MFTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.CustomInstructionKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class WalkingMushroomEntity extends MushroomEntity implements IAnimatable, IAnimationTickable {



    private AnimationFactory factory = new AnimationFactory(this);
    private static final Logger LOGGER = LogUtils.getLogger();

    public static Map<Item, Integer> knives = ImmutableMap.of(
            MFItems.WOOD_KNIFE.get(), 10,
            MFItems.STONE_KNIFE.get(), 20,
            MFItems.IRON_KNIFE.get(), 30,
            MFItems.GOLDEN_KNIFE.get(), 40,
            MFItems.DIAMOND_KNIFE.get(), 60,
            MFItems.NETHERITE_KNIFE.get(),70,
            MFItems.ADAMANTITE_KNIFE.get(), 80,
            MFItems.MITHRIL_KNIFE.get(),90);
    public static HashMap<Item, Integer> knifeChance = new HashMap<>(knives);
    public static final ResourceLocation MUSHROOM_LOOT = prefix("entities/mushroom/walking_mushroom");
    static Random rand = new Random();
    public WalkingMushroomEntity(EntityType<WalkingMushroomEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes(){
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_KNOCKBACK, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 10.0D)
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 12f)
                .add(Attributes.ATTACK_SPEED, 1f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .build();
    }
    //TODO Spray attack
    protected void registerGoals(){
        if(!this.getIsDead()) {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
            this.goalSelector.addGoal(2, new MushroomAttackGoal(this, 1, 1));
            this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
            this.goalSelector.addGoal(4, new HurtByTargetGoal(this).setAlertOthers());
            this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
            this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        }
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()) {

            event.getController().setAnimation(new AnimationBuilder().addAnimation("walking.walkingmushroom", true));
            return PlayState.CONTINUE;
        }

        if(this.getIsDead() && (this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("dead.walkingmushroom", false));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle.walkingmushroom", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {

        if (this.entityData.get(ATTACK_STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack.walkingmushroom", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;

    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<WalkingMushroomEntity> controller = new AnimationController<>(this, "controller",0, this::predicate);
        AnimationController<WalkingMushroomEntity> controller1 = new AnimationController<>(this, "controller1",0, this::predicate1);
        data.addAnimationController(controller);
        data.addAnimationController(controller1);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int tickTimer() {
        return tickCount;
    }

    @Override
    public void kill() {
        this.remove(RemovalReason.KILLED);
        this.setIsDead(false);
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
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime >= 8000) {
            this.remove(Entity.RemovalReason.KILLED);
            this.setIsDead(false);
        }
        this.goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
    }

    @Override
    public ResourceLocation getDeadLootTable() {
    return MUSHROOM_LOOT;}

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        if (this.isAlive()) {
            return InteractionResult.PASS;
        }

        if(this.getIsDead()){
            if(this.level.isClientSide){
                return InteractionResult.FAIL;
            }
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            if(itemstack.is(MFTags.Items.KNIVES)) {
                if(this.getDeadState() == 0){
                    chanceItemDrop(itemstack, MFItems.WALKING_MUSHROOM_BODY.get(), pPlayer, pHand);
                    this.setDeadState(this.getDeadState() + 1);
                    return InteractionResult.CONSUME;
                }
                if(this.getDeadState() == 1){
                    chanceItemDrop(itemstack, MFItems.WALKING_MUSHROOM_FEET.get(), pPlayer, pHand);
                    this.setDeadState(this.getDeadState() + 1);
                    return InteractionResult.CONSUME;
                }
                if(this.getDeadState() == 2){
                    itemDrop(itemstack,pPlayer, pHand);
                    this.setDeadState(this.getDeadState() + 1);
                    return InteractionResult.CONSUME;
                }
                if(this.getDeadState() == 3){
                    itemDrop(itemstack,pPlayer, pHand);
                    this.setDeadState(0);
                    this.remove(Entity.RemovalReason.KILLED);
                    this.setIsDead(false);
                    return InteractionResult.CONSUME;
                }
                itemstack.hurtAndBreak(10, pPlayer, (p_41300_) -> {
                    p_41300_.broadcastBreakEvent(pHand);});
            }
        }
        return InteractionResult.FAIL;
    }

    public void chanceItemDrop(ItemStack itemStack, Item item, Player pPlayer, InteractionHand pHand){
        ItemStack drop = new ItemStack(item, 1);
        int randomNum = rand.nextInt(100);
        if(randomNum <= (knifeChance.get(itemStack.getItem())/2) && !level.isClientSide){
            this.spawnAtLocation(drop, 1);
        }
        itemStack.hurtAndBreak((100/(knifeChance.get(itemStack.getItem())/2))/2, pPlayer, (p_41300_) -> {
            p_41300_.broadcastBreakEvent(pHand);});
    }

    public void itemDrop(ItemStack itemStack, Player pPlayer, InteractionHand pHand){
        ItemStack drop = getSoundForDrop();
        if (!drop.isEmpty() && !level.isClientSide) {
            this.spawnAtLocation(drop, 1);
        }
        itemStack.hurtAndBreak((100/(knifeChance.get(itemStack.getItem())/2))/2, pPlayer, (p_41300_) -> {
            p_41300_.broadcastBreakEvent(pHand);});
    }


    protected SoundEvent getHurtSound(DamageSource damageSource) { return MFSounds.MUSHROOM_WALK.get();}

    protected SoundEvent getDeathSound() { return SoundEvents.SNOW_GOLEM_DEATH;}

    protected float getSoundVolume() {return 0.2F;}

    @Override
    public int getMaxSpawnClusterSize() {
        return 15;
    }




}
