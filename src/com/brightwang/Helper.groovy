package com.brightwang

import groovy.json.JsonSlurperClassic

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

    static Runner(file){
        use(GeneralBuildXml) {
            this.classLoader.parseClass(file as File).newInstance().run()
        }
    }
}