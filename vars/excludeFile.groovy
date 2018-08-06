def call(String[] files) {
    def method = 'excludeFile'
    echo env.generalBuildXml.class.toString()
    env.generalBuildXml."${method}"(files)
}

return this