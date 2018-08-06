def call(String[] dirs){
    generalBuildXml=new com.brightwang.GeneralBuildXml(env.xml)
    generalBuildXml.excludeDir(dirs)
    env.xml=generalBuildXml.getXmlString()
}

return this