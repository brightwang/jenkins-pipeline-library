def call(String[] dirs) {
    $method = "excludeDir"
    env.generalBuildXml."$method"(dirs)
}

return this