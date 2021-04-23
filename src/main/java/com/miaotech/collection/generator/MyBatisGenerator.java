package com.miaotech.collection.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * 源自：https://jerryjin.blog.csdn.net/article/details/90745016
 * 参考博主的代码，略有改进
 */
public class MyBatisGenerator {

    // 全局配置
    private final static String OUTPUT_DIR = "/src/main/java";// 生成文件的输出目录
    private final static String OUTPUT_XML_DIR = "/src/main/resources";// 生成文件的输出目录
    private final static String AUTHOR = "billchen";// 开发人员

    // 包配置
    private final static String PARENT = "com.miaotech.collection";// 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
    private final static String MODULE_NAME = "";// 父包模块名
    // 自定义基类
    private final static String SuperEntity = "com.miaotech.collection.common.BaseEntity";// 所有实体的基类(全类名)
    private final static String SuperController = "com.miaotech.collection.common.BaseController";// 所有控制器的基类(全类名)

    private final static String APPLICATION_PROPERTIES = "application.properties";

    public MyBatisGenerator() {

    }

    public void generate(String table) throws IOException{

        ClassPathResource resource = new ClassPathResource(APPLICATION_PROPERTIES);
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + OUTPUT_DIR);
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(properties.getProperty("spring.datasource.url"));
        dsc.setUsername(properties.getProperty("spring.datasource.username"));
        dsc.setPassword(properties.getProperty("spring.datasource.password"));
        // dsc.setSchemaName("public");
        dsc.setDriverName(properties.getProperty("spring.datasource.driver-class-name"));// JDK8
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setModuleName(MODULE_NAME);
        pc.setParent(PARENT);
        mpg.setPackageInfo(pc);

        // 自定义配置
        // 注意 dto.java.ftl 模板中顶部使用的是 ${cfg.Dto}
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("Dto", pc.getParent() + ".dto");
                this.setMap(map);
            }
        };

        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义 mapper
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + OUTPUT_XML_DIR + "/mapper/" +  tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        // 自定义 dto
        focList.add(new FileOutConfig("/templates/entity.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + OUTPUT_DIR + "/" + PARENT.replaceAll("\\.", "/") + "/" + MODULE_NAME + "/entity/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(SuperEntity);
        strategy.setEntityLombokModel(true);//【实体】是否为lombok模型
        strategy.setSuperControllerClass(SuperController);
        strategy.setRestControllerStyle(true);
        strategy.setEnableSqlFilter(false); // 当enableSqlFilter为false时，Include 允许正则表达式
        // strategy.setInclude(scanner("表名"));
        strategy.setInclude(table); // 所有表

        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        // 使用 freemarker 引擎。默认是Velocity。反正用哪个引擎就得自己添加哪个引擎的依赖
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }


    /**
     * RUN THIS
     */
    public static void main(String[] args) throws IOException {
        MyBatisGenerator generator = new MyBatisGenerator();
        generator.generate("collection");
    }
}
