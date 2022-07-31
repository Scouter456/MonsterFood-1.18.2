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
        public static final TagKey<Item>  KNIVES = tag("knives");
        public static final TagKey<Item>  BUTTER = tag("butter");
        public static final TagKey<Item>  ONION = tag("onion");
        public static final TagKey<Item>  GARLIC = tag("garlic");

        public static final TagKey<Item>  CUT_ONION = tag("cut_onion");
        public static final TagKey<Item>  CUT_GARLIC = tag("cut_garlic");

        public static final TagKey<Item>  COOKED_CUT_ONION = tag("cooked_cut_onion");
        public static final TagKey<Item>  COOKED_CUT_GARLIC = tag("cooked_cut_garlic");
        public static final TagKey<Item>  SPICE = tag("spice");

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
