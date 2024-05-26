package net.smileycorp.lootweapons.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.client.model.CompositeModel;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.minecraftforge.client.model.geometry.UnbakedGeometryHelper;

import java.util.List;
import java.util.function.Function;

public class PartModel implements IUnbakedGeometry<PartModel> {
    
    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation location) {
        TextureAtlasSprite sprite = spriteGetter.apply(context.getMaterial(location.toString()));
        var rootTransform = context.getRootTransform();
        if (!rootTransform.isIdentity()) modelState = UnbakedGeometryHelper.composeRootTransformIntoModelState(modelState, rootTransform);
        CompositeModel.Baked.Builder builder = CompositeModel.Baked.builder(context, sprite, overrides, context.getTransforms());
        List<BlockElement> elements = UnbakedGeometryHelper.createUnbakedItemElements(0, sprite.contents(), null);
        builder.addQuads(new RenderTypeGroup(RenderType.translucent(), ForgeRenderTypes.ITEM_UNSORTED_TRANSLUCENT.get()),
                UnbakedGeometryHelper.bakeElements(elements, material -> sprite, modelState, location));
        return builder.build();
    }
    
}
