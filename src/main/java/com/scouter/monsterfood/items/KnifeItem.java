package com.scouter.monsterfood.items;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.misc.MFSounds;
import com.scouter.monsterfood.structuresystem.data.PlayerStructure;
import com.scouter.monsterfood.structuresystem.data.PlayerStructureProvider;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.LocationTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.capabilities.CapabilityManager;
import org.slf4j.Logger;
import oshi.annotation.concurrent.Immutable;

import java.util.List;

public class KnifeItem extends Item {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static Item knife;
    public KnifeItem(Tier tier, Properties pProperties) {
        super(pProperties);
    }

}
