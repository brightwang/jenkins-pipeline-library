def call(String[] files){
    generalBuildXml=new com.brightwang.GeneralBuildXml(env.xml)
    generalBuildXml.excludeFile(files)
    env.xml=generalBuildXml.getXmlString()
}

return this