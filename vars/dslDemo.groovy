import com.brightwang.Exclude
import com.brightwang.Helper
import com.brightwang.ScriptBase
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import org.codehaus.groovy.runtime.MethodClosure

import static org.codehaus.groovy.syntax.Types.EQUAL
import com.brightwang.GeneralBuildXml

import static org.codehaus.groovy.syntax.Types.PLUS_PLUS

def call() {
    xml = """<?xml version="1.0" encoding="UTF-8"?>

<project name="{project-name}" basedir="/app" default="build">
    <target name="build:clear">
    </target>
</project>
"""
    env.WORKSPACE = pwd()
    file = new File("${env.WORKSPACE}/deploy.dsl")
    dsl = file.text
    def binding = new Binding()
    def g = new GeneralBuildXml(xml)
    def writer = new StringWriter()
    //binding.setProperty('excludeDir', new MethodClosure(g, 'excludeDir'))
    //binding.setProperty('excludeFile', new MethodClosure(g, 'excludeFile'))
    binding.setProperty("out", new PrintWriter(writer))
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
            "java.lang.Object",'com.brightwang.GeneralBuildXml'
    ));
    conf.addCompilationCustomizers(customizer);
    //new GroovyShell(binding,conf).parse(dsl)
    new GroovyShell(new GroovyClassLoader(this.class.classLoader),binding,conf).evaluate("""\
def excludeDir(String[] dir){

}
def excludeFile(String[] file){

}
    def phpUnit(String command){}
${dsl}
""")

    this.class.classLoader.parseClass(dsl).newInstance().run()
    echo g.getXmlString()
    return g.getXmlString()
}

return this