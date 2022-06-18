package com.scouter.monsterfood.entity.ai;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.entity.entities.WalkingMushroomEntity;
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
        //this.moveSpeedAmp = moveSpeedAmpIn;
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
        //LOGGER.info("Running MushroomAttack Goal");
        //LOGGER.info("Attack state " + this.entity.getAttackingState());
        LivingEntity livingEntity = this.entity.getTarget();

        if(livingEntity != null){
            //LOGGER.info("LivingEntity " + livingEntity);
            boolean inLineOfSight = this.entity.getSensing().hasLineOfSight(livingEntity);
            this.attackTime++;
            this.entity.lookAt(livingEntity, 30.0F, 30.0F);
            double d0 = this.entity.distanceToSqr(livingEntity.getX(), livingEntity.getY(),
                    livingEntity.getZ());
            LOGGER.info("d0 " + d0);

            double d1 = this.getAttackReachSqr(livingEntity);
            LOGGER.info("d1 " + d1);
            if (inLineOfSight) {
                //LOGGER.info("LineofSight " + inLineOfSight);
                LOGGER.info("Distance " + this.entity.distanceTo(livingEntity));
                if (this.entity.distanceTo(livingEntity) >= 3.0D) {
                    this.entity.getNavigation().moveTo(livingEntity, this.moveSpeedAmp);
                    this.attackTime = -5;
                } else {
                    if (this.attackTime == 4) {
                        this.entity.getNavigation().moveTo(livingEntity, this.moveSpeedAmp);
                        if (d0 <= d1) {
                            //LOGGER.info("Attack state1 " + this.entity.getAttackingState());
                            this.entity.setAttackingState(stateCheck);
                            this.entity.doHurtTarget(livingEntity);
                        }
                        livingEntity.invulnerableTime = 0;
                    }
                    if (this.attackTime == 8) {
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
