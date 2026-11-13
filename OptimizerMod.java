package optimizer;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.game.EventType.Trigger;
import mindustry.mod.Mod;
import mindustry.Vars;

public class OptimizerMod extends Mod {
    private Table panel;
    private Label readout;

    private float updateTimer;
    private int lowestFps = Integer.MAX_VALUE;
    private boolean wasInGame;

    @Override
    public void init() {
        Events.on(ClientLoadEvent.class, event -> createMonitor());

        Events.run(Trigger.update, () -> {
            if (panel == null || readout == null) return;

            boolean inGame = Vars.state.isGame();
            panel.visible = inGame;

            if (!inGame) {
                wasInGame = false;
                return;
            }

            if (!wasInGame) {
                lowestFps = Integer.MAX_VALUE;
                updateTimer = 0f;
                wasInGame = true;
            }

            int fps = Core.graphics.getFramesPerSecond();
            float frameMs = Core.graphics.getDeltaTime() * 1000f;

            if (fps > 0 && fps < lowestFps) {
                lowestFps = fps;
            }

            // Refresh the text four times per second, not on every frame.
            updateTimer += Core.graphics.getDeltaTime();
            if (updateTimer >= 0.25f) {
                updateTimer = 0f;

                String lowest = lowestFps == Integer.MAX_VALUE
                    ? "--"
                    : Integer.toString(lowestFps);

                readout.setText(
                    "[accent]MINDUSTRY OPTIMIZER[]\n" +
                    "FPS: [white]" + fps + "[]\n" +
                    "Frame: [white]" + String.format(java.util.Locale.ROOT, "%.1f", frameMs) + " ms[]\n" +
                    "Session low: [white]" + lowest + " FPS[]"
                );
            }
        });
    }

    private void createMonitor() {
        if (Vars.ui == null || Vars.ui.hudGroup == null) return;

        panel = new Table();
        panel.setPosition(8f, Core.graphics.getHeight() - 8f, Align.topLeft);
        panel.top().left();
        panel.pad(6f);

        readout = new Label("MINDUSTRY OPTIMIZER");
        readout.setColor(Color.white);
        readout.setFontScale(0.8f);

        panel.add(readout).left();
        panel.setTouchable(arc.scene.event.Touchable.disabled);
        Vars.ui.hudGroup.addChild(panel);
    }
}
