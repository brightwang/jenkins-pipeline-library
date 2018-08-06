def call(String[] files){
    if (!env.excludeFile) {
        env.excludeFile = []
    }
    env.excludeFile + files
}

return this