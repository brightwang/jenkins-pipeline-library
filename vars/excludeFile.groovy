def call(String[] files) {
    def method = 'excludeFile'
    new com.brightwang.GeneralBuildXml("""<?xml version="1.0" encoding="UTF-8"?>

<project name="{project-name}" basedir="/app" default="build">
    <target name="build:clear">
    </target>
</project>
""")."${method}"(files)
}

return this