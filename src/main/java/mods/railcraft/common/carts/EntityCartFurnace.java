/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/
package mods.railcraft.common.carts;

import mods.railcraft.common.plugins.forge.FuelPlugin;
import mods.railcraft.common.util.inventory.InvTools;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCartFurnace extends EntityMinecartFurnace implements IRailcraftCart {

    public EntityCartFurnace(World world) {
        super(world);
    }

    public EntityCartFurnace(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public IRailcraftCartContainer getCartType() {
        return RailcraftCarts.FURNACE;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        cartInit();
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        saveToNBT(compound);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        loadFromNBT(compound);
    }

    /**
     * Checks if the entity is in range to render.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        return CartTools.isInRangeToRenderDist(this, distance);
    }

    @Override
    public ItemStack getCartItem() {
        return createCartItem(this);
    }

    @Override
    public void killMinecart(DamageSource par1DamageSource) {
        killAndDrop(this);
    }

//    public double getDrag() {
//        return DRAG_FACTOR;
//    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (MinecraftForge.EVENT_BUS.post(new MinecartInteractEvent(this, player, hand)))
            return true;
        ItemStack stack = player.getHeldItem(hand);
        if (!InvTools.isEmpty(stack)) {
            int burnTime = FuelPlugin.getBurnTime(stack);

            if (burnTime > 0 && fuel + burnTime <= 32000) {
                if (!player.capabilities.isCreativeMode)
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, InvTools.depleteItem(stack));
                fuel += burnTime;

                pushX = posX - player.posX;
                pushZ = posZ - player.posZ;
            }
        }

        return true;
    }

    //    private static final double DRAG_FACTOR = 0.99;
    private static final double PUSH_FACTOR = 0.1D;
}
