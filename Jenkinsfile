node { 
    stage('SCM Checkout') { 
        git url: 'git@github.com:sparksis/cineplex-notifier.git'
    }
    stage('Build') { 
        sh 'mvn clean install -DskipTests'
    }
    stage('Test') {
        sh 'mvn test'
    }
    stage('Deploy') {
        sh 'git push -f ssh://582052912d5271024a000027@cineplexnotifierdev-sparksis.rhcloud.com/~/git/cineplexnotifierdev.git/'
    }
}
