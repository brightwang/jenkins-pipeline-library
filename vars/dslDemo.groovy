import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import org.codehaus.groovy.runtime.MethodClosure

import static org.codehaus.groovy.syntax.Types.EQUAL
import static org.codeha.us.groovy.syntax.Types.*
import com.brightwang.GeneralBuildXml

import static org.codehaus.groovy.syntax.Types.PLUS_PLUS

def call() {
    xml = """<?xml version="1.0" encoding="UTF-8"?>

<project name="{project-name}" basedir="/app" default="build">
    <target name="build:clear">
    </target>
</project>
"""
    dsl = new File('./deploy.dsl').text
    def binding = new Binding()
    def g = new GeneralBuildXml(xml)
    def writer = new StringWriter()
    binding.setProperty('excludeDir', new MethodClosure(g, 'excludeDir'))
    binding.setProperty('excludeFile', new MethodClosure(g, 'excludeFile'))
    binding.setProperty('transfer', new MethodClosure(g, 'transfer'))
    binding.setVariable('g', g)
    binding.setProperty("out", new PrintWriter(writer))
    CompilerConfiguration conf = new CompilerConfiguration();
    SecureASTCustomizer customizer = new SecureASTCustomizer();
    customizer.with {
        closuresAllowed = true // 用户能写闭包
        methodDefinitionAllowed = true // 用户能定义方法
        importsWhitelist = [] // 白名单为空意味着不允许导入
        //staticImportsWhitelist = [] // 同样，对于静态导入也是这样
        //staticStarImportsWhitelist = ['java.lang.Math', 'java.lang.String', 'java.lang.Object', 'GeneralBuildXml']
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
        ].asImmutable()
    }

    customizer.setReceiversWhiteList(Arrays.asList(
            "java.lang.Object"
    ));
    conf.addCompilationCustomizers(customizer);
    println(new GroovyShell(binding))
    def d = new GroovyShell(binding, conf).parse(dsl)
    new GroovyShell(binding, conf).evaluate(dsl)
    println(g.getXmlString())
}

return this