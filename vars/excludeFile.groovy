def call(String[] files) {
    def method = 'excludeFile'
    new com.brightwang.GeneralBuildXml('')."${method}"(files)
}

return this