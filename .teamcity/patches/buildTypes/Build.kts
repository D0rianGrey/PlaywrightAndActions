package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Build'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("Build")) {
    expectSteps {
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
    steps {
        update<ScriptBuildStep>(1) {
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            clearConditions()
            scriptContent = """
                echo '87Qqr0Hc' | sudo -S npx playwright install-deps
                npx playwright install-deps
            """.trimIndent()
        }
    }
}
