node { 
    stage('SCM Checkout') { 
        git url: 'git@github.com:sparksis/cineplex-notifier.git'
    }
    stage('Build') { 
        sh 'mvn clean install -DskipTests'
    }
    stage('Test') {
        // Prep the test environment
        sh './scripts/build-test-environment.sh  < /dev/null'
    
        withEnv(['JBOSS_HOME=/tmp/cinemanotifier/wildfly-10.1.0.Final']){
            withCredentials([string(credentialsId: 'SENDGRID_API_KEY', variable: 'SENDGRID_API_KEY')]) {
                sh 'mvn -P arq-wildfly-managed clean -Dmaven.test.failure.ignore=true test'
            }
        }
        junit '**/surefire-reports/*.xml'
    }
    stage('Deploy') {
        sh 'git push -f ssh://582052912d5271024a000027@cineplexnotifierdev-sparksis.rhcloud.com/~/git/cineplexnotifierdev.git/'
    }
}
