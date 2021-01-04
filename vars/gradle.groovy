/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/
import ejecucion
def call(){
    switch (params.Stage){
        case "Build":
            stage('Build'){
            sh './gradlew clean build'
            }
        break
        case "Sonar":
            stage('Sonar'){
            //env.STG_NAME = 'sonar'
            def scannerHome = tool 'sonar';
            withSonarQubeEnv('sonar'){
            sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build"
             }
            }
        break
        case "Run":
            stage('Run'){
        // env.STG_NAME = 'run'
            sh "nohup bash gradlew bootRun &"
            sleep(10)
            }
        break
        case "Rest":
            stage('Rest'){
            //env.STG_NAME = 'rest'
            sleep (15)
            sh 'curl -X GET http://localhost:8086/rest/mscovid/test?msg=testing'
            }
        break
        case "Nexus":
            stage('Upload Nexus'){
            env.STG_NAME = 'UploadNexus'
            nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: '/Users/maricelrodriguez/.jenkins/workspace/ultib_gradle_feature-dir-inicial/build/libs/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '1.0.0']]]
            }
        break
    }  
 }
return this;