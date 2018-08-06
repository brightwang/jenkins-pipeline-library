def call(String[] dirs) {
    def method = "excludeDir"
    env.generalBuildXml."$method"(dirs)
}

return this