# -*- encoding: UTF-8 -*-
import sys
reload(sys)
sys.setdefaultencoding('utf-8')

if __name__ == '__main__':
    print(len(sys.argv))
    for i in range(0, len(sys.argv)):
        print(sys.argv[i])
