def call(String[] files) {
    def method = 'excludeFile'
    echo env.generalBuildXml.toString()
    env.generalBuildXml."$method"(files)
}

return this