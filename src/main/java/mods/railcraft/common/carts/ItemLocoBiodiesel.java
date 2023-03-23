/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2019
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/

package mods.railcraft.common.carts;

import buildcraft.api.BCBlocks.Core;
import forestry.api.core.ForestryAPI;
import ic2.api.item.IC2Items;
import mods.railcraft.client.render.carts.LocomotiveRenderType;
import mods.railcraft.common.blocks.RailcraftBlocks;
import mods.railcraft.common.items.ItemGear;
import mods.railcraft.common.items.Metal;
import mods.railcraft.common.items.RailcraftItems;
import mods.railcraft.common.modules.ModuleForestry;
import mods.railcraft.common.plugins.color.EnumColor;
import mods.railcraft.common.plugins.forestry.ForestryPlugin;
import mods.railcraft.common.plugins.forge.CraftingPlugin;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

/**
 * Created by CovertJaguar on 8/30/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class ItemLocoBiodiesel extends ItemLocomotive {
    public ItemLocoBiodiesel(IRailcraftCartContainer cart) {
        super(cart, LocomotiveRenderType.ELECTRIC, EnumColor.GREEN, EnumColor.BLACK);
    }

    @Override
    public void defineRecipes() {
        super.defineRecipes();
        // TODO: make a recipe
        CraftingPlugin.addShapedRecipe(getStack(),
                "LD ",
                "TBT",
                "GMG",
                'L', Blocks.REDSTONE_LAMP,
                'D', ForestryPlugin.getItem("engine_biogas"),
                'B', RailcraftBlocks.GLASS,
                'M', Items.MINECART,
                'G', RailcraftItems.GEAR, ItemGear.EnumGear.STEEL,
                'T', RailcraftItems.PLATE, Metal.STEEL);

        if (Loader.isModLoaded("ic2"))
            CraftingPlugin.addShapedRecipe(getStack(),
                "LD ",
                "TBT",
                "GMG",
                'L', Blocks.REDSTONE_LAMP,
                'D', IC2Items.getItem("fluid_generator"),
                'B', RailcraftBlocks.GLASS,
                'M', Items.MINECART,
                'G', RailcraftItems.GEAR, ItemGear.EnumGear.STEEL,
                'T', RailcraftItems.PLATE, Metal.STEEL);
    }
}
