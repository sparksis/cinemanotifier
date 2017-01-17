node { 
    stage('Build') { 
        sh 'mvn clean install -DskipTests'
    }
    stage('Test') {
        sh 'mvn test'
    }
    stage('Deploy') {
        sh 'git push dev'
    }
}
