package g_raffes.somemod.blockentity;

import g_raffes.somemod.blockentity.ChainBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

public class ChainBlockEntityRenderer implements BlockEntityRenderer<ChainBlockEntity> {
    public ChainBlockEntityRenderer(BlockEntityRendererFactory.Context context) {}

    @Override
    public void render(ChainBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(0.5D, 0D, 0.5D);
        matrices.scale(0.5F, 0.5F, 0.5F);

        float itemRotation = entity.getItemRotation();
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(itemRotation));

        int seed = (int)entity.getPos().asLong();
        ItemStack hangingStack = entity.getHangingItem();
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        itemRenderer.renderItem(hangingStack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, seed);

        matrices.pop();
    }
}
