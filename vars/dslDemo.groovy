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
            "java.lang.Object", 'java.io.File','com.brightwang.GeneralBuildXml'
    ));
    conf.addCompilationCustomizers(customizer);
//    println(new GroovyShell(binding))
//    def d = new GroovyShell(this.class.classLoader,binding).evaluate("""\
//import com.brightwang.GeneralBuildXml
//config=["excludeDir":[],"excludeFile":[]]
//
//xml=new File('${env.WORKSPACE}/build.xml').text
//def excludeDir(String[] a){
//new File("${env.WORKSPACE}/testDir").write(xml)
//config["excludeDir"]=a
//}
//def excludeFile(String[] a){
//new File("${env.WORKSPACE}/testFile").write(xml)
//config["excludeFile"]=a
//}
//${dsl}
//""")
    use(com.brightwang.GeneralBuildXml) {
        new GroovyClassLoader().parseClass(dsl).newInstance().run()
    }
    println(g.getXmlString())
    //println(binding.getVariable('config')["excludeDir"].each {echo it})
}

return this