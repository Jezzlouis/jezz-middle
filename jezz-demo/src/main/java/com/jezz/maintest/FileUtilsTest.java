package opt;

import java.io.File;
import java.util.*;

public class FileUtilsTest {

    public static void test(String treePath){
        List<FileTreeEntity> fileTreeVoList = new ArrayList<>();
        File file = null;
        if ("/".equals(treePath.toLowerCase().trim())) {//查看更多目录
            file = new File(treePath);
        }
        long s = System.currentTimeMillis();
        System.out.println("展示output消耗时间1====="+ (System.currentTimeMillis()-s));
        File[] files = file.listFiles();
        if (files == null) {
            System.out.println("111");
        }
        System.out.println("展示output消耗时间2====="+ (System.currentTimeMillis()-s));
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        System.out.println("展示output消耗时间3====="+ (System.currentTimeMillis()-s));
        for (File fi : files) {
            if (fi.isDirectory()) {
                FileTreeEntity fileTreeEntity = new FileTreeEntity();
                fileTreeEntity.setFileName(fi.getName());
                fileTreeVoList.add(fileTreeEntity);
            }
        }
        System.out.println("展示output消耗时间4====="+ (System.currentTimeMillis()-s));
        System.out.println("---getOutputUserDir--consumer time--" + (System.currentTimeMillis() - s));

        List<FileTreeEntity> fileTreeVoList1 = new LinkedList<>();
        for (File fi : files) {
            if (fi.isDirectory()) {
                FileTreeEntity fileTreeEntity = new FileTreeEntity();
                fileTreeEntity.setFileName(fi.getName());
                fileTreeVoList1.add(fileTreeEntity);
            }
        }
        System.out.println("展示output消耗时间5====="+ (System.currentTimeMillis()-s));
    }

    public static void main(String[] args) {
        test("/mnt/output/d/render_data/10000000/10000004/");
    }
}
class FileTreeEntity {

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}