package com.scouter.monsterfood.entity.entities;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.entity.MushroomEntity;
import com.scouter.monsterfood.entity.ai.MushroomAttackGoal;
import com.scouter.monsterfood.items.MFItems;
import com.scouter.monsterfood.utils.utils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import org.slf4j.Logger;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class WalkingMushroomEntity extends MushroomEntity implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);
    private static final Logger LOGGER = LogUtils.getLogger();

    public static double animTime;

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


    protected void registerGoals(){
        //if(!this.getIsDead()) {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            //this.goalSelector.addGoal(1 , new FleeSunGoal(this, 1F));
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
            this.goalSelector.addGoal(2, new MushroomAttackGoal(this, 1, 1));
            //this.goalSelector.addGoal(2, new RandomStrollGoal(this , 1.0D));
            this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
            this.goalSelector.addGoal(4, new HurtByTargetGoal(this).setAlertOthers());
            this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
            this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
       // }
    }


    @Override
    protected void updateControlFlags() {
        boolean flag = this.getTarget() != null && this.hasLineOfSight(this.getTarget());
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
        super.updateControlFlags();
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walking.walkingmushroom", true));
            return PlayState.CONTINUE;
        }

        if(this.getIsDead() && (this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("sprayattack.walkingmushroom", true));
            return PlayState.CONTINUE;
        }

//        if(this.isAttacking()){
//            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack.walkingmushroom", true));
//            animTime = event.getController().getCurrentAnimation().animationLength;
            //this.setAttacking(false);
//            return PlayState.CONTINUE;//       }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle.walkingmushroom", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {

        if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack.walkingmushroom", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;

    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",0, this::predicate));
        data.addAnimationController(new AnimationController(this, "controller1",0, this::predicate1));
    }


    @Override
    public AnimationFactory getFactory() {
        return this.factory;
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
                AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level, this.getX(), this.getY(),
                        this.getZ());
                areaeffectcloudentity.setParticle(ParticleTypes.BUBBLE_POP);
                areaeffectcloudentity.setRadius(3.0F);
                areaeffectcloudentity.setDuration(55);
                areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(areaeffectcloudentity);
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
        if (this.deathTime == 80) {
            this.remove(Entity.RemovalReason.KILLED);
            this.setIsDead(false);
        }
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if(this.isAlive()){
            LOGGER.info("ITS NOT DEAD AND INTERACTION");
            return InteractionResult.PASS;
        }

        LOGGER.info("Dead or not " + this.getIsDead());
        if(this.getIsDead()){
            LOGGER.info("ITS DEAD AND INTERACTION");
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            if(itemstack.getItem() == Items.NETHERITE_SWORD) {
                LOGGER.info("INTERACTION AND SWORD");
                ItemEntity item = new ItemEntity(pPlayer.level, this.getX(), this.getY(), this.getZ(), new ItemStack(MFItems.NIGHTMARE.get(), 1));
                this.deathTime += 5;
                this.level.addFreshEntity(item);
            }
        }
        return super.mobInteract(pPlayer, pHand);
    }
   /*@Override
   public void tick(){
        if(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()){
            this.setIsDead(true);
        }
   }*/

    protected SoundEvent getHurtSound(DamageSource damageSource) { return SoundEvents.MAGMA_CUBE_HURT;}

    protected SoundEvent getDeathSound() { return SoundEvents.SNOW_GOLEM_DEATH;}

    protected float getSoundVolume() {return 0.2F;}

    @Override
    public int getMaxSpawnClusterSize() {
        return 15;
    }

    /*static class AttackGoal extends Goal {
        private final WalkingMushroomEntity parentEntity;
        private int ticksUntilNextAttack;

        AttackGoal(WalkingMushroomEntity parentEntity) {
            this.parentEntity = parentEntity;
        }

        @Override
        public boolean canUse() {
            return this.parentEntity.getTarget() != null;
        }

        @Override
        public void start(){
            super.start();
            this.parentEntity.setAttacking(true);
        }

        @Override
        public void stop(){
            super.stop();
            this.parentEntity.setAttacking(false);
        }


        @Override
        public void tick(){
            //LivingEntity livingEntity = null;
            if(this.parentEntity.level == null || this.parentEntity.getTarget() == null){
                LOGGER.info("Level or target is null");
                stop();
                return;
            }
            LivingEntity livingEntity = this.parentEntity.getTarget();

            if(this.parentEntity.getTarget() != null){
                livingEntity =
            }
            if(livingEntity == null) {
                return;
            }
            if(this.parentEntity.hasLineOfSight(livingEntity)){

                Level world = this.parentEntity.level;

                Vec3 vector3d = this.parentEntity.getViewVector(1.0F);
                this.parentEntity.setDistance((int)Math.floor(this.parentEntity.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ())));
                double d0 = this.parentEntity.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());

                this.parentEntity.getNavigation().moveTo(livingEntity, 1F);
                LOGGER.info("d0:" + d0);
                LOGGER.info("d0:" + this.parentEntity.getDistance() + "ID: " + this.parentEntity.getId() + "Position: "+ this.parentEntity.position());
                LOGGER.info("ticksUntilNextAttack: " + this.parentEntity.getTimer());
                //LOGGER.info("d3:" + d3);
                //LOGGER.info("d4:" + d4);
                this.parentEntity.setTimer(this.parentEntity.getTimer().intValue() + 3);
                if(d0 < 1.3D){
                    this.parentEntity.setAttacking(true);
                    this.parentEntity.setNoActionTime(10);
                    if(this.parentEntity.getTimer() > 22 && d0 < 1.3D) {
                        this.parentEntity.doHurtTarget(livingEntity);
                        this.parentEntity.setTimer(0);
                        ticksUntilNextAttack = 0;
                    }
//                    this.parentEntity.getTarget().hurt(this.parentEntity, this.parentEntity.getAttributeValue(Attributes.ATTACK_DAMAGE));
//                    Object CombatTracker = new Object();
//                    utils.enqueueTask(this.parentEntity.level, (Runnable)livingEntity.hurt(DamageSource.mobAttack(this.parentEntity), 1f),100);
                    //this.parentEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    //this.parentEntity.attr
                } else if(d0 > 1.3D && d0 < 20D)
                {

                    ticksUntilNextAttack = 0;
                    this.parentEntity.setTimer(0);
                    this.parentEntity.setAttacking(false);
                    this.parentEntity.getNavigation().moveTo(livingEntity, 1F);
                }
                if(d0 > 20D){
                    this.parentEntity.setAttacking(false);
                    this.parentEntity.getNavigation().isDone();
                    this.parentEntity.setTimer(0);
                }

            }

            this.parentEntity.getNavigation().moveTo(livingEntity, 1F);
            this.parentEntity.lookAt(livingEntity, 30.0F, 30.0F);

        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return (double)(this.parentEntity.getBbWidth() * 2.0F * this.parentEntity.getBbWidth() * 2.0F + attackTarget.getBbWidth());
        }

    }*/

}
