package com.brightwang

abstract class ScriptBase extends Script {
    def excludeDir(dirs){
        new File('/tmp/dir.txt').write('dir')
    }

    def excludeFile(files){
        new File('/tmp/file.txt').write('file')

    }
}
