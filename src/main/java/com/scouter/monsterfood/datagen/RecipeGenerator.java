package com.scouter.monsterfood.datagen;

import com.scouter.monsterfood.blocks.MFBlocks;
import com.scouter.monsterfood.datagen.recipe.CuttingRecipes;
import com.scouter.monsterfood.items.MFItems;
import com.scouter.monsterfood.utils.MFTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        /*ShapelessRecipeBuilder.shapeless(MFItems.NIGHTMARE.get())
                .requires(MFItems.WHITE_SPICE.get(), 2)
                .requires(MFItems.CUT_WALKING_MUSHROOM_BODY.get())
                .requires(MFItems.CUT_WALKING_MUSHROOM_FEET.get())
                .unlockedBy("has_mushroom_feet", has(MFItems.CUT_WALKING_MUSHROOM_FEET.get()))
                .save(consumer);
*/
        ShapelessRecipeBuilder.shapeless(MFItems.RAW_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET.get())
                .requires(MFItems.RED_SPICE.get())
                .requires(MFTags.Items.CUT_GARLIC)
                .requires(MFTags.Items.CUT_ONION)
                .requires(MFTags.Items.BUTTER)
                .requires(MFItems.CUT_WALKING_MUSHROOM_FEET.get(),3)
                .requires(MFItems.CUT_WALKING_MUSHROOM_BODY.get(),1)
                .requires(MFItems.SKILLET.get())
                .unlockedBy("has_mushroom_feet", has(MFItems.CUT_WALKING_MUSHROOM_FEET.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(MFBlocks.SKILLET.get(),1 )
                .pattern("a")
                .pattern("a")
                .pattern("a")
                .define('a', Items.IRON_INGOT)
                .unlockedBy("has_" + Items.IRON_INGOT.getDescriptionId().toLowerCase(), has(Items.IRON_INGOT)).save(consumer);

        ShapedRecipeBuilder.shaped(Items.STICK, 1)
                .define('#', MFBlocks.RED_BAMBOO.get())
                .pattern("#")
                .pattern("#")
                .group("sticks")
                .unlockedBy("has_red_bamboo", has(MFBlocks.RED_BAMBOO.get()))
                .save(consumer, "stick_from_red_bamboo_item");

        ShapedRecipeBuilder.shaped(Blocks.SCAFFOLDING, 6)
                .define('~', Items.STRING)
                .define('I', MFBlocks.RED_BAMBOO.get())
                .pattern("I~I")
                .pattern("I I")
                .pattern("I I")
                .unlockedBy("has_red_bamboo", has(MFBlocks.RED_BAMBOO.get()))
                .save(consumer);


        simpleCookingRecipe(consumer, "smelting", RecipeSerializer.SMELTING_RECIPE, 100, MFItems.RAW_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET.get(), MFItems.COOKED_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET.get(), 0.50F);
        //simpleCookingRecipe(consumer, "smelting", RecipeSerializer.SMELTING_RECIPE, 100, MFItems.CUT_ONION.get(), MFItems.COOKED_CUT_ONION.get(), 0.50F);
        //simpleCookingRecipe(consumer, "smelting", RecipeSerializer.SMELTING_RECIPE, 100, MFItems.GARLIC_CLOVES.get(), MFItems.COOKED_GARLIC_CLOVES.get(), 0.50F);

        CuttingRecipes.register(consumer);
    }
}
