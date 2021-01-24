package me.redstoneguy129.gillygogs.bokunominecraft.common.objects.quirks;

import me.redstoneguy129.gillygogs.bokunominecraft.BokuNoMinecraft;
import me.redstoneguy129.gillygogs.bokunominecraft.common.quirk.Quirk;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class BNMRQuirks {
    public static final DeferredRegister<Quirk> REGISTERS = DeferredRegister.create(Quirk.QUIRK, BokuNoMinecraft.MOD_ID);

    public static final RegistryObject<Quirk> QUIRKLESS = REGISTERS.register("quirkless", Quirk::new);
    public static final RegistryObject<Quirk> DARK_SHADOW = REGISTERS.register("dark_shadow", DarkShadowQuirk::new);
}