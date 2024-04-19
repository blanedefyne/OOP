package ru.nsu.zelenin.checker

def compile(ArrayList tasks, String path) {
    for (String task : tasks) {
//        def sout = new StringBuilder()
//        def serr = new StringBuilder()
        def proc1 = ("gradlew compile" + path + "/" + task).execute()
    }
}