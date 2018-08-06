package com.brightwang

abstract class ScriptBase extends Script {
    def excludeDir(dirs){
        new File('./dir.txt').write('dir')
    }

    def excludeFile(files){
        new File('./file.txt').write('file')

    }
}
