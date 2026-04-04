package com.lokins.cad.gui;

import com.lokins.cad.CreateAttackDefense;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * Command Center GUI screen.
 * Displays: situation map, alert level, CU usage, FE consumption, device status.
 */
public class CommandCenterScreen extends AbstractContainerScreen<CommandCenterMenu> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            CreateAttackDefense.MOD_ID, "textures/gui/command_center.png");

    public CommandCenterScreen(CommandCenterMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 256;
        this.imageHeight = 200;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        // Dark background for tactical display
        graphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xCC1A1A2E);

        // Title bar
        graphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + 16, 0xFF2D2D44);
        graphics.drawCenteredString(font, "C.A.D. TACTICAL COMMAND", leftPos + imageWidth / 2, topPos + 4, 0x00FF00);

        int x = leftPos + 8;
        int y = topPos + 22;

        // Alert Level
        String[] alertNames = {"GREEN", "YELLOW", "RED"};
        int[] alertColors = {0x00FF00, 0xFFFF00, 0xFF0000};
        int alert = menu.getAlertLevel();
        graphics.drawString(font, "ALERT: " + alertNames[Math.min(alert, 2)], x, y,
                alertColors[Math.min(alert, 2)]);
        y += 14;

        // Compute Usage
        int totalCU = menu.getTotalCU();
        int usedCU = menu.getUsedCU();
        float cuRatio = totalCU > 0 ? (float) usedCU / totalCU : 0;
        int cuColor = cuRatio > 0.75f ? 0xFF0000 : cuRatio > 0.5f ? 0xFFFF00 : 0x00FF00;
        graphics.drawString(font, String.format("COMPUTE: %d/%d CU (%.0f%%)", usedCU, totalCU, cuRatio * 100), x, y, cuColor);
        y += 14;

        // FE Consumption
        graphics.drawString(font, String.format("POWER: %d FE/t", menu.getTotalFE()), x, y, 0x00CCFF);
        y += 14;

        // Separator
        graphics.fill(x, y, leftPos + imageWidth - 8, y + 1, 0xFF444444);
        y += 6;

        // Placeholder for situation map
        graphics.drawCenteredString(font, "[ TACTICAL MAP ]", leftPos + imageWidth / 2, y + 30, 0x555555);

        // Device count placeholder
        y += 70;
        graphics.fill(x, y, leftPos + imageWidth - 8, y + 1, 0xFF444444);
        y += 6;
        graphics.drawString(font, "DEVICES: --", x, y, 0xAAAAAA);
        graphics.drawString(font, "TARGETS: --", x, y + 12, 0xAAAAAA);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        // Suppress default inventory labels
    }
}
