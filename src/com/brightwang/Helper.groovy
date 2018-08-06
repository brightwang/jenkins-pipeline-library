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

    static Runner(conf, file) {
        this.classLoader.config=conf
        def c =this.classLoader.parseClass(file as File).newInstance()
        //def c = new GroovyClassLoader(this.classLoader, conf).parseClass(file as File).newInstance()
        c.run()
    }
}