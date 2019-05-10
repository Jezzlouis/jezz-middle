package com.jezz.file.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<String> getAllFile(String path){
        List<String> allFile = new ArrayList<>();
        File f = new File(path) ;
        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isDirectory()){
                String directoryPath = file.getPath();
                getAllFile(directoryPath);
            }else {
                String filePath = file.getPath();
                if (!filePath.endsWith(".txt")){
                    continue;
                }
                allFile.add(filePath) ;
            }
        }
        return allFile ;
    }
//    Stream<String> stringStream = Files.lines(Paths.get(path), StandardCharsets.UTF_8);
//    List<String> collect = stringStream.collect(Collectors.toList());

}
