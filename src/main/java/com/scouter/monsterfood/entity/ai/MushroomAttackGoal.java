package com.scouter.monsterfood.entity.ai;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.entity.entities.WalkingMushroomEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import org.slf4j.Logger;

import java.util.EnumSet;

public class MushroomAttackGoal extends Goal {
    private static final Logger LOGGER = LogUtils.getLogger();

    private int stateCheck;
    private int attackTime = -1;
    private double moveSpeedAmp = 1;
    private final WalkingMushroomEntity entity;

    public MushroomAttackGoal(WalkingMushroomEntity mob, double moveSpeedAmpIn, int state) {
        this.entity = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.moveSpeedAmp = moveSpeedAmpIn;
        this.stateCheck = state;
    }

    @Override
    public boolean canUse() {
        return this.entity.getTarget() != null;
    }


    public boolean canContinueToUse(){return this.canUse();}

    public void start() {
        super.start();
        this.entity.setAggressive(true);
    }

    public void stop() {
        super.stop();
        this.entity.setAggressive(false);
        this.entity.setAttackingState(0);
        this.attackTime = -1;
    }

    public void tick(){
        LivingEntity livingEntity = this.entity.getTarget();
        if(livingEntity != null){
            boolean inLineOfSight = this.entity.getSensing().hasLineOfSight(livingEntity);
            this.attackTime++;
            this.entity.lookAt(livingEntity, 30.0F, 30.0F);
            double d0 = this.entity.distanceToSqr(livingEntity.getX(), livingEntity.getY(),
                    livingEntity.getZ());
            double d1 = this.getAttackReachSqr(livingEntity);
            if (inLineOfSight) {
                if (this.entity.distanceTo(livingEntity) >= 1.6D) {
                    this.entity.getNavigation().moveTo(livingEntity, this.moveSpeedAmp);
                    this.entity.setAttackingState(0);
                    this.attackTime = -3;
                } else {
                    if (this.attackTime == 2) {
                        this.entity.getNavigation().moveTo(livingEntity, this.moveSpeedAmp);
                        if (d0 <= d1) {
                            this.entity.setAttackingState(stateCheck);
                        }

                    }
                    if(attackTime == 8) {
                        if(d0 <= d1 && this.entity.getAttackingState() == this.stateCheck) {
                            this.entity.doHurtTarget(livingEntity);
                        }
                        livingEntity.invulnerableTime = 0;

                    }

                    if (this.attackTime == 10) {
                        this.attackTime = -5;
                        this.entity.setAttackingState(0);
                    }
                }
            }
        }
    }

    protected double getAttackReachSqr(LivingEntity attackTarget) {
        return (double) (this.entity.getBbWidth() * 2F * this.entity.getBbWidth() * 2F + attackTarget.getBbWidth());
    }
}
