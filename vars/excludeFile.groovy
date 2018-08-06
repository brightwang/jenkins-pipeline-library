def call(String[] files) {
    def method = 'excludeFile'
    env.generalBuildXml."${method}"(files)
}

return this