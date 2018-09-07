# -*- encoding: UTF-8 -*-
import sys

def sayHi():
    print("hello,world")
    
def getName():
    return "python"

def sayMsg(msg):
    print(msg)
    return msg

if __name__ == '__main__':
    print(len(sys.argv))
    for i in range(0, len(sys.argv)):
        print(sys.argv[i])
