/**
 *
 */
package com.jezz.plugin;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MybatisBuilder {
    public static void main(String[] args) {
        try {
            System.setProperty("generated.source.dir", "");
            List<String> warnings = new ArrayList<>();
            boolean overwrite = false;
            String relativelyPath = System.getProperty("user.dir");
            File configFile = new File(relativelyPath + "/mybatis-build/generatorConfig.xml");
            System.out.println(relativelyPath + "/mybatis-build/generatorConfig.xml");
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            for (String warning : warnings) {
                System.out.println(warning);
            }
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
