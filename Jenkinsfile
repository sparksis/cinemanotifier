node { 
    stage('SCM Checkout') { 
        git poll: false, url: 'git@github.com:sparksis/cineplex-notifier.git'
    }
    stage('Build') { 
        sh 'mvn clean install -DskipTests'
    }
    stage('Test') {
        sh 'mvn test'
    }
    stage('Deploy') {
        sh 'git remote add dev "ssh://582052912d5271024a000027@cineplexnotifierdev-sparksis.rhcloud.com/~/git/cineplexnotifierdev.git/"'
        sh 'git push dev'
    }
}
