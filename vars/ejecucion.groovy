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
				//string(name: 'Stage', defaultValue: '', description: 'Selección de stage Opciones para Gradle: Build; Sonar; Run; Rest; Nexus; Opciones para Maven: Compile; Unit; Jar; Sonar; Test')
			}
	    stages {
	        stage('Pipeline') {
	            steps {
	                script{
	                	sh 'env'
	                	//figlet params.eleccion
	                	println 'Herramienta de Ejecución seleccionada: '+ params.eleccion
	                	//println 'stage seleccionado: '+ params.Stage
	                	if (params.eleccion == 'gradle'){
	                		gradle.call()
	                	} else {
	                		maven.call()
	                	}
	                   
	                 }
	            }
	        }
	    }
	}
 }
return this;