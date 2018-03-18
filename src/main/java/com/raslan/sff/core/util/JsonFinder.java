package com.raslan.sff.core.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class JsonFinder extends SimpleFileVisitor<Path>{
	private List<Path> jsons;

    public JsonFinder() {
    	jsons = new ArrayList<Path>();
	}

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        Path name = file.getFileName();
        if (name != null && (name.toString().endsWith(".json"))) {
            jsons.add(file);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return FileVisitResult.CONTINUE;
    }

    public List<Path> getJsonFiles() {
        return jsons;
    }
	
}
