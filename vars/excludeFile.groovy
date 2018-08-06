def call(String[] files) {
    def method = 'excludeFile'
    g=env.generalBuildXml as com.brightwang.GeneralBuildXml
    g."${method}"(files)
}

return this