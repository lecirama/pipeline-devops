/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
	  pipeline {
	    agent any
	        parameters {
	            choice(name: 'eleccion', choices: ['Gradle', 'Maven'], description: 'Herramientas de Construcción')
	        }
	    stages {
	        stage('Pineline') {
	            steps {
	                script{
	                   //Invocacion al archivo dependiendo del paramentro generado
	                    env.STG_NAME=''
	                    def build=(params.eleccion == 'Gradle') ? 'gradle.groovy' : 'maven.groovy'
	                    def ejecucion = load build
	                    ejecucion.call()
	                 }
	            }
	        }
	    }
	    post{
	        success{
	            slackSend color: 'good', message: "Build Success: [Maricel Rodriguez][${env.STG_NAME}][${params.eleccion}] Ejecución exitosa.", teamDomain: 'dipdevopsusach2020', tokenCredentialId: 'slack-token'
	        }
	        failure{
	            slackSend color: 'danger', message: "Build Failure: [Maricel Rodriguez][${env.STG_NAME}][${params.eleccion}] Ejecución fallida en stage [${env.STG_NAME}].", teamDomain: 'dipdevopsusach2020', tokenCredentialId: 'slack-token'
	         }
	    }
	}
 }

return this;