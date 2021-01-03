/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/
def call(){
    stage('build & Test'){
        env.STG_NAME = 'build & test'
        sh './gradlew clean build'
    }
    stage('sonar'){
        env.STG_NAME = 'sonar'
        def scannerHome = tool 'sonar';
        withSonarQubeEnv('sonar'){
        sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build"
    	}
    }
    stage('run'){
        env.STG_NAME = 'run'
        sh "nohup bash gradlew bootRun &"
        sleep(10)
    }
    stage('rest'){
        env.STG_NAME = 'rest'
        sh "curl http://localhost:8086/rest/mscovid/test?msg=testing"
        sleep(5)
    }
    stage('Upload Nexus'){
        env.STG_NAME = 'UploadNexus'
        nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: '/Users/maricelrodriguez/.jenkins/workspace/ultib_gradle_feature-dir-inicial/build/libs/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '1.0.0']]]
    }
 }
return this;