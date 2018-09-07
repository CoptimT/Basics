# -*- encoding: UTF-8 -*-
from cn.zxw.java.jython import SayHello

execpy = SayHello()

#将python属性传入后续调用的java实例
execpy.setUserName(userName)

def say():
    execpy.say(5)
    return

say()
