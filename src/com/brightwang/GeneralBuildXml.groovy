package com.brightwang

import groovy.xml.MarkupBuilder
import groovy.xml.XmlUtil

class GeneralBuildXml {
    groovy.util.Node xmlNode

    GeneralBuildXml(String xml) {
        this.xmlNode = new XmlParser().parseText(xml)
    }

//    def excludeDir(dirs) {
//        println("dir")
//        dirs.each {
//            def writer = new StringWriter()
//            def builder = new MarkupBuilder(writer);
//            builder.delete("dir": '${build.dir}/' + it)
//            def target = this.xmlNode.findAll { it.attribute("name") == "build:clear" }[0]
//            def fragment = writer.toString()
//            def fragmentNode = new XmlParser().parseText(fragment)
//            target.children().add(fragmentNode)
//        }
//    }
//
//    def excludeFile(files) {
//        println('file')
//        files.each {
//            def writer = new StringWriter()
//            def builder = new MarkupBuilder(writer);
//            builder.delete("file": '${build.dir}/' + it)
//            def target = this.xmlNode.findAll { it.attribute("name") == "build:clear" }[0]
//            def fragment = writer.toString()
//            def fragmentNode = new XmlParser().parseText(fragment)
//            target.children().add(fragmentNode)
//        }
//    }

    static void excludeDir(dirs){

    }

    static void excludeFile(files){
    }

    void transfer(closure){
        println(222)
        closure.delegate = this
        closure()
    }

    def getXmlString() {
        return XmlUtil.serialize(this.xmlNode)
    }
}