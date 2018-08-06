package com.brightwang

class Exclude {
    def dir(String[] dirs){
        new File('/tmp/dir').write('dir')
    }
    def file(String[] files){
        new File('/tmp/file').write('file')
    }
}
