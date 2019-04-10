package trapcraft.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import trapcraft.ModBlocks;
import trapcraft.client.renders.RenderDummy;
import trapcraft.client.renders.TileEntityMagneticChestRenderer;
import trapcraft.entity.EntityDummy;
import trapcraft.handler.GuiHandler;
import trapcraft.tileentity.TileEntityMagneticChest;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {
	
    public ClientProxy() {
    	super();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> GuiHandler::openGui);
        RenderingRegistry.registerEntityRenderingHandler(EntityDummy.class, RenderDummy::new);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMagneticChest.class, new TileEntityMagneticChestRenderer());
    }
   
    @Override
    protected void postInit(InterModProcessEvent event) {
    	super.postInit(event);
    	
    	Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> GrassColors.get(0.5D, 1.0D), ModBlocks.GRASS_COVERING);
    	
		Minecraft.getInstance().getBlockColors().register((state, blockAccess, pos, tintIndex) -> {
	         return blockAccess != null && pos != null ? BiomeColors.getGrassColor(blockAccess, pos) : -1;
	      }, ModBlocks.GRASS_COVERING);
    }
    
    @Override
	public EntityPlayer getPlayerEntity() {
		return Minecraft.getInstance().player;
	}
}