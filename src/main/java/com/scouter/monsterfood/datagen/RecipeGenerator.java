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
                .requires(MFTags.Items.ONION)
                .requires(MFTags.Items.GARLIC)
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

        simpleCookingRecipe(consumer, "smelting", RecipeSerializer.SMELTING_RECIPE, 100, MFItems.RAW_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET.get(), MFItems.COOKED_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET.get(), 0.50F);
        CuttingRecipes.register(consumer);
    }
}
