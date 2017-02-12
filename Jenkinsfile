node { 
    stage('SCM Checkout') { 
        git branch: 'production', url: 'git@github.com:sparksis/cineplex-notifier.git'
    }
    stage('Build') { 
        sh 'mvn clean install -DskipTests'
    }
    stage('Test') {
        sh 'mvn test'
    }
    stage('Deploy') {
        sh 'git push -f ssh://57fc1c3f7628e14b7e00002d@cineplexnotifier-sparksis.rhcloud.com/~/git/cineplexnotifier.git/'
    }
}
