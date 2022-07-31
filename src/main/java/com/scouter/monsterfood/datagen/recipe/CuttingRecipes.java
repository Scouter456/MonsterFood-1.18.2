package com.scouter.monsterfood.datagen.recipe;

import com.scouter.monsterfood.datagen.builder.CuttingBoardRecipeBuilder;
import com.scouter.monsterfood.items.MFItems;
import com.scouter.monsterfood.utils.MFTags;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class CuttingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer){
        cuttingRecipes(consumer);

    }


    private static void cuttingRecipes(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(MFItems.WALKING_MUSHROOM_FEET.get()), Ingredient.of(MFTags.Items.KNIVES), MFItems.CUT_WALKING_MUSHROOM_FEET.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(MFItems.WALKING_MUSHROOM_BODY.get()), Ingredient.of(MFTags.Items.KNIVES), MFItems.CUT_WALKING_MUSHROOM_BODY.get(), 2)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(MFItems.CUT_ONION.get()), Ingredient.of(MFTags.Items.ONION), MFItems.CUT_ONION.get(), 4)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(MFItems.GARLIC_CLOVES.get()), Ingredient.of(MFTags.Items.CUT_GARLIC), MFItems.GARLIC_CLOVES.get(), 4)
                .build(consumer);
    }
}
