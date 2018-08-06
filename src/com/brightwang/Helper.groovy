package com.brightwang

import groovy.json.JsonSlurperClassic
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer

import static org.codehaus.groovy.syntax.Types.EQUAL
import static org.codehaus.groovy.syntax.Types.PLUS_PLUS
import org.codehaus.groovy.runtime.MethodClosure


class Helper {
    static def parseJsonToMap(String json) {
        return new HashMap<>(Helper.parseJson(json))
    }

    static def parseJson(String json) {
        final slurper = new JsonSlurperClassic()
        return slurper.parseText(json)
    }

    static def renderSign(secret, data) {
        def url = buildQueryString(data) + secret
        return java.security.MessageDigest.getInstance("MD5").digest(url.bytes).encodeHex().toString();
    }

    static def buildQueryString(data) {
        return data.collect { k, v -> "$k=" + java.net.URLEncoder.encode(v, "UTF-8") }.join('&');
    }

    static def toJson(data) {
        def json = groovy.json.JsonOutput.toJson(data)
        return json
    }

    static Runner(file) {
        CompilerConfiguration conf = new CompilerConfiguration();
        SecureASTCustomizer customizer = new SecureASTCustomizer();
        customizer.with {
            closuresAllowed = true // 用户能写闭包
            methodDefinitionAllowed = true // 用户能定义方法
            importsWhitelist = [] // 白名单为空意味着不允许导入
            staticImportsWhitelist = ['com.brightwang.GeneralBuildXml'] // 同样，对于静态导入也是这样
            staticStarImportsWhitelist = ['java.lang.Math', 'java.lang.String', 'java.lang.Object', 'com.brightwang.GeneralBuildXml']
            // 用户能找到的令牌列表
            //org.codehaus.groovy.syntax.Types 中所定义的常量
            tokensWhitelist = [
                    PLUS_PLUS,
                    EQUAL
            ].asImmutable()
            //将用户所能定义的常量类型限制为数值类型
            constantTypesClassesWhiteList = [
                    String.class,
                    Object.class,
                    GeneralBuildXml.class
            ].asImmutable()
        }

        customizer.setReceiversWhiteList(Arrays.asList(
                "java.lang.Object", 'java.io.File', 'com.brightwang.GeneralBuildXml'
        ));
        conf.addCompilationCustomizers(customizer);
        def c = this.classLoader.parseClass(file as File).newInstance()
        c.run()
    }
}