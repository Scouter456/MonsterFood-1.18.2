package com.scouter.monsterfood.recipes;

import com.scouter.monsterfood.MonsterFood;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MFRecipes  {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MonsterFood.MODID);
//    public static final RegistryObject<RecipeSerializer<CuttingBoardRecipe>> CUTTING_SERIALIZER = SERIALIZERS.register("cutting", () -> CuttingBoardRecipe.Serializer.INSTANCE);

}
