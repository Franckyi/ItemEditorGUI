package com.franckyi.itemeditor;

import com.franckyi.itemeditor.client.gui.ModGuiHandler;
import com.franckyi.itemeditor.config.ModConfiguration;
import com.franckyi.itemeditor.event.ModEventHandler;
import com.franckyi.itemeditor.network.EditAttributesMessage;
import com.franckyi.itemeditor.network.EditAttributesMessage.EditAttributesMessageHandler;
import com.franckyi.itemeditor.network.EditEnchantMessage;
import com.franckyi.itemeditor.network.EditEnchantMessage.EditEnchantMessageHandler;
import com.franckyi.itemeditor.network.EditHideFlagsMessage;
import com.franckyi.itemeditor.network.EditHideFlagsMessage.EditHideFlagsMessageHandler;
import com.franckyi.itemeditor.network.EditLoreMessage;
import com.franckyi.itemeditor.network.EditLoreMessage.EditLoreMessageHandler;
import com.franckyi.itemeditor.network.EditNameMessage;
import com.franckyi.itemeditor.network.EditNameMessage.EditNameMessageHandler;
import com.franckyi.itemeditor.network.GetClientStackMessage;
import com.franckyi.itemeditor.network.GetClientStackMessage.GetClientStackMessageHandler;
import com.franckyi.itemeditor.network.ModPacketHandler;
import com.franckyi.itemeditor.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = ModReference.MODID, name = ModReference.NAME, version = ModReference.VERSION, acceptedMinecraftVersions = ModReference.MCVERSION, guiFactory = ModReference.GUI_FACTORY_CLASS, updateJSON = ModReference.UPDATE_JSON)
public class ItemEditorMod {

	@Instance
	public static ItemEditorMod instance;

	public static ModConfiguration config;

	@SidedProxy(clientSide = ModReference.CLIENT_PROXY_CLASS, serverSide = ModReference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent e) {
		config = new ModConfiguration(e.getSuggestedConfigurationFile());
	}

	@EventHandler
	public static void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new ModGuiHandler());
		FMLCommonHandler.instance().bus().register(config);
		MinecraftForge.EVENT_BUS.register(new ModEventHandler());
		proxy.registerKeyBindings();
		registerMessages();
	}

	private static void registerMessages() {
		ModPacketHandler.INSTANCE.registerMessage(GetClientStackMessageHandler.class, GetClientStackMessage.class, 10,
				Side.SERVER);
		ModPacketHandler.INSTANCE.registerMessage(EditNameMessageHandler.class, EditNameMessage.class, 0, Side.SERVER);
		ModPacketHandler.INSTANCE.registerMessage(EditLoreMessageHandler.class, EditLoreMessage.class, 1, Side.SERVER);
		ModPacketHandler.INSTANCE.registerMessage(EditEnchantMessageHandler.class, EditEnchantMessage.class, 2,
				Side.SERVER);
		ModPacketHandler.INSTANCE.registerMessage(EditHideFlagsMessageHandler.class, EditHideFlagsMessage.class, 3,
				Side.SERVER);
		ModPacketHandler.INSTANCE.registerMessage(EditAttributesMessageHandler.class, EditAttributesMessage.class, 4,
				Side.SERVER);
	}

}
