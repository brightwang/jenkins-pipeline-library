def call(String[] dirs) {
    if (!env.excludeDir) {
        env.excludeDir = []
    }
    env.excludeDir + dirs
}

return this