package com.dawn.zgstep.tests.dd1;

/**
 * Create by rye
 * at 2021/5/9
 *
 * @description:
 */
interface RequestManagerFactoryEx {
    RequestManagerEx build(String content);
}

class RequestManagerEx {
    String content;

    public RequestManagerEx(String content) {
        this.content = content;
    }
}

class Test {
    final RequestManagerFactoryEx DEFAULT_FACTORY = new RequestManagerFactoryEx() {
        @Override
        public RequestManagerEx build(String content) {
            return new RequestManagerEx(content);
        }
    };

    void test() {
        DEFAULT_FACTORY.build("hello");
    }
}