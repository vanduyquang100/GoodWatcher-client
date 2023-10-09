package org.example.models;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FolderView {
    Path currentPath;
    Iterable<Path> subPaths;
    private int folderLevel;
    public FolderView() {
        subPaths = FileSystems.getDefault().getRootDirectories();
        currentPath = subPaths.iterator().next();
        folderLevel = -1;
    }

    public void changeDirectory(String relativePathString) {
        if (relativePathString.isEmpty()) {
            return;
        }
        if (relativePathString.equals("./")) {
            rollBack();
            return;
        }

        Path newPath = Paths.get(relativePathString);
        if (folderLevel == -1) {
            newPath = currentPath.resolve(newPath);
        }

        if (!Files.exists(newPath)) {
            throw new IllegalArgumentException("File path doesn't exist.");
        }

        if (folderLevel == -1) {
            folderLevel = newPath.getNameCount();
        }

        currentPath = newPath;
        ArrayList<Path> newSubPaths = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(currentPath, 1)) {
            stream.skip(1).filter(Files::isDirectory).forEach(newSubPaths::add);
            subPaths = newSubPaths;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rollBack() {
        if (folderLevel == -1 || folderLevel == 0) {
            resetDirectories();
        } else {
            Path parentPath = currentPath.getParent();
            if (parentPath != null) {
                changeDirectory(parentPath.toString());
            }
        }
    }

    public void resetDirectories() {
        subPaths = FileSystems.getDefault().getRootDirectories();
        currentPath = subPaths.iterator().next();
        folderLevel = -1;
    }

    public Path getPath() {
        if (folderLevel == -1) {
            throw new IllegalStateException("The root folder has no path.");
        }
        return currentPath;
    }

    public ArrayList<String> getSubFolderNames() {
        ArrayList<String> result = new ArrayList<>();
        if (folderLevel == -1 || folderLevel == 0) {
            for (Path path : subPaths) {
                result.add(path.toString());
            }
        } else {
            for (Path path : subPaths) {
                Path relativePath = currentPath.relativize(path);
                result.add(relativePath.toString());
            }
        }
        return result;
    }
}
