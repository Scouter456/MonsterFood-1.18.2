package com.scouter.monsterfood.datagen;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.MFBlocks;
import com.scouter.monsterfood.items.MFItems;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper){
        super(generator, MonsterFood.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels(){
        for(Item i : Registry.ITEM){
            if (i instanceof SpawnEggItem && i.getRegistryName().getNamespace().equals(MonsterFood.MODID)){
                getBuilder(i.getRegistryName().getPath()).parent((getExistingFile(new ResourceLocation("item/template_spawn_egg"))));
            }
        }

        //toBlock(MVBlocks.earth_block.get())
        singleTex(MFItems.NIGHTMARE);
        singleTex(MFItems.WALKING_MUSHROOM_BODY);
        singleTex(MFItems.WALKING_MUSHROOM_FEET);
        singleTex(MFItems.WALKING_MUSHROOM_SLIVER);
        singleTex(MFItems.CUT_WALKING_MUSHROOM_FEET);
        singleTex(MFItems.CUT_WALKING_MUSHROOM_BODY);
        singleTex(MFItems.WHITE_SPICE);
        singleTex(MFItems.RED_SPICE);
        singleTex(MFItems.BLACK_SPICE);
        singleTex(MFItems.SALT);
        singleTex(MFItems.BUTTER);
        singleTex(MFItems.ONION);
        singleTex(MFItems.GARLIC);
        singleTex(MFItems.RAW_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET);
        singleTex(MFItems.RED_BAMBOO);
        //singleTex(MFItems.RAW_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET);
        //singleTex(MFItems.COOKED_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET);
        singleTex(MFItems.CUT_ONION);
        singleTex(MFItems.GARLIC_CLOVES);

        toBlockModel(MFBlocks.GOLD_TRAPDOOR.get(), "gold_trapdoor_bottom");

        knife(MFItems.WOOD_KNIFE);
        knife(MFItems.STONE_KNIFE);
        knife(MFItems.IRON_KNIFE);
        knife(MFItems.GOLDEN_KNIFE);
        knife(MFItems.DIAMOND_KNIFE);
        knife(MFItems.NETHERITE_KNIFE);
        knife(MFItems.ADAMANTITE_KNIFE);
        knife(MFItems.MITHRIL_KNIFE);

        //skillet(MFItems.SKILLET);
    }
    private void toBlock(Block b) {
        toBlockModel(b, b.getRegistryName().getPath());
    }

    private void toBlockModel(Block b, String model) {
        toBlockModel(b, prefix("block/" + model));
    }

    private void toBlockModel(Block b, ResourceLocation model) {
        withExistingParent(b.getRegistryName().getPath(), model);
    }

    private ItemModelBuilder singleTex(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), prefix("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldknife(String name, ResourceLocation... layers){
        ItemModelBuilder builder = withExistingParent(name, prefix("item/knifehandheld"));
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private ItemModelBuilder handheldskillet(String name, ResourceLocation... layers){
        ItemModelBuilder builder = withExistingParent(name, prefix("item/skillethandheld"));
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private ItemModelBuilder knife(RegistryObject<Item> item){
        return handheldknife(item.getId().getPath(), prefix("item/knives/" + item.getId().getPath()));
    }

    private ItemModelBuilder skillet(RegistryObject<Item> item){
        return handheldknife(item.getId().getPath(), prefix("item/skillet/" + item.getId().getPath()));
    }

    private ItemModelBuilder generated(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }
}
