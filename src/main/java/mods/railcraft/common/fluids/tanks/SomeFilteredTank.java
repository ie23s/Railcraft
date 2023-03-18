/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2023
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/

package mods.railcraft.common.fluids.tanks;

import mods.railcraft.common.blocks.TileRailcraft;
import mods.railcraft.common.fluids.FluidItemHelper;
import mods.railcraft.common.fluids.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

public class SomeFilteredTank extends StandardTank {

    private List<Optional<Supplier<Optional<FluidStack>>>>
        filters = new LinkedList<>();
    public SomeFilteredTank(int capacity) {
        super(capacity);
    }

    public SomeFilteredTank(int capacity, @Nullable TileRailcraft tile) {
        super(capacity, tile);
    }

    public SomeFilteredTank setFilterFluid(Supplier<Optional<Fluid>>[] filters) {
        for (Supplier<Optional<Fluid>> filter:
            filters) {

            addFilterFluidStack(() -> filter.get().map(f -> new FluidStack(f, 1)));
        }
        return this;
    }

    public SomeFilteredTank addFilterFluidStack(Supplier<Optional<FluidStack>> filter) {
        filters.add(Optional.ofNullable(filter));
        return this;
    }
    @Override
    public boolean matchesFilter(@Nullable FluidStack fluidStack) {
        if (fluidStack == null)
            return true;
        Iterator<Optional<Supplier<Optional<FluidStack>>>> iterator = filters.listIterator();
        boolean out = false;
        while (iterator.hasNext() && !out){
            out = iterator.next().flatMap(Supplier::get)
                    .map(fs -> Fluids.areEqual(fs, fluidStack))
                    .orElse(true);
        }
        return out;
    }

    @Override
    public boolean matchesFilter(@Nullable ItemStack itemStack) {

        Iterator<Optional<Supplier<Optional<FluidStack>>>> iterator = filters.listIterator();
        boolean out = false;
        while (iterator.hasNext() && !out){
            out = iterator.next().flatMap(Supplier<Optional<FluidStack>>::get)
                    .map(fs -> FluidItemHelper.handlesFluid(itemStack, fs))
                    .orElse(true);
        }
        return out;
    }
}
