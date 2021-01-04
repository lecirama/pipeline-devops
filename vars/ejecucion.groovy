/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
	  pipeline {
	    agent any
	        parameters {
	            choice(name: 'eleccion', choices: ['gradle', 'maven'], description: 'Herramientas de Construcción')
	            //string(name: 'stage', defaultValue:'', description: 'Valor Stage')
				string(name: 'Stage', defaultValue: '', description: 'Selección de stage Opciones para Gradle: build & Test; sonar; run; rest; Upload Nexus; Opciones para Maven: Compile; Unit; Jar; Sonar; Sonar; Test')

	        }
	    stages {
	        stage('Pipeline') {
	            steps {
	                script{
	                	println 'Herramienta de Ejecución seleccionada: '+ params.eleccion
	                	//println 'stage seleccionado: '+ params.Stage
	                	//"${params.eleccion.call()}"
	                	if (params.eleccion == 'gradle'){
	                		gradle.call()
	                	} else {
	                		maven.call()
	                	}
	                   //Invocacion al archivo dependiendo del paramentro generado
	                   // env.STG_NAME=''
	                    //def build=(params.eleccion == 'Gradle') ? 'gradle.groovy' : 'maven.groovy'
	                    //def ejecucion=(params.eleccion == 'Gradle') ? 'gradle.groovy' : 'maven.groovy'
	                    //def ejecucion = load build
	                    //ejecucion.call()
	                 }
	            }
	        }
	    }
	    /*post{
	        success{
	            slackSend color: 'good', message: "Build Success: [Maricel Rodriguez][${env.STG_NAME}][${params.eleccion}] Ejecución exitosa.", teamDomain: 'dipdevopsusach2020', tokenCredentialId: 'slack-token'
	        }
	        failure{
	            slackSend color: 'danger', message: "Build Failure: [Maricel Rodriguez][${env.STG_NAME}][${params.eleccion}] Ejecución fallida en stage [${env.STG_NAME}].", teamDomain: 'dipdevopsusach2020', tokenCredentialId: 'slack-token'
	         }
	    }*/
	}
 }
return this;