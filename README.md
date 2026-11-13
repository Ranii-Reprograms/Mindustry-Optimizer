# Mindustry Optimizer — diagnostic prototype

Target: Mindustry v8 / Build 160.x, Java 17.

This first prototype is diagnostic-only. It adds a small client-side HUD readout showing:

- Current FPS as reported by Mindustry's graphics engine
- Frame time in milliseconds from the graphics delta-time value
- Lowest reported FPS observed since entering the current game

It does not change simulation speed, production, conveyors, units, bullets, or network behavior. The monitor appears while in a game and hides outside gameplay.

## Android-compatible build

A normal Java `jar` task produces a desktop-only JAR. Android needs the Java classes dexed as well, so use the included GitHub Actions workflow:

1. Create a GitHub repository and upload the contents of this project.
2. Open the repository's **Actions** tab.
3. Wait for **Build Mindustry Optimizer** to finish successfully.
4. Open the workflow run and download the `Mindustry-Optimizer-Android` artifact.
5. Extract the downloaded artifact ZIP. It contains `Mindustry-Optimizer.jar`.
6. Import that JAR into Mindustry v8.

The workflow installs the Android SDK and runs the Android-compatible `deploy` task based on the official Mindustry Java mod template approach.

## Measurement note

FPS is the engine's reported average, not a guaranteed instantaneous measurement. Frame time is based on the graphics delta-time value, which may be smoothed. Treat the session low as a rough comparison metric, not a laboratory-grade benchmark.

## Next test

After confirming that the mod loads on Android, compare the readout in the same map at the start of a session and again after 30 minutes / around wave 100. Record FPS and frame time, and capture the game's log if the mod fails to load.
