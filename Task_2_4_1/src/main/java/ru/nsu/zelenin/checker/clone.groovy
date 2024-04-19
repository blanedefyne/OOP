package ru.nsu.zelenin.checker

def conf = new GroovyClassLoader().parseClass(new File("src/main/java/" +
        "ru/nsu/zelenin/checker/configuration.groovy"))

def clone(var groups) {
    var groupNames = groups.keySet()
    for (String groupName : groupNames) {
        println("Cloning " + groupName + "...\n")
        LinkedHashMap students = groups.get(groupName)
        def names = students.keySet()
        for (String name : names) {
//            def sout = new StringBuilder()
//            def serr = new StringBuilder()
            def path = " repos/" + groupName + "/\"" + name + "\""
            def proc = ("git clone " + students.get(name)
                    + path).execute()
//            proc.consumeProcessOutput(sout, serr)
//            proc.waitForOrKill(5000)
//            println "out> $sout\nerr> $serr"
            println(name + "'s repository cloned!")
        }
        println()
    }
}

clone(conf.groups)