import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab
import jetbrains.buildServer.configs.kotlin.projectFeatures.dockerRegistry
import jetbrains.buildServer.configs.kotlin.triggers.vcs

version = "2023.11"

project {

    buildType(Build)

    features {
        dockerRegistry {
            id = "PROJECT_EXT_3"
            name = "Docker Registry"
            userName = "utyfdfrthby08"
            password = "credentialsJSON:b8c3ae78-f231-4fe1-bba7-660e6e88dda2"
        }
        buildReportTab {
            id = "PROJECT_EXT_6"
            title = "Test results"
            startPage = "allure-report.zip!/allure-report/index.html"
        }
    }
}

object Build : BuildType({
    name = "Build"

    allowExternalStatus = true

    params {
        password(
            "SECURE_PASSWORD",
            "credentialsJSON:1be937be-cd3f-4e79-bf4b-0cde61ef2fe1",
            display = ParameterDisplay.HIDDEN
        )
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            name = "Npm install"
            id = "Npm_install"
            scriptContent = "npm install"
        }
        script {
            name = "Install deps"
            id = "simpleRunner"
            scriptContent = "npx playwright install-deps"
        }
        script {
            name = "Install browsers"
            id = "Install_browsers"
            scriptContent = "npx playwright install"
        }
        script {
            name = "Test"
            id = "Test"
            scriptContent = "npx playwright test"
        }
        script {
            name = "Create report"
            id = "Create_report"
            scriptContent = "allure generate allure-results --clean"
        }
        step {
            name = "Allure"
            id = "Allure"
            type = "allureReportGeneratorRunner"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
        dockerSupport {
            enabled = false
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_3"
            }
        }
    }
})
