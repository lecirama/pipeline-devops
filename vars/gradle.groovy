import pipeline.*
def call()
{
    figlet 'Gradle'
    rama = GIT_BRANCH

    def CI = ['buildAndTest','sonar','runJar', 'rest','nexusCI']
    def CD = ['downloadNexus','runDoenloadedJar','rest','nexusCD']

    
    if (rama == 'feature')
    {
        figlet 'Continuos Integration'
        buildAndTest()
        sonar()
        runJar()
        rest()
        nexusCI()
    }
    else
    {
        figlet 'Continuos Deploy'
        downloadNexus()
        runDownloadedJar()
        rest()
        nexusCD()

    }
}

stage('Compile')
def buildAndTest()

{
    sh './gradlew clean build'
}
def sonar()
stage('Sonar') 
{
    def scannerHome = tool 'sonar';
    withSonarQubeEnv('sonar')
    {
        sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build"
    }
}
def runJar()
stage('Run')
{
    sh "nohup bash gradlew bootRun &"
    sleep(10)
}
def rest()
{    
    sleep (15)
    sh 'curl -X GET http://localhost:8086/rest/mscovid/test?msg=testing'
}
//def downloadNexus()
//{
//    sh "curl -X GET -u admin:maricel GET http://localhost:8081/repository/test-nexus/com/devopsusach2020/DevOpsUsach2020/1.0.0/DevOpsUsach2020-1.0.0.jar -O'
//}
//def runDownloadedJar()
//{
//    sh "java -jar DevOpsUsach2020-0.0.1.jar &"
//    sleep (10)
//}
def nexusCI()
{
    nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '1.0.0']]]
}  
def nexusCD()
{
    nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '2.0.0']]]
}  
return this;