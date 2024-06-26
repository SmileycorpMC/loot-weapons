package net.smileycorp.lootweapons.common;

import com.google.common.collect.Lists;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.LiteralContents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class LootWeaponsLogger {
    private static Logger logger = LogManager.getLogger(Constants.MODID);

    private static Path log_file = Paths.get("logs/elites.log");

    public static void clearLog() {
        try {
            Files.write(log_file, Lists.newArrayList(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("Failed to write to log file", e);
            e.printStackTrace();
        }
    }

    public static void logSilently(Object message) {
        writeToFile(message);
    }

    public static void logInfo(Object message) {
        writeToFile(message);
        logger.info(message);
    }

    public static void logError(Object message, Exception e) {
        writeToFile(message + " " + e);
        for (StackTraceElement traceElement : e.getStackTrace()) writeToFile(traceElement);
        logger.error(message, e);
        e.printStackTrace();
    }

    private static boolean writeToFile(Object message) {
        return writeToFile(Lists.newArrayList(String.valueOf(message)));
    }

    private static boolean writeToFile(List<String> out) {
        try {
            Files.write(log_file, out, StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        } catch (Exception e) {
            logger.error("Failed to write to log file", e);
            e.printStackTrace();
            return false;
        }
    }

    public static MutableComponent getFiletext() {
        String file = log_file.toAbsolutePath().toString();
        MutableComponent text = MutableComponent.create(new LiteralContents(file));
        text.setStyle(Style.EMPTY.withUnderlined(true).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, MutableComponent.create(new LiteralContents(file)))));
        return text;
    }
    
}
