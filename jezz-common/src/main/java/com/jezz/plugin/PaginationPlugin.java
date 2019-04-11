package com.jezz.plugin;

import com.jezz.utils.CollectionUtils;
import com.jezz.utils.StringUtils;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;

import java.util.List;

public class PaginationPlugin extends PluginAdapter {

    List<IntrospectedColumn> newColumns = null;

    @Override
    public boolean modelBaseRecordClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("java.io.Serializable");
        topLevelClass.addSuperInterface(new FullyQualifiedJavaType("java.io.Serializable"));

        Field field = new Field();
        // private static final long serialVersionUID = 1L;
        field.setName("serialVersionUID");
        field.setType(new FullyQualifiedJavaType("long"));
        field.setStatic(true);
        field.setFinal(true);
        field.setInitializationString("1L");
        topLevelClass.addField(field);
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass,
                                       IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable,
                                       ModelClassType modelClassType) {
        String remarks = introspectedColumn.getRemarks();
        if (!StringUtils.isBlankOrNull(remarks)) {
            field.addJavaDocLine("//" + remarks);
        }
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn,
                introspectedTable, modelClassType);
    }

    @Override
    public void setContext(Context context) {
        CommentGeneratorConfiguration cgc = new CommentGeneratorConfiguration();
        cgc.addProperty("suppressAllComments", "true");
        context.setCommentGeneratorConfiguration(cgc);
//        context.set(newColumns);
        // GeneratedXmlFile
        super.setContext(context);
    }

    @Override
    public boolean modelExampleClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // add field, getter, setter for ratelimit clause
        addLimitParam(topLevelClass, introspectedTable, "limitStart");
        addLimitParam(topLevelClass, introspectedTable, "limitEnd");
        addLimit(topLevelClass, introspectedTable, "ratelimit");
        addPage(topLevelClass, introspectedTable, "page");
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean sqlMapInsertElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        System.out.println(introspectedTable.getFullyQualifiedTable().getIntrospectedTableName());
        element.addAttribute(new Attribute("keyProperty",
                introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty()));// /设置主键返回策略
        element.addAttribute(new Attribute("useGeneratedKeys", "true"));
        return super.sqlMapInsertElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
//        XmlElement element = new XmlElement("cache");
//        element.addAttribute(new Attribute("eviction", "LRU"));
//        sqlMap.getDocument().getRootElement().addElement(element);
        return super.sqlMapGenerated(sqlMap, introspectedTable);
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {// 设置主键返回策略
        element.addAttribute(new Attribute("keyProperty",
                introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty()));
        element.addAttribute(new Attribute("useGeneratedKeys", "true"));
        return super.sqlMapInsertSelectiveElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        addLimitXml(element);
        XmlElement page = new XmlElement("if");
        page.addAttribute(new Attribute("test", "page != null"));
        page.addElement(new TextElement("ratelimit #{page.begin} , #{page.length}"));
        element.addElement(page);
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        addLimitXml(element);
        XmlElement page = new XmlElement("if");
        page.addAttribute(new Attribute("test", "page != null"));
        page.addElement(new TextElement("ratelimit #{page.begin} , #{page.length}"));
        element.addElement(page);
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    private void addLimitXml(XmlElement element) {
        /*
         * XmlElement isParameterPresenteElemen = (XmlElement) element
         * .getElements().get(element.getElements().size() - 1);
         */
        XmlElement if1Element = new XmlElement("if"); //$NON-NLS-1$
        if1Element.addAttribute(new Attribute("test", "ratelimit == null"));

        XmlElement sonEleIf = new XmlElement("if"); //$NON-NLS-1$
        sonEleIf.addAttribute(new Attribute("test", "limitStart gt 0 and limitEnd gt 0 "));
        //isNotNullElement.addAttribute(new Attribute("compareValue", "0"));
        sonEleIf.addElement(new TextElement("ratelimit ${limitStart} , ${limitEnd}"));
        if1Element.addElement(sonEleIf);

        XmlElement sonEleElse = new XmlElement("if"); //$NON-NLS-1$
        sonEleElse.addAttribute(new Attribute("test", "limitStart lt 1 and limitEnd lt 0 "));
        //isNotNullElement.addAttribute(new Attribute("compareValue", "0"));
        sonEleElse.addElement(new TextElement("ratelimit ${limitEnd}"));
        if1Element.addElement(sonEleElse);
        element.addElement(if1Element);

        XmlElement elseSon = new XmlElement("if");
        elseSon.addAttribute(new Attribute("test", " ratelimit != null "));
        //isNotNullElement.addAttribute(new Attribute("compareValue", "0"));
        elseSon.addElement(new TextElement("ratelimit ${ratelimit.start} , ${ratelimit.size}"));
        element.addElement(elseSon);
    }

    private void addLimitParam(TopLevelClass topLevelClass,
                               IntrospectedTable introspectedTable, String name) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(FullyQualifiedJavaType.getIntInstance());
        field.setName(name);
        field.setInitializationString("0");
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), name));
        method.addBodyLine("this." + name + "=" + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    private void addLimit(TopLevelClass topLevelClass,
                          IntrospectedTable introspectedTable, String name) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        FullyQualifiedJavaType limitType =
                new FullyQualifiedJavaType("com.jezz.plugin.Limit");
        topLevelClass.addImportedType(limitType);
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(limitType);
        field.setName(name);
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(limitType, name));
        method.addBodyLine("this." + name + "=" + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(limitType);
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    public static void generate() {
        String config = PaginationPlugin.class.getClassLoader()
                .getResource("generatorConfig.xml").getFile();
        String[] arg = {"-configfile", config, "-overwrite"};
        ShellRunner.main(arg);
    }

    public static void main(String[] args) {
        generate();
    }

    /**
     * 去掉V前缀
     */
    private String getJavaName(String cname, String name) {
        if (cname.matches("._.*")) {
            char firstChar = Character.toLowerCase(name.charAt(1));
            name = name.substring(2, name.length());
            name = firstChar + name;
        }
        return name;
    }

    @Override
    public boolean validate(List<String> arg0) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        if (CollectionUtils.isNotEmpty(newColumns)) {
            columns = newColumns;
        } else {
            newColumns = columns;
        }
        introspectedTable.addPrimaryKeyColumn(columns.get(0).getActualColumnName());
        for (IntrospectedColumn introspectedColumn : columns) {
            String javaProperty = this.getJavaName(introspectedColumn.getActualColumnName(),
                    introspectedColumn.getJavaProperty());
            introspectedColumn.setJavaProperty(javaProperty);
        }
        super.initialized(introspectedTable);
    }

    private void addPage(TopLevelClass topLevelClass, IntrospectedTable introspectedTable,
                         String name) {
        String fullQualifiedJavaType = "com.jezz.plugin.Page";
        topLevelClass.addImportedType(new FullyQualifiedJavaType(
                fullQualifiedJavaType));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(new FullyQualifiedJavaType(fullQualifiedJavaType));
        field.setName(name);
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(new FullyQualifiedJavaType(fullQualifiedJavaType), name));
        method.addBodyLine("this." + name + "=" + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(fullQualifiedJavaType));
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }
}