def call(String[] files) {
    $method = "excludeFile"
    env.generalBuildXml."$method"(files)
}

return this