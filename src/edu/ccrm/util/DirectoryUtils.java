package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class DirectoryUtils {

    // Recursive method to calculate directory size
    public static long calculateDirectorySize(Path path) throws IOException {
        try (Stream<Path> walk = Files.walk(path)) {
            return walk
                .filter(Files::isRegularFile)
                .mapToLong(p -> {
                    try {
                        return Files.size(p);
                    } catch (IOException e) {
                        System.err.println("Failed to get size for " + p + " " + e.getMessage());
                        return 0L;
                    }
                })
                .sum();
        }
    }
}
