package net.smileycorp.lootweapons.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.UnaryOperator;

@Mixin(Rarity.class)
public class MixinRarity {
    
    @Inject(at = @At("HEAD"), method = "getStyleModifier", cancellable = true, remap = false)
    public void getStyleModifier(CallbackInfoReturnable<UnaryOperator<Style>> callback) {
        //change uncommon colour to green because it looks better and more distinct with our rarities and matches apotheosis rarities
        if ((Object)this == Rarity.UNCOMMON) callback.setReturnValue(style -> style.withColor(ChatFormatting.GREEN));
    }
    
}
