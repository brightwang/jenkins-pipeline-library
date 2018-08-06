import com.brightwang.Exclude

def call(Closure cl){
    e=new Exclude()
    def code = cl.rehydrate(e, this, this)
    code.resolveStrategy = Closure.DELEGATE_ONLY
    code()
}
return this