pipeline {
    agent none
    stages {
        stage('Check') {
            agent   {
                docker {
                    image 'maven:3-jdk-10'
                    args '-v /var/lib/jenkins/.m2:/nonexistent/.m2'
                }
            }
            stages {
                stage('Quick Check') {
                    steps {
                        sh 'mvn -B clean test-compile'
                    }
                }
                stage('Build and Unit Test') {
                    steps {
                        sh 'mvn -B verify -Dgroups=!acceptance-tests'  // TODO
                        // Must use stash now because future steps use agent none.
                        stash includes: 'webapp/target/webapp-1.0-SNAPSHOT-exec.jar', name: 'fatJar'
                    }
                    post {
                        always {
                            junit '**/target/surefire-reports/*.xml'
                            junit '**/target/failsafe-reports/*.xml'
                        }
                    }
                }
            }
        }
        stage('Parallel Steps') {
            parallel {
                stage('Unit-Test Coverage') {
                    agent {
                        docker {
                            image 'maven:3-jdk-10'
                            args '-v /var/lib/jenkins/.m2:/nonexistent/.m2'
                        }
                    }
                    steps {
                        sh 'mvn -B test -Djacoco.skip=false'
                    }
                    post {
                        always {
                            jacoco changeBuildStatus: true, execPattern: '*/target/coverage-reports/jacoco-ut.exec', maximumBranchCoverage: '75', maximumMethodCoverage: '85'
                        }
                    }
                }
                stage('Docs') {
                    agent{ 
                        docker {
                            image 'maven:3-jdk-10'
                            args '-v /var/lib/jenkins/.m2:/nonexistent/.m2'
                        }
                    }
                    steps {
                        sh 'mvn -B javadoc:aggregate'
                        publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: false, reportDir: 'target/site/apidocs/', reportFiles: 'overview-summary.html', reportName: 'Javadoc', reportTitles: ''])
                    }
                }

                stage('Build and Deploy') {
                   // when {
                   //     branch 'master'
                   //  }
                    steps{
                        script{
                            node{
                                stage("Build and Smoke Test Docker Container"){
                                    // Build fails when unstash is not inside a node.

                                    /*
                                    [Pipeline] unstash
                                    Fetching changes from the remote Git repository
                                        > git config remote.origin.url git@src.thetestpeople.com:development-academy/java-parsons # timeout=10
                                    Fetching without tags
                                    Fetching upstream changes from git@src.thetestpeople.com:development-academy/java-parsons
                                        > git --version # timeout=10
                                    Required context class hudson.FilePath is missing
                                    using GIT_SSH to set credentials java-parsons@ci.ten10.com
                                        > git fetch --no-tags --progress git@src.thetestpeople.com:development-academy/java-parsons +refs/heads/*:refs/remotes/origin/*

                                    Perhaps you forgot to surround the code with a step that provides this, such as: node,dockerNode
                                    [Pipeline] error
                                    */
                                    
                                    unstash 'fatJar'
                                    // Build an image
                                    def customImage = docker.build("java-parsons:${env.BUILD_ID}")
                                    // Validate that running the image starts a TCP listener on 8080
                                    customImage.withRun { c ->
                                        docker.image('alpine').inside("--link ${c.id}:app") {
                                            sh 'while ! nc -vz app 8080; do sleep 2; done'
                                        }
                                    }
                                    sshagent (credentials: ['dockeruser']) {
                                        stage('Deploy Image') {
                                            sh "docker save ${customImage.id} | ssh -o StrictHostKeyChecking=no dockeruser@172.18.200.63 docker load"
                                        }
                                        stage('Stopping any existing container') {
                                            sh 'ssh dockeruser@172.18.200.63 docker stop java-parsons && ssh dockeruser@172.18.200.63 docker rm java-parsons || true'
                                        }
                                        stage('Restart Container') {
                                           sh "ssh dockeruser@172.18.200.63 docker run -d -p 8080:8080 --name java-parsons java-parsons:${env.BUILD_ID}"
                                        }
                                    }
                                }
                                stage('Acceptance tests') {
                                    echo "Running Acceptance Tests"
                                }
                            }
                        }
                    }
                }  
            }
        }
    }
}
