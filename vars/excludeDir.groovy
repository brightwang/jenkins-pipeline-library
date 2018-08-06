def call(String[] dirs) {
    generalBuildXml=new com.brightwang.GeneralBuildXml(env.xml)
    generalBuildXml.excludeFile(dirs)
    env.xml=generalBuildXml.getXmlString()
}

return this