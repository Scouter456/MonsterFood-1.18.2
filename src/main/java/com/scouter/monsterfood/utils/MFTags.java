package com.scouter.monsterfood.utils;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class MFTags {
    public static class Blocks {
        public static final TagKey<Block> t= tag("t");
        private static TagKey<Block> tag(String name){
            return BlockTags.create(prefix(name));

        }
        private static TagKey<Block> forgeTag(String name){
            return BlockTags.create(new ResourceLocation("forge", name));

        }
    }

    public static class Items {
        public static final TagKey<Item>  KNIVES = forgeTag("knives");
        public static final TagKey<Item>  BUTTER = forgeTag("butter");
        public static final TagKey<Item>  ONION = forgeTag("onion");
        public static final TagKey<Item>  GARLIC = forgeTag("garlic");
        public static final TagKey<Item>  SPICE = forgeTag("spice");

        private static TagKey<Item> tag(String name){
            return ItemTags.create(prefix(name));

        }
        private static TagKey<Item> forgeTag(String name){
            return ItemTags.create(new ResourceLocation("forge", name));

        }
    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>>  SNAIL = forgeTag("snail");

        private static TagKey<EntityType<?>> tag(String name){
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, prefix(name));

        }
        private static TagKey<EntityType<?>> forgeTag(String name){
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("forge", name));

        }
    }
}
