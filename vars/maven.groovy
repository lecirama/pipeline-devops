/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/
def call(){
    switch(env.Stage){
        case "Compile"
            stage('Compile') {
            sh './mvnw clean compile -e'       
            } 
        break  
        case "Unit"
            stage('Unit') {
            sh './mvnw clean test -e'     
             }
        break
        case "Jar":
            stage('Jar') {
            sh './mvnw clean package -e'
            }
        break
        case "Sonar":
            stage('Sonar') {
            withSonarQubeEnv('sonar') { // You can override the credential to be used
            sh './mvnw org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
            }
            }   
        break
        case CASE_NAME:
            stage('Run_Jar') {
            sh 'nohup ./mvnw spring-boot:run &'
            }     
        break
        case CASE_NAME:
            stage('Testing_App') {
            sleep 20
            sh 'curl -X GET http://localhost:8086/rest/mscovid/test?msg=testing'
            }   
        break
        case CASE_NAME:
            stage('Upload Nexus'){
            nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: '/Users/maricelrodriguez/.jenkins/workspace/ultib_gradle_feature-dir-inicial/build/libs/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '1.0.0']]]
             }
        break

    }
 }
return this;